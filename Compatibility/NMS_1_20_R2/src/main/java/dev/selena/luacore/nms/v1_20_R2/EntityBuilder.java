package dev.selena.luacore.nms.v1_20_R2;

import dev.selena.luacore.nms.INMSEntityBuilder;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

public class EntityBuilder implements INMSEntityBuilder {


    @Getter
    private final LivingEntity entity;
    
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
    public INMSEntityBuilder setAttackSpeed(float bonus) {
        entity.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(bonus);
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
    public INMSEntityBuilder setKnockBackResistance(float bonus) {
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setLuck(float bonus) {
        entity.getAttribute(Attribute.GENERIC_LUCK).setBaseValue(bonus);
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
    public INMSEntityBuilder setHorseJumpStrength(float bonus) {
        entity.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(bonus);
        return this;
    }

    @Override
    public INMSEntityBuilder setEntityScale(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setEntityInteractWithBlockDistance(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setEntityInteractWithLivingEntityDistance(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setStepHeight(float height) {
        return this;
    }

    @Override
    public INMSEntityBuilder spawnZombieReinforcements(boolean spawn) {
        entity.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(spawn ? 1.0 : 0.0);
        return this;
    }

    @Override
    public INMSEntityBuilder spawnZombieReinforcements(double spawn) {
        entity.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(spawn);
        return this;
    }
}
