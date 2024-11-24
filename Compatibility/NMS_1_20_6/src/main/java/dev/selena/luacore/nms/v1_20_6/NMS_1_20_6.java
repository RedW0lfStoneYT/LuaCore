package dev.selena.luacore.nms.v1_20_6;

import dev.selena.luacore.nms.INMSEntityBuilder;
import dev.selena.luacore.nms.INMSVersionClass;
import org.bukkit.entity.LivingEntity;
/**
 * Used for getting NMS 1.20.5-1.20.6 specific classes
 */
public class NMS_1_20_6 implements INMSVersionClass {
    @Override
    public INMSEntityBuilder getEntityBuilder(LivingEntity entity) {
        return new dev.selena.luacore.nms.v1_20_6.EntityBuilder(entity);
    }
}
