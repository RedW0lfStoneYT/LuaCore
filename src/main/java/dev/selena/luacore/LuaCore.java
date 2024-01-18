package dev.selena.luacore;

import dev.selena.luacore.nms.Compatibility;
import dev.selena.luacore.nms.Version;
import dev.selena.luacore.utils.data.UserDataManager;
import dev.selena.luacore.utils.items.ItemEvent;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;

/**
 * Main class for the library
 */
public class LuaCore {

    /**
     * -- GETTER --
     *  Gets your plugins instance
     *
     * @return Plugin instance
     */
    @Getter
    private static Plugin plugin;
    /**
     * -- GETTER --
     *  Used for checking if verbose is enabled
     *
     * @return The verbose value
     */
    @Getter
    private static boolean verbose;
    /**
     * -- GETTER --
     *  Used for accessing the UserDataManager class, This is in early beta currently
     *
     * @return The instance of UserDataManger
     */
    @Getter
    private static UserDataManager userDataManager;
    @Getter
    @Setter
    private static CoreLogger coreLogger;
    @Getter
    private static boolean nmsVersionSupported = true;
    @Getter
    private static Version nmsVersion;

    /**
     * This needs to be called in the OnEnable method of your plugin for it to work
     * @param plugin Your main class that extends JavaPlugin
     */
    public static void setPlugin(Plugin plugin) {
        LuaCore.plugin = plugin;
    }

    /**
     * Method to setup the library with the required methods (setPlugin and setCoreLogger)
     * @param plugin Your plugin instance
     */
    public static void setupCore(Plugin plugin) {
        setPlugin(plugin);
        setCoreLogger(new CoreLogger(plugin));
        if (Compatibility.getVersion().equals("NOT_SUPPORTED"))
            nmsVersionSupported = false;
        else
            nmsVersion = Version.valueOf(Compatibility.getVersion());

    }

    /**
     * Used for setting up user data management, This is in early beta currently
     * @param userDataManager The instance of the UserDataManager class
     */
    public static void setUserDataManager(UserDataManager userDataManager) {
        LuaCore.userDataManager = userDataManager;
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
     * Used for NBT compounds
     * @return Returns the compound name for your plugin
     */
    public static String getCompountName() {
        String name = LuaCore.getPlugin().getName();
        LuaMessageUtils.verboseMessage(name);
        return name;
    }

    /**
     * Used for saving all needed data call this in onDisable
     */
    public static void save() {
        try {
            if (userDataManager != null)
                userDataManager.saveAllUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
