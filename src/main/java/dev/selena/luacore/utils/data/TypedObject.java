package dev.selena.luacore.utils.data;

import lombok.Getter;

import java.lang.reflect.Type;

/**
 * A class that holds a value and its type.
 * This is useful for generic programming where the type of the value is not known at runtime.
 *
 */
public class TypedObject {
    private Object value;
    @Getter
    private final Type type;

    /**
     * Constructor for TypedObject.
     * @param value the value to be stored, can be of any type
     * @param type the type of the value, used for type safety
     */
    public TypedObject(Object value, Type type) {
        this.value = value;
        this.type = type;
    }
    /**
     * Used for getting the value of the TypedObject.
     */
    public <T> T getValue() {
        return (T) value;
    }
    /**
     * Used for setting the value of the TypedObject.
     * @param value the new value to be set, can be of any type
     */
    public <T> void setValue(T value) {
        this.value = value;
    }
}
