package dev.selena.luacore.utils.items;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import dev.selena.luacore.LuaCore;

import java.util.ArrayList;
import java.util.List;

/**
 * NBT utility class mainly for serialization and storing custom nbt compounds
 */
public class NBTUtils {

    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();


    /**
     * Used internally to serialize the nbt data
     * @param content The class you want to serialize
     * @return The now serialized json string
     */
    private static String serializeContent(Object content) {
        return gson.toJson(content);
    }

    /**
     * Used to convert the stored json into a populated class
     * @param cls The class you want to populate
     * @param json The NBT json compound
     * @return The now populated class
     * @param <T> The type of the class you want to populate
     */
    private static <T> T deserializeContent(Class<T> cls, String json) {
        return gson.fromJson(json, cls);
    }

    /**
     * Stores custom NBT content
     * @param compoundTag The Compound tag you want to store in, usually use your plugins one
     * @see LuaCore#getCompountName()
     * @param content The value of the data you want to store
     * @param nameSpaceKey where you should get the data from later
     */
    public static void storeNBTContent(NBTCompound compoundTag, Object content, String nameSpaceKey) {
        String json = serializeContent(content);

        compoundTag.setString(nameSpaceKey, json);
    }


    /**
     * Gets custom NBT data
     * @param cls The class you want to map to
     * @param nameSpaceKey The key you set in {@link #storeNBTContent(NBTCompound, Object, String)}
     * @param compoundTag The NBTCompound you are storing the custom NBT in
     * @return Fully mapped class
     * @param <T> The class type
     */
    public static <T> T getNBTContent(Class<T> cls, String nameSpaceKey, NBTCompound compoundTag) {
        if (compoundTag.hasTag(nameSpaceKey)) {
            String json = compoundTag.getString(nameSpaceKey);
            return deserializeContent(cls, json);
        }
        return null;
    }

}
