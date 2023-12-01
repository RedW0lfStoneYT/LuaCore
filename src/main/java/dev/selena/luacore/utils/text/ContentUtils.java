package dev.selena.luacore.utils.text;


import com.iridium.iridiumcolorapi.IridiumColorAPI;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ContentUtils {


    /**
     * Used for making the first Letter of each word caps
     * @param content The String you want to capatlize
     * @return The formatted string content
     */
    public static String capsFirst(String content) {
        return WordUtils.capitalizeFully(content.replace("_", " "));
    }

    /**
     * Used for coloring string using Iridium
     * @param message The string content you want to color
     * @return The newly colored string
     */
    public static String color(String message) {
        return IridiumColorAPI.process(message);
    }

    /**
     * Used for coloring string using Iridium
     * then convert them to Components
     * @param message The string content you want to color
     * @return The newly colored Component
     */
    public static Component colorStringToComponent(String message) {
        return Component.text(IridiumColorAPI.process(message));
    }

    /**
     * Colors a List using Iridium
     * @param content The String List content
     * @return The colored String List
     */
    public static List<String> colorList(List<String> content) {
                return IridiumColorAPI.process(content);
    }

    /**
     * Colors an array using Iridium
     * @param content The Object array content
     * @return The colored String array
     */
    public static String[] colorArray(Object[] content) {
        List<String> contentStr = Arrays.stream(content).map(Object::toString).toList();
        return colorList(contentStr).toArray(String[]::new);

    }

    /**
     * Colors a List using Iridium then convert to a String array
     * @param content The Object List content
     * @return The colored String array
     */
    public static String[] colorArray(List<Objects> content) {
        List<String> contentStr = content.stream()
                .map(object -> Objects.toString(object, null))
                .toList();
        return colorList(contentStr).toArray(String[]::new);
    }



}
