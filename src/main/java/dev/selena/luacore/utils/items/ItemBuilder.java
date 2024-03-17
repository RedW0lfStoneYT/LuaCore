package dev.selena.luacore.utils.items;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTType;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.nbt.NBTConstants;
import dev.selena.luacore.utils.nbt.NBTUtils;
import dev.selena.luacore.utils.text.ContentUtils;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.*;

/**
 * Used for easily creating custom items
 */
public class ItemBuilder {

    private Material type;
    private String title;
    private Color itemColor;
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
    private ArmorTrim trim;
    private PlayerProfile skullProfile;


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
    public ItemBuilder setLore(String ... lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Used for setting the lore using List
     * @param lore The List you want to set the lore to
     * @return This instance to continue the building
     * @see #setLore(String...) 
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
    public ItemBuilder setEnchantsFromKey(Map<String, Integer> enchants) {
        this.enchants = enchants;
        return this;
    }

    /**
     * Used for setting the enchantment map using spigot Enchantments
     * @param enchantments The map of enchantments you want to set
     * @return This instance to continue the building
     */
    public ItemBuilder setEnchantsFromEnchantments(Map<Enchantment, Integer> enchantments) {
        Map<String, Integer> newMap = new HashMap<>();
        for(Enchantment enchant : enchantments.keySet()) {
            newMap.put(enchant.getKey().getKey(), enchantments.get(enchant));
        }
        this.enchants = newMap;
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
     * Used for adding an enchantment
     * @param enchant The enchantment you want to add
     * @param level Level of the enchantment
     * @return This instance to continue the building
     */
    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        this.enchants.put(enchant.getKey().getKey(),level);
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
     * Used for setting color of leather armor
     * @param itemColor The color you want to set using bukkit Color
     * @return This instance to continue
     */
    public ItemBuilder setColor(Color itemColor) {
        this.itemColor = itemColor;
        return this;
    }

    /**
     * Used to add armor trims to a piece of armor
     * @param trim The armor trim you want to add
     * @return This instance to continue
     */
    public ItemBuilder setArmorTrim(ArmorTrim trim) {
        this.trim = trim;
        return this;
    }

    /**
     * Used for setting the skull texture (Won't work if the material isn't {@link Material#PLAYER_HEAD})
     * @param textureUrl The skull texture (NOTE: MUST be a <a href="https://textures.minecraft.net" target="_blank">https://textures.minecraft.net</a> texture)
     * @return This instance to continue
     */
    public ItemBuilder setSkullTexture(URL textureUrl) {
        return setSkullProfile(textureUrl, UUID.randomUUID(), null);
    }

    /**
     * Used for setting the skulls {@link PlayerProfile} texture.
     * @param skullTextureUrl The skull texture (NOTE: MUST be a <a href="https://textures.minecraft.net" target="_blank">https://textures.minecraft.net</a> texture)
     * @param skullTextureUUID The UUID of the skull, used for internal minecraft stuff
     * @param skullTextureName The texture name (from memory must be unique, but I haven't used this in a while)
     * @return This instance to continue
     */
    public ItemBuilder setSkullProfile(@NotNull URL skullTextureUrl, @NotNull UUID skullTextureUUID, @Nullable String skullTextureName) {
        skullProfile = LuaCore.getPlugin().getServer().createPlayerProfile(skullTextureUUID, skullTextureName);
        skullProfile.getTextures().setSkin(skullTextureUrl);
        return this;
    }

    /**
     * Used for setting the skulls {@link PlayerProfile}
     * @param skullProfile The {@link PlayerProfile} you want to set for the skull
     * @return This instance to continue
     */
    public ItemBuilder setSkullProfile(PlayerProfile skullProfile) {
        this.skullProfile = skullProfile;
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

        if (type == Material.PLAYER_HEAD) {
            if (skullProfile != null && skullProfile.isComplete()) {
                SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

                skullMeta.setOwnerProfile(skullProfile);
                item.setItemMeta(skullMeta);
            }
        }

        item.setAmount(Math.max(1, amount));
        ItemMeta meta = item.getItemMeta();
        if (title != null)
            meta.setDisplayName(ContentUtils.color(title));
        if (lore != null) {
            List<String> loreLines = new ArrayList<>();
            for (String line : lore) {
                loreLines.add(ContentUtils.color(line));
            }
            meta.setLore(loreLines);
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
            nbtItem.setUUID(NBTConstants.UNSTACKABLE.getNbtString(), UUID.randomUUID());

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
            LuaMessageUtils.verboseMessage("NBT Compound name: " + LuaCore.getCompountName());
            for (String nameSpace : customNBT.keySet()) {
                LuaMessageUtils.verboseMessage(nameSpace);
                NBTCompound compound = nbtItem.getOrCreateCompound(LuaCore.getCompountName());

                Object content = customNBT.get(nameSpace);
                LuaMessageUtils.json_dump(content);
                NBTUtils.storeNBTContent(compound, content, nameSpace);
            }
        }
        if (itemColor != null && ItemUtils.isLeather(item)) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) item.getItemMeta();
            leatherArmorMeta.setColor(itemColor);
            item.setItemMeta(leatherArmorMeta);
        }
        if (trim != null && ItemUtils.isArmor(item)) {
            ArmorMeta armorMeta = (ArmorMeta) item.getItemMeta();
            armorMeta.setTrim(trim);
            item.setItemMeta(armorMeta);

        }

        if (!usable)
            nbtItem.setBoolean("USABLE", false);




        return nbtItem.getItem();
    }

