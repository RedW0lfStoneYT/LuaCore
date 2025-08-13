package dev.selena.luacore.nms.v1_21_5;

import dev.selena.luacore.nms.INMSPlayerAttributeModifier;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
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
            Objects.requireNonNull(player.getAttribute(Attribute.ARMOR)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setArmorToughnessBonus(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ARMOR_TOUGHNESS)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackDamageBonus(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ATTACK_DAMAGE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackKnockBack(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ATTACK_KNOCKBACK)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackSpeed(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ATTACK_SPEED)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBlockBreakSpeed(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.BLOCK_BREAK_SPEED)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBurningTime(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.BURNING_TIME)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setCameraDistance(float bonus) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setExplosionKnockBackResistance(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.EXPLOSION_KNOCKBACK_RESISTANCE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setFallDamageMultiplier(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.FALL_DAMAGE_MULTIPLIER)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setGravity(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GRAVITY)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setKnockBackResistance(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.KNOCKBACK_RESISTANCE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setLuck(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.LUCK)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMaxAbsorption(float max) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.MAX_ABSORPTION)).setBaseValue(max);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMaxHealth(float max) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(max);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMovementSpeed(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setJumpStrength(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.JUMP_STRENGTH)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setEntityScale(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SCALE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBlockInteractionRange(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setEntityInteractionRange(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setStepHeight(float height) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.STEP_HEIGHT)).setBaseValue(height);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMiningEfficiency(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.MINING_EFFICIENCY)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMovementEfficiency(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.MOVEMENT_EFFICIENCY)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setOxygenBonus(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.OXYGEN_BONUS)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSafeFallDistance(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SAFE_FALL_DISTANCE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSneakingSpeed(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SNEAKING_SPEED)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSubmergedMiningSpeed(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SUBMERGED_MINING_SPEED)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSweepingDamageRatio(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SWEEPING_DAMAGE_RATIO)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setWaterMoveEfficiency(float bonus) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setWaypointReceiveRange(float bonus) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setWaypointTransmitRange(float bonus) {
        return this;
    }
}
