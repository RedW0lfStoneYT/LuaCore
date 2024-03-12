package dev.selena.luacore.nms.v1_20_R3;

import dev.selena.luacore.nms.INMSEntityBuilder;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

/**
 * NMS Entity builder for 1.20.3-1.20.4
 */
public class EntityBuilder implements INMSEntityBuilder {


    @Getter
    private final LivingEntity entity;

    /**
     * Used for creating instance of 1.20.3-1.20.4 entity builder
     *
     * @param entity The entity you want to alter
     */
    public EntityBuilder(LivingEntity entity) {
        this.entity = entity;
    }


    @Override
    public INMSEntityBuilder setArmorBonus(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setArmorToughnessBonus(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setAttackDamageBonus(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setAttackKnockBack(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setAttackSpeed(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setFlyingSpeed(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_FLYING_SPEED).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setFollowRange(float range) {
        try {
            entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(range);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setKnockBackResistance(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setLuck(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_LUCK).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setMaxAbsorption(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_MAX_ABSORPTION).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setMaxHealth(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setMovementSpeed(float bonus) {
        try {
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
        return this;
    }

    @Override
    public INMSEntityBuilder setHorseJumpStrength(float bonus) {
        try {
            entity.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(bonus);
        } catch (NullPointerException ignore) {
        }
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
