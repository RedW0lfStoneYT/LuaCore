package dev.selena.luacore.exceptions.lua;


import java.io.FileNotFoundException;

/**
 * Thrown when the Lua file does not have any return table
 */
public class NoReturnValueException extends FileNotFoundException {


    /**
     * Thrown when the Lua file does not have any return table
     * @param message The error message
     */
    public NoReturnValueException(String message) {
        super(message);
    }
}
