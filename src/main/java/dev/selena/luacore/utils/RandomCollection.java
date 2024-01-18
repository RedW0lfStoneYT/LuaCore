package dev.selena.luacore.utils;

import dev.selena.luacore.utils.text.LuaMessageUtils;

import java.util.*;

/**
 * Used for weighted random item/object generation
 * For examples check the <a href="https://github.com/RedW0lfStoneYT/LuaCore/wiki/Using-the-built-in-weighted-random-for-rewards">wiki</a>
 * @param <E>
 * The reward of any type
 */
public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    /**
     * Used to initialize the class using a new instance of random
     */
    public RandomCollection() {
        this(new Random());
    }

    /**
     * Used to initialize the class using a fixed instance of random
     * @param random The random instance you want to parse in
     */
    public RandomCollection(Random random) {
        this.random = random;
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
}