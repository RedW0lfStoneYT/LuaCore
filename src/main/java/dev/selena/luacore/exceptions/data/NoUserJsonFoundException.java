package dev.selena.luacore.exceptions.data;

import java.io.FileNotFoundException;

public class NoUserJsonFoundException extends FileNotFoundException {

    /**
     * Thrown when the user folder has not been set up correctly and does not contain any json files
     * @param message The message you want to use for the exception
     */
    public NoUserJsonFoundException(String message) {
        super(message);
    }

}
