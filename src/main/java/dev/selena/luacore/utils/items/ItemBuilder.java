package dev.selena.luacore.utils.items;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.selena.luacore.LuaCore;
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

/**
 * Used for easily creating custom items
 */
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
    private Map<String, Object> customNBT;


    /**
     * Start the creation of the ItemBuilder and setting the type
     * @param type Material you want to start with
     */
    public ItemBuilder(Material type) {
        this.type = type;
        this.enchants = new TreeMap<>();
        this.stringNBT = new TreeMap<>();
        this.intNBT = new TreeMap<>();
        this.booleanNBT = new TreeMap<>();
        this.customNBT = new TreeMap<>();
    }

    /**
     * Used for creating the item builder with no initial
     * material type
     * @see ItemBuilder(Material)
     */
    public ItemBuilder() {
        this(null);
    }

    /**
     * Used for setting the material type
     * @param type Material you want to use
     * @return This instance to continue the building
     */
    public ItemBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    /**
     * Used for setting the display name of the item
     * @param title The display name you want
     * @return This instance to continue the building
     */
    public ItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the lore using a string array
     * @param lore The string array you want to set the lore
     * @return This instance to continue the building
     * @see #setLore(List)
     */
    public ItemBuilder setLore(String[] lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Used for setting the lore using List
     * @param lore The List you want to set the lore to
     * @return This instance to continue the building
     * @see #setLore(String[])
     */
    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore.toArray(new String[0]);
        return this;
    }

    /**
     * Used for setting enchantments
     * @param enchants Map of the enchantment namespace key and level
     * @return This instance to continue the building
     * @see #addEnchant(String, int)
     */
    public ItemBuilder setEnchants(Map<String, Integer> enchants) {
        this.enchants = enchants;
        return this;
    }

    /**
     * Used for adding an enchantment
     * @param enchant Namespace key of the enchantment
     * @param level Level of the enchantment
     * @return This instance to continue the building
     */
    public ItemBuilder addEnchant(String enchant, int level) {
        this.enchants.put(enchant,level);
        return this;

    }

    /**
     * Used for making items only be able to stack to 1
     * @param stackable if false the item will not stack
     * @return This instance to continue the building
     */
    public ItemBuilder setStackable(boolean stackable) {
        this.stackable = stackable;
        return this;
    }

    /**
     * Used for making an item glow
     * @param glowing true will give it an enchantment glint
     * @return This instance to continue the building
     */
    public ItemBuilder setGlowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    /**
     * Used for setting the NBTString map
     * @param stringNBT Map of NBT strings
     * @return This instance to continue the building
     * @see #addNBTString(String, String)
     */
    public ItemBuilder setStringNBT(Map<String, String> stringNBT) {
        this.stringNBT = stringNBT;
        return this;
    }

    /**
     * Used for adding an NBT string value to the item
     * @param key The key you will use to get the string later
     * @param value The string you want stored
     * @return This instance to continue the building
     */
    public ItemBuilder addNBTString(String key, String value) {
        this.stringNBT.put(key,value);
        return this;
    }

    /**
     * Used for setting the value of the NBT Int Map
     * @param intNBT The map of NBT data you want to add
     * @return This instance to continue the building
     */
    public ItemBuilder setIntNBT(Map<String, Integer> intNBT) {
        this.intNBT = intNBT;
        return this;
    }

    /**
     * Used for adding an NBT Int to the item
     * @param key The key you will use to get the int later
     * @param value The value you want to store
     * @return This instance to continue the building
     */
    public ItemBuilder addNBTInt(String key, int value) {
        this.intNBT.put(key,value);
        return this;
    }

    /**
     * Used for setting the values of the NBT boolean map
     * @param booleanNBT The map of booleans you want to store
     * @return This instance to continue the building
     */
    public ItemBuilder setBooleanNBT(Map<String, Boolean> booleanNBT) {
        this.booleanNBT = booleanNBT;
        return this;
    }

    /**
     * Used for adding a boolean NBT value to the item
     * @param key The key you will use to get the boolean later
     * @param value The value you want to store
     * @return This instance to continue the building
     */
    public ItemBuilder addNBTBoolean(String key, boolean value) {
        this.booleanNBT.put(key, value);
        return this;
    }

    /**
     * Used for setting the values of the custom NBT map
     * @param customNBT The map of custom NBT you want to store
     * @return This instance to continue the building
     */
    public ItemBuilder setCustomNBT(Map<String, Object> customNBT) {
        this.customNBT = customNBT;
        return this;
    }

    /**
     * Used for adding custom NBT data to the item
     * @param nameSpace The key you will use to get the custom content later
     * @param content The content you want to store later
     * @return This instance to continue the building
     */
    public ItemBuilder addCustomNBT(String nameSpace, Object content) {
        this.customNBT.put(nameSpace, content);
        return this;
    }

    /**
     * Used for setting the amount of items
     * @param amount The amount you want the ItemStack to be in
     * @return This instance to continue the building
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Allows you to stop users from using items that should not be used
     * @param usable set value as false to prevent players from using item
     * @return This instance to continue the building
     */
    public ItemBuilder setUsable(boolean usable) {
        this.usable = usable;
        return this;
    }

    /**
     * Used for creating the previously created ItemStack
     * @return The built ItemStack
     */
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
            nbtItem.setUUID("UNSTACKABLE", UUID.randomUUID());

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
        if (customNBT != null && !customNBT.isEmpty()) {
            for (String nameSpace : customNBT.keySet()) {
                NBTCompound compound = nbtItem.getCompound(LuaCore.getCompountName());
                Object content = customNBT.get(nameSpace);
                NBTUtils.storeNBTContent(compound, content, nameSpace);
            }
        }

        if (!usable)
            nbtItem.setBoolean("USABLE", false);




        return nbtItem.getItem();
    }

}
