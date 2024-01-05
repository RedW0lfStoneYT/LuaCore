package dev.selena.luacore.utils.entities;

import lombok.Setter;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;

import java.util.Map;

public class EntityBuilder {
    
    @Setter
    private double
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
            entityInteractWithEntityDistance;
    @Setter
    private boolean zombieReinforcements;
    @Setter
    private EntityType entityType;
    @Setter
    private String displayName;
    @Setter
    private PotionEffect[] potionEffects;
    @Setter
    private ItemStack
            helmet,
            chestplate,
            boots,
            leggings,
            mainHandItem,
            offHandItem;
    @Setter
    private ItemStack[] drops;
    @Setter
    private FixedMetadataValue[] metadataValues;
    @Setter
    // Not to sure how well this will work yet, but it should allow the entity to keep the data on reload
    private Map<String, Object> customNBTDatal;
    @Setter
    // This will require NMS reflections woo :/
    // But hey, the person calling this method will also need to
    private Class<?> customPathfinderGoal;


    public EntityBuilder() {

    }

    public EntityBuilder(EntityType type) {

    }


}
