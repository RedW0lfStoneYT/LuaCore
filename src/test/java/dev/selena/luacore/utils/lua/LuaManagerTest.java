package dev.selena.luacore.utils.lua;

import dev.selena.luacore.LuaCore;
import dev.selena.test.utils.MockUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class LuaManagerTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    @BeforeAll
    public static void setUp() throws IOException, URISyntaxException {
        temporaryFolder.create();
        MockUtils.setUp().mockDataFolder(temporaryFolder.getRoot());
    }

    @Test
    void runScript() {
    }

    @Test
    void testRunScript() {
    }

    @Test
    void testRunScript1() {
    }

    @Test
    void testRunScript2() {
    }

    @Test
    void runEvent() {
    }

    @Test
    void testRunEvent() {
    }

    @Test
    void testRunEvent1() {
    }

    @Test
    void loadResourceFolder() throws URISyntaxException, IOException {
        LuaManager.loadResourceFolder("test");
        File file = new File(MockUtils.getPluginMock().getDataFolder(), "test");
        assertTrue(file.exists());
    }
}