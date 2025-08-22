package dev.selena.luacore.utils.data;

import java.lang.reflect.Type;

/**
 * A class that holds a value and its type.
 * This is useful for generic programming where the type of the value is not known at runtime.
 *
 */
public class TypedObject {
    private Object value;
    private final String typeName;

    /**
     * Constructor for TypedObject.
     * Use this when you want to store a value that needs a type and only use it them.
     * If you parse in something like String.class
     * it will error out because it will try to use a different serialization method
     * @param value the value to be stored, can be of any type
     * @param type the type of the value, used for type safety
     */
    public TypedObject(Object value, Type type) {
        this.value = value;
        this.typeName = type == null ? null : type.getTypeName();
    }

    /**
     * Use this for objects that do not need a type for serialization
     * @param value the value to be stored, can be of any type
     */
    public TypedObject(Object value) {
        this(value, null);
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

    public Type getType() throws ClassNotFoundException {
        if (typeName == null || typeName.isEmpty()) {
            return null;
        }
        return Class.forName(typeName);
    }
}
