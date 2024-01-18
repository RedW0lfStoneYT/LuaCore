package dev.selena.luacore.nms;

import org.bukkit.entity.LivingEntity;

public interface INMSEntityBuilder {

    INMSEntityBuilder setArmorBonus(float bonus);
    INMSEntityBuilder setArmorToughnessBonus(float bonus);
    INMSEntityBuilder setAttackDamageBonus(float bonus);
    INMSEntityBuilder setAttackKnockBack(float bonus);
    INMSEntityBuilder setAttackSpeed(float bonus);
    INMSEntityBuilder setFlyingSpeed(float bonus);
    INMSEntityBuilder setFollowRange(float range);
    INMSEntityBuilder setKnockBackResistance(float bonus);
    INMSEntityBuilder setLuck(float bonus);
    INMSEntityBuilder setMaxAbsorption(float max);
    INMSEntityBuilder setMaxHealth(float max);
    INMSEntityBuilder setMovementSpeed(float bonus);
    INMSEntityBuilder setHorseJumpStrength(float bonus);
    INMSEntityBuilder setEntityScale(float bonus);
    INMSEntityBuilder setEntityInteractWithBlockDistance(float bonus);
    INMSEntityBuilder setEntityInteractWithLivingEntityDistance(float bonus);
    INMSEntityBuilder setStepHeight(float height);
    INMSEntityBuilder spawnZombieReinforcements(boolean spawn);
    INMSEntityBuilder spawnZombieReinforcements(double spawn);

    LivingEntity getEntity();
}
