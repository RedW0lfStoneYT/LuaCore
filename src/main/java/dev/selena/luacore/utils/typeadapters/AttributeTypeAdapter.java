package dev.selena.luacore.utils.typeadapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;

import java.io.IOException;

/**
 * Type adapter for serializing and deserializing Bukkit's Attribute objects.
 */
public class AttributeTypeAdapter extends TypeAdapter<Attribute> {

    @Override
    public void write(JsonWriter out, Attribute attribute) throws IOException {
        if (attribute == null) {
            out.nullValue();
            return;
        }
        NamespacedKey key = RegistryAccess.registryAccess().getRegistry(RegistryKey.ATTRIBUTE).getKey(attribute);
        String serialized = key != null ? key.toString() : attribute.getKey().getKey();
        out.value(serialized);
    }

    @Override
    public Attribute read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String raw = in.nextString();
        NamespacedKey parsed = NamespacedKey.fromString(raw);
        if (parsed == null) {
            throw new IOException("Invalid Attribute key: " + raw);
        }
        Attribute attribute = RegistryAccess.registryAccess().getRegistry(RegistryKey.ATTRIBUTE).get(parsed);
        if (attribute == null) {
            throw new IOException("Unknown Attribute: " + raw);
        }
        return attribute;
    }
}