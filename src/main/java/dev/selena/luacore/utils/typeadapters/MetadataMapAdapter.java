package dev.selena.luacore.utils.typeadapters;

import com.google.gson.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * A Gson TypeAdapter for serializing and deserializing a Map of MetadataValue objects keyed with Strings.
 * This adapter handles the serialization of MetadataValue objects, including their class type
 * and the owning plugin, allowing for proper reconstruction during deserialization.
 */
public class MetadataMapAdapter implements JsonSerializer<Map<String, MetadataValue>>, JsonDeserializer<Map<String, MetadataValue>> {
    private final Plugin plugin;

    public MetadataMapAdapter(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public JsonElement serialize(Map<String, MetadataValue> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<String, MetadataValue> entry : src.entrySet()) {
            MetadataValue value = entry.getValue();
            JsonObject valueObj = new JsonObject();
            valueObj.addProperty("class", value.getClass().getName());
            valueObj.add("value", context.serialize(value.value()));
            if (value.getOwningPlugin() != null) {
                valueObj.addProperty("plugin", value.getOwningPlugin().getName());
            }
            obj.add(entry.getKey(), valueObj);
        }
        return obj;
    }

    @Override
    public Map<String, MetadataValue> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Map<String, MetadataValue> map = new HashMap<>();

        for (String key : obj.keySet()) {
            JsonObject valueObj = obj.getAsJsonObject(key);
            String className = valueObj.get("class").getAsString();
            JsonElement valueElement = valueObj.get("value");
            Object value = context.deserialize(valueElement, Object.class);

            MetadataValue metadataValue;
            switch (className) {
                case "org.bukkit.metadata.FixedMetadataValue":
                    metadataValue = new FixedMetadataValue(plugin, value);
                    break;
                case "org.bukkit.metadata.LazyMetadataValue":
                    // fallback: can't reconstruct fully
                    metadataValue = new FixedMetadataValue(plugin, value);
                    break;
                default:
                    // fallback for unknown/custom MetadataValue types
                    metadataValue = new FixedMetadataValue(plugin, value);
            }

            map.put(key, metadataValue);
        }

        return map;
    }
}