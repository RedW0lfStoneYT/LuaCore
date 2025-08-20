package dev.selena.luacore.utils.entities;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.exceptions.entity.EntityBuildException;
import dev.selena.luacore.nms.INMSEntityBuilder;
import dev.selena.luacore.utils.RandomCollection;
import dev.selena.luacore.utils.items.ItemBuilder;
import dev.selena.luacore.utils.items.ItemUtils;
import dev.selena.luacore.utils.nbt.NBTUtils;
import dev.selena.luacore.utils.text.ContentUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * THIS CLASS IS NOT AT ALL TESTED, USAGE IS AT YOUR OWN RISK.<br>
 * PLEASE REPORT ANY BUGS ON THE <a href="https://github.com/RedW0lfStoneYT/LuaCore/issues">GITHUB ISSUE TRACKER</a>
 */

public class EntityBuilder {

    // TODO Add default value and range to all attribute documentation

    private float
            armorBonus,
            armorToughnessBonus,
            attackDamageBonus,
            attackKnockBack,
            burningTime,
            cameraDistance,
            explosionKnockbackResistance,
            fallDamageMultiplier,
            gravity,
            spawnReinforcementsChance,
            movementEfficiency,
            oxygenBonus,
            safeFallDistance,
            temptRange,
            waterMovementEfficiency,
            waypointReceiveRange,
            waypointTransmitRange,
            flyingSpeed,
            followRange,
            knockBackResistance,
            maxAbsorption,
            maxHealth,
            movementSpeed,
            jumpStrength,
            entityScale,
            stepHeight;
    private boolean armorBonus_isCustom = false,
            armorToughnessBonus_isCustom = false,
            attackDamageBonus_isCustom = false,
            attackKnockBack_isCustom = false,
            flyingSpeed_isCustom = false,
            followRange_isCustom = false,
            knockBackResistance_isCustom = false,
            maxAbsorption_isCustom = false,
            maxHealth_isCustom = false,
            movementSpeed_isCustom = false,
            jumpStrength_isCustom = false,
            entityScale_isCustom = false,
            stepHeight_isCustom = false,
            burningTime_isCustom = false,
            cameraDistance_isCustom = false,
            explosionKnockbackResistance_isCustom = false,
            fallDamageMultiplier_isCustom = false,
            gravity_isCustom = false,
            spawnReinforcementsChance_isCustom = false,
            movementEfficiency_isCustom = false,
            oxygenBonus_isCustom = false,
            safeFallDistance_isCustom = false,
            temptRange_isCustom = false,
            waterMovementEfficiency_isCustom = false,
            waypointReceiveRange_isCustom = false,
            waypointTransmitRange_isCustom = false;
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
    // Not to sure how well this will work yet, but it should allow the entity to keep the data on reload
    private Map<String, Object> customNBTData;
    // This will require NMS reflections woo :/
    // But hey, the person calling this method will also need to
    private Class<?> customPathfinderGoal;


    /**
     * Used for creating a blank instance
     */
    public EntityBuilder() {
        this(null);
    }

