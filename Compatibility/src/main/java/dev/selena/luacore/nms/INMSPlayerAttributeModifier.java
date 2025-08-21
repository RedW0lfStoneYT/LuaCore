package dev.selena.luacore.nms;

public interface INMSPlayerAttributeModifier {

    /**
     * Used for setting the armor bonus attribute (0.0 to 30.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setArmorBonus(float bonus);

    /**
     * Resets the armor bonus attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetArmorBonus() {
        return setArmorBonus(0.0f);
    }

    /**
     * Used for setting the armor toughness attribute (0.0 to 20.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param toughness The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setArmorToughness(float toughness);

    /**
     * Resets the armor toughness attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetArmorToughness() {
        return setArmorToughness(0.0f);
    }

    /**
     * Used for setting the attack damage attribute (0.0 to 2048.0 default, 1.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param damage The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setAttackDamage(float damage);

    /**
     * Resets the attack damage attribute to 1.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetAttackDamage() {
        return setAttackDamage(1.0f);
    }

    /**
     * Used for setting the attack knock back attribute (0.0 to 5.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param knockBack The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setAttackKnockBack(float knockBack);

    /**
     * Resets the attack knock back attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetAttackKnockBack() {
        return setKnockBackResistance(0.0f);
    }

    /**
     * Used for setting the attack speed attribute (0.0 to 1024.0, default 4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param speed The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setAttackSpeed(float speed);

    /**
     * Resets the attack speed attribute to 4.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetAttackSpeed() {
        return setAttackSpeed(4.0f);
    }

    /**
     * Used for setting the block breaking speed attribute (0.0 to 1024.0, default 1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param speed The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setBlockBreakSpeed(float speed);

    /**
     * Resets the block break speed multiplier attribute to 1.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetBlockBreakSpeed() {
        return setBlockBreakSpeed(1.0f);
    }

    /**
     * Used for setting the burning timer multiplier attribute (0.0 to 1024.0, default 1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param multiplier The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setBurningTime(float multiplier);

    /**
     * Resets the burning time multiplier attribute to 1.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetBurningTime() {
        return setBurningTime(1.0f);
    }

    /**
     * Used for setting the camera distance attribute (0.0 to 32.0, default 4)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param distance The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setCameraDistance(float distance);

    /**
     * Resets the camera distance attribute to 4.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetCameraDistance() {
        return setCameraDistance(4.0f);
    }

    /**
     * Used for setting the explosion knock back resistance attribute (0.0 to 1.0, default 0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param resistance The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setExplosionKnockBackResistance(float resistance);

    /**
     * Resets the explosion knock back resistance attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetExplosionKnockBackResistance() {
        return setExplosionKnockBackResistance(0.0f);
    }

    /**
     * Used for setting the fall damage multiplier attribute (0.0 to 100.0, default 1.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param multiplier The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setFallDamageMultiplier(float multiplier);

    /**
     * Resets the fall damage multiplier attribute to 1.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetFallDamageMultiplier() {
        return setFallDamageMultiplier(1.0f);
    }

    /**
     * Used for setting the gravity attribute (-1.0 to 1.0, default 0.08)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param gravity The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setGravity(float gravity);

    /**
     * Resets the gravity attribute to 0.08
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetGravity() {
        return setGravity(0.08f);
    }

    /**
     * Used for setting the knock back resistance attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param resistance The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setKnockBackResistance(float resistance);

    /**
     * Resets the knock back resistance attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetKnockBackResistance() {
        return setKnockBackResistance(0.0f);
    }

    /**
     * Used for setting the luck attribute (-1024.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param luck The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setLuck(float luck);

    /**
     * Resets the luck attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetLuck() {
        return setLuck(0.0f);
    }

    /**
     * Used for setting the maximum absorption attribute (0.0 to 2048.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param max The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setMaxAbsorption(float max);

    /**
     * Resets the max absorption attribute to
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetMaxAbsorption() {
        return setMaxAbsorption(0.0f);
    }

    /**
     * Used for setting the maximum health attribute (0.0 to 1024.0, default 20.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param max The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setMaxHealth(float max);

    /**
     * Resets the max health attribute to 20.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetMaxHealth() {
        return setMaxHealth(20.0f);
    }

    /**
     * Used for setting the entity movement speed attribute (0.0 to 1024.0, default 0.1)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param speed The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setMovementSpeed(float speed);

    /**
     * Resets the movement speed attribute to 0.1
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetMovementSpeed() {
        return setMovementSpeed(0.1f);
    }

    /**
     * Used for setting the jump strength attribute (0.0 to 32.0, default 0.42)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param strength The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setJumpStrength(float strength);

    /**
     * Resets the jump strength attribute to 0.42
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetJumpStrength() {
        return setJumpStrength(0.42f);
    }

    /**
     * Used for setting the entity scale attribute (0.0625 to 16.0, default 1.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param scale The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setEntityScale(float scale);

    /**
     * Resets the entity scale attribute to 1.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetEntityScale() {
        return setEntityScale(1.0f);
    }

    /**
     * Used to set the entity interaction with block distance attribute (0.0 to 64, default 4.5)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param range The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setBlockInteractionRange(float range);

    /**
     * Resets the block interaction range attribute to 4.5
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetBlockInteractionRange() {
        return setBlockInteractionRange(4.5f);
    }

    /**
     * Used to set the entity interaction with entity distance attribute (0.0 to 64.0, default 3.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param range The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setEntityInteractionRange(float range);

    /**
     * Resets the entity interaction range attribute to 3.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetEntityInteractionRange() {
        return setEntityInteractionRange(3.0f);
    }

    /**
     * Used for setting the entity step height attribute (0.0 to 10.0, default 0.6)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param height The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setStepHeight(float height);

    /**
     * Resets the step height attribute to 0.6
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetStepHeight() {
        return setStepHeight(0.6f);
    }

    /**
     * Used for setting the mining efficiency attribute (0.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param efficiency The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setMiningEfficiency(float efficiency);

    /**
     * Resets the mining efficiency attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetMiningEfficiency() {
        return setMiningEfficiency(0.0f);
    }

    /**
     * Used for setting the movement efficiency attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param efficiency The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setMovementEfficiency(float efficiency);

    /**
     * Resets the movement efficiency attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetMovementEfficiency() {
        return setMovementEfficiency(0.0f);
    }

    /**
     * Used for setting the oxygen bonus attribute (0.0 to 1024.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param bonus The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setOxygenBonus(float bonus);

    /**
     * Resets the oxygen bonus attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetOxygenBonus() {
        return setOxygenBonus(0.0f);
    }

    /**
     * Used for setting the safe fall distance attribute (-1024.0 to 1024.0, default 3.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param distance The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setSafeFallDistance(float distance);

    /**
     * Resets the safe fall distance attribute to 3.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetSafeFallDistance() {
        return setSafeFallDistance(3.0f);
    }

    /**
     * Used for setting the sneaking speed attribute (0.0 to 1.0, default 0.3)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param speed The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setSneakingSpeed(float speed);

    /**
     * Resets the sneaking speed attribute to 0.3
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetSneakingSpeed() {
        return setSneakingSpeed(0.3f);
    }

    /**
     * Used for setting the submerged mining speed attribute (0.0 to 20.0, default 0.2)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param speed The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setSubmergedMiningSpeed(float speed);

    /**
     * Resets the submerged mining speed attribute to 0.2
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetSubmergedMiningSpeed() {
        return setSubmergedMiningSpeed(0.2f);
    }

    /**
     * Used for setting the sweeping damage ratio attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param ratio The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setSweepingDamageRatio(float ratio);

    /**
     * Resets the sweeping damage ratio attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetSweepingDamageRatio() {
        return setSweepingDamageRatio(0.0f);
    }

    /**
     * Used for setting the water move efficiency attribute (0.0 to 1.0, default 0.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param efficiency The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setWaterMoveEfficiency(float efficiency);

    /**
     * Resets the water movement efficiency attribute to 0.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetWaterMoveEfficiency() {
        return setWaterMoveEfficiency(0.0f);
    }

    /**
     * Used for setting the waypoint receive range attribute
     * (0.0 to 60,000,000.0, default 60,000,000.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param range The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setWaypointReceiveRange(float range);

    /**
     * Resets the waypoint receive range attribute to 60,000,000.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetWaypointReceiveRange() {
        return setWaypointReceiveRange(60000000.0f);
    }

    /**
     * Used for setting the waypoint transmit range attribute
     * (0.0 to 60,000,000.0, default 60,000,000.0)
     * @see <a href="https://minecraft.wiki/w/Attribute">Minecraft attributes wiki</a>
     * @param range The value for the attribute
     * @return the current NMS player modifier
     */
    INMSPlayerAttributeModifier setWaypointTransmitRange(float range);

    /**
     * Resets the waypoint transmit range attribute to 60,000,000.0
     * @return the current NMS player modifier
     */
    default INMSPlayerAttributeModifier resetWaypointTransmitRange() {
        return setWaypointReceiveRange(60000000.0f);
    }
    
}
