package dev.selena.luacore.nms;

import org.bukkit.entity.LivingEntity;

/**
 * Used for getting NMS version specific classes
 */
public interface INMSVersionClass {

    /**
     * Used for getting the NMS entity builder
     * @param entity The entity you want to alter
     * @return The builder
     */
    INMSEntityBuilder getEntityBuilder(LivingEntity entity);

}
