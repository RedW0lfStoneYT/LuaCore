package dev.selena.luacore.utils.entities;

import com.google.gson.reflect.TypeToken;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.exceptions.entity.EntityBuildException;
import dev.selena.luacore.nms.ICustomGoalWrapper;
import dev.selena.luacore.nms.INMSEntityBuilder;
import dev.selena.luacore.nms.IPathfinderInjector;
import dev.selena.luacore.utils.RandomCollection;
import dev.selena.luacore.utils.data.TypedObject;
import dev.selena.luacore.utils.items.ItemBuilder;
import dev.selena.luacore.utils.items.ItemUtils;
import dev.selena.luacore.utils.nbt.NBTUtils;
import dev.selena.luacore.utils.text.ComponentUtils;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Type;
import java.util.*;

/**
 * THIS CLASS IS <b>HIGHLY</b> EXPERIMENTAL, USAGE IS AT YOUR OWN RISK.<br>
 * PLEASE REPORT ANY BUGS ON THE <a href="https://github.com/RedW0lfStoneYT/LuaCore/issues">GITHUB ISSUE TRACKER</a>
 */
@ApiStatus.Experimental
public class EntityBuilder {

    private Map<String, Float> attributes;
    private EntityType entityType;
    private String displayName;
    private List<PotionEffect> potionEffects;
    private ItemStack
            helmet,
            chestplate,
            boots,
            leggings,
            mainHandItem,
            offHandItem;
    private RandomCollection<ItemBuilder> drops;
    private Map<String, MetadataValue> metadataValues;
    private Map<String, TypedObject> customNBTData;
    private Map<Integer, ICustomGoalWrapper> customPathfinderGoal;
    private boolean hasAi = true;
    private boolean silent = false;
    private boolean vanillaDrops = true;
    private boolean removeDefaultGoals = false;


    /**
     * Used for creating a blank instance
     */
    public EntityBuilder() {
        this(null);
    }

    /**
     * Used for creating an instance with EntityType
     *
     * @param type The EntityType you want to spawn
     */
    public EntityBuilder(EntityType type) {
        this.potionEffects = new ArrayList<>();
        this.drops = new RandomCollection<>();
        this.metadataValues = new TreeMap<>();
        this.customNBTData = new TreeMap<>();
        this.entityType = type;
    }

