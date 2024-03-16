package dev.selena.luacore.utils.lua;

import dev.selena.luacore.LuaCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A useful tool for script assistance
 */
public class ScriptHelper {


    /**
     * applies a potion effect to the player
     * Note: if you are running the script on a different thread
     * you will need to use applyPotionEffectAsync
     * @see ScriptHelper#applyPotionEffectAsync(Player, PotionEffectType, int, int)
     * @param player The player you want to effect
     * @param effectType The potion effect type
     * @param duration Duration of the effect (-1 for infinite)
     * @param amplifier The amplifier of the effect
     */
    public void applyPotionEffect(Player player, PotionEffectType effectType, int duration, int amplifier) {
        PotionEffect effect = new PotionEffect(effectType, duration, amplifier);

        player.addPotionEffect(effect);
    }

    /**
     * Used for applying potion effects when the Lua script
     * isn't on the main thread
     * @param player The player you want to effect
     * @param effectType The potion effect type
     * @param duration The duration of the effect
     * @param amplifier The amplifier of the effect
     */
    public void applyPotionEffectAsync(Player player, PotionEffectType effectType, int duration, int amplifier) {
        Bukkit.getScheduler().runTask(LuaCore.getPlugin(), () -> {
            applyPotionEffect(player, effectType, duration, amplifier);
        });
    }

    /**
     * Used for when you cant seem to bind the class in lua
     * @param player The player you want to effect
     * @param effectType The String Name of the potion effect
     * @param duration The duration
     * @param amplifier The amplifier
     */
    public void applyPotionEffectAsync(Player player, String effectType, int duration, int amplifier) {
        Bukkit.getScheduler().runTask(LuaCore.getPlugin(), () -> {
            applyPotionEffect(player, PotionEffectType.getByName(effectType), duration, amplifier);
        });
    }

    /**
     * Used for getting an ItemStack in lua
     * @param type The Material type
     * @param amount The ItemStack size
     * @return The requested ItemStack
     */
    public ItemStack getItem(Material type, int amount) {
        return new ItemStack(type, amount);
    }

    /**
     * Used for getting the location of a block
     * @param block The block you want the location of
     * @return Location of the block
     */
    public Location getBlockLocation(Block block) {
        return block.getLocation();
    }

    /**
     * Used for getting the Block at a location
     * @param loc The location you want the block of
     * @return The block at the requested location
     */
    public Block getBlockAtLocation(Location loc) {
        return loc.getBlock();
    }

    /**
     * Used for dropping an ItemStack at a location in the world
     * @param item The Item you want to drop
     * @param loc The location you want to drop it
     */
    public void dropItem(ItemStack item, Location loc) {
        loc.getWorld().dropItem(loc, item);
    }

    /**
     * User for giving a player an ItemStack
     * @param player The player you want to give the Item to
     * @param item The ItemStack you want to give
     */
    public void giveItem(Player player, ItemStack item) {
        player.getInventory().addItem(item);
    }

    /**
     * Used for getting a LuaValue from a script
     * @param path The script name and path
     * @param constantName The variable you want to get
     * @return Returns a LuaValue object from the script
     * @see ScriptHelper#getStringFromLuaValue(LuaValue)
     */
    public static LuaValue extractConstantValue(String path, String constantName) {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile(LuaCore.getPlugin().getDataFolder() + "/" + path + ".lua");
        chunk.call();
        return globals.get(constantName);
    }

