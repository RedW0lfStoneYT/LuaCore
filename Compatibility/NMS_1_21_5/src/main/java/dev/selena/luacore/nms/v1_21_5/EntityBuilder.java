package dev.selena.luacore.nms.v1_21_5;

import dev.selena.luacore.nms.INMSEntityBuilder;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.Objects;

/**
 * NMS Entity builder for 1.21.5
 */
public class EntityBuilder implements INMSEntityBuilder {

    @Getter
    private final LivingEntity entity;

    /**
     * Used for creating instance of 1.21.5
     *
     * @param entity The entity you want to alter
     */
    public EntityBuilder(LivingEntity entity) {
        this.entity = entity;
    }


    @Override
    public INMSEntityBuilder setArmorBonus(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.ARMOR)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setArmorToughnessBonus(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.ARMOR_TOUGHNESS)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setAttackDamageBonus(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.ATTACK_DAMAGE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setAttackKnockBack(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.ATTACK_KNOCKBACK)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setBurningTime(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.BURNING_TIME)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setCameraDistance(float bonus) {
        return this;
    }

    @Override
    public INMSEntityBuilder setExplosionKnockBackResistance(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.EXPLOSION_KNOCKBACK_RESISTANCE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setFallDamageMultiplier(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.FALL_DAMAGE_MULTIPLIER)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setFlyingSpeed(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.FLYING_SPEED)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setFollowRange(float range) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.FOLLOW_RANGE)).setBaseValue(range);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setGravity(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.GRAVITY)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setKnockBackResistance(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.KNOCKBACK_RESISTANCE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setMaxAbsorption(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.MAX_ABSORPTION)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setMaxHealth(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setMovementSpeed(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setJumpStrength(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.JUMP_STRENGTH)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setEntityScale(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.SCALE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setStepHeight(float height) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.STEP_HEIGHT)).setBaseValue(height);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder spawnReinforcements(float spawn) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.SPAWN_REINFORCEMENTS)).setBaseValue(spawn);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setMovementEfficiency(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.MOVEMENT_EFFICIENCY)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setOxygenBonus(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.OXYGEN_BONUS)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setSafeFallDistance(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.SAFE_FALL_DISTANCE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setTemptRange(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.TEMPT_RANGE)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setWaterMoveEfficiency(float bonus) {
        try {
            Objects.requireNonNull(entity.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY)).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
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
