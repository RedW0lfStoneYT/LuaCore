package dev.selena.luacore.utils.nbt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import dev.selena.luacore.utils.typeadapters.ConfigurationSerializableAdapter;
import dev.selena.luacore.utils.typeadapters.ItemStackAdapter;
import dev.selena.luacore.utils.typeadapters.SpigotTypeAdapter;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.lang.reflect.Type;

/**
 * NBT utility class mainly for serialization and storing custom nbt compounds
 */
public class NBTUtils {

    @Getter
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .serializeNulls()
            .setStrictness(Strictness.LENIENT)
            .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
            .registerTypeAdapter(Location.class, new SpigotTypeAdapter<>(Location::deserialize))
            .registerTypeAdapter(ItemStack.class, new ItemStackAdapter())
            .registerTypeAdapter(BoundingBox.class, new SpigotTypeAdapter<>(BoundingBox::deserialize))
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
     * Used internally to serialize the nbt data
     * @param content The class you want to serialize
     * @param type The object Type
     * @return The now serialized json string
     */
    private static String serializeContent(Object content, Type type) {
        return gson.toJson(content, type);
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
     * Used to convert the stored json into a populated class
     * @param type The object type you want to populate
     * @param json The NBT json compound
     * @return The now populated class
     * @param <T> The type of the class you want to populate
     */
    private static <T> T deserializeContent(Type type, String json) {
        return gson.fromJson(json, type);
    }

    /**
     * Stores custom NBT content
     * @param compoundTag The Compound tag you want to store in, usually use your plugins one
     * @see LuaCore#getCompountName()
     * @param content The value of the data you want to store
     * @param nameSpaceKey where you should get the data from later
     */
    public static void storeNBTContent(NBTCompound compoundTag, Object content, String nameSpaceKey) {
        LuaMessageUtils.json_dump(content);
        String json = serializeContent(content);
        compoundTag.setString(nameSpaceKey, json);
    }

    /**
     * Stores custom NBT content for entity
     * @param readWriteNBT The ReadWriteNBT tag you want to store in, usually use your plugins one
     * @see LuaCore#getCompountName()
     * @param content The value of the data you want to store
     * @param nameSpaceKey where you should get the data from later
     */
    public static void storeEntityNBTContent(ReadWriteNBT readWriteNBT, Object content, String nameSpaceKey) {
        LuaMessageUtils.json_dump(content);
        String json = serializeContent(content);
        readWriteNBT.setString(nameSpaceKey, json);
    }

    /**
     * Stores custom NBT content for entity
     * @param readWriteNBT The ReadWriteNBT tag you want to store in, usually use your plugins one
     * @see LuaCore#getCompountName()
     * @param content The value of the data you want to store
     * @param nameSpaceKey where you should get the data from later
     * @param type The object type you want to populate
     */
    public static void storeEntityNBTContent(ReadWriteNBT readWriteNBT, Object content, String nameSpaceKey, Type type) {
        LuaMessageUtils.json_dump(content);
        String json = serializeContent(content, type);
        readWriteNBT.setString(nameSpaceKey, json);
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

    /**
     * Gets custom NBT data
     * @param cls The class you want to map to
     * @param nameSpaceKey The key you set in {@link #storeEntityNBTContent(ReadWriteNBT, Object, String)} 
     * @param readWriteNBT The NBTCompound you are storing the custom NBT in
     * @return Fully mapped class
     * @param <T> The class type
     */
    public static <T> T getEntityNBTContent(Class<T> cls, String nameSpaceKey, ReadWriteNBT readWriteNBT) {
        if (readWriteNBT.hasTag(nameSpaceKey)) {
            String json = readWriteNBT.getString(nameSpaceKey);
            return deserializeContent(cls, json);
        }
        LuaMessageUtils.verboseError(nameSpaceKey + " Does not exist");
        return null;
    }

    /**
     * Gets custom NBT data
     * @param type The object type you want to map to
     * @param nameSpaceKey The key you set in {@link #storeEntityNBTContent(ReadWriteNBT, Object, String, Type)}
     * @param readWriteNBT The NBTCompound you are storing the custom NBT in
     * @return Fully mapped class
     * @param <T> The class type
     */
    public static <T> T getEntityNBTContent(Type type, String nameSpaceKey, ReadWriteNBT readWriteNBT) {
        if (readWriteNBT.hasTag(nameSpaceKey)) {
            String json = readWriteNBT.getString(nameSpaceKey);
            LuaMessageUtils.verboseMessage("getEntityContent " + json);
            return deserializeContent(type, json);
        }
        LuaMessageUtils.verboseError(nameSpaceKey + " Does not exist");
        return null;
    }

}
