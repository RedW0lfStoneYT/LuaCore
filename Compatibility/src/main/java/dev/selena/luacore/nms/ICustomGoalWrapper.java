package dev.selena.luacore.nms;

/**
 * Interface for custom pathfinder goal wrappers in the NMS (Net Minecraft Server) system.
 * Implementations of this interface should provide a way to create a handle for a custom goal.
 */
public interface ICustomGoalWrapper {
    /**
     * Creates a handle for a custom goal using the provided NMS mob object.
     * @param nmsMob the NMS mob object to create the handle for
     * @return the handle object for the custom goal
     */
    Object createHandle(Object nmsMob);
}
