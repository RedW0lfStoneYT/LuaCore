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
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * THIS CLASS IS NOT AT ALL READY YET PLEASE DO NOT USE
 */

public class EntityBuilder {

    // TODO Add default value and range to all attribute documentation

    private float
            armorBonus,
            armorToughnessBonus,
            attackDamageBonus,
            attackKnockBack,
            attackSpeed,
            flyingSpeed,
            followRange,
            knockBackResistance,
            luck,
            maxAbsorption,
            maxHealth,
            movementSpeed,
            horseJumpStrength,
            // Preparing for 1.20.5
            entityScale,
            entityInteractWithBlockDistance,
            entityInteractWithEntityDistance,
            stepHeight;
    private boolean armorBonus_isCustom = false,
            armorToughnessBonus_isCustom = false,
            attackDamageBonus_isCustom = false,
            attackKnockBack_isCustom = false,
            attackSpeed_isCustom = false,
            flyingSpeed_isCustom = false,
            followRange_isCustom = false,
            knockBackResistance_isCustom = false,
            luck_isCustom = false,
            maxAbsorption_isCustom = false,
            maxHealth_isCustom = false,
            movementSpeed_isCustom = false,
            horseJumpStrength_isCustom = false,
            // Preparing for 1.20. = false5
            entityScale_isCustom = false,
            entityInteractWithBlockDistance_isCustom = false,
            entityInteractWithEntityDistance_isCustom = false,
            stepHeight_isCustom = false,
            zombieReinforcements_isCustom = false;
    private boolean zombieReinforcements;
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
     * Used for setting the attack speed attribute (0.0 to 1024.0, default 4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param attackSpeed The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
        this.attackSpeed_isCustom = true;
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
     * Used for setting the luck attribute (-1024.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param luck The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setLuck(float luck) {
        this.luck = luck;
        this.luck_isCustom = true;
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
     * @param horseJumpStrength The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setHorseJumpStrength(float horseJumpStrength) {
        this.horseJumpStrength = horseJumpStrength;
        this.horseJumpStrength_isCustom = true;
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
     * Used to set the entity interaction with block distance attribute (0.0 to 64, default 4.5)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param entityInteractWithBlockDistance The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setEntityInteractWithBlockDistance(float entityInteractWithBlockDistance) {
        this.entityInteractWithBlockDistance = entityInteractWithBlockDistance;
        this.entityInteractWithBlockDistance_isCustom = true;
        return this;
    }

    /**
     * Used to set the entity interaction with entity distance attribute (0.0 to 64.0, default 3.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param entityInteractWithEntityDistance The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setEntityInteractWithEntityDistance(float entityInteractWithEntityDistance) {
        this.entityInteractWithEntityDistance = entityInteractWithEntityDistance;
        this.entityInteractWithEntityDistance_isCustom = true;
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
     * Used for setting the zombie reinforcements attribute (true or false, default false)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param zombieReinforcements The value for the attribute
     * @return The current builder instance
     */
    public EntityBuilder setZombieReinforcements(boolean zombieReinforcements) {
        this.zombieReinforcements = zombieReinforcements;
        this.zombieReinforcements_isCustom = true;
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
        // Sadge I missed the functional entity creation stuff
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
            if (attackSpeed_isCustom) nmsBuilder.setAttackSpeed(attackSpeed);
            if (flyingSpeed_isCustom) nmsBuilder.setFlyingSpeed(flyingSpeed);
            if (followRange_isCustom) nmsBuilder.setFollowRange(followRange);
            if (knockBackResistance_isCustom) nmsBuilder.setKnockBackResistance(knockBackResistance);
            if (luck_isCustom) nmsBuilder.setLuck(luck);
            if (maxAbsorption_isCustom) nmsBuilder.setMaxAbsorption(maxAbsorption);
            if (maxHealth_isCustom) nmsBuilder.setMaxHealth(maxHealth);
            if (movementSpeed_isCustom) nmsBuilder.setMovementSpeed(movementSpeed);
            if (horseJumpStrength_isCustom) nmsBuilder.setHorseJumpStrength(horseJumpStrength);
            if (entityScale_isCustom) nmsBuilder.setEntityScale(entityScale);
            if (entityInteractWithBlockDistance_isCustom) nmsBuilder.setEntityInteractWithBlockDistance(entityInteractWithBlockDistance);
            if (entityInteractWithEntityDistance_isCustom) nmsBuilder.setEntityInteractWithLivingEntityDistance(entityInteractWithEntityDistance);
            if (stepHeight_isCustom) nmsBuilder.setStepHeight(stepHeight);
            if (zombieReinforcements_isCustom) nmsBuilder.spawnZombieReinforcements(zombieReinforcements);

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
