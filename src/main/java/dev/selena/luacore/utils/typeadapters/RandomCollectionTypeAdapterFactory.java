package dev.selena.luacore.utils.typeadapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import dev.selena.luacore.utils.RandomCollection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Factory for creating TypeAdapters for {@link RandomCollection} types.
 */
public class RandomCollectionTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!RandomCollection.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        Type elementType = ((ParameterizedType) type.getType()).getActualTypeArguments()[0];
        @SuppressWarnings("unchecked")
        TypeAdapter<T> adapter = (TypeAdapter<T>) new RandomCollectionTypeAdapter<>(gson, elementType);
        return adapter;
    }
}