package dev.selena.luacore.utils;

import dev.selena.test.utils.MockUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomCollectionTest {


    public static RandomCollection<ItemStack> items;

    @BeforeAll
    public static void setUpClass() {
        MockUtils.setUp();
        items = new RandomCollection<>();
    }


    @Test
    void add() {
        assertNotNull(items.add(5, new ItemStack(Material.STONE))); // Adds stone with a lower chance of getting chosen
        items.add(10, new ItemStack(Material.REDSTONE)); // Adds Redstone with twice the chance of getting chosen
    }

    @Test
    void getRandom() {
        assertInstanceOf(ItemStack.class, items.getRandom());
    }

}