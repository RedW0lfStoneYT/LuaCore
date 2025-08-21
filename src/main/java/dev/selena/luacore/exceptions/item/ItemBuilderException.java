package dev.selena.luacore.exceptions.item;

import org.bukkit.Location;

public class ItemBuilderException extends NullPointerException {

    /**
     * Exception thrown when an error occurs in the ItemBuilder.
     * @param message the detail message of the exception
     */
    public ItemBuilderException(String message) {
        super(message);
    }
}
