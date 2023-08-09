package dev.selena.luacore.utils.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static boolean isWeapon(ItemStack item) {
        return isSword(item)
                || isAxe(item);
    }

    public static boolean isSword(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("sword");
    }

    public static boolean isAxe(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("axe");
    }

    public static boolean isBow(ItemStack item) {
        return (item.getType() == Material.BOW);
    }

    public static boolean isCrossBow(ItemStack item) {
        return (item.getType() == Material.CROSSBOW);
    }

    public static boolean isArmor(ItemStack item) {
        return isHelmet(item)
                || isChestplate(item)
                || isLeggings(item)
                || isBoots(item);
    }

    public static boolean isHelmet(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("helmet")
                || name.endsWith("cap");
    }

    public static boolean isChestplate(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("chestplate")
                || name.endsWith("tunic");
    }

    public static boolean isLeggings(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("pants")
                || name.endsWith("leggings");
    }

    public static boolean isBoots(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("boots");
    }

    public static boolean isTool(ItemStack item) {
        return isAxe(item)
                || isPickaxe(item)
                || isShovel(item)
                || isHoe(item);
    }

    public static boolean isPickaxe(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("pickaxe");
    }

    public static boolean isShovel(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("shovel");
    }

    public static boolean isHoe(ItemStack item) {
        String name = getLowerName(item);
        return name.endsWith("hoe");
    }

    private static String getLowerName(ItemStack item) {
        return item.getType().name().toLowerCase();
    }

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

    public static ItemStack deleteItem(ItemStack item, int amount) {
        int newAmount = item.getAmount() - amount;
        if (newAmount > 0) {
            item.setAmount(newAmount);
            return item;
        }
        return new ItemStack(Material.AIR);


    }

}
