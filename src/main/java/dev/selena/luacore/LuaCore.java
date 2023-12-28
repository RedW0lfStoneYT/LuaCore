package dev.selena.luacore;

import com.google.gson.annotations.Expose;
import dev.selena.luacore.utils.data.UserDataManager;
import dev.selena.luacore.utils.items.ItemEvent;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;


public class LuaCore {

    private static Plugin plugin;
    private static boolean verbose;
    private static UserDataManager userDataManager;



    /**
     * This needs to be called in the OnEnable method of your plugin for it to work
     * @param plugin Your main class that extends JavaPlugin
     */
    public static void setPlugin(Plugin plugin) {
        LuaCore.plugin = plugin;
    }

    /**
     * Used for setting up user data management, This is in early beta currently
     * @param userDataManager The instance of the UserDataManager class
     */
    public static void setUserDataManager(UserDataManager userDataManager) {
        LuaCore.userDataManager = userDataManager;
    }


    /**
     * Used for accessing the UserDataManager class, This is in early beta currently
     * @return The instance of UserDataManger
     */
    public static UserDataManager getUserDataManager() {
        return userDataManager;
    }

    /**
     * This should only be called by one plugin on your server,
     * Used for preventing the placement/usage of un usable items
     */
    public static void registerItemEvents() {
        plugin.getServer().getPluginManager().registerEvents(new ItemEvent(), plugin);

    }

    /**
     * Used for enabling extra messages
     * @param verbose True if you want extra messages
     */
    public static void setVerbose(boolean verbose) {
        LuaCore.verbose = verbose;
        LuaMessageUtils.verboseMessage("Verbose mode is enabled");
    }

    /**
     * Used for checking if verbose is enabled
     * @return The verbose value
     */
    public static boolean isVerbose() {
        return verbose;
    }

    /**
     * Gets your plugins instance
     * @return Plugin instance
     */
    public static Plugin getPlugin() {
        return plugin;
    }

    /**
     * Used for NBT compounds
     * @return Returns the compound name for your plugin
     */
    public static String getCompountName() {
        String name = LuaCore.getPlugin().getName();
        LuaMessageUtils.verboseMessage(name);
        return name;
    }
}
