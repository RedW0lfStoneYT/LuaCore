package dev.selena.luacore.nms.v1_21_5;

import dev.selena.luacore.nms.INMSEntityBuilder;
import dev.selena.luacore.nms.INMSPlayerAttributeModifier;
import dev.selena.luacore.nms.INMSVersionClass;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class NMS_1_21_5 implements INMSVersionClass {
    @Override
    public INMSEntityBuilder getEntityBuilder(LivingEntity entity) {
        return new dev.selena.luacore.nms.v1_21_5.EntityBuilder(entity);
    }

    @Override
    public INMSPlayerAttributeModifier getPlayerAttributeModifier(Player player) {
        return new dev.selena.luacore.nms.v1_21_5.PlayerAttributeModifier(player);
    }
}
