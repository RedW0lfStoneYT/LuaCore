package dev.selena.luacore;

import dev.selena.luacore.utils.RandomCollection;
import dev.selena.luacore.utils.entities.LuaEntity;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Used for handling custom drops only currently, but you can use this for whatever you need.
 */
public class LuaEntityDeathEvent extends Event implements Cancellable {

    private boolean cancelled;
    @Getter
    private static final HandlerList handlerList = new HandlerList();
    @Getter
    private LuaEntity luaEntity;
    @Getter
    private RandomCollection<ItemStack> dropCollection;
    @Getter
    private Entity spigotEntity;
    @Getter
    @Setter
    private int dropAmount;

    /**
     * Used for handling custom drops only currently, but you can use this for whatever you need.
     * @param luaEntity The {@link LuaEntity} you are dealing with
     */
    public LuaEntityDeathEvent(LuaEntity luaEntity) {
        this.luaEntity = luaEntity;
        this.dropCollection = luaEntity.getDrops();
        this.spigotEntity = luaEntity.getSpigotEntity();
        this.dropAmount = 1;
    }

    /**
     * So long as the event isn't canceled, you will not need to call this method.
     */
    public void doDrops() {
        for (int i = 0; i < dropAmount; i++) {
            Location location = spigotEntity.getLocation();
            location.getWorld().dropItem(location, dropCollection.getRandom());
        }
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
