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
        String[] ver = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
        String versionString = "NOT_SUPPORTED";
        if (ver.length >= 4)
            versionString = ver[3];
        else
            versionString = Bukkit.getBukkitVersion().split("-")[0];
        return versionString;
    }
}
