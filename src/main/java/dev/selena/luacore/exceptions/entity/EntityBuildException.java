package dev.selena.luacore.exceptions.entity;

import org.bukkit.Location;

public class EntityBuildException extends NullPointerException {

    /**
     * Thrown when there is an error with the {@link dev.selena.luacore.utils.entities.EntityBuilder#spawn(Location)}
     * @param message The message you want to send alongside it
     */
    public EntityBuildException(String message) {
        super(message);
    }

}
