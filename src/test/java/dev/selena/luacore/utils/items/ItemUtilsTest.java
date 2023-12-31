package dev.selena.luacore.utils.items;

import dev.selena.test.utils.MockUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemUtilsTest {


    @BeforeAll
    static void setUp() {
        MockUtils.setUp().mockLogger();
    }

    @Test
    void isWeapon_Sword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        assertTrue(ItemUtils.isWeapon(item));
    }
    @Test
    void isWeapon_Axe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        assertTrue(ItemUtils.isWeapon(item));
    }
    @Test
    void isWeapon_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK);
        assertFalse(ItemUtils.isWeapon(item));
    }

    @Test
    void isSword_Sword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        assertTrue(ItemUtils.isSword(item));
    }

    @Test
    void isSword_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK);
        assertFalse(ItemUtils.isSword(item));
    }

    @Test
    void isAxe_Axe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        assertTrue(ItemUtils.isAxe(item));
    }
    @Test
    void isAxe_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK);
        assertFalse(ItemUtils.isAxe(item));
    }

    @Test
    void isBow_Bow() {
        ItemStack item = new ItemStack(Material.BOW);
        assertTrue(ItemUtils.isBow(item));
    }

    @Test
    void isBow_Fail() {
        ItemStack item = new ItemStack(Material.CROSSBOW);
        assertFalse(ItemUtils.isBow(item));
    }

    @Test
    void isCrossBow_CrossBow() {
        ItemStack item = new ItemStack(Material.CROSSBOW);
        assertTrue(ItemUtils.isCrossBow(item));
    }

    @Test
    void isCrossBow_Fail() {
        ItemStack item = new ItemStack(Material.BOW);
        assertFalse(ItemUtils.isCrossBow(item));
    }

    @Test
    void isArmor_Helmet() {
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
        assertTrue(ItemUtils.isArmor(item));
    }

    @Test
    void isArmor_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK);
        assertFalse(ItemUtils.isArmor(item));
    }

    @Test
    void isHelmet_Helmet() {
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
        assertTrue(ItemUtils.isHelmet(item));
    }

    @Test
    void isHelmet_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK);
        assertFalse(ItemUtils.isHelmet(item));
    }

    @Test
    void isChestplate_Chestplace() {
        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        assertTrue(ItemUtils.isChestplate(item));
    }

    @Test
    void isChestplate_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        assertFalse(ItemUtils.isChestplate(item));
    }

    @Test
    void isLeggings_Leggings() {
        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
        assertTrue(ItemUtils.isLeggings(item));
    }

    @Test
    void isLeggings_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        assertFalse(ItemUtils.isLeggings(item));
    }

    @Test
    void isBoots_Boots() {
        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
        assertTrue(ItemUtils.isBoots(item));
    }

    @Test
    void isBoots_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        assertFalse(ItemUtils.isBoots(item));
    }

    @Test
    void isTool_Axe() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        assertTrue(ItemUtils.isTool(item));
    }

    @Test
    void isTool_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        assertFalse(ItemUtils.isTool(item));
    }

    @Test
    void isPickaxe_Pickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        assertTrue(ItemUtils.isPickaxe(item));
    }

    @Test
    void isPickaxe_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        assertFalse(ItemUtils.isPickaxe(item));
    }

    @Test
    void isShovel_Shovel() {
        ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL);
        assertTrue(ItemUtils.isShovel(item));
    }

    @Test
    void isShovel_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        assertFalse(ItemUtils.isShovel(item));
    }

    @Test
    void isHoe_Hoe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        assertTrue(ItemUtils.isHoe(item));
    }

    @Test
    void isHoe_Fail() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        assertFalse(ItemUtils.isHoe(item));
    }


    @Test
    void deleteItem_DeletedAll() {
        ItemStack item = new ItemStack(Material.STONE, 64);
        item = ItemUtils.deleteItem(item, 64);
        assertTrue(item.getType().isEmpty());
    }

    @Test
    void deleteItem_DeletedSome() {
        ItemStack item = new ItemStack(Material.STONE, 64);
        item = ItemUtils.deleteItem(item, 32);
        assertFalse(item.getType().isEmpty());
        assertEquals(item.getAmount(), 32);
    }

    @Test
    void deleteItem_FailedBelowZero() {
        ItemStack item = new ItemStack(Material.STONE, 64);
        ItemStack nItem = ItemUtils.deleteItem(item, -32);
        assertEquals(nItem, item);
    }

    @Test
    void deleteItem_FailedZero() {
        ItemStack item = new ItemStack(Material.STONE, 64);
        ItemStack nItem = ItemUtils.deleteItem(item, 0);
        assertEquals(nItem, item);
    }

    @Test
    void isLeather_Success() {
        ItemStack item = new ItemStack(Material.LEATHER_HORSE_ARMOR);
        assertTrue(ItemUtils.isLeather(item));
    }

    @Test
    void isLeather_Fail() {
        ItemStack item = new ItemStack(Material.STONE);
        assertFalse(ItemUtils.isLeather(item));
    }


}