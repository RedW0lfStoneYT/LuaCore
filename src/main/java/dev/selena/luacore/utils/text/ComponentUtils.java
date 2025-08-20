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


    public static Component color(String input) {
        if (input == null || input.isEmpty()) {
            return Component.empty();
        }
        input = input.replace('\u00A7', '&');
        Component legacy = LegacyComponentSerializer.legacyAmpersand().deserialize(input);
        String miniSource = MINI_MESSAGE.serialize(legacy).replace("\\", "");
        return MINI_MESSAGE.deserialize("<italic:false>" + convertFromIridium(miniSource));
    }

    public static List<Component> color(List<String> strings) {
        return strings.stream()
                .map(ComponentUtils::color)
                .collect(Collectors.toList());
    }



    public static String convertFromIridium(String input) {
        if (input == null || input.isEmpty()) return "";

        input = convertGradient(input);
        input = convertRainbow(input);
        input = convertSolid(input);

        return input;
    }

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
