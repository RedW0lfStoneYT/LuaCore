package dev.selena.luacore.utils.lua;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.data.UserDataManager;
import dev.selena.test.utils.MockUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerJoinEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LuaManagerTest {

    private static Path temporaryFolderPath;

    @BeforeAll
    static void setUp() throws Exception {
        temporaryFolderPath = Files.createTempDirectory("test-data");
        MockUtils.setUp()
                .mockDataFolder(temporaryFolderPath.toFile())
                .mockLogger()
                .withDataFolder();
    }

    @AfterAll
    static void tearDown() {
        MockBukkit.unmock();
        MockUtils.tearDownStaticMocks();
    }

    @Test
    void loadResourceFolder() throws URISyntaxException, IOException {
        LuaManager.loadResourceFolder("test");
        File file = new File(MockUtils.getPluginMock().getDataFolder(), "test");
        assertTrue(file.exists());
    }

    @Test
    void runScript_Success() throws URISyntaxException, IOException {
        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runScript("run", "test", "RunTests.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"));
        assertTrue(ranFine);
    }

    @Test
    void runEvent_Success() throws URISyntaxException, IOException {

        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runEvent("test", "RunTests.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"),
                new LuaArgValue("event", new PlayerJoinEvent(MockUtils.getPluginMock().getServer().getPlayer("GAY"), Component.text("GAY"))));
        assertTrue(ranFine);
    }

    @Test
    void runScript_TypoFail() throws URISyntaxException, IOException {
        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runScript("run", "tast", "RunTests.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"));
        assertFalse(ranFine);
    }

    @Test
    void runEvent_TypoFail() throws URISyntaxException, IOException {

        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runEvent("test", "RunTe.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"),
                new LuaArgValue("event", new PlayerJoinEvent(MockUtils.getPluginMock().getServer().getPlayer("GAY"), Component.text("GAY"))));
        assertFalse(ranFine);
    }

    @Test
    void runScript_FunctionDoesNotExistFail() throws URISyntaxException, IOException {
        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runScript("runs", "test", "RunTests.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"));
        assertFalse(ranFine);
    }

    @Test
    void runEvent_FunctionDoesNotExistFail() throws URISyntaxException, IOException {

        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runEvent("test", "RunTestsNoEvent.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"),
                new LuaArgValue("event", new PlayerJoinEvent(MockUtils.getPluginMock().getServer().getPlayer("GAY"), Component.text("GAY"))));
        assertFalse(ranFine);
    }


    @Test
    void runScript() throws URISyntaxException, IOException {
        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runScript("run", "test/RunTests.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"));
        assertTrue(ranFine);
    }

    @Test
    void runEvent() throws URISyntaxException, IOException {
        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runEvent("test/RunTests.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"));
        assertTrue(ranFine);
    }


    @Test
    void runScript_FailDueToLuaError() throws URISyntaxException, IOException {
        LuaManager.loadResourceFolder("test");
        boolean ranFine = LuaManager.runScript("runFailWithExtraVariable", "test", "RunTestsNoEvent.lua",
                new LuaArgValue("test", "abc"),
                new LuaArgValue("test2", "def"));
        assertFalse(ranFine);
    }
}