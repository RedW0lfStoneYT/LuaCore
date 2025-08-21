package dev.selena.luacore.utils.lua;

import dev.selena.luacore.exceptions.lua.NoReturnValueException;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.Material;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.lang.reflect.Field;

/**
 * Used for mapping Lua values to a class
 * Note there is still a bit of work needed here
 */
public class LuaValueMapper {

    private static final Globals luaGlobals = JsePlatform.standardGlobals();

    /**
     * Used for loading a Lua table into memory
     *
     * @param scriptPath The path to the script
     * @return The Lua Table for the script returns
     * @throws NoReturnValueException Thrown when there is no lua return table
     */
    public static LuaTable loadTable(String scriptPath) throws NoReturnValueException {
        LuaMessageUtils.verboseMessage("Checking if " + scriptPath + " has a table");

        if (luaGlobals.finder.findResource(scriptPath) == null) {
            throw new NoReturnValueException("There is no returns in the lua script: " + scriptPath);
        }
        LuaValue luaScript = luaGlobals.loadfile(scriptPath).call();

        if (luaScript.istable()) {
            try {
                LuaTable table = luaScript.checktable();
                LuaMessageUtils.verboseMessage("Table exists");
                return table;
            } catch (LuaError e) {
                LuaMessageUtils.verboseError("There is no table in " + scriptPath);
                throw new NoReturnValueException(e.getMessage());
            }
        }
        LuaMessageUtils.verboseError("There is no table in " + scriptPath);
        return null;
    }

    /**
     * Used for mapping lua values to the class
     *
     * @param cls        The class you want to map to
     * @param scriptPath The path to the script
     * @param <T>        The mapper class
     * @return The mapped class instance
     * @throws NoReturnValueException Thrown when there is no lua return table
     */
    public static <T> T mapToClass(Class<T> cls, String scriptPath) throws NoReturnValueException {
        try {
            LuaTable table = loadTable(scriptPath);

            T clazz = cls.getConstructor().newInstance();

            if (table == null)
                throw new NullPointerException("The lua table cannot be null");

            for (LuaValue key : table.keys()) {
                String fieldName = key.tojstring();
                try {
                    Field field = cls.getDeclaredField(fieldName);
                    field.setAccessible(true);

                    LuaValue value = table.get(key);
                    setField(field, clazz, value);

                } catch (NoSuchFieldException e) {
                    LuaMessageUtils.verboseWarn("Skipping field: " + fieldName + ", Due to there being no field in the class");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return clazz;
        } catch (NoReturnValueException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


// Still can be improved, I'm sure

    /**
     * Used for accessing the fields of the class and populating the values
     * @param field The field you want to access
     * @param clazz The class the field is in
     * @param value The value you want to set it to
     * @throws IllegalAccessException Thrown when you do not have the ability to access the field
     */
    private static void setField(Field field, Object clazz, LuaValue value) throws IllegalAccessException {
        LuaMessageUtils.verboseMessage("Attempting to access " + field.getName());
        Class<?> fieldType = field.getType();
        switch(fieldType.getName()) {
            case "org.bukkit.Material" -> field.set(clazz, Material.getMaterial(value.tojstring()));
            default -> {
                LuaMessageUtils.verboseMessage("Field type: " + fieldType.getName());
                try {
                    field.get(clazz);
                } catch (IllegalAccessException e) {
                    LuaMessageUtils.verboseMessage("Field " + field.getName() + " is not accessible");
                }

                LuaMessageUtils.verboseMessage("Attempting to set " + field.getName() + " to " + value);
                Object convertedValue = CoerceLuaToJava.coerce(value, fieldType);
                field.set(clazz, convertedValue);
            }
        }
    }



}
