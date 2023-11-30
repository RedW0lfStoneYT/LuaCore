package dev.selena.luacore.utils.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
     * Used for Custom enchants plugin but can be used for other plugins
     * @param item The ItemStack you want to check
     * @param argument The string argument of the application type
     * @return True if you can apply the string related request
     */
    public static boolean canApply(ItemStack item, String argument) {
        return switch (argument.toLowerCase()) {
            case "any" -> isTool(item) || isArmor(item) || isWeapon(item) || isBow(item) || isCrossBow(item);
            // armor
            case "armor" -> isArmor(item);
            case "helmet" -> isHelmet(item);
            case "chestplate", "chest plate" -> isChestplate(item);
            case "leggings" -> isLeggings(item);
            case "boots" -> isBoots(item);
            // weapons
            case "weapon" -> isWeapon(item);
            case "sword" -> isSword(item);
            case "axe" -> isAxe(item);
            case "bow" -> isBow(item);
            case "crossbow" -> isCrossBow(item);
            // tools
            case "tool", "tools" -> isTool(item);
            case "shovel" -> isShovel(item);
            case "pickaxe" -> isPickaxe(item);
            case "hoe" -> isHoe(item);
            default -> false;
        };
    }

    /**
     * Used for removing x items from a players inventory or some form of stack
     * @param item The item you want to remove from
     * @param amount The amount of items you want to remove
     * @return if the value is less than or equal to 0 it will return an Air ItemStack
     */
    public static ItemStack deleteItem(ItemStack item, int amount) {
        int newAmount = item.getAmount() - amount;
        if (newAmount > 0) {
            item.setAmount(newAmount);
            return item;
        }
        return new ItemStack(Material.AIR);


    }

}
