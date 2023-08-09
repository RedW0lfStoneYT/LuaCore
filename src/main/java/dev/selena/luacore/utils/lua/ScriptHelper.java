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

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScriptHelper {


    private static final PotionEffectType[] debuffs = {
            PotionEffectType.BLINDNESS,
            PotionEffectType.CONFUSION,
            PotionEffectType.HUNGER,
            PotionEffectType.POISON,
            PotionEffectType.SLOW,
            PotionEffectType.SLOW_DIGGING,
            PotionEffectType.WEAKNESS,
            PotionEffectType.WITHER
    };

    public void applyPotionEffect(Player player, PotionEffectType effectType, int duration, int amplifier) {
        PotionEffect effect = new PotionEffect(effectType, duration, amplifier);

        player.addPotionEffect(effect);
    }

    public void applyPotionEffectAsync(Player player, PotionEffectType effectType, int duration, int amplifier) {
        Bukkit.getScheduler().runTask(LuaCore.getPlugin(), () -> {
            applyPotionEffect(player, effectType, duration, amplifier);
        });
    }

    public void applyPotionEffectAsync(Player player, String effectType, int duration, int amplifier) {
        Bukkit.getScheduler().runTask(LuaCore.getPlugin(), () -> {
            applyPotionEffect(player, PotionEffectType.getByName(effectType), duration, amplifier);
        });
    }

    public ItemStack getItem(Material type, int amount, short damage) {
        return new ItemStack(type, amount, damage);
    }
    public Location getBlockLocation(Block block) {
        return block.getLocation();
    }
    public Block getBlockAtLocation(Location loc) {
        return loc.getBlock();
    }
    public void dropItem(ItemStack item, Location loc) {
        loc.getWorld().dropItem(loc, item);
    }

    public void giveItem(Player player, ItemStack item) {
        player.getInventory().addItem(item);
    }




    public static LuaValue extractConstantValue(String path, String constantName) {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile(LuaCore.getPlugin().getDataFolder() + "/" + path + ".lua");
        chunk.call();
        return globals.get(constantName);
    }

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

    public static String getStringFromLuaValue(LuaValue content) {
        return content.isnil() ? null : content.tojstring();
    }

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

    public static int getIntFromLuaValue(LuaValue content) {
        return content.isnil() ? 0 : content.toint();
    }

    public static boolean isDebuff(PotionEffectType effectType) {

        return Arrays.asList(debuffs).contains(effectType);
    }

    public static boolean hasDebuff(Player player) {
        AtomicBoolean has = new AtomicBoolean(false);
        player.getActivePotionEffects().forEach(effect -> {
            if (isDebuff(effect.getType())) {
                has.set(true);
            }
        });
        return has.get();
    }

    public static boolean hasPotionEffect(Player player, String potionEffect) {
        AtomicBoolean has = new AtomicBoolean(false);
        player.getActivePotionEffects().forEach(effect -> {
            if (effect.getType() == PotionEffectType.getByName(potionEffect)) {
                has.set(true);
            }
        });
        return has.get();
    }

    public static void removeDebuffs(Player player) {
        for (PotionEffectType type : debuffs) {
            if (player.hasPotionEffect(type))
                player.removePotionEffect(type);
        }
    }

    public static void removeEffects(Player player, PotionEffectType ... effects ) {
        for (PotionEffectType type : effects) {
            if (player.hasPotionEffect(type))
                player.removePotionEffect(type);

        }
    }

    public static void removeEffectsAsync(Player player, PotionEffectType effects) {
        Bukkit.getScheduler().runTask(LuaCore.getPlugin(), () -> {
            removeEffects(player, effects);

        });
    }

    public static Object[] multiplyArray(Object[] array, int amount) {
        Object[] multipliedArray = new String[array.length * amount];
        for (int i = 0; i < multipliedArray.length; i++) {
            multipliedArray[i] = array[i % array.length];
        }
        return multipliedArray;
    }


}
