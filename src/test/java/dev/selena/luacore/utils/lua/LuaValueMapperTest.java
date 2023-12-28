package dev.selena.luacore.utils.lua;

import com.google.gson.annotations.Expose;
import dev.selena.luacore.exceptions.lua.NoReturnValueException;
import dev.selena.luacore.utils.config.FileManager;
import dev.selena.test.utils.MockUtils;
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
    private static MappingTest unmapped = new MappingTest();

    @BeforeAll
    public static void setup() throws IOException, URISyntaxException {
        temporaryFolder.create();
        MockUtils.setUp().mockDataFolder(temporaryFolder.getRoot());
        LuaManager.loadResourceFolder("test");
    }

    @Test
    void StringMap() throws NoReturnValueException {
        MappingTest mapped = LuaValueMapper.mapToClass(MappingTest.class, FileManager.folderPath("test") + "Test.lua");
        assertNotNull(mapped);
        assertNotEquals(mapped.Test_String, unmapped.Test_String);
    }

    @Test
    void BooleanMap() throws NoReturnValueException {
        MappingTest mapped = LuaValueMapper.mapToClass(MappingTest.class, FileManager.folderPath("test") + "Test.lua");
        assertNotNull(mapped);
        assertNotEquals(mapped.Test_Boolean, unmapped.Test_Boolean);
    }
    @Test
    void IntMap() throws NoReturnValueException {
        MappingTest mapped = LuaValueMapper.mapToClass(MappingTest.class, FileManager.folderPath("test") + "Test.lua");
        assertNotNull(mapped);
        assertNotEquals(mapped.Test_Int, unmapped.Test_Int);

    }


    @Test
    void Class_Mapping_Fail() {
        assertThrows(NoReturnValueException.class, () -> LuaValueMapper.mapToClass(MappingTest.class, FileManager.folderPath("test") + "MapWithMissingValues.lua"));

    }


    public static class MappingTest {
        @Expose
        public String Test_String = "123";
        @Expose
        public boolean Test_Boolean = false;
        @Expose
        public int Test_Int = 1;
    }
}