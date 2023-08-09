package dev.selena.luacore.utils.text;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentUtils {

    public static String color(String message) {
        message = translateHexColorCodes("<#", message, ">");
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Component componentColor(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return translateHexColorComponent("<#", message, ">");
    }

    public static String translateHexColorCodes(String colorPrefix, String message, String colorSuffix) {

        final Pattern hexPattern = Pattern.compile(colorPrefix + "([A-Fa-f0-9]){6}" + colorSuffix);
        for(Matcher matcher = hexPattern.matcher(message); matcher.find(); matcher = hexPattern.matcher(message)) {

            String hexCode = matcher.group().substring(1, matcher.group().length() - 1);
            StringBuilder builder = new StringBuilder(ChatColor.COLOR_CHAR + "x");
            char[] hexChats = hexCode.substring(1).toCharArray();
            for (char c : hexChats) {
                builder.append(ChatColor.COLOR_CHAR).append(c);
            }
            String hexColor = builder.toString();
            String before = message.substring(0, matcher.start());
            String after = message.substring(matcher.end());
            message = before + hexColor + after;
        }
        return message;
    }

    public static Component translateHexColorComponent(String colorPrefix, String message, String colorSuffix) {

        final Pattern hexPattern = Pattern.compile(colorPrefix + "([A-Fa-f0-9]){6}" + colorSuffix);
        String[] messageArgs = message.split(colorPrefix + "([A-Fa-f0-9]){6}" + colorSuffix);
        TextComponent component = Component.text(messageArgs[0]);

        int i = 0;
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {

            String hexCode = matcher.group().substring(1, matcher.group().length() - 1);
            int color;
            String hexStr = hexCode.substring(1);
            color = Integer.decode("0x" + hexStr);
            component = component.append(Component.text(messageArgs[i+1]).decoration(TextDecoration.ITALIC, false).color(TextColor.color(color)));
            i++;
        }
        return component;
    }


    public static String matcherChars(Matcher matcher) {
        StringBuilder rString = new StringBuilder("§x");
        for (int i = 0; i < 6; i++) {
            rString.append("§").append(matcher.group().charAt(i));
        }
        return rString.toString();
    }

}
