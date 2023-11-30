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
     * Returns a folder from your Plugins data folder
     * @param path Folder name inside the data folder
     * @return A string path of the requested folder
     */
    public static String folderPath(String path) {
        File file = new File(LuaCore.getPlugin().getDataFolder(), path);
        if (!file.exists())
            file.mkdirs();
        return file.getPath() + File.separator;
    }


    /**
     * Uses the GSON ConfigLoader class to map the config json to the specified class
     * @see ConfigLoader
     * @param clazz The class type you want it to be mapped to
     * @param file The file you are mapping from
     * @return Class T mapped with the data from the Json file
     * @param <T> Class type
     */
    public static <T> T loadFile(Class<T> clazz, File file) {
        try {
            return ConfigLoader.loadConfig(clazz, file);
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * Used for getting the instance of this class
     * @return FileManager instance
     */
    public static FileManager get() {
        return instance;
    }
}