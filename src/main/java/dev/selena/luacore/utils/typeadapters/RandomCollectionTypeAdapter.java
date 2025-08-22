package dev.selena.luacore.utils.typeadapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.selena.luacore.utils.RandomCollection;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * A custom TypeAdapter for serializing and deserializing {@link RandomCollection} objects.
 * This adapter handles the conversion of {@link RandomCollection} to and from JSON format.
 *
 * @param <E> the type of elements in the {@link RandomCollection}
 */
public class RandomCollectionTypeAdapter<E> extends TypeAdapter<RandomCollection<E>> {
    private final TypeAdapter<E> elementAdapter;

    public RandomCollectionTypeAdapter(Gson gson, Type elementType) {
        this.elementAdapter =
                (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
    }

    @Override
    public void write(JsonWriter writer, RandomCollection<E> value) throws IOException {
        writer.beginArray();
        for (Map.Entry<E, Double> entry : value.getRawMap().entrySet()) {
            writer.beginObject();
            writer.name("element");
            elementAdapter.write(writer, entry.getKey());
            writer.name("weight").value(entry.getValue());
            writer.endObject();
        }
        writer.endArray();
    }

    @Override
    public RandomCollection<E> read(JsonReader in) throws IOException {
        RandomCollection<E> collection = new RandomCollection<>();
        in.beginArray();
        while (in.hasNext()) {
            in.beginObject();
            E element = null;
            Double weight = null;
            while (in.hasNext()) {
                String name = in.nextName();
                if (name.equals("element")) {
                    element = elementAdapter.read(in);
                } else if (name.equals("weight")) {
                    weight = in.nextDouble();
                } else {
                    in.skipValue();
                }
            }
            if (element != null && weight != null) {
                collection.add(weight, element);
            }
            in.endObject();
        }
        in.endArray();
        return collection;
    }
}
