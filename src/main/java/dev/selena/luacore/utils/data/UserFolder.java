package dev.selena.luacore.utils.data;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.exceptions.NoUserJsonFoundException;
import dev.selena.luacore.utils.config.FileManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class UserFolder {

    private List<String> loadedFiles;

    /**
     * Used for setting up the class extending UserFolder
     * @param uuid The users UUID
     * @throws NoUserJsonFoundException when there is no Json files inside the user folder
     */
    public void init(UUID uuid) throws NoUserJsonFoundException {
        loadedFiles = new ArrayList<>();
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
            data = FileManager.loadFile(clazz, FileManager.file(UserDataManager.folderName, fileName));
            loadedFiles.add(fileName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return data;
    }

}
