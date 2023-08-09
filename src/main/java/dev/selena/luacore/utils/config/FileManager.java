package dev.selena.luacore.utils.config;

import dev.selena.luacore.LuaCore;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class FileManager {


    private static FileManager instance;

    public FileManager() {
        FileManager.instance = this;
    }


    public static File file(String parent, String file) {

        return new File(folderPath(parent) + file);
    }

    public static String folderPath(String path) {
        File file = new File("plugins" + File.separator + LuaCore.getPlugin().getName(), path);
        if (!file.exists())
            file.mkdirs();
        return file.getPath() + File.separator;
    }


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



    public static FileManager get() {
        return instance;
    }
}