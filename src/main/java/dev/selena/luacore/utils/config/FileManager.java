package dev.selena.luacore.utils.config;

import dev.selena.luacore.LuaCore;
import org.luaj.vm2.compiler.LuaC;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Internally used file manager for loading, creating, and saving files
 */
public class FileManager {


    private static FileManager instance;

    /**
     * Used for initial class setup
     */
    public FileManager() {
        FileManager.instance = this;
    }

    /**
     * Returns the specified file
     * @param parent The parent folder
     * @param file File name
     * @return The file you requested
     */
    public static File file(String parent, String file) {

        return new File(folderPath(parent), file);
    }

    /**
     * Used for getting a folder
     * @param path The folder name relative to your plugin data folder
     * @return The folder in the form of a Java File
     */
    public static File folder(String path) {
        return new File(folderPath(path));
    }

    /**
     * Returns a folder from your Plugins data folder
     * @param path Folder name inside the data folder
     * @return A string path of the requested folder
     */
    public static String folderPath(String path) {
        File file = new File(LuaCore.getPlugin().getDataFolder(), path);
        if (!file.exists())
            if (!file.mkdirs())
                try {
                    throw new IOException("There was an issue loading folder: " + path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        return file.getPath() + File.separator;
    }


    /**
     * Uses the GSON ConfigLoader class to map the config json to the specified class
     * @see ConfigLoader
     * @param clazz The class type you want it to be mapped to
     * @param file The file you are mapping from
     * @return Class T mapped with the data from the Json file
     * @param <T> Class type
     * @throws IOException Thrown when the file can not be found
     * @throws InstantiationException Throwing when the class has an initialization issue
     * @throws IllegalAccessException Thrown when trying to access a field without the correct access level
     * @throws InvocationTargetException Thrown when the mapper class has a constructor that does not match the arguments (There should be none)
     * @throws NoSuchMethodException Thrown when the class somehow manages not to have a constructor
     */
    public static <T> T loadFile(Class<T> clazz, File file) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return ConfigLoader.loadConfig(clazz, file);

    }


    /**
     * Used for getting the instance of this class
     * @return FileManager instance
     */
    public static FileManager get() {
        return instance;
    }
}