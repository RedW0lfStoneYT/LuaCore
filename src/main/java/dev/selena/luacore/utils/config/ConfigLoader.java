package dev.selena.luacore.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

/**
 * Config loader used to load and save json config files
 */
public class ConfigLoader {
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    /**
     * Loads a provided config object from a given JSON file.
     * If the file does not exist it also creates the file using the given object defaults
     *
     * @param cls The object type you wish to load, also dictates the class of the returned object
     * @param file The file that is to be created/read from
     * @return The object loaded from file
     */
    public static <T> T loadConfig(Class<T> cls, File file) throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (file.createNewFile()) { //File does not exist, save to file
            String classGson = gson.toJson(cls.getConstructor().newInstance());
            String json = gson.toJson(JsonParser.parseString(classGson));
            try (PrintWriter out = new PrintWriter(file)) {
                out.println(json);
            }
        }
        return gson.fromJson(new String(Files.readAllBytes(file.toPath())), cls);

    }


    /**
     * Saves a config object to the specified file in JSON format
     *
     * @param config The object to be saved
     * @param file   The file to which the object is saved
     */
    @SuppressWarnings("all")
    public static void saveConfig(Object config, File file) throws IOException {
        file.createNewFile();
        String json = gson.toJson(JsonParser.parseString(gson.toJson(config)));
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(json);
        }
    }
}
