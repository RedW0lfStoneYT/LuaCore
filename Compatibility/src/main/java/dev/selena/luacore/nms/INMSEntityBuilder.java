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
     * Used for setting the burning timer multiplier attribute (0.0 to 1024.0, default 1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setBurningTime(float bonus);

    /**
     * Used for setting the camera distance attribute (0.0 to 32.0, default 4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setCameraDistance(float bonus);

    /**
     * Used for setting the explosion knockback resistance attribute (0.0 to 1.0, default 0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setExplosionKnockBackResistance(float bonus);

    /**
     * Used for setting the fall damage multiplier attribute (0.0 to 100.0, default 1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setFallDamageMultiplier(float bonus);

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
     * Used for setting the gravity attribute (-1.0 to 1.0, default 0.08)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setGravity(float bonus);
    /**
     * Used for setting the knock back resistance attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setKnockBackResistance(float bonus);
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
     * Used for setting the jump strength attribute (0.0 to 2.0, default 0.7)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setJumpStrength(float bonus);
    /**
     * Used for setting the entity scale attribute (0.0625 to 16.0, default 1.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setEntityScale(float bonus);
    /**
     * Used for setting the entity step height attribute (0.0 to 10.0, default 0.6)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param height The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setStepHeight(float height);
    /**
     * Used for setting the spawn reinforcements attribute (0.0 to 1.0, default is random between 0.0 and 0.1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param spawn The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder spawnReinforcements(float spawn);

    /**
     * Used for setting the movement efficiency attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setMovementEfficiency(float bonus);

    /**
     * Used for setting the oxygen bonus attribute (0.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setOxygenBonus(float bonus);

    /**
     * Used for setting the safe fall distance attribute (-1024.0 to 1024.0, default 3.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setSafeFallDistance(float bonus);

    /**
     * Used for setting the temptation range attribute (0.0 to 2048.0, default 10.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setTemptRange(float bonus);

    /**
     * Used for setting the water move efficiency attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setWaterMoveEfficiency(float bonus);

    /**
     * Used for setting the waypoint receive range attribute
     * (0.0 to 60,000,000.0, default 60,000,000.0 for players and 0.0 for other mobs)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setWaypointReceiveRange(float bonus);

    /**
     * Used for setting the waypoint transmit range attribute
     * (0.0 to 60,000,000.0, default 60,000,000.0 for players and 0.0 for other mobs)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSEntityBuilder setWaypointTransmitRange(float bonus);

    /**
     * Used for getting the altered entity
     * @return The LivingEntity
     */
    LivingEntity getEntity();
}
