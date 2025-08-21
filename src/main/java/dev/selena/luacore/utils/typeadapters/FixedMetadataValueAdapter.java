package dev.selena.luacore.utils.typeadapters;

import com.google.gson.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Type;
/**
 * A Gson TypeAdapter for serializing and deserializing FixedMetadataValue objects.
 * This adapter handles the serialization of the internal value and the owning plugin name.
 * Not currently used and instead uses the {@link MetadataMapAdapter} for serialization.
 */
public class FixedMetadataValueAdapter implements JsonSerializer<FixedMetadataValue>, JsonDeserializer<FixedMetadataValue> {
    private final Plugin plugin;

    public FixedMetadataValueAdapter(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public JsonElement serialize(FixedMetadataValue src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("internalValue", String.valueOf(src.value()));
        if (src.getOwningPlugin() != null)
            obj.addProperty("plugin", src.getOwningPlugin().getName());
        return obj;
    }

    @Override
    public FixedMetadataValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Object value = obj.get("internalValue").getAsString();
        return new FixedMetadataValue(plugin, value);
    }
}