    /**
     * Used for creating an instance of ItemBuilder from an ItemStack
     * @param item The ItemStack you want to convert to ItemBuilder
     * @return A fresh instance of ItemBuilder
     */
    public static ItemBuilder fromItemStack(ItemStack item) {
        return new ItemBuilder(item.getType()){{
            ItemMeta meta = item.getItemMeta();
            setTitle(meta.getDisplayName());
            if (meta instanceof LeatherArmorMeta leatherArmorMeta)
                setColor(leatherArmorMeta.getColor());
            setAmount(item.getAmount());
            setLore(meta.getLore());
            setEnchantsFromEnchantments(meta.getEnchants());
            NBT.modify(item, nbt ->{
                setStackable(!nbt.hasTag(NBTConstants.UNSTACKABLE.getNbtString()));
                nbt.getKeys().forEach(key -> {
                    NBTType type = nbt.getType(key);
                    String value = "Un related";
                    if (type == NBTType.NBTTagInt) {
                        if (!key.equals("HideFlags")) {
                            addNBTInt(key, nbt.getInteger(key));
                            value = String.valueOf(nbt.getInteger(key));
                        }
                    }
                    if (type == NBTType.NBTTagString) {
                        addNBTString(key, nbt.getString(key));
                        value = nbt.getString(key);
                    }
                    if (type == NBTType.NBTTagByte) {
                        if (key.equals("USABLE")) {
                            setUsable(nbt.getBoolean("USABLE"));
                        } else {
                            addNBTBoolean(key, nbt.getBoolean(key));
                            value = String.valueOf(nbt.getBoolean(key));
                        }
                    }

                    LuaMessageUtils.verboseMessage(key + " -> " + nbt.getType(key) + " -> " + value);
                });
                ReadWriteNBT compound = nbt.getCompound(LuaCore.getCompountName());
                if (compound != null) {
                    compound.getKeys().forEach(key -> {
                        addCustomNBT(key, compound.getString(key));
                    });
                }
            });
            setGlowing(meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS));
            if (meta instanceof ArmorMeta armorMeta)
                setArmorTrim(armorMeta.hasTrim() ? armorMeta.getTrim() : null);
            if (meta instanceof SkullMeta skullMeta)
                setSkullProfile(skullMeta.getOwnerProfile());
        }};
    }

}
