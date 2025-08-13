package dev.selena.luacore.nms;

public interface INMSPlayerAttributeModifier {

    /**
     * Used for setting the armor bonus attribute (0.0 to 30.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setArmorBonus(float bonus);
    /**
     * Used for setting the armor toughness bonus attribute (0.0 to 20.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setArmorToughnessBonus(float bonus);
    /**
     * Used for setting the attack damage bonus (0.0 to 2048.0 default, 2.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setAttackDamageBonus(float bonus);
    /**
     * Used for setting the attack knock back attribute (0.0 to 5.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setAttackKnockBack(float bonus);
    /**
     * Used for setting the attack speed attribute (0.0 to 1024.0, default 4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setAttackSpeed(float bonus);

    /**
     * Used for setting the block breaking speed attribute (0.0 to 1024.0, default 1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setBlockBreakSpeed(float bonus);

    /**
     * Used for setting the burning timer multiplier attribute (0.0 to 1024.0, default 1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setBurningTime(float bonus);

    /**
     * Used for setting the camera distance attribute (0.0 to 32.0, default 4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setCameraDistance(float bonus);

    /**
     * Used for setting the explosion knockback resistance attribute (0.0 to 1.0, default 0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setExplosionKnockBackResistance(float bonus);

    /**
     * Used for setting the fall damage multiplier attribute (0.0 to 100.0, default 1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setFallDamageMultiplier(float bonus);

    /**
     * Used for setting the gravity attribute (-1.0 to 1.0, default 0.08)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setGravity(float bonus);
    /**
     * Used for setting the knock back resistance attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setKnockBackResistance(float bonus);
    /**
     * Used for setting the luck attribute (-1024.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setLuck(float bonus);
    /**
     * Used for setting the maximum absorption attribute (0.0 to 2048.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param max The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setMaxAbsorption(float max);
    /**
     * Used for setting the maximum health attribute (0.0 to 1024.0, default 20.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param max The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setMaxHealth(float max);
    /**
     * Used for setting the entity movement speed attribute (0.0 to 1024.0, default depends on entity)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setMovementSpeed(float bonus);
    /**
     * Used for setting the jump strength attribute (0.0 to 2.0, default 0.7)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setJumpStrength(float bonus);
    /**
     * Used for setting the entity scale attribute (0.0625 to 16.0, default 1.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setEntityScale(float bonus);
    /**
     * Used to set the entity interaction with block distance attribute (0.0 to 64, default 4.5)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setBlockInteractionRange(float bonus);
    /**
     * Used to set the entity interaction with entity distance attribute (0.0 to 64.0, default 3.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setEntityInteractionRange(float bonus);
    /**
     * Used for setting the entity step height attribute (0.0 to 10.0, default 0.6)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param height The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setStepHeight(float height);

    /**
     * Used for setting the mining efficiency attribute (0.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setMiningEfficiency(float bonus);

    /**
     * Used for setting the movement efficiency attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setMovementEfficiency(float bonus);

    /**
     * Used for setting the oxygen bonus attribute (0.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setOxygenBonus(float bonus);

    /**
     * Used for setting the safe fall distance attribute (-1024.0 to 1024.0, default 3.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setSafeFallDistance(float bonus);

    /**
     * Used for setting the sneaking speed attribute (0.0 to 1.0, default 0.3)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setSneakingSpeed(float bonus);

    /**
     * Used for setting the submerged mining speed attribute (0.0 to 20.0, default 0.2)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setSubmergedMiningSpeed(float bonus);

    /**
     * Used for setting the sweeping damage ratio attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setSweepingDamageRatio(float bonus);

    /**
     * Used for setting the water move efficiency attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setWaterMoveEfficiency(float bonus);

    /**
     * Used for setting the waypoint receive range attribute
     * (0.0 to 60,000,000.0, default 60,000,000.0 for players and 0.0 for other mobs)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setWaypointReceiveRange(float bonus);

    /**
     * Used for setting the waypoint transmit range attribute
     * (0.0 to 60,000,000.0, default 60,000,000.0 for players and 0.0 for other mobs)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS entity builder
     */
    INMSPlayerAttributeModifier setWaypointTransmitRange(float bonus);
    
}
