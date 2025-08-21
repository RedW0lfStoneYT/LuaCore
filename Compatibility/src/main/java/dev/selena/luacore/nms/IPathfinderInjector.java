package dev.selena.luacore.nms;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public interface IPathfinderInjector {
    IPathfinderInjector addCustomPathfinder(int priority, ICustomGoalWrapper customGoal);
    IPathfinderInjector clearPathfinders();
    LivingEntity getEntity();
}
