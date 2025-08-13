package dev.selena.luacore.nms.v1_20_R2;

import dev.selena.luacore.nms.INMSEntityBuilder;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

/**
 * NMS Entity builder for 1.20.2
 */
public class EntityBuilder implements INMSEntityBuilder {



    @Getter
    private final LivingEntity entity;

    /**
     * Used for creating instance of 1.20.2 entity builder
     * @param entity The entity you want to alter
     */
    public EntityBuilder(LivingEntity entity) {
        this.entity = entity;
    }


    @Override
    public INMSEntityBuilder setArmorBonus(float bonus) {
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setArmorToughnessBonus(float bonus) {
        entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setAttackDamageBonus(float bonus) {
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setAttackKnockBack(float bonus) {
        entity.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setBurningTime(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setCameraDistance(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setExplosionKnockBackResistance(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setFallDamageMultiplier(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setFlyingSpeed(float bonus) {
        entity.getAttribute(Attribute.GENERIC_FLYING_SPEED).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setFollowRange(float range) {
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(range);
        return this;
    }

    @Override
    public INMSEntityBuilder setGravity(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setKnockBackResistance(float bonus) {
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setMaxAbsorption(float bonus) {
        entity.getAttribute(Attribute.GENERIC_MAX_ABSORPTION).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setMaxHealth(float bonus) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setMovementSpeed(float bonus) {
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setJumpStrength(float bonus) {
        entity.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setEntityScale(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setStepHeight(float height) {
        return this;
    }

    @Override
    public INMSEntityBuilder spawnReinforcements(float spawn) {
        try {
            entity.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(spawn);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setMovementEfficiency(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setOxygenBonus(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setSafeFallDistance(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setTemptRange(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setWaterMoveEfficiency(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setWaypointReceiveRange(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setWaypointTransmitRange(float bonus) {
        return this;
    }

}
