package dev.selena.luacore.utils.entities;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.LuaEntityDeathEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityEvents implements Listener {

    /**
     * Used for calling the LuaEntityDeathEvent.
     * This will only clear the drops if you have custom drops
     */
    @EventHandler
    public void luaEntityDeathCall(EntityDeathEvent event) {
        Entity dead = event.getEntity();
        LuaEntity luaEntity = new LuaEntity(dead);
        if (!luaEntity.isLuaEntity())
            return;
        if (luaEntity.getDrops() == null)
            return;
        LuaCore.getPlugin().getServer().getPluginManager().callEvent(new LuaEntityDeathEvent(luaEntity));
        if (!luaEntity.getDrops().isEmpty())
            event.getDrops().clear();
    }

    /**
     * Listens to LuaEntityDeathEvent will be called last, so if you wish to change the drop amount you can
     * @param event The LuaEntityDeathEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void luaEntityDeathListen(LuaEntityDeathEvent event) {
        event.doDrops();
    }
}
