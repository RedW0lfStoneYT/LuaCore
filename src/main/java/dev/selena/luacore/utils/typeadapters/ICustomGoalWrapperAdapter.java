package dev.selena.luacore.utils.typeadapters;

import com.google.gson.*;
import dev.selena.luacore.nms.ICustomGoalWrapper;

import java.lang.reflect.Type;

/**
 * A Gson TypeAdapter for serializing and deserializing {@link ICustomGoalWrapper} instances.
 * This adapter handles the serialization of the concrete class name to allow for proper deserialization.
 */
public class ICustomGoalWrapperAdapter implements JsonSerializer<ICustomGoalWrapper>, JsonDeserializer<ICustomGoalWrapper> {
    @Override
    public JsonElement serialize(ICustomGoalWrapper src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = context.serialize(src).getAsJsonObject(); // serialize normally
        obj.addProperty("class", src.getClass().getName());          // include concrete class
        return obj;
    }

    @Override
    public ICustomGoalWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String className = obj.get("class").getAsString();

        try {
            Class<?> clazz = Class.forName(className);
            return context.deserialize(obj, clazz); // deserialize to the concrete class
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown ICustomGoalWrapper class: " + className, e);
        }
    }
}
