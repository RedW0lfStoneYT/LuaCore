package dev.selena.luacore.exceptions.lua;


import java.io.FileNotFoundException;

public class NoReturnValueException extends FileNotFoundException {


    public NoReturnValueException(String message) {
        super(message);
    }
}
