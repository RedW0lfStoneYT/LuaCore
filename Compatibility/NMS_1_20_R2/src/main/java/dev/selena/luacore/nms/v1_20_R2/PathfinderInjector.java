package dev.selena.luacore.nms.v1_20_R2;

import dev.selena.luacore.nms.IPathfinderInjector;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftMob;
import org.bukkit.entity.LivingEntity;

public class PathfinderInjector implements IPathfinderInjector {

    private final CraftMob mob;

    public PathfinderInjector(org.bukkit.entity.LivingEntity entity) {
        this.mob = (CraftMob) entity;
    }

    @Override
    public IPathfinderInjector addCustomPathfinder(int priority, dev.selena.luacore.nms.ICustomGoalWrapper wrapper) {
        net.minecraft.world.entity.Mob nmsMob = mob.getHandle();
        net.minecraft.world.entity.ai.goal.Goal goal = (net.minecraft.world.entity.ai.goal.Goal) wrapper.createHandle(nmsMob);
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
