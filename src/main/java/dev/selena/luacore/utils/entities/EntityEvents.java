package dev.selena.luacore.utils.entities;

import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Used by LuaCore to call {@link LuaEntityDeathEvent}
 */
public class EntityEvents implements Listener {

    /**
     * Used for calling the LuaEntityDeathEvent.
     * This will only clear the drops if you have custom drops
     */
    @EventHandler
    public void luaEntityDeathCall(EntityDeathEvent event) {
        try {
            Entity dead = event.getEntity();
            LuaEntity luaEntity = new LuaEntity(dead);
            if (!luaEntity.isLuaEntity()) {
                LuaMessageUtils.verboseMessage("Not a LuaEntity: " + dead.getType().name() + " at " + dead.getLocation());
                return;
            }
            if (luaEntity.getDrops() == null) {
                LuaMessageUtils.verboseMessage("No Custom Drops for " + dead.getType().name() + " at " + dead.getLocation());
                return;
            }
            if (luaEntity.getOwningPlugin() != LuaCore.getPlugin()) {
                LuaMessageUtils.verboseError("Not the owning plugin!");
                return;
            }
            LuaCore.getPlugin().getServer().getPluginManager().callEvent(new LuaEntityDeathEvent(luaEntity));
            if (!luaEntity.getDrops().isEmpty() || !luaEntity.isUseVanillaDrops())
                event.getDrops().clear();
        } catch (Exception e) {
            LuaMessageUtils.verboseError("An error occurred while processing LuaEntityDeathEvent: " + e.getMessage());
            e.printStackTrace();
        }
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
