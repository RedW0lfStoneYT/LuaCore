package dev.selena.luacore.nms.v1_20_R2;

import dev.selena.luacore.nms.INMSEntityBuilder;
import dev.selena.luacore.nms.INMSVersionClass;
import org.bukkit.entity.LivingEntity;

/**
 * Used for getting NMS 1.20.2 specific classes
 */
public class NMS_1_20_R2 implements INMSVersionClass {
    @Override
    public INMSEntityBuilder getEntityBuilder(LivingEntity entity) {
        return new EntityBuilder(entity);
    }
}
