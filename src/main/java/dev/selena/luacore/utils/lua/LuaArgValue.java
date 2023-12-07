package dev.selena.luacore.utils.lua;

/**
 * Used for parsing args into the Lua script using {@link LuaManager#runScript(String, String, String, LuaArgValue...) runScript}
 * @param name  The name or key that will be used to get the value in the Lua
 * @param value The value of the argument variable
 */
public record LuaArgValue(String name, Object value) {


    /**
     * Used to get the argument variable name
     * @return The argument variable name
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Used to get the value of the argument variable
     * @return The value of the argument variable
     */
    @Override
    public Object value() {
        return value;
    }
}
