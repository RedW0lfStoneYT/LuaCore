package dev.selena.luacore.utils.typeadapters;

import com.google.gson.*;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * A Gson TypeAdapter for serializing and deserializing PotionEffect objects.
 * This adapter converts PotionEffect objects to JSON and vice versa.
 */
public class PotionEffectAdapter implements JsonSerializer<PotionEffect>, JsonDeserializer<PotionEffect> {

    @Override
    public JsonElement serialize(PotionEffect src, Type typeOfSrc, JsonSerializationContext context) {
        Map<String, Object> map = src.serialize(); // Bukkit already has serialize()
        return context.serialize(map);
    }

    @Override
    public PotionEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        int duration = jsonObject.get("duration").getAsNumber().intValue();
        int amplifier = jsonObject.get("amplifier").getAsNumber().intValue();
        String effectName = jsonObject.get("effect").getAsString();
        PotionEffectType type = PotionEffectType.getByName(effectName);

        boolean ambient = jsonObject.has("ambient") && jsonObject.get("ambient").getAsBoolean();
        boolean particles = jsonObject.has("has-particles") && jsonObject.get("has-particles").getAsBoolean();
        boolean icon = jsonObject.has("has-icon") && jsonObject.get("has-icon").getAsBoolean();

        return new PotionEffect(type, duration, amplifier, ambient, particles, icon);
    }
}
