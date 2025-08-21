package dev.selena.luacore.nms;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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

    /**
     * Used for getting the {@link INMSPlayerAttributeModifier} instance for a player.
     * @param player The player you want to get the attribute modifier for
     * @return The player attribute modifier
     */
    INMSPlayerAttributeModifier getPlayerAttributeModifier(Player player);

    /**
     * Used for getting the {@link IPathfinderInjector} instance for a specific entity.
     * @param entity The entity you want to get the pathfinder injector for
     * @return The pathfinder injector for the entity
     */
    IPathfinderInjector getPathfinderInjector(LivingEntity entity);

}
