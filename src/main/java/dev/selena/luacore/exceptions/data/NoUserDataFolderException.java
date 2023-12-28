package dev.selena.luacore.exceptions.data;

import dev.selena.luacore.utils.data.UserDataManager;

import java.io.FileNotFoundException;
import java.util.UUID;

public class NoUserDataFolderException extends FileNotFoundException {

    /**
     * Thrown when the folder has not been set up make sure to call {@link UserDataManager#createUserDataFolder(UUID)}
     * @param message The message you want sent alongside the exception
     */
    public NoUserDataFolderException(String message) {
        super(message);
    }

}
