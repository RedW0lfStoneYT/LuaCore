package dev.selena.luacore;

import org.bukkit.plugin.Plugin;

public class LuaCore {

    private static Plugin plugin;

    /**
     * This needs to be called in the OnEnable method of your plugin for it to work
     * @param plugin Your main class that extends JavaPlugin
     */
    public LuaCore(Plugin plugin) {
        LuaCore.plugin = plugin;
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
        return LuaCore.getPlugin().getName();
    }
}
