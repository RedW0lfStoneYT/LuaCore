package dev.selena.luacore.nms.v1_21_5;

import dev.selena.luacore.nms.INMSPlayerAttributeModifier;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerAttributeModifier implements INMSPlayerAttributeModifier {

    @Getter
    private final Player player;

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
    public INMSPlayerAttributeModifier setArmorToughness(float toughness) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ARMOR_TOUGHNESS)).setBaseValue(toughness);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackDamage(float damage) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ATTACK_DAMAGE)).setBaseValue(damage);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackKnockBack(float knockBack) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ATTACK_KNOCKBACK)).setBaseValue(knockBack);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setAttackSpeed(float speed) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ATTACK_SPEED)).setBaseValue(speed);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBlockBreakSpeed(float speed) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.BLOCK_BREAK_SPEED)).setBaseValue(speed);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBurningTime(float multiplier) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.BURNING_TIME)).setBaseValue(multiplier);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setCameraDistance(float distance) {
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setExplosionKnockBackResistance(float resistance) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.EXPLOSION_KNOCKBACK_RESISTANCE)).setBaseValue(resistance);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setFallDamageMultiplier(float multiplier) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.FALL_DAMAGE_MULTIPLIER)).setBaseValue(multiplier);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setGravity(float gravity) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.GRAVITY)).setBaseValue(gravity);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setKnockBackResistance(float resistance) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.KNOCKBACK_RESISTANCE)).setBaseValue(resistance);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setLuck(float luck) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.LUCK)).setBaseValue(luck);
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
    public INMSPlayerAttributeModifier setMovementSpeed(float speed) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(speed);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setJumpStrength(float strength) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.JUMP_STRENGTH)).setBaseValue(strength);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setEntityScale(float scale) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SCALE)).setBaseValue(scale);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setBlockInteractionRange(float range) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE)).setBaseValue(range);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setEntityInteractionRange(float range) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE)).setBaseValue(range);
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
    public INMSPlayerAttributeModifier setMiningEfficiency(float efficiency) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.MINING_EFFICIENCY)).setBaseValue(efficiency);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setMovementEfficiency(float efficiency) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.MOVEMENT_EFFICIENCY)).setBaseValue(efficiency);
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
    public INMSPlayerAttributeModifier setSafeFallDistance(float distance) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SAFE_FALL_DISTANCE)).setBaseValue(distance);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSneakingSpeed(float speed) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SNEAKING_SPEED)).setBaseValue(speed);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSubmergedMiningSpeed(float speed) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SUBMERGED_MINING_SPEED)).setBaseValue(speed);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setSweepingDamageRatio(float ratio) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.SWEEPING_DAMAGE_RATIO)).setBaseValue(ratio);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSPlayerAttributeModifier setWaterMoveEfficiency(float efficiency) {
        try {
            Objects.requireNonNull(player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY)).setBaseValue(efficiency);
        } catch (NullPointerException ignore) {
        }
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
