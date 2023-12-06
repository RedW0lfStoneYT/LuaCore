package dev.selena.test.utils;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.lua.LuaManager;
import org.bukkit.plugin.Plugin;
import org.mockito.Mockito;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.logging.Logger;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class MockUtils {

    private static Plugin pluginMock; // Mocked plugin
    private static LuaCore libMock; // Mocked plugin

    private MockUtils() {
    } // Hides constructor

    public static MockUtils setUp() // Initialises the mock utility with mocked plugin class
    {
        MockUtils utils = new MockUtils().mockLibClass().mockPluginClass();
        libMock.setPlugin(pluginMock);
        return utils;
    }

    private MockUtils mockLibClass() // Mocks Plugin class
    {
        libMock = Mockito.mock(LuaCore.class);
        return this;
    }

    private MockUtils mockPluginClass() // Mocks Plugin class
    {
        pluginMock = Mockito.mock(Plugin.class);
        return this;
    }

    public MockUtils mockLogger() // Mocks JavaPlugin.getLogger
    {
        Logger testLogger = Logger.getLogger("TestLogger");
        given(pluginMock.getServer().getLogger()).willReturn(testLogger);
        return this;
    }

    public MockUtils mockDataFolder(File folder) throws URISyntaxException // Mocks JavaPlugin.getDataFolder
    {
        when(LuaCore.getPlugin().getDataFolder()).thenReturn(folder);
        return this;
    }

    public static Plugin getPluginMock() // Returns mocked plugin
    {
        return pluginMock;
    }
}