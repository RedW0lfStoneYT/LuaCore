package dev.selena.luacore;

import dev.selena.luacore.utils.items.ItemEvent;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.plugin.Plugin;


public class LuaCore {

    private static Plugin plugin;
    private static boolean verbose;



    /**
     * This needs to be called in the OnEnable method of your plugin for it to work
     * @param plugin Your main class that extends JavaPlugin
     */
    public static void setPlugin(Plugin plugin) {
        LuaCore.plugin = plugin;
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
