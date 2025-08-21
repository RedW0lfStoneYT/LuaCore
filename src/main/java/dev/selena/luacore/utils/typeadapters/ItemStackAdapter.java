package dev.selena.luacore.utils.typeadapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dev.selena.luacore.utils.nbt.NBTUtils;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Kinda works but not fully its a bit iffy.
 */
public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {


    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<String, Object> map = NBTUtils.getGson().fromJson(json, TypeToken.get(Map.class).getType());
//        map.putIfAbsent("v", Bukkit.getUnsafe().getDataVersion());
        map.remove("v");

        if(map.containsKey("meta")) {
            Map<String, Object> meta = (Map<String, Object>) map.get("meta");
            ItemMeta deserializedMeta = deserializeItemMeta(meta);
            Map<String, Double> enchants = (Map<String, Double>) meta.get("enchants");
            map.remove("meta");
            ItemStack is = ItemStack.deserialize(map);
            is.setItemMeta(deserializedMeta);
            for (String enchantName : enchants.keySet()) {
                LuaMessageUtils.verboseMessage("Enchant name: " + enchantName);
                is.addUnsafeEnchantment(Enchantment.getByName(enchantName), Integer.parseInt(String.valueOf(Math.round(enchants.get(enchantName)))));
            }
            return is;
        } else {
            LuaMessageUtils.verboseMessage("ItemStack without meta: " + map);
            return ItemStack.deserialize(map);
        }
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        Map<String, Object> map = src.serialize();
        LuaMessageUtils.json_dump("ItemStack map", map);
//        map.putIfAbsent("v", Bukkit.getUnsafe().getDataVersion());
        if(src.hasItemMeta()) {
            LuaMessageUtils.json_dump("Item Meta Json:", src.getItemMeta().serialize());
            map.put("meta", src.getItemMeta().serialize());
        }
        return NBTUtils.getGson().toJsonTree(map);
    }


    public static @Nullable Map<? extends @NonNull String, @NonNull ?> serializeItemMeta(
            @Nullable ItemMeta itemMeta
    ) {
        // Check if ItemMeta is empty (equivalent to ItemStack#hasItemMeta):
        if (!Bukkit.getItemFactory().equals(itemMeta, null)) {
            assert itemMeta != null;
            return itemMeta.serialize(); // Assert: Not null or empty
        } else {
            return null;
        }
    }

    public static @Nullable ItemMeta deserializeItemMeta(@Nullable Map<String, Object> itemMetaData) {
        if (itemMetaData == null) return null;

        // Get the class CraftBukkit internally uses for the deserialization:
        Class<? extends ConfigurationSerializable> serializableItemMetaClass = ConfigurationSerialization.getClassByAlias("ItemMeta");
        if (serializableItemMetaClass == null) {
            throw new IllegalStateException(
                    "Missing ItemMeta ConfigurationSerializable class for key/alias 'ItemMeta'!"
            );
        }

        // Can be null:
        return (ItemMeta) ConfigurationSerialization.deserializeObject(itemMetaData, serializableItemMetaClass);
    }
}
