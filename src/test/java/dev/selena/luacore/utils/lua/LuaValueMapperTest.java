package dev.selena.luacore.utils.lua;

import dev.selena.luacore.utils.config.FileManager;
import dev.selena.test.utils.MockUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class LuaValueMapperTest {


    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();
    private static MappingTest mapped;
    private static MappingTest unmapped = new MappingTest();

    @BeforeAll
    public static void setup() throws IOException, URISyntaxException {
        temporaryFolder.create();
        MockUtils.setUp().mockDataFolder(temporaryFolder.getRoot());
        LuaManager.loadResourceFolder("test");
        mapped = LuaValueMapper.mapToClass(MappingTest.class, FileManager.folderPath("test") + "Test.lua");
    }

    @Test
    void StringMap() {
        assertNotEquals(mapped.Test_String, unmapped.Test_String);
    }

    @Test
    void BooleanMap() {
        assertNotEquals(mapped.Test_Boolean, unmapped.Test_Boolean);
    }
    @Test
    void IntMap() {
        assertNotEquals(mapped.Test_Int, unmapped.Test_Int);
    }


    private static class MappingTest {
        public String Test_String = "123";
        public boolean Test_Boolean = false;
        public int Test_Int = 1;
    }
}