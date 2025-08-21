package dev.selena.test.utils;

import dev.selena.luacore.CoreLogger;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.data.UserDataManager;
import dev.selena.luacore.utils.lua.LuaManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class MockUtils {

    private static PluginMock pluginMock; // Mocked plugin
    private static ServerMock server;
    private static LuaCore libMock; // Mocked plugin
    private static MockedStatic<LuaCore> luaCoreStaticMock;
//
//    static {
//        mockedStatic = Mockito.mockStatic(Bukkit.class);
//    }

    private MockUtils() {
    } // Hides constructor

    public static MockUtils setUp() {
        server = MockBukkit.mock();
        MockUtils utils = new MockUtils().mockLibClass().mockPluginClass();
        LuaCore.setupCore(pluginMock);
        LuaCore.setCoreLogger(new CoreLogger(pluginMock));
//        luaCoreStaticMock = Mockito.mockStatic(LuaCore.class);
//        luaCoreStaticMock.when(LuaCore::getPlugin).thenReturn(pluginMock);
        return utils;
    }

    public MockUtils verbose() {
        LuaCore.setVerbose(true);
        return this;
    }

    public MockUtils withDataFolder() {
        LuaCore.setUserDataManager(new UserDataManager("data"));
        return this;
    }

    public MockUtils withResourceFolder(String folderName) throws IOException {
        LuaManager.loadResourceFolder(folderName);
        return this;
    }


    private MockUtils mockLibClass() // Mocks Plugin class
    {
        libMock = Mockito.mock(LuaCore.class);
        return this;
    }

    private MockUtils mockPluginClass() // Mocks Plugin class
    {
        pluginMock = spy(MockBukkit.createMockPlugin());
        return this;
    }

    public MockUtils mockLogger()
    {
        Logger testLogger = Logger.getLogger("TestLogger");
        given(pluginMock.getLogger()).willReturn(testLogger);
        return this;
    }

    public MockUtils mockDataFolder(File folder) throws URISyntaxException {
        Mockito.doReturn(folder).when(pluginMock).getDataFolder();
        System.out.println("Using mock DataFolder of: " + folder.getPath());
        return this;
    }

    public static Plugin getPluginMock() // Returns mocked plugin
    {
        return pluginMock;
    }

    public static void tearDownStaticMocks() {
        if (luaCoreStaticMock != null) {
            luaCoreStaticMock.close();
        }
    }
}