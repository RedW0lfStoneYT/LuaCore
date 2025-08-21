package dev.selena.luacore.nms.v1_21_5;

import dev.selena.luacore.nms.ICustomGoalWrapper;
import dev.selena.luacore.nms.IPathfinderInjector;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.craftbukkit.entity.CraftMob;
import org.bukkit.entity.LivingEntity;

public class PathfinderInjector implements IPathfinderInjector {
    private final CraftMob mob;
    public PathfinderInjector(LivingEntity entity) {
        mob = (CraftMob) entity;
    }
    @Override
    public IPathfinderInjector addCustomPathfinder(int priority, ICustomGoalWrapper wrapper) {
        net.minecraft.world.entity.Mob nmsMob = mob.getHandle();
        Goal goal = (Goal) wrapper.createHandle(nmsMob);
        nmsMob.goalSelector.addGoal(priority, goal);
        return this;
    }

    @Override
    public IPathfinderInjector clearPathfinders() {
        net.minecraft.world.entity.Mob nmsMob = mob.getHandle();
        nmsMob.goalSelector.removeAllGoals(goal -> true);
        return this;
    }

    @Override
    public LivingEntity getEntity() {
        return mob;
    }
}
