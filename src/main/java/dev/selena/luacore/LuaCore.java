package dev.selena.luacore;

import org.bukkit.plugin.Plugin;

public class LuaCore {

    private static Plugin plugin;

    public LuaCore(Plugin plugin) {
        LuaCore.plugin = plugin;
    }


    public static Plugin getPlugin() {
        return plugin;
    }

    public static String getCompountName() {
        return LuaCore.getPlugin().getName();
    }
}
