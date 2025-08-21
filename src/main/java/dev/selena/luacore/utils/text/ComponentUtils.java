package dev.selena.luacore.utils.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ComponentUtils {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final Pattern IRIDIUM_GRADIENT_PATTERN = Pattern.compile("<GRADIENT:([0-9A-Fa-f]{6})>(.*?)</GRADIENT:([0-9A-Fa-f]{6})>");
    private static final Pattern IRIDIUM_SOLID_PATTERN = Pattern.compile("<SOLID:([0-9A-Fa-f]{6})>|#\\{([0-9A-Fa-f]{6})}");
    private static final Pattern IRIDIUM_RAINBOW_PATTERN = Pattern.compile("<RAINBOW(!)?([0-9]{1,3})>(.*?)</RAINBOW>");


    /**
     * Converts a string to an Adventure Component with MiniMessage.
     * This method supports Iridium's gradient, rainbow, and solid color formats too.
     * @param input the string to convert
     * @return a MiniMessage component with the converted colors
     */
    public static Component color(String input) {
        if (input == null || input.isEmpty()) {
            return Component.empty();
        }
        input = input.replace('\u00A7', '&');
        Component legacy = LegacyComponentSerializer.legacyAmpersand().deserialize(input);
        String miniSource = MINI_MESSAGE.serialize(legacy).replace("\\", "");
        return MINI_MESSAGE.deserialize("<italic:false>" + convertFromIridium(miniSource));
    }

    /**
     * Converts a list of strings to a list of Adventure Components with MiniMessage.
     * This method supports Iridium's gradient, rainbow, and solid color formats too.
     * @param strings the list of strings to convert
     * @return a list of MiniMessage components with the converted colors
     */
    public static List<Component> color(List<String> strings) {
        return strings.stream()
                .map(ComponentUtils::color)
                .collect(Collectors.toList());
    }

    /**
     * Converts a string from Iridium's color format to MiniMessage format.
     * This method supports gradient, rainbow, and solid color formats.
     * @param input the string to convert
     * @return the converted string in MiniMessage format
     */
    public static String convertFromIridium(String input) {
        if (input == null || input.isEmpty()) return "";
        input = convertGradient(input);
        input = convertRainbow(input);
        input = convertSolid(input);
        return input;
    }

    /**
     * Converts an Iridium gradient format to MiniMessage format.
     * @param input the string containing the Iridium gradient format
     * @return the converted string in MiniMessage format
     */
    private static String convertGradient(String input) {
        Matcher matcher = IRIDIUM_GRADIENT_PATTERN.matcher(input);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            String start = matcher.group(1);
            String text = matcher.group(2);
            String end = matcher.group(3);
            String replacement = "<gradient:#" + start + ":#" + end + ">" + text + "</gradient>";
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * Converts an Iridium rainbow format to MiniMessage format.
     * @param input the string containing the Iridium rainbow format
     * @return the converted string in MiniMessage format
     */
    private static String convertRainbow(String input) {
        Matcher matcher = IRIDIUM_RAINBOW_PATTERN.matcher(input);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            boolean invert = matcher.group(1) != null;
            String offset = matcher.group(2);
            String text = matcher.group(3);

            String tag = "<rainbow:" + (invert ? "!" : "") + offset + ">" + text + "</rainbow>";
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(tag));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * Converts an Iridium solid color format to MiniMessage format.
     * @param input the string containing the Iridium solid color format
     * @return the converted string in MiniMessage format
     */
    private static String convertSolid(String input) {
        Matcher matcher = IRIDIUM_SOLID_PATTERN.matcher(input);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            String hex = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            String replacement = "<#" + hex + ">";
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
