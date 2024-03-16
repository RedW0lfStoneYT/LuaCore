package dev.selena.luacore.utils;

import dev.selena.luacore.utils.text.LuaMessageUtils;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Used for weighted random item/object generation
 * For examples check the <a href="https://github.com/RedW0lfStoneYT/LuaCore/wiki/Using-the-built-in-weighted-random-for-rewards">wiki</a>
 * @param <E>
 * The reward of any type
 */
public class RandomCollection<E> {
    protected final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private transient final Random random;
    private final long randomSeed;
    private double total = 0;

    /**
     * Used to initialize the class using a new instance of random
     */
    public RandomCollection() {
        this(new Random());
    }

    /**
     * Used to initialize the class using a fixed instance of random (Note the seed will be the next long generated this is for serialization reason)
     * @see RandomCollection#RandomCollection(long)
     * @param random The random instance you want to parse in
     */
    public RandomCollection(Random random) {
        this.randomSeed = random.nextLong();
        random.setSeed(randomSeed);
        this.random = random;
    }

    /**
     * Used to initialize the class using a new instance of random. You should use this over {@link RandomCollection#RandomCollection(Random)}
     * @param seed The seed you want to use
     */
    public RandomCollection(long seed) {
        this.randomSeed = seed;
        random = new Random(seed);
    }

    /**
     * Used for adding a weight
     * @param weight The weight of the item added to the map
     * @param result The item itself
     * @return This instance for further usage
     */
    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        LuaMessageUtils.verboseMessage("New total weight value: " + total);
        return this;
    }

    /**
     * Gets a weighted random value
     * @return The random value from the map
     */
    public E getRandom() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    /**
     * Used for appending the collection map with another existing map
     * @param results The map of results/loot you want to append to the existing collection
     */
    public void addAll(Map<E, Double> results) {
        for (E result : results.keySet()) {
            total += results.get(result);
            map.put(total, result);
        }
    }

    /**
     * Used for checking if the collection is empty
     * @return True if empty
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }


    public void forEach(Function<E, E> function) {
        for (double weight : map.keySet()) {
            E entity = map.get(weight);
            map.put(weight, function.apply(entity));
        }
    }

    public <T> RandomCollection<T> cloneTo(Class<T> elementType, Function<E, T> function) {
        RandomCollection<T> newCollection = new RandomCollection<>();
        for (double weight : map.keySet()) {
            E oldEntry = map.get(weight);
            T newEntry = function.apply(oldEntry);
            newCollection.map.put(weight, newEntry);
        }
        return newCollection;
    }
}