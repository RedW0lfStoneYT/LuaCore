package dev.selena.luacore.nms;

import org.bukkit.entity.LivingEntity;

/**
 * Interface for the version-specific EntityBuilder methods
 */
public interface INMSEntityBuilder {

    /**
     * Used for setting the armor bonus attribute (0.0 to 30.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setArmorBonus(float bonus);
    /**
     * Used for setting the armor toughness bonus attribute (0.0 to 20.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setArmorToughnessBonus(float bonus);
    /**
     * Used for setting the attack damage bonus (0.0 to 2048.0 default, 2.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setAttackDamageBonus(float bonus);
    /**
     * Used for setting the attack knock back attribute (0.0 to 5.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setAttackKnockBack(float bonus);
    /**
     * Used for setting the attack speed attribute (0.0 to 1024.0, default 4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setAttackSpeed(float bonus);
    /**
     * Used for setting the flight speed attribute (0.0 to 1024.0, default 0.4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setFlyingSpeed(float bonus);
    /**
     * Used for setting the entity follow range attribute (0.0 to 2048, default 32.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param range The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setFollowRange(float range);
    /**
     * Used for setting the knock back resistance attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setKnockBackResistance(float bonus);
    /**
     * Used for setting the luck attribute (-1024.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setLuck(float bonus);
    /**
     * Used for setting the maximum absorption attribute (0.0 to 2048.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param max The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setMaxAbsorption(float max);
    /**
     * Used for setting the maximum health attribute (0.0 to 1024.0, default 20.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param max The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setMaxHealth(float max);
    /**
     * Used for setting the entity movement speed attribute (0.0 to 1024.0, default depends on entity)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setMovementSpeed(float bonus);
    /**
     * Used for setting the horse jump strength attribute (0.0 to 2.0, default 0.7)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setHorseJumpStrength(float bonus);
    /**
     * Used for setting the entity scale attribute (0.0625 to 16.0, default 1.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setEntityScale(float bonus);
    /**
     * Used to set the entity interaction with block distance attribute (0.0 to 64, default 4.5)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setEntityInteractWithBlockDistance(float bonus);
    /**
     * Used to set the entity interaction with entity distance attribute (0.0 to 64.0, default 3.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setEntityInteractWithLivingEntityDistance(float bonus);
    /**
     * Used for setting the entity step height attribute (0.0 to 10.0, default 0.6)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param height The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setStepHeight(float height);
    /**
     * Used for setting the zombie reinforcements attribute (true or false, default false)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param spawn The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder spawnZombieReinforcements(boolean spawn);
    /**
     * Used for setting the zombie reinforcements attribute as double (1.0 or 0.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param spawn The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder spawnZombieReinforcements(double spawn);

    /**
     * Used for getting the altered entity
     * @return The LivingEntity
     */
    LivingEntity getEntity();
}
