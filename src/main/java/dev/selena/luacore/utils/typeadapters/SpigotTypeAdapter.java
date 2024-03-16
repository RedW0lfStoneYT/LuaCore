package dev.selena.luacore.utils.typeadapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

public class SpigotTypeAdapter<T extends ConfigurationSerializable> implements JsonSerializer<T>, JsonDeserializer<T> {

    private final Type typeToken = new TypeToken<Map<String, Object>>() {}.getType();
    private final Function<Map<String, Object>, T> spigotProxy;

    public SpigotTypeAdapter(Function<Map<String, Object>, T> spigotProxy) {
        this.spigotProxy = spigotProxy;
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.serialize());
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<String, Object> data = context.deserialize(json, typeToken);
        return spigotProxy.apply(data);
    }

}
