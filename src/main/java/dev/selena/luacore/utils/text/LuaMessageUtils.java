package dev.selena.luacore.utils.text;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.nbt.NBTUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * Used for sending messages to both console and users<br>
 * NOTE: ALL VERBOSE METHODS NEED {@link LuaCore#setVerbose(boolean)} SET TO TRUE
 */
public class LuaMessageUtils extends ContentUtils {

    private static final Gson gson = NBTUtils.getGson();


    /**
     * Dumps a Java class to console, this will only show values with the @Expose annotation.<br>
     * NOTE: THIS IS A VERBOSE METHOD AND NEEDS {@link LuaCore#setVerbose(boolean)} SET TO TRUE
     * @param cls The Class instance you want to dump to console
     * @see LuaMessageUtils#json_dump(String, Object)
     */
    public static void json_dump(Object cls) {
        json_dump("", cls);
    }

    /**
     * Dumps a Java class to console, this will only show values with the @Expose annotation.<br>
     * NOTE: THIS IS A VERBOSE METHOD AND NEEDS {@link LuaCore#setVerbose(boolean)} SET TO TRUE
     * @param prefix The prefix you want to add before the json dump
     * @param cls The Class instance you want to dump to console
     * @see LuaMessageUtils#json_dump(Object) 
     */
    public static void json_dump(String prefix, Object cls) {
        if (!LuaCore.isVerbose())
            return;
        String classGson = gson.toJson(cls);
        consoleSend(prefix + " \n" + gson.toJson(JsonParser.parseString(classGson)));
    }

    /**
     * Used for sending messages to the console.<br>
     * This method uses {@link dev.selena.luacore.CoreLogger#log(Level, String)} on {@link Level#INFO}
     * @param content The message you want to send to the console
     * @see LuaMessageUtils#verboseMessage(String)
     * @see LuaMessageUtils#verboseTracedMessage(String)
     */
    public static void consoleSend(Object content) {
        LuaCore.getCoreLogger().log(Level.INFO, content.toString());
    }

    /**
     * Used for sending a warning to the console.<br>
     * This method uses {@link dev.selena.luacore.CoreLogger#log(Level, String)} on {@link Level#WARNING}
     * @param content The warning message you want to send to the console
     * @see LuaMessageUtils#verboseWarn(String)
     * @see LuaMessageUtils#verboseTracedWarn(String)
     */
    public static void consoleWarn(Object content) {
        LuaCore.getCoreLogger().log(Level.WARNING, content.toString());
    }

    /**
     * Used for sending an error to the console.<br>
     * This method uses {@link dev.selena.luacore.CoreLogger#log(Level, String)} on {@link Level#SEVERE}
     * @param content The error message you want to send to the console
     * @see LuaMessageUtils#verboseError(String)
     * @see LuaMessageUtils#verboseTracedError(String)
     */
    public static void consoleError(Object content) {
        LuaCore.getCoreLogger().log(Level.SEVERE, content.toString());
    }

    /**
     * Sends a series of messages to a Player
     * @param player The Player you want to send the messages to
     * @param content The messages you want to send to the Player
     * @see LuaMessageUtils#verbosePlayerSend(Player, String...)
     * @see LuaMessageUtils#verbosePlayerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, String...)
     * @see LuaMessageUtils#playerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, Object)
     */
    public static void playerSend(Player player, Object... content) {
        for (String colored : ContentUtils.colorArray(content)) {
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, colored));
        }
    }

    /**
     * Sends a series of messages to a Player
     * @param player The Player you want to send the messages to
     * @param content The messages you want to send to the Player
     * @see LuaMessageUtils#verbosePlayerSend(Player, String...)
     * @see LuaMessageUtils#verbosePlayerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, Object...)
     * @see LuaMessageUtils#playerSend(Player, Object)
     */
    public static void playerSend(Player player, String ... content) {
        for (String colored : ContentUtils.colorArray(content)) {
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, colored));
        }
    }


    /**
     * Sends a message to a Player
     * @param player The Player you want to send the message to
     * @param content The message you want to send to the Player
     * @see LuaMessageUtils#verbosePlayerSend(Player, String...)
     * @see LuaMessageUtils#verbosePlayerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, String...)
     * @see LuaMessageUtils#playerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, Object...)
     */
    public static void playerSend(Player player, Object content) {
        player.sendMessage(PlaceholderAPI.setPlaceholders(player, ContentUtils.color((String) content)));
    }

    /**
     * Sends a message to a Player
     * @param player The Player you want to send the message to
     * @param content The message you want to send to the Player
     * @see LuaMessageUtils#verbosePlayerSend(Player, String...)
     * @see LuaMessageUtils#verbosePlayerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, String...)
     * @see LuaMessageUtils#playerSend(Player, Object...)
     * @see LuaMessageUtils#playerSend(Player, Object)
     */
    public static void playerSend(Player player, String content) {
        player.sendMessage(PlaceholderAPI.setPlaceholders(player, ContentUtils.color(content)));

    }


    /**
     * Sends a series of messages to a CommandSender
     * @param sender The CommandSender you want to send the messages to
     * @param content The messages you want to send to the CommandSender
     * @see LuaMessageUtils#verboseSender(CommandSender, String...)
     */
    public static void sender(CommandSender sender, Object... content) {
        if (sender instanceof Player player) {
            playerSend(player, content);
            return;
        }
        for (String colored : ContentUtils.colorArray(content)) {
            sender.sendMessage(PlaceholderAPI.setPlaceholders(null, colored));
        }
    }


    /**
     * Sends a message to all online players.<br>
     * NOTE: Any PAPI placeholders that are tied to a player will use the player its sent to.<br>
     * If you would like PAPI to use the player who triggered it,
     * please use {@link LuaMessageUtils#announce(Player, String...)}
     * @param message The message you want to send to all players.
     * @see LuaMessageUtils#announce(Player, String...)
     */
    public static void announce(Object ... message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerSend(player, message);
        }
    }

    /**
     * Sends a message to all online players and uses the trigger Player for PAPI placeholders<br>
     * If you would like PAPI to use the player who its being sent to,
     * please use {@link LuaMessageUtils#announce(Object...)}
     * @param trigger The Player who triggered this
     * @param message The message you want to send to all players.
     */
    public static void announce(Player trigger, String ... message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerSend(player, PlaceholderAPI.setPlaceholders(trigger, Arrays.asList(message)).toArray());
        }
    }

    /**
     * Used for announcing a message to a series of players.
     * @param trigger The player that triggered it for placeholders (It will be the player used for PAPI placeholders)
     * @param players The array of players you want to send the announcement to
     * @param message The message you want to send to the players.
     */
    public static void announceTo(Player trigger, Player[] players, String ... message) {
        announceTo(trigger, Arrays.asList(players), message);
    }

    /**
     * Used for announcing a message to a series of players.
     * @param trigger The player that triggered it for placeholders (It will be the player used for PAPI placeholders)
     * @param players The iterable of players you want to send the announcement to
     * @param message The message you want to send to the players.
     */
    public static void announceTo(Player trigger, Iterable<Player> players, String ... message) {
        for (Player player : players) {
            playerSend(player, PlaceholderAPI.setPlaceholders(trigger, Arrays.asList(message)).toArray());
        }
    }

    /**
     * Used for announcing a message to a series of players.<br>
     * Each player will get the message with their own PAPI placeholders
     * @param players The array of players you want to send the announcement to
     * @param message The message you want to send to the players.
     */
    public static void announceTo(Player[] players, String ... message) {
        announceTo(Arrays.asList(players), message);
    }

    /**
     * Used for announcing a message to a series of players.<br>
     * Each player will get the message with their own PAPI placeholders
     * @param players The iterable of players you want to send the announcement to
     * @param message The message you want to send to the players.
     */
    public static void announceTo(Iterable<Player> players, String ... message) {
        for (Player player : players) {
            playerSend(player, message);
        }
    }

    /**
     * Used for sending a debug warning to the console with call trace<br>
     * Example output: package.name.ClassName:methodName:lineNumber -> warning
     * @param warning The warning message you want to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaMessageUtils#verboseWarn(String)
     */
    public static void verboseTracedWarn(String warning) {
        if (LuaCore.isVerbose())
            consoleWarn(getTrace(3) + " -> " + warning);
    }

    /**
     * Used for sending a debug warning to the console.<br>
     * if {@link LuaCore#setDefaultWarnTrace(boolean)} is set to true the call trace will be included
     * @param warning The warning you want to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaCore#setDefaultWarnTrace(boolean)
     * @see LuaMessageUtils#verboseTracedWarn(String)
     */
    public static void verboseWarn(String warning) {
        // Cant just call the overload because that will change the trace to point to here not the original call
        if (LuaCore.isVerbose())
            consoleWarn((LuaCore.isDefaultWarnTrace() ? getTrace(3) + " -> " : "") + warning);
    }

    /**
     * Used for sending a debug error to the console with call trace<br>
     * Example output: package.name.ClassName:methodName:lineNumber -> error
     * @param error The error message you want to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaMessageUtils#verboseError(String)
     */
    public static void verboseTracedError(String error) {
        if (LuaCore.isVerbose())
            consoleError(getTrace(3) + " -> "+ error);
    }

    /**
     * Used for sending a debug error to the console.<br>
     * if {@link LuaCore#setDefaultErrorTrace(boolean)} is set to true the call trace will be included
     * @param error The error you error to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaCore#setDefaultErrorTrace(boolean)
     * @see LuaMessageUtils#verboseTracedError(String)
     */
    public static void verboseError(String error) {
        // Cant just call the overload because that will change the trace to point to here not the original call
        if (LuaCore.isVerbose())
            consoleError((LuaCore.isDefaultErrorTrace() ? getTrace(3) + " -> " : "") + error);

    }

    /**
     * Used for sending a debug message to the console with call trace<br>
     * Example output: package.name.ClassName:methodName:lineNumber -> message
     * @param message The message you want to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaCore#setDefaultMessageTrace(boolean)
     */
    public static void verboseTracedMessage(String message) {
        if (LuaCore.isVerbose())
            consoleSend(getTrace(3) + " -> " + message);
    }

    /**
     * Used for sending a debug message to the console.<br>
     * if {@link LuaCore#setDefaultMessageTrace(boolean)} is set to true the call trace will be included
     * @param message The message you want to send
     * @see LuaCore#setVerbose(boolean)
     * @see LuaCore#setDefaultMessageTrace(boolean)
     * @see LuaMessageUtils#verboseTracedMessage(String)
     */
    public static void verboseMessage(String message) {
        // Cant just call the overload because that will change the trace to point to here not the original call
        if (LuaCore.isVerbose())
            consoleSend((LuaCore.isDefaultMessageTrace() ? getTrace(3) + " -> " : "") + message);
    }


    /**
     * Used for sending a series of debug messages to a player
     * @param player The Player you want to send the messages to
     * @param content Array of messages you want to send to the Player
     * @see LuaCore#setVerbose(boolean)
     * @see LuaMessageUtils#verbosePlayerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, String...)
     * @see LuaMessageUtils#playerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, Object...)
     * @see LuaMessageUtils#playerSend(Player, Object)
     */
    public static void verbosePlayerSend(Player player, String... content) {
        if (!LuaCore.isVerbose())
            return;
        for (String colored : ContentUtils.colorArray(content)) {
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, colored));
        }
    }

    /**
     * Used for sending a debug message to a player
     * @param player The Player you want to send the message to
     * @param content The message you want to send to the player
     * @see LuaCore#setVerbose(boolean)
     * @see LuaMessageUtils#verbosePlayerSend(Player, String...)
     * @see LuaMessageUtils#playerSend(Player, String...)
     * @see LuaMessageUtils#playerSend(Player, String)
     * @see LuaMessageUtils#playerSend(Player, Object...)
     * @see LuaMessageUtils#playerSend(Player, Object)
     */
    public static void verbosePlayerSend(Player player, String content) {
        if (!LuaCore.isVerbose())
            return;
        player.sendMessage(PlaceholderAPI.setPlaceholders(player, ContentUtils.color(content)));
    }


    /**
     * Used for sending a series of debug messages to a CommandSender
     * @param sender The CommandSender
     * @param content The array of messages you want to send the CommandSender
     * @see LuaCore#setVerbose(boolean)
     * @see LuaMessageUtils#sender(CommandSender, Object...)
     */
    public static void verboseSender(CommandSender sender, String... content) {
        if (!LuaCore.isVerbose())
            return;
        if (sender instanceof Player player) {
            playerSend(player, content);
            return;
        }
        for (String colored : ContentUtils.colorArray(content)) {
            sender.sendMessage(PlaceholderAPI.setPlaceholders(null, colored));
        }
    }

    /**
     * Used for getting the call trace
     * @param traceIndex What trace index you want<br>
     * NOTE:
     *                   The {@link Thread#getStackTrace()} will be the 0th trace index
     *                   and this method will be the 1st trace index so start at the 2nd index
     * @return The call trace in the format of 'package.name.ClassName:methodName:lineNumber'
     */
    public static String getTrace(int traceIndex) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return elements[traceIndex].getClassName() + ":" + elements[traceIndex].getMethodName() + ":" + elements[traceIndex].getLineNumber();
    }

}