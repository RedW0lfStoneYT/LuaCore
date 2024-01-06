package dev.selena.luacore.utils.items;

import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Useful item utility class
 */
public class ItemUtils {

    /**
     * Used for seeing if an ItemStack is a weapon
     * @param item The itemStack you want to check
     * @return True if the item is a sword or axe
     * @see ItemUtils#isSword(ItemStack)
     * @see ItemUtils#isAxe(ItemStack)
     */
    public static boolean isWeapon(ItemStack item) {
        return isSword(item)
                || isAxe(item);
    }

    /**
     * Checks if the ItemStack in question is a sword
     * @param item The itemStack you want to check
     * @return True if the item is a sword
     */
    public static boolean isSword(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("sword");
    }

    /**
     * Checks if the ItemStack is an axe
     * @param item The itemStack you want to check
     * @return True if the item is an axe
     */
    public static boolean isAxe(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("axe");
    }

    /**
     * Checks if the ItemStack in question is a bow
     * @param item The itemStack you want to check
     * @return True if the item is a bow
     */
    public static boolean isBow(ItemStack item) {
        return (item.getType() == Material.BOW);
    }

    /**
     * Checks if the ItemStack in question is a cross bow
     * @param item The itemStack you want to check
     * @return True if the item is a cross bow
     */
    public static boolean isCrossBow(ItemStack item) {
        return (item.getType() == Material.CROSSBOW);
    }

    /**
     * Checks if the ItemStack is armor
     * @param item The itemStack you want to check
     * @return True if the item is a helmet, chestplate, leggings, or boots
     * @see ItemUtils#isHelmet(ItemStack)
     * @see ItemUtils#isChestplate(ItemStack)
     * @see ItemUtils#isLeggings(ItemStack)
     * @see ItemUtils#isBoots(ItemStack)
     */
    public static boolean isArmor(ItemStack item) {
        return isHelmet(item)
                || isChestplate(item)
                || isLeggings(item)
                || isBoots(item);
    }

    /**
     * Checks if the ItemStack is a helmet
     * @param item The itemStack you want to check
     * @return True if the item is a helmet
     */
    public static boolean isHelmet(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("helmet")
                || name.endsWith("cap");
    }

    /**
     * Checks if the ItemStack is a chestplate
     * @param item The itemStack you want to check
     * @return True if the item is a chestplate
     */
    public static boolean isChestplate(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("chestplate")
                || name.endsWith("tunic");
    }

    /**
     * Checks if the ItemStack is leggings
     * @param item The itemStack you want to check
     * @return True if the item is leggings
     */
    public static boolean isLeggings(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("pants")
                || name.endsWith("leggings");
    }

    /**
     * Checks if the ItemStack is boots
     * @param item The itemStack you want to check
     * @return True if the item is boots
     */
    public static boolean isBoots(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("boots");
    }

    /**
     * Used for checking if an item is leather for dying
     * @param item The item you want to check
     * @return True if leather armor or horse armor
     */
    public static boolean isLeather(ItemStack item) {

        if (!isArmor(item) && !item.getType().equals(Material.LEATHER_HORSE_ARMOR))
            return false;
        return getLowerName(item).startsWith("leather");
    }

    /**
     * Checks if the ItemStack is a tool
     * @param item The itemStack you want to check
     * @return True if the item is an Axe, Pickaxe, Shovel, or Hoe
     * @see ItemUtils#isAxe(ItemStack)
     * @see ItemUtils#isPickaxe(ItemStack)
     * @see ItemUtils#isShovel(ItemStack)
     * @see ItemUtils#isHoe(ItemStack)
     */
    public static boolean isTool(ItemStack item) {
        return isAxe(item)
                || isPickaxe(item)
                || isShovel(item)
                || isHoe(item);
    }

    /**
     * Checks if the ItemStack is a pickaxe
     * @param item The itemStack you want to check
     * @return True if the item is a pickaxe
     */
    public static boolean isPickaxe(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("pickaxe");
    }

    /**
     * Checks if the ItemStack is a shovel
     * @param item The itemStack you want to check
     * @return True if the item is a shovel
     */
    public static boolean isShovel(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("shovel");
    }

    /**
     * Checks if the ItemStack is a hoe
     * @param item The itemStack you want to check
     * @return True if the item is a hoe
     */
    public static boolean isHoe(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("hoe");
    }

    private static String getLowerName(ItemStack item) {
        return item.getType().name().toLowerCase();
    }

    /**
     * Used for removing x items from a player inventory or some form of stack
     * @param item The item you want to remove from
     * @param amount The number of items you want to remove
     * @return if the value is less than or equal to 0, it will return an Air ItemStack
     */
    public static ItemStack deleteItem(ItemStack item, int amount) {
        if (amount <= 0) {
            LuaMessageUtils.consoleError("Cannot remove " + amount + " from item. The amount must be above 0");
            return item;
        }
        int newAmount = item.getAmount() - amount;
        if (newAmount > 0) {
            item.setAmount(newAmount);
            return item;
        }
        return new ItemStack(Material.AIR);


    }

}
