package dev.selena.luacore.utils;

import dev.selena.test.utils.MockUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
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