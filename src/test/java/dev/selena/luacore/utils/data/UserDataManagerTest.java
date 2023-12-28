package dev.selena.luacore.utils.data;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.exceptions.data.NoUserDataFolderException;
import dev.selena.luacore.exceptions.data.NoUserJsonFoundException;
import dev.selena.luacore.exceptions.data.NotUserFolderException;
import dev.selena.test.utils.MockUtils;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDataManagerTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();
    private final UserDataManager manager = LuaCore.getUserDataManager();

    @BeforeAll
    public static void setUp() throws IOException, URISyntaxException {
        temporaryFolder.create();
        MockUtils.setUp().mockDataFolder(temporaryFolder.getRoot()).mockLogger().withDataFolder();

    }

    @Test
    void getUserFolderPath() {
        String generated = manager.getUserFolderPath(UUID.fromString("5f87bb6c-38f4-4d94-93ab-73cd18986499"));
        String base = temporaryFolder.getRoot().getPath() + File.separator + "data" + File.separator + "5f87bb6c-38f4-4d94-93ab-73cd18986499\\";
        System.out.println(base + " = " + generated);
        assertEquals(base , generated);
    }

    @Test
    void getUserDataFolder_Initializes_Folder_Class_Success() throws NoUserDataFolderException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoUserJsonFoundException {
        TestDataFolder_Success folder = manager.getUserDataFolder(TestDataFolder_Success.class, UUID.randomUUID());
        assertNotNull(folder);
    }

    @Test
    void getUserDataFolder_Loads_File_Class_Success() throws NoUserDataFolderException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoUserJsonFoundException {
        TestDataFolder_Success folder = manager.getUserDataFolder(TestDataFolder_Success.class, UUID.randomUUID());
        assertTrue(folder.file.test);
    }

    @Test
    void getUserDataFolder_Initializes_Folder_Class_Fail_Null()  {
        TestDataFolder_Fail folder;
        try {
            folder = manager.getUserDataFolder(TestDataFolder_Fail.class, UUID.randomUUID());
            assertNull(folder);
        } catch (Exception ignored) {
        }
    }

    @Test
    void getUserDataFolder_Loads_File_Class_Fail_Null() {
        TestDataFolder_Fail folder;
        try {
            folder = manager.getUserDataFolder(TestDataFolder_Fail.class, UUID.randomUUID());
            assertNull(folder.file);
        } catch (Exception ignored) {
        }
    }

    @Test
    void getUserDataFolder_Initializes_Folder_Class_Throws_NoUserJsonFoundException() {
        assertThrows(NoUserJsonFoundException.class, () -> manager.getUserDataFolder(TestDataFolder_Fail.class, UUID.randomUUID()));
    }

    @Test
    void getUserDataFolder_Initializes_Folder_Class_Throws_NotUserFolderException() {
        assertThrows(NotUserFolderException.class, () -> manager.getUserDataFolder(TestDataFolder_Fail_Wrong_Class_Type.class, UUID.randomUUID()));
    }


    public static class TestDataFolder_Success extends UserFolder {

        public TestDataFile file = loadData(TestDataFile.class, "test.json");

        public TestDataFolder_Success(UUID uuid) {
            super(uuid);
        }

        @Override
        public void init() throws NoUserJsonFoundException {
            super.init();
        }
    }

    public static class TestDataFolder_Fail extends UserFolder {

        public TestDataFile file;

        public TestDataFolder_Fail(UUID uuid) {
            super(uuid);
        }

        @Override
        public void init() throws NoUserJsonFoundException {
            super.init();
        }
    }

    public static class TestDataFile {
        public final boolean test = true;
    }

    public static class TestDataFolder_Fail_Wrong_Class_Type {


    }
}