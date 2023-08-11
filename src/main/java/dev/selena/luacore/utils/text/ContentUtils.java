package dev.selena.luacore.utils.text;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentUtils {

    /**
     * Used for coloring Strings using hex formatting {@literal <#HEX>}
     * @param message The message you want to color
     * @return The colored string value
     */
    public static String color(String message) {
        message = translateHexColorCodes("<#", message, ">");
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Used for coloring a String and returning a TextComponent
     * @param message The string you want to color
     * @return A colored text component
     */
    public static Component componentColor(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return translateHexColorComponent("<#", message, ">");
    }

    /**
     * Converts hex color codes into a colored string
     * @param colorPrefix The prefix that comes before your hex code
     * @param message The content you want to color
     * @param colorSuffix The suffix that comes after your hex code
     * @return Colored string based off the hex you parsed in
     * @see #translateHexColorComponent(String, String, String)
     * @see #color(String)
     * @see #componentColor(String)
     */
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

    /**
     * Converts hex color codes into a colored Text Component
     * @param colorPrefix The prefix that comes before your hex code
     * @param message The content you want to color
     * @param colorSuffix The suffix that comes after your hex code
     * @return Colored Text Component based off the hex you parsed in
     * @see #translateHexColorCodes(String, String, String)
     * @see #color(String)
     * @see #componentColor(String)
     */
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



}
