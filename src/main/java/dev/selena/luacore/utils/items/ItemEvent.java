package dev.selena.luacore.utils.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemEvent implements Listener {

    @EventHandler
    public void itemUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType().isEmpty())
            return;
        NBTItem nItem = new NBTItem(item);
        if (!nItem.hasCustomNbtData())
            return;
        if (!nItem.hasTag("USABLE"))
            return;
        if (!nItem.getBoolean("USABLE"))
            event.setCancelled(true);
    }
    
    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.getType().isEmpty())
            return;
        NBTItem nItem = new NBTItem(item);
        if (!nItem.hasCustomNbtData())
            return;
        if (!nItem.hasTag("USABLE"))
            return;
        if (!nItem.getBoolean("USABLE"))
            event.setCancelled(true);
    }
}
