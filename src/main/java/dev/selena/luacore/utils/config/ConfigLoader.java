package dev.selena.luacore.utils.config;

import com.google.gson.*;
import dev.selena.luacore.annotations.gson.Comment;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Config loader used to load and save json config files
 */
public class ConfigLoader {
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .setStrictness(Strictness.LENIENT)
            .create();

    /**
     * Loads a provided config object from a given JSON file.
     * If the file does not exist, it also creates the file using the given object defaults
     *
     * @param cls The object type you wish to load, also dictates the class of the returned object
     * @param file The file that is to be created/read from
     * @return The object loaded from file
     * @param <T> The class type you want to map to
     * @throws IOException Thrown when the file cannot be found
     * @throws InstantiationException Throwing when the class has an initialization issue
     * @throws IllegalAccessException Thrown when trying to access a field without the correct access level
     * @throws InvocationTargetException Thrown when the mapper class has a constructor that does not match the arguments (There should be none)
     * @throws NoSuchMethodException Thrown when the class somehow manages not to have a constructor
     */
    public static <T> T loadConfig(Class<T> cls, File file)
            throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        if (file.createNewFile()) { // File does not exist, save to file
            T instance = cls.getConstructor().newInstance(); // Create default instance
            String json = toJsonWithComments(instance, gson); // Inject comments into raw object
            try (PrintWriter out = new PrintWriter(file)) {
                out.print(json); // Write commented JSON directly
            }
        }

        return gson.fromJson(new String(Files.readAllBytes(file.toPath())), cls);
    }

    /**
     * Converts an Object to a JSON string using
     * GSON and injects the value of the @Comment annotation above
     * @param obj The object to convert to JSON.
     * @param gson The Gson instance used for serialization.
     * @return A JSON string with comments for each field.
     */
    public static String toJsonWithComments(Object obj, Gson gson) {
        Class<?> clazz = obj.getClass();
        String json = gson.toJson(obj);

        Map<String, String> comments = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            Comment comment = field.getAnnotation(Comment.class);
            if (comment != null) {
                comments.put(field.getName(), comment.value());
            }

        }

        StringBuilder result = new StringBuilder();
        String[] lines = json.split("\n");

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("\"")) {
                String fieldName = trimmed.substring(1, trimmed.indexOf("\"", 1));
                if (comments.containsKey(fieldName)) {
                    result.append("  // ").append(comments.get(fieldName)).append("\n");
                }
            }
            result.append(line).append("\n");
        }
        return result.toString();
    }



    /**
     * Saves a config object to the specified file in JSON format
     *
     * @param config The object to be saved
     * @param file The file to which the object is saved
     * @throws IOException Thrown when the file has some issue saving
     */
    @SuppressWarnings("all")
    public static void saveConfig(Object config, File file) throws IOException {
        file.createNewFile();
        String json = toJsonWithComments(config, gson);
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(json);
        }
    }
}
