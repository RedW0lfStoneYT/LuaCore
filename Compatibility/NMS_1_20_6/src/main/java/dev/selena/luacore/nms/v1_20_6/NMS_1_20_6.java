package dev.selena.luacore.nms.v1_20_6;

import dev.selena.luacore.nms.INMSEntityBuilder;
import dev.selena.luacore.nms.INMSPlayerAttributeModifier;
import dev.selena.luacore.nms.INMSVersionClass;
import dev.selena.luacore.nms.IPathfinderInjector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Used for getting NMS 1.20.5-1.20.6 specific classes
 */
public class NMS_1_20_6 implements INMSVersionClass {
    @Override
    public INMSEntityBuilder getEntityBuilder(LivingEntity entity) {
        return new dev.selena.luacore.nms.v1_20_6.EntityBuilder(entity);
    }

    @Override
    public INMSPlayerAttributeModifier getPlayerAttributeModifier(Player player) {
        return new dev.selena.luacore.nms.v1_20_6.PlayerAttributeModifier(player);
    }
    @Override
    public IPathfinderInjector getPathfinderInjector(LivingEntity entity) {
        return new dev.selena.luacore.nms.v1_20_6.PathfinderInjector(entity);
    }
}
