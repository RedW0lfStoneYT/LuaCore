package dev.selena.luacore.utils.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import dev.selena.luacore.LuaCore;
import org.bukkit.Bukkit;
import org.bukkit.Utility;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class LuaMessageUtils extends ContentUtils {

    private static final boolean debug = true;
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .serializeNulls()
            .create();


    /**
     * Dumps a Java class to console, can be used for most.
     * @param cls The Class instance you want to dump to console
     */
    public static void json_dump(Object cls) {
        if (!debug)
            return;
        String classGson = gson.toJson(cls);
        consoleSend("\n" + gson.toJson(JsonParser.parseString(classGson)));
    }

    /**
     * Sends a message to console
     * @param content The content you want to send to console
     */
    public static void consoleSend(Object content) {
        LuaCore.getPlugin().getLogger().info(color(content.toString()));
    }

    /**
     * Used for sending a warning to the console
     * @param content The warning message you want to send
     */
    public static void consoleWarn(Object content) {
        LuaCore.getPlugin().getLogger().warning(content.toString());
    }

    /**
     * Used for sending an error to the console
     * @param content The error message you want to send
     */
    public static void consoleError(Object content) {
        LuaCore.getPlugin().getLogger().log(Level.SEVERE, content.toString());
    }

    /**
     * Sends a message to the selected player
     * @param player Player you want to message
     * @param content Message content you want to send to the player
     */
    public static void playerSend(Player player, Object... content) {
        player.sendMessage(colorArray(content));
    }


    /**
     * Colors an array using hex coloring
     * @param content The array content
     * @return The colored array
     */
    public static String[] colorArray(Object[] content) {
        String[] lines = new String[content.length];
        for (int i = 0; i < content.length; i++) {
            lines[i] = color(content[i] == null ? "null" : content[i].toString());
        }
        return lines;
    }


    /**
     * Sends a message to a command sender
     * @param sender The command sender
     * @param content The message content
     */
    public static void sender(CommandSender sender, Object... content) {
        sender.sendMessage(colorArray(content));
    }


    /**
     * Sends a message to all online players
     * @param message Message content.
     */
    public static void announce(Object ... message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerSend(player, message);
        }
    }

    /**
     * Used for debugging sending a warning to the console
     * @param warning The warning message you want to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaCore#isVerbose()
     */
    public static void verboseWarn(String warning) {
        if (LuaCore.isVerbose())
            consoleWarn(warning);
    }

    /**
     * Used for debugging sending an error to the console
     * @param error The error message you want to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaCore#isVerbose()
     */
    public static void verboseError(String error) {
        if (LuaCore.isVerbose())
            consoleError(error);
    }

    /**
     * Used for debugging sending a message to the console
     * @param message The message you want to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaCore#isVerbose()
     */
    public static void verboseMessage(String message) {
        if (LuaCore.isVerbose())
            consoleSend(message);
    }

}