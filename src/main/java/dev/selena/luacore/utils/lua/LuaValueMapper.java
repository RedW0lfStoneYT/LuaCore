package dev.selena.luacore.utils.lua;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.text.MessageUtils;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Used for mapping Lua values to a class
 * Note there is still a bit of work needed here
 */
public class LuaValueMapper {

    private static final Globals luaGlobals = JsePlatform.standardGlobals();


    /**
     * Used for loading a Lua table into memory
     * @param scriptPath The path to the script
     * @return The Lua Table for the script returns
     */
    public static LuaTable loadTable(String scriptPath) {
        LuaValue luaScript = luaGlobals.loadfile(scriptPath).call();

        if (luaScript.istable()) {
            return luaScript.checktable();
        }
        return null;
    }

    /**
     * Used for mapping lua values to the class
     * @param cls The class you want to map to
     * @param scriptPath The path to the script
     * @return The mapped class instance
     * @param <T> The mapper class
     */
    public static <T> T mapToClass(Class<T> cls, String scriptPath) {
        try {
            LuaTable table = loadTable(scriptPath);

            T clazz = cls.getConstructor().newInstance();

            if (table == null)
                throw new NullPointerException("The lua table cannot be null");
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            for (LuaValue key : table.keys()) {
                String fieldName = key.tojstring();
                try {
                    Field field = cls.getDeclaredField(fieldName);
                    field.setAccessible(true);

                    LuaValue value = table.get(key);
                    setField(field, clazz, value);

                } catch (NoSuchFieldException e) {
                    if (LuaCore.isVerbose())
                        MessageUtils.consoleWarn("Skipping field: " + fieldName + ", Due to there being no field in the class");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return clazz;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //Likely not the best way to do this, feel free to help me out with a PR
    private static void setField(Field field, Object clazz, LuaValue value) throws IllegalAccessException {
        Class<?> fieldType = field.getType();

        if (fieldType == boolean.class) {
            field.setBoolean(clazz, value.toboolean());
        } else if (fieldType == int.class) {
            field.setInt(clazz, value.toint());
        } else if (fieldType == float.class) {
            field.setFloat(clazz, (float) value.todouble());
        } else if (fieldType == double.class) {
            field.setDouble(clazz, value.todouble());
        } else if (fieldType == byte.class) {
            field.setByte(clazz, value.tobyte());
        } else if (fieldType == Material.class) {
            field.set(clazz, Material.getMaterial(value.tojstring()));
        } else if (fieldType == long.class) {
            field.setLong(clazz, value.tolong());
        } else if (fieldType == short.class) {
            field.setLong(clazz, value.toshort());
        } else if (fieldType == char.class) {
            field.setLong(clazz, value.tochar());
        } else if (fieldType == String.class) {
            field.set(clazz, value.tojstring());
        }
    }

}