    /**
     * Used for converting an Int to roman numerals
     * @param num The number you want to convert
     * @return The roman numeral value for the int
     */
    public static String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanLetters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num = num - values[i];
                roman.append(romanLetters[i]);
            }
        }
        return roman.toString();
    }

    /**
     * Used for getting a string from a LuaValue
     * @param content The LuaValue you want to convert to a string
     * @return String from the LuaValue
     * @see ScriptHelper#extractConstantValue(String, String)
     */
    public static String getStringFromLuaValue(LuaValue content) {
        return content.isnil() ? null : content.tojstring();
    }

    /**
     * Used for getting a string array from a LuaValue
     * @param content The LuaValue you want to convert
     * @return String[] of the LuaValue parsed in
     * @see ScriptHelper#extractConstantValue(String, String)
     */
    public static String[] getStringArrayFromLuaValue(LuaValue content) {
        if (content.istable()) {
            LuaTable luaTable = (LuaTable) content;
            int length = luaTable.length();
            String[] stringArray = new String[length];

            for (int i = 1; i <= length; i++) {
                stringArray[i - 1] = luaTable.get(i).tojstring();
            }

            return stringArray;
        }
        return null;
    }

    /**
     * Used for getting an Integer from a LuaValue
     * @param content The LuaValue you want to convert
     * @return int of the LuaValue parsed in
     * @see ScriptHelper#extractConstantValue(String, String)
     */
    public static int getIntFromLuaValue(LuaValue content) {
        return content.isnil() ? 0 : content.toint();
    }

    /**
     * Used for checking of a potion effect is a debuff
     * @param effectType The potion effect you want to check
     * @return True if the effect is a debuff
     */
    public static boolean isDebuff(PotionEffectType effectType) {
        return effectType == PotionEffectType.BLINDNESS
                || effectType == PotionEffectType.SLOW
                || effectType == PotionEffectType.SLOW_DIGGING
                || effectType == PotionEffectType.CONFUSION
                || effectType == PotionEffectType.SLOW
                || effectType == PotionEffectType.HUNGER
                || effectType == PotionEffectType.WEAKNESS
                || effectType == PotionEffectType.POISON
                || effectType == PotionEffectType.WITHER
                || effectType == PotionEffectType.UNLUCK
                || effectType == PotionEffectType.BAD_OMEN
                || effectType == PotionEffectType.DARKNESS;
    }

    /**
     * Checks if the player has a debuff potion effect
     * @param player The player you want to check
     * @return True if the player has a debuff
     */
    public static boolean hasDebuff(Player player) {
        AtomicBoolean has = new AtomicBoolean(false);
        player.getActivePotionEffects().forEach(effect -> {
            if (isDebuff(effect.getType())) {
                has.set(true);
            }
        });
        return has.get();
    }

    /**
     * Used for checking if the player has a potion effect
     * @param player The player you want to check
     * @param potionEffect The name of the potion effect
     * @return True if the player has the effect (False if the effect doesn't exist)
     */
    public static boolean hasPotionEffect(Player player, String potionEffect) {
        PotionEffectType effect = PotionEffectType.getByName(potionEffect);
        if (effect == null)
            return false;
        return player.hasPotionEffect(effect);
    }

    /**
     * Removes all debuffs from the player
     * @param player The player you want to remove the debuffs from
     */
    public static void removeDebuffs(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            PotionEffectType type = effect.getType();
            if (isDebuff(type))
                player.removePotionEffect(type);
        }
    }

    /**
     * Used for removing potion effects from a player
     * @param player The player you want to remove the effects from
     * @param effects The potion effects you want to remove
     */
    public static void removeEffects(Player player, PotionEffectType ... effects ) {
        for (PotionEffectType type : effects) {
            if (player.hasPotionEffect(type))
                player.removePotionEffect(type);

        }
    }

    /**
     * Used for removing potion effects when on a different thread
     * @param player The player you want to remove potion effects from
     * @param effects The potion effect you want to remove from the player
     */
    public static void removeEffectsAsync(Player player, PotionEffectType effects) {
        Bukkit.getScheduler().runTask(LuaCore.getPlugin(), () -> {
            removeEffects(player, effects);

        });
    }

    /**
     * Used for duplicating the array x times
     * Example of use, I had a mask plugin and one of the maks gave 2x the rewards from fishing
     * Using this method you can double the rewards easily
     * @param array The array you want to duplicate
     * @param amount The amount of duplicates that should go into the array
     * @return The new array
     */
    public static Object[] multiplyArray(Object[] array, int amount) {
        Object[] multipliedArray = new String[array.length * amount];
        for (int i = 0; i < multipliedArray.length; i++) {
            multipliedArray[i] = array[i % array.length];
        }
        return multipliedArray;
    }


}