    /**
     * Used for creating an instance with EntityType
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
     * @param potionEffects The list of potion effects you want to set
     * @return The current builder instance
     */
    public EntityBuilder setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
        return this;
    }

    /**
     * Used for adding a potion effect to the list of effects
     * @param effect The effect you want to add
     * @return The current builder instance
     */
    public EntityBuilder addPotionEffect(PotionEffect effect) {
        this.potionEffects.add(effect);
        return this;
    }

    /**
     * Used for adding an array of potion effects to the entity
     * @param effects The array of effects
     * @return The current builder instance
     */
    public EntityBuilder addPotionEffects(PotionEffect ... effects) {
        this.potionEffects.addAll(Arrays.asList(effects));
        return this;
    }

    /**
     * Used for adding a list of potion effects to the entity
     * @param effects The list of effects you want to add
     * @return The current builder instance
     */
    public EntityBuilder addPotionEffects(List<PotionEffect> effects) {
        this.potionEffects.addAll(effects);
        return this;
    }

    /**
     * Used for adding a drop to the collection
     * @param chance The weighted chance
     * @param item The items you want added
     * @return The current builder instance
     */
    public EntityBuilder addDrop(double chance, ItemBuilder item) {
        this.drops.add(chance, item);
        return this;
    }

    /**
     * used to add a map of drops to the drop collection
     * @param drops The map of drops
     * @return The current builder instance
     */
    public EntityBuilder addDrops(Map<ItemBuilder, Double> drops) {
        this.drops.addAll(drops);
        return this;
    }

    /**
     * Used for adding a drop to the collection
     * @param chance The weighted chance
     * @param item The items you want added
     * @return The current builder instance
     */
    public EntityBuilder addDrop(double chance, ItemStack item) {
        this.drops.add(chance, ItemBuilder.fromItemStack(item));
        return this;
    }

    /**
     * used to add a map of drops to the drop collection
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
     * @param key The key used for getting the metadata value
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
     * @param metadataValues The map of metadata values
     * @return The current builder instance
     */
    public EntityBuilder addMetaDataValues(Map<String, MetadataValue> metadataValues) {
        this.metadataValues.putAll(metadataValues);
        return this;
    }

    /**
     * Used for adding custom NBTData to the entity
     * @param key The key used to gather the data
     * @param cls The NBTData class (NOTE: can be essentially anything)
     * @return The current builder instance
     */
    public EntityBuilder addCustomNBTData(String key, Object cls) {
        this.customNBTData.put(key, cls);
        return this;
    }

    /**
     * Used for adding a map of NBTData to add to the entity
     * @param values The map of NBTData classes
     * @return The current builder instance
     */
    public EntityBuilder addCustomNBTDataValues(Map<String, Object> values) {
        this.customNBTData.putAll(values);
        return this;
    }

    /**
     * Used for setting the armor bonus attribute (0.0 to 30.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param armorBonus The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setArmorBonus(float armorBonus) {
        this.armorBonus = armorBonus;
        this.armorBonus_isCustom = true;
        return this;
    }

    /**
     * Used for setting the armor toughness bonus attribute (0.0 to 20.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param armorToughnessBonus The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setArmorToughnessBonus(float armorToughnessBonus) {
        this.armorToughnessBonus = armorToughnessBonus;
        this.armorToughnessBonus_isCustom = true;
        return this;
    }

    /**
     * Used for setting the attack damage bonus (0.0 to 2048.0 default, 2.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param attackDamageBonus The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setAttackDamageBonus(float attackDamageBonus) {
        this.attackDamageBonus = attackDamageBonus;
        this.attackDamageBonus_isCustom = true;
        return this;
    }

    /**
     * Used for setting the attack knock back attribute (0.0 to 5.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param attackKnockBack The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setAttackKnockBack(float attackKnockBack) {
        this.attackKnockBack = attackKnockBack;
        this.attackKnockBack_isCustom = true;
        return this;
    }

    /**
     * Used for setting the flight speed attribute (0.0 to 1024.0, default 0.4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param flyingSpeed The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setFlyingSpeed(float flyingSpeed) {
        this.flyingSpeed = flyingSpeed;
        this.flyingSpeed_isCustom = true;
        return this;
    }

    /**
     * Used for setting the entity follow range attribute (0.0 to 2048, default 32.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param followRange The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setFollowRange(float followRange) {
        this.followRange = followRange;
        this.followRange_isCustom = true;
        return this;
    }

    /**
     * Used for setting the knock back resistance attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param knockBackResistance The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setKnockBackResistance(float knockBackResistance) {
        this.knockBackResistance = knockBackResistance;
        this.knockBackResistance_isCustom = true;
        return this;
    }

    /**
     * Used for setting the maximum absorption attribute (0.0 to 2048.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param maxAbsorption The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setMaxAbsorption(float maxAbsorption) {
        this.maxAbsorption = maxAbsorption;
        this.maxAbsorption_isCustom = true;
        return this;
    }

    /**
     * Used for setting the maximum health attribute (0.0 to 1024.0, default 20.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param maxHealth The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
        this.maxHealth_isCustom = true;
        return this;
    }

    /**
     * Used for setting the entity movement speed attribute (0.0 to 1024.0, default depends on entity)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param movementSpeed The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
        this.movementSpeed_isCustom = true;
        return this;
    }

    /**
     * Used for setting the horse jump strength attribute (0.0 to 2.0, default 0.7)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param jumpStrength The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setJumpStrength(float jumpStrength) {
        this.jumpStrength = jumpStrength;
        this.jumpStrength_isCustom = true;
        return this;
    }

    /**
     * Used for setting the entity scale attribute (0.0625 to 16.0, default 1.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param entityScale The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setEntityScale(float entityScale) {
        this.entityScale = entityScale;
        this.entityScale_isCustom = true;
        return this;
    }

    /**
     * Used for setting the entity step height attribute (0.0 to 10.0, default 0.6)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param stepHeight The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
        this.stepHeight_isCustom = true;
        return this;
    }

    /**
     * Used for setting the reinforcements spawn chance attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param reinforcementsChance The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setSpawnReinforcements(float reinforcementsChance) {
        this.spawnReinforcementsChance = reinforcementsChance;
        this.spawnReinforcementsChance_isCustom = true;
        return this;
    }

    /**
     * Used for setting the burn timer multiplier attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setBurningTimer(float value) {
        this.burningTime = value;
        this.burningTime_isCustom = true;
        return this;
    }

    /**
     * Used for setting the camera distance attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setCameraDistance(float value) {
        this.cameraDistance = value;
        this.cameraDistance_isCustom = true;
        return this;
    }

    /**
     * Used for setting the explosion knockback resistance attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setExplosionKnockbackResistance(float value) {
        this.explosionKnockbackResistance = value;
        this.explosionKnockbackResistance_isCustom = true;
        return this;
    }

    /**
     * Used for setting the fall damage multiplier attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setFallDamageMultiplier(float value) {
        this.fallDamageMultiplier = value;
        this.fallDamageMultiplier_isCustom = true;
        return this;
    }

    /**
     * Used for setting the gravity attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setGravity(float value) {
        this.gravity = value;
        this.gravity_isCustom = true;
        return this;
    }

    /**
     * Used for setting the movement efficiency attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setMovementEfficiency(float value) {
        this.movementEfficiency = value;
        this.movementEfficiency_isCustom = true;
        return this;
    }

    /**
     * Used for setting the oxygen bonus attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setOxygenBonus(float value) {
        this.oxygenBonus = value;
        this.oxygenBonus_isCustom = true;
        return this;
    }

    /**
     * Used for setting the safe fall distance attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setSafeFallDistance(float value) {
        this.safeFallDistance = value;
        this.safeFallDistance_isCustom = true;
        return this;
    }

    /**
     * Used for setting the temptation range attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setTemptRange(float value) {
        this.temptRange = value;
        this.temptRange_isCustom = true;
        return this;
    }

    /**
     * Used for setting the water movement efficiency attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setWaterMovementEfficiency(float value) {
        this.waterMovementEfficiency = value;
        this.waterMovementEfficiency_isCustom = true;
        return this;
    }

    /**
     * Used for setting the waypoint receive range attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setWaypointReceiveRange(float value) {
        this.waypointReceiveRange = value;
        this.waypointReceiveRange_isCustom = true;
        return this;
    }

    /**
     * Used for setting the waypoint transmit range attribute
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param value The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setWaypointTransmitRange(float value) {
        this.waypointTransmitRange = value;
        this.waypointTransmitRange_isCustom = true;
        return this;
    }


    /**
     * Used for setting the type of entity to spawn
     * @param entityType The EntityType you want to spawn
     * @return The current builder instance
     */
    public EntityBuilder setEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    /**
     * Used for setting the display name of the entity (Color is translated once applied so no need to do it before)
     * @param displayName The entity display name
     * @return The current builder instance
     */
    public EntityBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Used for setting the entity helmet
     * @param helmet The item you want to set for helmet
     * @return The current builder instance
     */
    public EntityBuilder setHelmet(ItemStack helmet) {
        this.helmet = helmet;
        return this;
    }

    /**
     * Used for setting the entity chestplate
     * @param chestplate The item you want to set for the chestplate (NOTE: Must be a chestplate item)
     * @return The current builder instance
     */
    public EntityBuilder setChestplate(ItemStack chestplate) {
        this.chestplate = chestplate;
        return this;
    }

    /**
     * Used for setting the entity leggings
     * @param leggings The item you want to set for the leggings (NOTE: Must be a legging item)
     * @return The current builder instance
     */
    public EntityBuilder setLeggings(ItemStack leggings) {
        this.leggings = leggings;
        return this;
    }

    /**
     * Used for setting the entity boots
     * @param boots The item you want to set for the boots (NOTE: Must be a boot item)
     * @return The current builder instance
     */
    public EntityBuilder setBoots(ItemStack boots) {
        this.boots = boots;
        return this;
    }

    /**
     * Used for setting the item in the entities main hand
     * @param mainHandItem The item you want to place in the main hand
     * @return The current builder instance
     */
    public EntityBuilder setMainHandItem(ItemStack mainHandItem) {
        this.mainHandItem = mainHandItem;
        return this;
    }

    /**
     * Used for setting the item in the entities offhand
     * @param offHandItem The item you want to place in the offhand
     * @return The current builder instance
     */
    public EntityBuilder setOffHandItem(ItemStack offHandItem) {
        this.offHandItem = offHandItem;
        return this;
    }

    /**
     * Used for setting the drop collection for entity death
     * @param drops The {@link RandomCollection} of ItemBuilder drops
     * @return The current builder instance
     */
    public EntityBuilder setDrops(RandomCollection<ItemBuilder> drops) {
        this.drops = drops;
        return this;
    }

    /**
     * Used for setting the drop collection for entity death
     * @param itemStackDrops The {@link RandomCollection} of ItemStack drops
     * @return The current builder instance
     */
    public EntityBuilder setDropsFromItemCollection(RandomCollection<ItemStack> itemStackDrops) {
        this.drops = itemStackDrops.cloneTo(ItemBuilder.class, ItemBuilder::fromItemStack);
        return this;
    }

    /**
     * Used for setting the map of metadata values (NOTE: I suggest using {@link EntityBuilder#setCustomNBTData(Map)})
     * @param metadataValues The map of metadata values
     * @return The current builder instance
     */
    public EntityBuilder setMetadataValues(Map<String, MetadataValue> metadataValues) {
        this.metadataValues = metadataValues;
        return this;
    }

    /**
     * Used for setting the custom NBTData map
     * @param customNBTData The map of custom NBTData
     * @return The current builder instance
     */
    public EntityBuilder setCustomNBTData(Map<String, Object> customNBTData) {
        this.customNBTData = customNBTData;
        return this;
    }

    /**
     * Used for setting a custom pathfinder goal
     * NOTE: THIS IS NOT ADDED YET
     * @param customPathfinderGoal The class of the pathfinder goal
     * @return The current builder instance
     */
    public EntityBuilder setCustomPathfinderGoal(Class<?> customPathfinderGoal) {
        this.customPathfinderGoal = customPathfinderGoal;
        return this;
    }

    /**
     * Used for spawning the entity in the world
     * @see EntityBuilder#spawn(Location) 
     * @param location The location you want to spawn the entity
     * @param world The world you want to spawn the entity in
     * @return The entity that has just been spawned
     */
    public Entity spawn(Location location, World world) {

        if (entityType == null || entityType.getEntityClass() == null)
            throw new EntityBuildException("Entity type cannot be null");
        if (!entityType.isSpawnable())
            throw new EntityBuildException("Entity type must be spawnable");
        if (!entityType.isAlive())
            throw new EntityBuildException("Entity type must be living");
        AtomicReference<Entity> ent = new AtomicReference<>();
        Entity entity = world.spawn(location, entityType.getEntityClass());
            LivingEntity entityLiving = (LivingEntity) entity;
            INMSEntityBuilder nmsBuilder = LuaCore.getNmsVersion().getClazz().getEntityBuilder(entityLiving);
            boolean hasName = this.displayName != null && !this.displayName.isEmpty();
            entityLiving.setCustomNameVisible(hasName);
            if (hasName)
                entityLiving.setCustomName(ContentUtils.color(this.displayName));

            if (armorBonus_isCustom) nmsBuilder.setArmorBonus(armorBonus);
            if (armorToughnessBonus_isCustom) nmsBuilder.setArmorToughnessBonus(armorToughnessBonus);
            if (attackDamageBonus_isCustom) nmsBuilder.setAttackDamageBonus(attackDamageBonus);
            if (attackKnockBack_isCustom) nmsBuilder.setAttackKnockBack(attackKnockBack);
            if (flyingSpeed_isCustom) nmsBuilder.setFlyingSpeed(flyingSpeed);
            if (followRange_isCustom) nmsBuilder.setFollowRange(followRange);
            if (knockBackResistance_isCustom) nmsBuilder.setKnockBackResistance(knockBackResistance);
            if (maxAbsorption_isCustom) nmsBuilder.setMaxAbsorption(maxAbsorption);
            if (maxHealth_isCustom) nmsBuilder.setMaxHealth(maxHealth);
            if (movementSpeed_isCustom) nmsBuilder.setMovementSpeed(movementSpeed);
            if (jumpStrength_isCustom) nmsBuilder.setJumpStrength(jumpStrength);
            if (entityScale_isCustom) nmsBuilder.setEntityScale(entityScale);
            if (stepHeight_isCustom) nmsBuilder.setStepHeight(stepHeight);
            if (spawnReinforcementsChance_isCustom) nmsBuilder.spawnReinforcements(spawnReinforcementsChance);
            if (burningTime_isCustom) nmsBuilder.setBurningTime(burningTime);
            if (cameraDistance_isCustom) nmsBuilder.setCameraDistance(cameraDistance);
            if (explosionKnockbackResistance_isCustom) nmsBuilder.setExplosionKnockBackResistance(explosionKnockbackResistance);
            if (fallDamageMultiplier_isCustom) nmsBuilder.setFallDamageMultiplier(fallDamageMultiplier);
            if (gravity_isCustom) nmsBuilder.setGravity(gravity);
            if (movementEfficiency_isCustom) nmsBuilder.setMovementEfficiency(movementEfficiency);
            if (oxygenBonus_isCustom) nmsBuilder.setOxygenBonus(oxygenBonus);
            if (safeFallDistance_isCustom) nmsBuilder.setSafeFallDistance(safeFallDistance);
            if (temptRange_isCustom) nmsBuilder.setTemptRange(temptRange);
            if (waterMovementEfficiency_isCustom) nmsBuilder.setWaterMoveEfficiency(waterMovementEfficiency);
            if (waypointReceiveRange_isCustom) nmsBuilder.setWaypointReceiveRange(waypointReceiveRange);
            if (waypointTransmitRange_isCustom) nmsBuilder.setWaypointTransmitRange(waypointTransmitRange);


            entityLiving = nmsBuilder.getEntity();
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
            ReadWriteNBT compound = nbt.getOrCreateCompound(LuaCore.getCompountName());
            compound.setBoolean("LuaCoreEntity", true);
            for (String nameSpace : customNBTData.keySet()) {
                Object content = customNBTData.get(nameSpace);
                NBTUtils.storeEntityNBTContent(compound, content, nameSpace);
            }
            if (!drops.isEmpty())
                NBTUtils.storeEntityNBTContent(compound, drops, "EntityDrops");
        });
        for (String metadataKey : metadataValues.keySet()) {
            entity.setMetadata(metadataKey, metadataValues.get(metadataKey));
        }
        return entity;
    }

    /**
     * Used for spawning the entity in the world
     * @see EntityBuilder#spawn(Location, World) 
     * @param location The location you want to spawn the entity (World must not be null)
     * @return The entity that has just been spawned
     */
    public Entity spawn(Location location) {
        if (location.getWorld() == null)
            throw new EntityBuildException("World cannot be null");
        return this.spawn(location, location.getWorld());
    }

}
