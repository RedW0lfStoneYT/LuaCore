package dev.selena.luacore.utils.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import dev.selena.luacore.LuaCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils extends ContentUtils {

    private static final boolean debug = true;
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static void json_dump(Object cls) {
        if (!debug)
            return;
        String classGson = gson.toJson(cls);
        consoleSend("\n" + gson.toJson(JsonParser.parseString(classGson)));
    }

    public static void consoleSend(Object content) {
        LuaCore.getPlugin().getLogger().info(color(content.toString()));
    }

    public static void playerSend(Player player, Object... content) {
        player.sendMessage(colorArray(content));
    }


    public static String[] colorArray(Object[] content) {
        String[] lines = new String[content.length];
        for (int i = 0; i < content.length; i++) {
            lines[i] = color(content[i] == null ? "null" : content[i].toString());
        }
        return lines;
    }



    public static void sender(CommandSender sender, Object... content) {
        sender.sendMessage(colorArray(content));
    }


    public static void announce(Object ... message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerSend(player, message);
        }
    }
}