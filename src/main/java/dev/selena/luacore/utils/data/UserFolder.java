package dev.selena.luacore.utils.data;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.exceptions.data.NoUserJsonFoundException;
import dev.selena.luacore.utils.config.FileManager;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * This class is required for any User folder class
 */
public abstract class UserFolder {

    @Getter
    private final Map<Class<?>, FileClass> loadedFiles = new HashMap<>();
    @Getter
    private final UUID uuid;

    /**
     * Used for setting the UUID for various internal uses
     * @param uuid The players UUID
     */
    public UserFolder(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Used for setting up the class extending UserFolder
     * @throws NoUserJsonFoundException when there is no Json files inside the user folder
     */
    public void init() throws NoUserJsonFoundException {
        File parent = new File(LuaCore.getUserDataManager().getUserFolderPath(uuid));
        File[] children = parent.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        if (children == null || loadedFiles.isEmpty()) {
            throw new NoUserJsonFoundException("There is no json files inside " + parent.getName() + ". Please be sure to call loadData(Class<T>, String) before super");
        }
    }

    /**
     * Used for setting up the GSON mapping classes for user data
     * @param clazz The class you want to map to
     * @param fileName The file name relative to the users data folder
     * @return The mapped class
     * @param <T> The class type
     */
    public <T> T loadData(@NotNull Class<T> clazz, @NotNull String fileName) {
        T data = null;
        try {
            File file = FileManager.file(LuaCore.getUserDataManager().getRelativeUserFolderPath(uuid), fileName);
            data = FileManager.loadFile(clazz, file);
            loadedFiles.put(clazz, new FileClass(fileName, data));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return data;
    }

    /**
     * Data file class used for managing data internally
     */
    public static class FileClass {
        String fileName;
        Object data;

        /**
         * Used to create a new FileClass instance for user data
         * @param name The name of the file
         * @param data The data being parsed in
         */
        public FileClass(String name, Object data) {
            fileName = name;
            this.data = data;
        }
    }

}
