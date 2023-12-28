package dev.selena.test.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.data.UserDataManager;
import dev.selena.luacore.utils.lua.LuaManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.logging.Logger;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class MockUtils {

    private static Plugin pluginMock; // Mocked plugin
    private static LuaCore libMock; // Mocked plugin
    static MockedStatic<Bukkit> mockedStatic;

    static {
        mockedStatic = Mockito.mockStatic(Bukkit.class);
    }

    private MockUtils() {
    } // Hides constructor

    public static MockUtils setUp() {
        mockedStatic.when(Bukkit::getVersion).thenReturn("1.20.1-R0.1-SNAPSHOT");
        MockUtils utils = new MockUtils().mockLibClass().mockPluginClass();
        mockedStatic.when(pluginMock::getServer).thenReturn(Mockito.mock(Server.class));
        LuaCore.setPlugin(pluginMock);
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
        given(pluginMock.getLogger()).willReturn(testLogger);
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