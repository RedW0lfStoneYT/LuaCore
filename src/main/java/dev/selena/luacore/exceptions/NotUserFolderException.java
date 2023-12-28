package dev.selena.luacore.exceptions;

public class NotUserFolderException extends ClassCastException {

    /**
     * Thrown when the class you are trying to use as the mapper is not instance of {@link dev.selena.luacore.utils.data.UserFolder}
     * @param message The message you want to send alongside it
     */
    public NotUserFolderException(String message) {
        super(message);
    }
}
