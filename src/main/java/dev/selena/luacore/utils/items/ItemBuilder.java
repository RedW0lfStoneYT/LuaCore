package dev.selena.luacore.utils.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.selena.luacore.utils.text.ContentUtils;
import dev.selena.luacore.utils.text.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {

    private Material type;
    private String title;
    private int amount;
    private String[] lore;
    private Map<String, Integer> enchants;
    private boolean stackable = true;
    private boolean usable = true;
    private boolean glowing;
    private Map<String, String> stringNBT;
    private Map<String, Integer> intNBT;
    private Map<String, Boolean> booleanNBT;
    private Map<String, Object> objectNBT;


    public ItemBuilder(Material type) {
        this.type = type;
        this.enchants = new TreeMap<>();
        this.stringNBT = new TreeMap<>();
        this.intNBT = new TreeMap<>();
        this.booleanNBT = new TreeMap<>();
        this.objectNBT = new TreeMap<>();
    }

    public ItemBuilder() {
        this(null);
    }

    public ItemBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    public ItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder setLore(String[] lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore.toArray(new String[0]);
        return this;
    }

    public ItemBuilder setEnchants(Map<String, Integer> enchants) {
        this.enchants = enchants;
        return this;
    }

    public ItemBuilder addEnchant(String enchant, int level) {
        this.enchants.put(enchant,level);
        return this;

    }

    public ItemBuilder setStackable(boolean stackable) {
        this.stackable = stackable;
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    public ItemBuilder setStringNBT(Map<String, String> stringNBT) {
        this.stringNBT = stringNBT;
        return this;
    }

    public ItemBuilder addNBTString(String key, String value) {
        this.stringNBT.put(key,value);
        return this;
    }

    public ItemBuilder setObjectNBT(Map<String, Object> objectNBT) {
        this.objectNBT = objectNBT;
        return this;
    }

    public ItemBuilder addNBTObject(String key, Object value) {
        this.objectNBT.put(key,value);
        return this;
    }

    public ItemBuilder setIntNBT(Map<String, Integer> intNBT) {
        this.intNBT = intNBT;
        return this;
    }

    public ItemBuilder addNBTInt(String key, int value) {
        this.intNBT.put(key,value);
        return this;
    }

    public ItemBuilder setBooleanNBT(Map<String, Boolean> booleanNBT) {
        this.booleanNBT = booleanNBT;
        return this;
    }

    public ItemBuilder addNBTBoolean(String key, boolean value) {
        this.booleanNBT.put(key, value);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setUsable(boolean usable) {
        this.usable = usable;
        return this;
    }

    public ItemStack build() {

        if (type == null)
            throw(new NullPointerException("Item cannot be null"));


        ItemStack item = new ItemStack(type);

        item.setAmount(Math.max(1, amount));
        ItemMeta meta = item.getItemMeta();
        if (title != null)
            meta.displayName(ContentUtils.componentColor(title));
        if (lore != null) {
            List<Component> loreLines = new ArrayList<>();
            for (String line : lore) {
                loreLines.add(ContentUtils.componentColor(line));
            }
            meta.lore(loreLines);
        }
        if (enchants != null && !enchants.isEmpty()) {
            for (String enchant : enchants.keySet()) {
                Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchant));
                assert enchantment != null;
                meta.addEnchant(enchantment, enchants.get(enchant), true);
            }
        }

        if (glowing && (enchants == null || enchants.isEmpty())) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        }


        item.setItemMeta(meta);


        NBTItem nbtItem = new NBTItem(item);

        if (!stackable)
            nbtItem.setString("UNSTACKABLE", UUID.randomUUID().toString());

        if (stringNBT != null && !stringNBT.isEmpty())
            for (String key : stringNBT.keySet()) {
                nbtItem.setString(key, stringNBT.get(key));
            }

        if (intNBT != null && !intNBT.isEmpty())
            for (String key : intNBT.keySet()) {
                nbtItem.setInteger(key, intNBT.get(key));
            }
        if (booleanNBT != null && !booleanNBT.isEmpty())
            for (String key : booleanNBT.keySet()) {
                nbtItem.setBoolean(key, booleanNBT.get(key));
            }

        if (objectNBT != null && !objectNBT.isEmpty())
            for (String key : objectNBT.keySet()) {
                nbtItem.setObject(key, objectNBT.get(key));
                MessageUtils.consoleSend(objectNBT.get(key));
            }

        if (!usable)
            nbtItem.setBoolean("USABLE", false);




        return nbtItem.getItem();
    }
}
