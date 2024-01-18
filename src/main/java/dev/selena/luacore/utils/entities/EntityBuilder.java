package dev.selena.luacore.utils.entities;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.exceptions.entity.EntityBuildException;
import dev.selena.luacore.nms.INMSEntityBuilder;
import dev.selena.luacore.utils.RandomCollection;
import dev.selena.luacore.utils.items.ItemUtils;
import dev.selena.luacore.utils.items.NBTUtils;
import dev.selena.luacore.utils.text.ContentUtils;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/**
 * THIS CLASS IS NOT AT ALL READY YET PLEASE DO NOT USE
 */
public class EntityBuilder {

    @Setter
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
    @Setter
    private boolean zombieReinforcements;
    @Setter
    private EntityType entityType;
    @Setter
    private String displayName;
    @Setter
    private List<PotionEffect> potionEffects;
    @Setter
    private ItemStack
            helmet,
            chestplate,
            boots,
            leggings,
            mainHandItem,
            offHandItem;
    @Setter
    private RandomCollection<ItemStack> drops;
    /**
     * Used for adding metadata to the entity (NOTE: I suggest using the custom NBT data)
     */
    @Setter
    private Map<String, MetadataValue> metadataValues;
    @Setter
    // Not to sure how well this will work yet, but it should allow the entity to keep the data on reload
    private Map<String, Object> customNBTData;
    @Setter
    // This will require NMS reflections woo :/
    // But hey, the person calling this method will also need to
    private Class<?> customPathfinderGoal;


    public EntityBuilder() {
        this(null);
    }

    public EntityBuilder(EntityType type) {
        this.potionEffects = new ArrayList<>();
        this.drops = new RandomCollection<>();
        this.metadataValues = new TreeMap<>();
        this.customNBTData = new TreeMap<>();
        this.entityType = type;
    }

    public EntityBuilder addPotionEffect(PotionEffect effect) {
        this.potionEffects.add(effect);
        return this;
    }

    public EntityBuilder addPotionEffects(PotionEffect ... effects) {
        this.potionEffects.addAll(Arrays.asList(effects));
        return this;
    }

    public EntityBuilder addPotionEffects(List<PotionEffect> effects) {
        this.potionEffects.addAll(effects);
        return this;
    }

    public EntityBuilder addDrop(double chance, ItemStack item) {
        this.drops.add(chance, item);
        return this;
    }

    public EntityBuilder addDrops(Map<ItemStack, Double> drops) {
        this.drops.addAll(drops);
        return this;
    }

    public EntityBuilder addMetaDataValue(String key, MetadataValue metadata) {
        this.metadataValues.put(key, metadata);
        return this;
    }

    public EntityBuilder addMetaDataValues(Map<String, MetadataValue> metadataValues) {
        this.metadataValues.putAll(metadataValues);
        return this;
    }

    public EntityBuilder addCustomNBTData(String key, Object cls) {
        this.customNBTData.put(key, cls);
        return this;
    }

    public EntityBuilder addCustomNBTDataValues(Map<String, Object> values) {
        this.customNBTData.putAll(values);
        return this;
    }

    public void spawn(Location location, World world) {
        if (entityType == null || entityType.getEntityClass() == null)
            throw new EntityBuildException("Entity type cannot be null");
        if (!entityType.isSpawnable())
            throw new EntityBuildException("Entity type must be spawnable");
        if (!entityType.isAlive())
            throw new EntityBuildException("Entity type must be living");
        world.spawn(location, entityType.getEntityClass(), entity -> {
            LivingEntity entityLiving = (LivingEntity) entity;
            INMSEntityBuilder builder = LuaCore.getNmsVersion().getClazz().getEntityBuilder(entityLiving);
            boolean hasName = !this.displayName.isEmpty();
            entityLiving.setCustomNameVisible(hasName);
            if (hasName)
                entityLiving.setCustomName(ContentUtils.color(this.displayName));
            builder.setArmorBonus(armorBonus)
                    .setArmorToughnessBonus(armorToughnessBonus)
                    .setAttackDamageBonus(attackDamageBonus)
                    .setAttackKnockBack(attackKnockBack)
                    .setAttackSpeed(attackSpeed)
                    .setFlyingSpeed(flyingSpeed)
                    .setFollowRange(followRange)
                    .setKnockBackResistance(knockBackResistance)
                    .setLuck(luck)
                    .setMaxAbsorption(maxAbsorption)
                    .setMaxHealth(maxHealth)
                    .setMovementSpeed(movementSpeed)
                    .setHorseJumpStrength(horseJumpStrength)
                    .setEntityScale(entityScale)
                    .setEntityInteractWithBlockDistance(entityInteractWithBlockDistance)
                    .setEntityInteractWithLivingEntityDistance(entityInteractWithEntityDistance)
                    .setStepHeight(stepHeight)
                    .spawnZombieReinforcements(zombieReinforcements);
            entityLiving = builder.getEntity();
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
            NBTEntity nbtEntity = new NBTEntity(entityLiving);
            NBTCompound compound = nbtEntity.getOrCreateCompound(LuaCore.getCompountName());
            for (String nameSpace : customNBTData.keySet()) {
                Object content = customNBTData.get(nameSpace);
                NBTUtils.storeNBTContent(compound, content, nameSpace);
            }
            if (!drops.isEmpty())
                NBTUtils.storeNBTContent(compound, drops, "EntityDrops");
            nbtEntity.setBoolean("LuaCoreEntity", true);
            for (String metadataKey : metadataValues.keySet()) {
                entityLiving.setMetadata(metadataKey, metadataValues.get(metadataKey));
            }
        });
    }

    public void spawn(Location location) {
        if (location.getWorld() == null)
            throw new EntityBuildException("World cannot be null");
        this.spawn(location, location.getWorld());
    }


}
