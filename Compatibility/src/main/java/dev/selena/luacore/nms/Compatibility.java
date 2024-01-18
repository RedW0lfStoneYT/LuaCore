package dev.selena.luacore.nms;

import org.bukkit.Bukkit;

public class Compatibility {

    public static String getVersion() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "NOT_SUPPORTED";
        }
    }
}
