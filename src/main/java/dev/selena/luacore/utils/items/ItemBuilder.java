package dev.selena.luacore.utils.items;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTType;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.exceptions.item.ItemBuilderException;
import dev.selena.luacore.utils.nbt.NBTConstants;
import dev.selena.luacore.utils.nbt.NBTUtils;
import dev.selena.luacore.utils.text.ComponentUtils;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * Used for easily creating custom items
 */
@SuppressWarnings("unused")
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
    private Map<String, Float> floatNBT;
    private Map<String, Object> customNBT;
    private ArmorTrim trim;
    private SkullProfile skullProfile;
    private Player placeholderPlayer;
    private int maxStackSize = -1;
    private Map<String, AttributeModifier> attributeModifiers;


    public static class SkullProfile {

        private final String textureUrl;
        private final UUID textureUUID;
        private final String textureName;

        public SkullProfile(String textureUrl, UUID textureUUID, String textureName) {
            this.textureUrl = textureUrl;
            this.textureUUID = textureUUID;
            this.textureName = textureName;
        }

    }

    /**
     * Start the creation of the ItemBuilder and setting the type
     * @param type Material you want to start with
     */
    public ItemBuilder(Material type) {
        this.type = type;
        this.placeholderPlayer = null;
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
     * Used for placeholderAPI stuff
     * @param player The player for the placeholders
     * @return This instance to continue the building
     */
    public ItemBuilder setPlaceholderPlayer(Player player) {
        this.placeholderPlayer = player;
        return this;
    }

    /**
     * Used for setting the max stack size of an item<br>
     * NOTE: THIS MIGHT MAKE THE LIBRARY NOT WORK ON VERSIONS THAT DO NOT HAVE setMaxSize(int)
     * @param maxStackSize The max stack size
     * @return This instance to continue building
     */
    public ItemBuilder setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
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
        if (this.enchants == null) {
            this.enchants = new TreeMap<>();
        }
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
        return this.addEnchant(enchant.getKey().getKey(),level);

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
        if (this.stringNBT == null) {
            this.stringNBT = new TreeMap<>();
        }
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
        if (this.intNBT == null) {
            this.intNBT = new TreeMap<>();
        }
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
        if (this.booleanNBT == null) {
            this.booleanNBT = new TreeMap<>();
        }
        this.booleanNBT.put(key, value);
        return this;
    }

    /**
     * Used for adding a boolean NBT value to the item
     * @param key The key you will use to get the boolean later
     * @param value The value you want to store
     * @return This instance to continue the building
     */
    public ItemBuilder addNBTFloat(String key, float value) {
        if (this.floatNBT == null) {
            this.floatNBT = new TreeMap<>();
        }
        this.floatNBT.put(key, value);
        return this;
    }

    /**
     * Used for setting the values of the NBT float map
     * @param floatNBT The map of floats you want to store
     * @return This instance to continue the building
     */
    public ItemBuilder setFloatNBT(Map<String, Float> floatNBT) {
        this.floatNBT = floatNBT;
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
        if (this.customNBT == null) {
            this.customNBT = new TreeMap<>();
        }
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
    public ItemBuilder setSkullTexture(String textureUrl) throws MalformedURLException {
        return setSkullProfile(URI.create(textureUrl).toURL(), UUID.randomUUID(), null);
    }

    /**
     * Used for setting the skulls {@link PlayerProfile} texture.
     * @param skullTextureUrl The skull texture (NOTE: MUST be a <a href="https://textures.minecraft.net" target="_blank">https://textures.minecraft.net</a> texture)
     * @param skullTextureUUID The UUID of the skull, used for internal minecraft stuff
     * @param skullTextureName The name of the skull profile (can be null, but should be unique)
     * @return This instance to continue
     */
    public ItemBuilder setSkullProfile(@NotNull URL skullTextureUrl, @NotNull UUID skullTextureUUID, @Nullable String skullTextureName) {
        skullProfile = new SkullProfile(skullTextureUrl.toString(), skullTextureUUID, skullTextureName);
        return this;
    }

    /**
     * Used for setting the skull profile using a URL, UUID and name
     * @param url The URL of the skull texture (NOTE: MUST be a https://textures.minecraft.net texture)
     * @param uuid The UUID of the skull, used for internal minecraft stuff
     * @param name The name of the skull profile (can be null, but should be unique)
     * @return This instance to continue
     */
    public ItemBuilder setSkullProfile(String url, UUID uuid, String name) {
        skullProfile = new SkullProfile(url, uuid, name);
        return this;
    }

    /**
     * Used for setting the skulls {@link PlayerProfile}
     * @param skullProfile The {@link PlayerProfile} you want to set for the skull
     * @return This instance to continue
     */
    public ItemBuilder setSkullProfile(PlayerProfile skullProfile) {
        this.skullProfile = new SkullProfile(
                skullProfile.getTextures().getSkin() == null ? "" : skullProfile.getTextures().getSkin().toString(),
                skullProfile.getUniqueId(),
                skullProfile.getName()
        );
        return this;
    }

    /**
     * Used for setting the attribute modifiers map of the item
     * @param attributeModifiers Map of {@link Attribute} modifiers where the key is the attribute key
     *                           (e.g. "max_health") and the value is the {@link AttributeModifier}
     * @return This instance to continue the building
     */
    public ItemBuilder setAttributeModifiers(Map<String, AttributeModifier> attributeModifiers) {
        this.attributeModifiers = attributeModifiers;
        return this;
    }


    /**
     * Used for adding an attribute modifier to the item
     * @param attribute The {@link Attribute} you want to add the modifier to
     * @param modifier The {@link AttributeModifier} you want to add
     * @return This instance to continue the building
     */
    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        return this.addAttributeModifier(attribute.getKey().getKey(), modifier);
    }

    /**
     * Used for adding an attribute modifier to the item
     * @param attributeKey The key of the attribute you want to add the modifier to (e.g. "max_health")
     * @param modifier The {@link AttributeModifier} you want to add
     * @return This instance to continue the building
     */
    public ItemBuilder addAttributeModifier(String attributeKey, AttributeModifier modifier) {
        if (this.attributeModifiers == null) {
            this.attributeModifiers = new HashMap<>();
        }
        this.attributeModifiers.put(attributeKey, modifier);
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
            if (skullProfile != null) {
                try {
                    PlayerProfile profile = LuaCore.getPlugin().getServer().createPlayerProfile(skullProfile.textureUUID, skullProfile.textureName);
                    profile.getTextures().setSkin(URI.create(skullProfile.textureUrl).toURL());
                    if (profile.isComplete()) {

                        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

                        skullMeta.setOwnerProfile(profile);
                        item.setItemMeta(skullMeta);
                    }
                } catch (MalformedURLException e) {
                    throw new ItemBuilderException("Invalid player profile");
                }
            }
        }

        item.setAmount(Math.max(1, amount));
        ItemMeta meta = item.getItemMeta();
        if (title != null)
            meta.customName(ComponentUtils.color(PlaceholderAPI.setPlaceholders(placeholderPlayer, title)));
        if (lore != null) {
            meta.lore(ComponentUtils.color(PlaceholderAPI.setPlaceholders(placeholderPlayer, List.of(lore))));
        }
        if (enchants != null && !enchants.isEmpty()) {
            for (String enchant : enchants.keySet()) {
                Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(NamespacedKey.minecraft(enchant));
//                Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchant));
                assert enchantment != null;
                meta.addEnchant(enchantment, enchants.get(enchant), true);
            }
        }

        if (glowing && (enchants == null || enchants.isEmpty())) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.POWER, 1, true);
        }

        if (maxStackSize > 0) {
            meta.setMaxStackSize(maxStackSize);
        }

        if (attributeModifiers != null && !attributeModifiers.isEmpty()) {
            for (Map.Entry<String, AttributeModifier> entry : attributeModifiers.entrySet()) {
                NamespacedKey key = NamespacedKey.fromString(entry.getKey());
                if (key == null) {
                    throw new ItemBuilderException("Invalid attribute key: " + entry.getKey());
                }
                Attribute attribute = RegistryAccess.registryAccess().getRegistry(RegistryKey.ATTRIBUTE).get(key);
                if (attribute == null) {
                    throw new ItemBuilderException("Invalid attribute: " + entry.getKey());
                }
                meta.addAttributeModifier(attribute, entry.getValue());
            }
        }

        item.setItemMeta(meta);

        NBT.modify(item, nbtItem -> {

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
            if (floatNBT != null && !floatNBT.isEmpty())
                for (String key : floatNBT.keySet()) {
                    nbtItem.setFloat(key, floatNBT.get(key));
                }
            if (customNBT != null && !customNBT.isEmpty()) {
                LuaMessageUtils.verboseMessage("NBT Compound name: " + LuaCore.getCompountName());
                for (String nameSpace : customNBT.keySet()) {
                    LuaMessageUtils.verboseMessage(nameSpace);
                    ReadWriteNBT compound = nbtItem.getOrCreateCompound(LuaCore.getCompountName());

                    Object content = customNBT.get(nameSpace);
                    LuaMessageUtils.json_dump(content);
                    NBTUtils.storeNBTContent(compound, content, nameSpace);
                }
            }
            if (!usable)
                nbtItem.setBoolean("USABLE", false);
        });
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
        return item;
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
