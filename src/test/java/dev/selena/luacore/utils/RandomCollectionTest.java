package dev.selena.luacore.utils;

import dev.selena.test.utils.MockUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomCollectionTest {


    public static RandomCollection<ItemStack> items;

    @BeforeAll
    public static void setUpClass() {
        MockUtils.setUp();
        items = new RandomCollection<>();
    }

    @AfterAll
    static void tearDown() {
        MockBukkit.unmock();
        MockUtils.tearDownStaticMocks();
    }

    @Test
    void add() {
        assertNotNull(items.add(5, new ItemStack(Material.STONE)));
        items.add(10, new ItemStack(Material.REDSTONE));
    }

    @Test
    void getRandom() {
        assertInstanceOf(ItemStack.class, items.getRandom());
    }

}