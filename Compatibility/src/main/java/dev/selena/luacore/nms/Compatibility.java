package dev.selena.luacore.nms;

import org.bukkit.Bukkit;

/**
 * Used for NMS reflection management
 */
public class Compatibility {

    /**
     * Allows you to get the version number or NOT_SUPPORTED if there is no NMS specific class
     * @return The version number or NOT_SUPPORTED
     */
    public static String getVersion() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "NOT_SUPPORTED";
        }
    }
}