    /**
     * Used for setting the list of potion effects
     *
     * @param potionEffects The list of potion effects you want to set
     * @return The current builder instance
     */
    public EntityBuilder setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
        return this;
    }

    /**
     * Used for adding a potion effect to the list of effects
     *
     * @param effect The effect you want to add
     * @return The current builder instance
     */
    public EntityBuilder addPotionEffect(PotionEffect effect) {
        this.potionEffects.add(effect);
        return this;
    }

    /**
     * Used for adding an array of potion effects to the entity
     *
     * @param effects The array of effects
     * @return The current builder instance
     */
    public EntityBuilder addPotionEffects(PotionEffect... effects) {
        this.potionEffects.addAll(Arrays.asList(effects));
        return this;
    }

    /**
     * Used for adding a list of potion effects to the entity
     *
     * @param effects The list of effects you want to add
     * @return The current builder instance
     */
    public EntityBuilder addPotionEffects(List<PotionEffect> effects) {
        this.potionEffects.addAll(effects);
        return this;
    }

    /**
     * Used for adding a drop to the collection
     *
     * @param chance The weighted chance
     * @param item   The items you want added
     * @return The current builder instance
     */
    public EntityBuilder addDrop(double chance, ItemBuilder item) {
        this.drops.add(chance, item);
        return this;
    }

    /**
     * used to add a map of drops to the drop collection
     *
     * @param drops The map of drops
     * @return The current builder instance
     */
    public EntityBuilder addDrops(Map<ItemBuilder, Double> drops) {
        this.drops.addAll(drops);
        return this;
    }

    /**
     * Used for adding a drop to the collection
     *
     * @param chance The weighted chance
     * @param item   The items you want added
     * @return The current builder instance
     */
    public EntityBuilder addDrop(double chance, ItemStack item) {
        this.drops.add(chance, ItemBuilder.fromItemStack(item));
        return this;
    }

    /**
     * used to add a map of drops to the drop collection
     *
     * @param drops The map of drops
     * @return The current builder instance
     */
    public EntityBuilder addDropsFromItemMap(Map<ItemStack, Double> drops) {
        for (ItemStack itemStack : drops.keySet()) {
            this.drops.add(drops.get(itemStack), ItemBuilder.fromItemStack(itemStack));
        }
        return this;
    }

    /**
     * Used for adding metadata to the entity (Not to sure if I can make it save on server restart)
     * (NOTE: I Suggest using {@link EntityBuilder#addCustomNBTData(String, Object)})
     *
     * @param key      The key used for getting the metadata value
     * @param metadata The metadata you want to store
     * @return The current builder instance
     */
    public EntityBuilder addMetaDataValue(String key, MetadataValue metadata) {
        this.metadataValues.put(key, metadata);
        return this;
    }

    /**
     * Used for adding a map of metadata values to the entity
     * (NOTE: I Suggest using {@link EntityBuilder#addCustomNBTDataValues(Map)})
     *
     * @param metadataValues The map of metadata values
     * @return The current builder instance
     */
    public EntityBuilder addMetaDataValues(Map<String, MetadataValue> metadataValues) {
        this.metadataValues.putAll(metadataValues);
        return this;
    }

    /**
     * Used for adding custom NBTData to the entity
     *
     * @param key The key used to gather the data
     * @param cls The NBTData class (NOTE: can be essentially anything)
     * @return The current builder instance
     */
    @Deprecated(forRemoval = true)
    public EntityBuilder addCustomNBTData(String key, Object cls) {
        this.customNBTData.put(key, new TypedObject(cls, cls.getClass()));
        return this;
    }

    /**
     * Used for adding custom NBTData to the entity
     * @param key The key used to gather the data
     * @param typedObject The {@link TypedObject} you want to add
     * @return The current builder instance
     */
    public EntityBuilder addCustomNBTData(String key, TypedObject typedObject) {
        this.customNBTData.put(key, typedObject);
        return this;
    }

    /**
     * Used for adding a map of NBTData to add to the entity
     *
     * @param values The map of NBTData classes
     * @return The current builder instance
     */
    public EntityBuilder addCustomNBTDataValues(Map<String, TypedObject> values) {
        this.customNBTData.putAll(values);
        return this;
    }

    /**
     * Used for setting the custom NBTData map
     *
     * @param customNBTData The map of custom NBTData
     * @return The current builder instance
     */
    public EntityBuilder setCustomNBTData(Map<String, TypedObject> customNBTData) {
        this.customNBTData = customNBTData;
        return this;
    }

    /**
     * Used for setting the armor bonus attribute (0.0 to 30.0, default 0.0)
     *
     * @param armorBonus The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setArmorBonus(float armorBonus) {
        addIfNull("armorBonus", armorBonus);
        return this;
    }

    /**
     * Used for setting the armor toughness bonus attribute (0.0 to 20.0, default 0.0)
     *
     * @param armorToughnessBonus The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setArmorToughnessBonus(float armorToughnessBonus) {
        addIfNull("armorToughnessBonus", armorToughnessBonus);
        return this;
    }

    /**
     * Used for setting the attack damage bonus (0.0 to 2048.0 default, 2.0)
     *
     * @param attackDamageBonus The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setAttackDamageBonus(float attackDamageBonus) {
        addIfNull("attackDamageBonus", attackDamageBonus);
        return this;
    }

    /**
     * Used for setting the attack knock back attribute (0.0 to 5.0, default 0.0)
     *
     * @param attackKnockBack The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setAttackKnockBack(float attackKnockBack) {
        addIfNull("attackKnockBack", attackKnockBack);
        return this;
    }

    /**
     * Used for setting the flight speed attribute (0.0 to 1024.0, default 0.4)
     *
     * @param flyingSpeed The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setFlyingSpeed(float flyingSpeed) {
        addIfNull("flyingSpeed", flyingSpeed);
        return this;
    }

    /**
     * Used for setting the entity follow range attribute (0.0 to 2048, default 32.0)
     *
     * @param followRange The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setFollowRange(float followRange) {
        addIfNull("followRange", followRange);
        return this;
    }

    /**
     * Used for setting the knock back resistance attribute (0.0 to 1.0, default 0.0)
     *
     * @param knockBackResistance The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setKnockBackResistance(float knockBackResistance) {
        addIfNull("knockBackResistance", knockBackResistance);
        return this;
    }

    /**
     * Used for setting the maximum absorption attribute (0.0 to 2048.0, default 0.0)
     *
     * @param maxAbsorption The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setMaxAbsorption(float maxAbsorption) {
        addIfNull("maxAbsorption", maxAbsorption);
        return this;
    }

    /**
     * Used for setting the maximum health attribute (0.0 to 1024.0, default 20.0)
     *
     * @param maxHealth The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setMaxHealth(float maxHealth) {
        addIfNull("maxHealth", maxHealth);
        return this;
    }

    /**
     * Used for setting the entity movement speed attribute (0.0 to 1024.0, default depends on entity)
     *
     * @param movementSpeed The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setMovementSpeed(float movementSpeed) {
        addIfNull("movementSpeed", movementSpeed);
        return this;
    }

    /**
     * Used for setting the horse jump strength attribute (0.0 to 2.0, default 0.7)
     *
     * @param jumpStrength The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setJumpStrength(float jumpStrength) {
        addIfNull("jumpStrength", jumpStrength);
        return this;
    }

    /**
     * Used for setting the entity scale attribute (0.0625 to 16.0, default 1.0)
     *
     * @param entityScale The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setEntityScale(float entityScale) {
        addIfNull("entityScale", entityScale);
        return this;
    }

    /**
     * Used for setting the entity step height attribute (0.0 to 10.0, default 0.6)
     *
     * @param stepHeight The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setStepHeight(float stepHeight) {
        addIfNull("stepHeight", stepHeight);
        return this;
    }

    /**
     * Used for setting the reinforcements spawn chance attribute
     *
     * @param reinforcementsChance The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setSpawnReinforcements(float reinforcementsChance) {
        addIfNull("spawnReinforcements", reinforcementsChance);
        return this;
    }

    /**
     * Used for setting the burn timer multiplier attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setBurningTimer(float value) {
        addIfNull("burningTimer", value);
        return this;
    }

    /**
     * Used for setting the camera distance attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setCameraDistance(float value) {
        addIfNull("cameraDistance", value);
        return this;
    }

    /**
     * Used for setting the explosion knockback resistance attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setExplosionKnockbackResistance(float value) {
        addIfNull("explosionKnockbackResistance", value);
        return this;
    }

    /**
     * Used for setting the fall damage multiplier attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setFallDamageMultiplier(float value) {
        addIfNull("fallDamageMultiplier", value);
        return this;
    }

    /**
     * Used for setting the gravity attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setGravity(float value) {
        addIfNull("gravity", value);
        return this;
    }

    /**
     * Used for setting the movement efficiency attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setMovementEfficiency(float value) {
        addIfNull("movementEfficiency", value);
        return this;
    }

    /**
     * Used for setting the oxygen bonus attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setOxygenBonus(float value) {
        addIfNull("oxygenBonus", value);
        return this;
    }

    /**
     * Used for setting the safe fall distance attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setSafeFallDistance(float value) {
        addIfNull("safeFallDistance", value);
        return this;
    }

    /**
     * Used for setting the temptation range attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setTemptRange(float value) {
        addIfNull("temptRange", value);
        return this;
    }

    /**
     * Used for setting the water movement efficiency attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setWaterMovementEfficiency(float value) {
        addIfNull("waterMovementEfficiency", value);
        return this;
    }

    /**
     * Used for setting the waypoint receive range attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setWaypointReceiveRange(float value) {
        addIfNull("waypointReceiveRange", value);
        return this;
    }

    /**
     * Used for setting the waypoint transmit range attribute
     *
     * @param value The value for the attribute
     * @return The current builder instance
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     */
    public EntityBuilder setWaypointTransmitRange(float value) {
        addIfNull("waypointTransmitRange", value);
        return this;
    }

    /**
     * Used for setting the type of entity to spawn
     *
     * @param entityType The EntityType you want to spawn
     * @return The current builder instance
     */
    public EntityBuilder setEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    /**
     * Used for setting the display name of the entity (Color is translated once applied so no need to do it before)
     *
     * @param displayName The entity display name
     * @return The current builder instance
     */
    public EntityBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Used for setting the entity helmet
     *
     * @param helmet The item you want to set for helmet
     * @return The current builder instance
     */
    public EntityBuilder setHelmet(ItemStack helmet) {
        this.helmet = helmet;
        return this;
    }

    /**
     * Used for setting the entity chestplate
     *
     * @param chestplate The item you want to set for the chestplate (NOTE: Must be a chestplate item)
     * @return The current builder instance
     */
    public EntityBuilder setChestplate(ItemStack chestplate) {
        this.chestplate = chestplate;
        return this;
    }

    /**
     * Used for setting the entity leggings
     *
     * @param leggings The item you want to set for the leggings (NOTE: Must be a legging item)
     * @return The current builder instance
     */
    public EntityBuilder setLeggings(ItemStack leggings) {
        this.leggings = leggings;
        return this;
    }

    /**
     * Used for setting the entity boots
     *
     * @param boots The item you want to set for the boots (NOTE: Must be a boot item)
     * @return The current builder instance
     */
    public EntityBuilder setBoots(ItemStack boots) {
        this.boots = boots;
        return this;
    }

    /**
     * Used for setting the item in the entities main hand
     *
     * @param mainHandItem The item you want to place in the main hand
     * @return The current builder instance
     */
    public EntityBuilder setMainHandItem(ItemStack mainHandItem) {
        this.mainHandItem = mainHandItem;
        return this;
    }

    /**
     * Used for setting the item in the entities offhand
     *
     * @param offHandItem The item you want to place in the offhand
     * @return The current builder instance
     */
    public EntityBuilder setOffHandItem(ItemStack offHandItem) {
        this.offHandItem = offHandItem;
        return this;
    }

    /**
     * Used for setting the drop collection for entity death
     *
     * @param drops The {@link RandomCollection} of ItemBuilder drops
     * @return The current builder instance
     */
    public EntityBuilder setDrops(RandomCollection<ItemBuilder> drops) {
        this.drops = drops;
        return this;
    }

    /**
     * Used for setting the drop collection for entity death
     *
     * @param itemStackDrops The {@link RandomCollection} of ItemStack drops
     * @return The current builder instance
     */
    public EntityBuilder setDropsFromItemCollection(RandomCollection<ItemStack> itemStackDrops) {
        this.drops = itemStackDrops.cloneTo(ItemBuilder.class, ItemBuilder::fromItemStack);
        return this;
    }

    /**
     * Used for setting the map of metadata values (NOTE: I suggest using {@link EntityBuilder#setCustomNBTData(Map)})
     *
     * @param metadataValues The map of metadata values
     * @return The current builder instance
     */
    public EntityBuilder setMetadataValues(Map<String, MetadataValue> metadataValues) {
        this.metadataValues = metadataValues;
        return this;
    }

    /**
     * Used for setting a custom pathfinder goal
     * NOTE: THIS IS VERY EXPERIMENTAL AND MAY NOT WORK AS INTENDED
     *
     * @param customPathfinderGoal The class of the pathfinder goal
     * @return The current builder instance
     */
    @ApiStatus.Experimental
    public EntityBuilder setCustomPathfinderGoal(Map<Integer, ICustomGoalWrapper> customPathfinderGoal) {
        this.customPathfinderGoal = customPathfinderGoal;
        return this;
    }

    /**
     * Used for adding a custom pathfinder goal to the entity
     * NOTE: THIS IS VERY EXPERIMENTAL AND MAY NOT WORK AS INTENDED
     *
     * @param priority The priority of the goal (lower number = higher priority)
     * @param goalWrapper The wrapper for the custom goal
     * @return The current builder instance
     */
    @ApiStatus.Experimental
    public EntityBuilder addCustomPathfinderGoal(int priority, ICustomGoalWrapper goalWrapper) {
        if (this.customPathfinderGoal == null) {
            this.customPathfinderGoal = new HashMap<>();
        }
        this.customPathfinderGoal.put(priority, goalWrapper);
        return this;
    }

    /**
     * Used for setting if the entity has AI or not
     * @param hasAi True if the entity has AI, false if it does not, true by default
     * @return The current builder instance
     */
    public EntityBuilder setAi(boolean hasAi) {
        this.hasAi = hasAi;
        return this;
    }

    /**
     * Used for setting if the entity is silent or not
     * @param silent True if the entity is silent, false if it is not, false by default
     * @return The current builder instance
     */
    public EntityBuilder setSilent(boolean silent) {
        this.silent = silent;
        return this;
    }

    /**
     * Used for setting if the entity should use vanilla drops or not
     * @param vanillaDrops True if the entity should use vanilla drops, false if it should not, false by default
     * @return The current builder instance
     */
    public EntityBuilder setVanillaDrops(boolean vanillaDrops) {
        this.vanillaDrops = vanillaDrops;
        return this;
    }

    /**
     * Used for setting if the entity should remove default goals or not
     * @param removeDefaultGoals True if the entity should remove default goals, false if it should not, false by default
     * @return The current builder instance
     */
    public EntityBuilder setRemoveDefaultGoals(boolean removeDefaultGoals) {
        this.removeDefaultGoals = removeDefaultGoals;
        return this;
    }

    private void addIfNull(String attribute, float value) {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }
        this.attributes.put(attribute, value);
    }

    /**
     * Used for spawning the entity in the world
     *
     * @param location The location you want to spawn the entity
     * @param world The world you want to spawn the entity in
     * @return The entity that has just been spawned
     * @see EntityBuilder#spawn(Location)
     */
    @SneakyThrows
    public Entity spawn(Location location, World world) {

        if (entityType == null || entityType.getEntityClass() == null)
            throw new EntityBuildException("Entity type cannot be null");
        if (!entityType.isSpawnable())
            throw new EntityBuildException("Entity type must be spawnable");
        if (!entityType.isAlive())
            throw new EntityBuildException("Entity type must be living");
        Entity entity = world.spawn(location, entityType.getEntityClass());
        LivingEntity entityLiving = (LivingEntity) entity;
        INMSEntityBuilder nmsBuilder = LuaCore.getNmsVersion().getClazz().getEntityBuilder(entityLiving);
        boolean hasName = this.displayName != null && !this.displayName.isEmpty();
        entityLiving.setCustomNameVisible(hasName);
        if (hasName)
            entityLiving.customName(ComponentUtils.color(this.displayName));
        if (attributes != null) {
            attributes.forEach((key, value) -> {
                switch (key) {
                    case "armorBonus" -> nmsBuilder.setArmorBonus(value);
                    case "armorToughnessBonus" -> nmsBuilder.setArmorToughnessBonus(value);
                    case "attackDamageBonus" -> nmsBuilder.setAttackDamageBonus(value);
                    case "attackKnockBack" -> nmsBuilder.setAttackKnockBack(value);
                    case "flyingSpeed" -> nmsBuilder.setFlyingSpeed(value);
                    case "followRange" -> nmsBuilder.setFollowRange(value);
                    case "knockBackResistance" -> nmsBuilder.setKnockBackResistance(value);
                    case "maxAbsorption" -> nmsBuilder.setMaxAbsorption(value);
                    case "maxHealth" -> nmsBuilder.setMaxHealth(value);
                    case "movementSpeed" -> nmsBuilder.setMovementSpeed(value);
                    case "jumpStrength" -> nmsBuilder.setJumpStrength(value);
                    case "entityScale" -> nmsBuilder.setEntityScale(value);
                    case "stepHeight" -> nmsBuilder.setStepHeight(value);
                    case "spawnReinforcementsChance" -> nmsBuilder.spawnReinforcements(value);
                    case "burningTime" -> nmsBuilder.setBurningTime(value);
                    case "cameraDistance" -> nmsBuilder.setCameraDistance(value);
                    case "explosionKnockbackResistance" -> nmsBuilder.setExplosionKnockBackResistance(value);
                    case "fallDamageMultiplier" -> nmsBuilder.setFallDamageMultiplier(value);
                    case "gravity" -> nmsBuilder.setGravity(value);
                    case "movementEfficiency" -> nmsBuilder.setMovementEfficiency(value);
                    case "oxygenBonus" -> nmsBuilder.setOxygenBonus(value);
                    case "safeFallDistance" -> nmsBuilder.setSafeFallDistance(value);
                    case "temptRange" -> nmsBuilder.setTemptRange(value);
                    case "waterMovementEfficiency" -> nmsBuilder.setWaterMoveEfficiency(value);
                    case "waypointReceiveRange" -> nmsBuilder.setWaypointReceiveRange(value);
                    case "waypointTransmitRange" -> nmsBuilder.setWaypointTransmitRange(value);
                }

            });
        }


        entityLiving = nmsBuilder.getEntity();
        if (customPathfinderGoal != null && !customPathfinderGoal.isEmpty()) {
            IPathfinderInjector injector = LuaCore.getNmsVersion().getClazz().getPathfinderInjector(entityLiving);
            if (removeDefaultGoals) {
                injector.clearPathfinders();
            }
            for (Map.Entry<Integer, ICustomGoalWrapper> entry : customPathfinderGoal.entrySet()) {
                injector.addCustomPathfinder(entry.getKey(), entry.getValue());
            }
            entityLiving = injector.getEntity();
        }
        entityLiving.addPotionEffects(potionEffects);
        EntityEquipment equipment = entityLiving.getEquipment();
        assert equipment != null;
        equipment.setHelmet(helmet, true);
        if (ItemUtils.isChestplate(chestplate))
            equipment.setChestplate(chestplate, true);
        if (ItemUtils.isLeggings(leggings))
            equipment.setLeggings(leggings, true);
        if (ItemUtils.isBoots(boots))
            equipment.setBoots(boots, true);
        equipment.setItemInMainHand(mainHandItem);
        equipment.setItemInOffHand(offHandItem);

        NBT.modifyPersistentData(entity, nbt -> {
            nbt.setString("OwningPlugin", LuaCore.getPlugin().getName());
            ReadWriteNBT compound = nbt.getOrCreateCompound(LuaCore.getCompountName());
            compound.setBoolean("VanillaDrops", vanillaDrops);
            compound.setBoolean("LuaCoreEntity", true);
            for (String nameSpace : customNBTData.keySet()) {
                TypedObject content = customNBTData.get(nameSpace);
                try {
                    Type type = content.getType();
                    if (type == null) {
                        NBTUtils.storeEntityNBTContent(compound, content.getValue(), nameSpace);
                    }
                    NBTUtils.storeEntityNBTContent(compound, content.getValue(), nameSpace, type);
                } catch (ClassNotFoundException e) {
                    NBTUtils.storeEntityNBTContent(compound, content.getValue(), nameSpace);
                }
            }
            if (!drops.isEmpty())
                NBTUtils.storeEntityNBTContent(compound, drops, "EntityDrops", TypeToken.getParameterized(RandomCollection.class, ItemBuilder.class).getType());
        });
        for (String metadataKey : metadataValues.keySet()) {
            entity.setMetadata(metadataKey, metadataValues.get(metadataKey));
        }
        entity.setSilent(this.silent);
        ((LivingEntity) entity).setAI(this.hasAi);
        return entity;
    }

    /**
     * Used for spawning the entity in the world
     *
     * @param location The location you want to spawn the entity (World must not be null)
     * @return The entity that has just been spawned
     * @see EntityBuilder#spawn(Location, World)
     */
    public Entity spawn(Location location) {
        if (location.getWorld() == null)
            throw new EntityBuildException("World cannot be null");
        return this.spawn(location, location.getWorld());
    }

}
