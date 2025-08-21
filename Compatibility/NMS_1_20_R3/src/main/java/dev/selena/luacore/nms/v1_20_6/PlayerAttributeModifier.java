package dev.selena.luacore.nms.v1_20_6;

import dev.selena.luacore.nms.INMSPlayerAttributeModifier;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerAttributeModifier implements INMSPlayerAttributeModifier {

    @Getter
    private final Player player;

    /**
     * Used for creating instance of
     *
     * @param player The player you want to set the attributes for
     */
    public PlayerAttributeModifier(Player player) {
        this.player = player;
    }

    @Override
    public INMSPlayerAttributeModifier setArmorBonus(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ARMOR)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setArmorToughness(float toughness) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)).setBaseValue(toughness);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackDamage(float damage) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(damage);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackKnockBack(float knockBack) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK)).setBaseValue(knockBack);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackSpeed(float speed) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)).setBaseValue(speed);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBlockBreakSpeed(float speed) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBurningTime(float multiplier) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setCameraDistance(float distance) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setExplosionKnockBackResistance(float resistance) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setFallDamageMultiplier(float multiplier) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setGravity(float gravity) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setKnockBackResistance(float resistance) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(resistance);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setLuck(float luck) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_LUCK)).setBaseValue(luck);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMaxAbsorption(float max) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_ABSORPTION)).setBaseValue(max);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMaxHealth(float max) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(max);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMovementSpeed(float speed) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setJumpStrength(float strength) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.HORSE_JUMP_STRENGTH)).setBaseValue(strength);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setEntityScale(float scale) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBlockInteractionRange(float range) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setEntityInteractionRange(float range) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setStepHeight(float height) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMiningEfficiency(float efficiency) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMovementEfficiency(float efficiency) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setOxygenBonus(float bonus) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSafeFallDistance(float distance) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSneakingSpeed(float speed) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSubmergedMiningSpeed(float speed) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSweepingDamageRatio(float ratio) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setWaterMoveEfficiency(float efficiency) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setWaypointReceiveRange(float range) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setWaypointTransmitRange(float range) {
        return this;
    }
}