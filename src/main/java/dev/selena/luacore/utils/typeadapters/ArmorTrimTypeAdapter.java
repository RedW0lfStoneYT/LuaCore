package dev.selena.luacore.utils.typeadapters;

import com.google.gson.*;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.lang.reflect.Type;

/**
 * Type adapter for serializing and deserializing ArmorTrim objects.
 */
public class ArmorTrimTypeAdapter implements JsonSerializer<ArmorTrim>, JsonDeserializer<ArmorTrim> {

    @Override
    public JsonElement serialize(ArmorTrim src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        JsonObject inner = new JsonObject();
        NamespacedKey materialNamespace = RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_MATERIAL).getKey(src.getMaterial());
        NamespacedKey patternNamespace = RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_PATTERN).getKey(src.getPattern());
        inner.addProperty("TrimMaterial", materialNamespace == null ? null : materialNamespace.toString());
        inner.addProperty("TrimPattern",  patternNamespace == null ? null : patternNamespace.toString());
        JsonObject wrapper = new JsonObject();
        wrapper.add("ArmorTrim", inner);
        return wrapper;
    }

    @Override
    public ArmorTrim deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (json == null || json.isJsonNull()) {
            return null;
        }
        JsonObject wrapper = json.getAsJsonObject();
        JsonObject inner   = wrapper.getAsJsonObject("ArmorTrim");
        if (inner == null) {
            throw new JsonParseException("Missing 'ArmorTrim' object");
        }

        String materialKey = inner.has("TrimMaterial") ? inner.get("TrimMaterial").getAsString() : null;
        String patternKey  = inner.has("TrimPattern") ? inner.get("TrimPattern").getAsString() : null;

        if (materialKey == null || patternKey == null) {
            throw new JsonParseException("Both 'TrimMaterial' and 'TrimPattern' must be present");
        }

        NamespacedKey materialNamespace = NamespacedKey.fromString(materialKey);
        if (materialNamespace == null) {
            throw new JsonParseException("Unknown TrimMaterial: " + materialKey);
        }
        TrimMaterial material = RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_MATERIAL).get(materialNamespace);;
        if (material == null) {
            throw new JsonParseException("Unknown TrimMaterial: " + materialKey);
        }
        NamespacedKey patternNamespace = NamespacedKey.fromString(patternKey);
        if (patternNamespace == null) {
            throw new JsonParseException("Unknown TrimPattern: " + patternKey);
        }
        TrimPattern pattern = RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_PATTERN).get(patternNamespace);
        if (pattern == null) {
            throw new JsonParseException("Unknown TrimPattern: " + patternKey);
        }
        return new ArmorTrim(material, pattern);
    }
}


