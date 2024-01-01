package dev.selena.luacore.utils.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Used for managing items, This should only be used in one plugin on your server.
 */
public class ItemEvent implements Listener {

    /**
     * event to stop users from consuming items with the unusable tag
     * @param event PlayerInteractEvent to intercept the player using the item
     */
    @EventHandler
    public void itemUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType().isAir() || item.getType().isEmpty())
            return;
        NBTItem nItem = new NBTItem(item);
        if (!nItem.hasCustomNbtData())
            return;
        if (!nItem.hasTag("USABLE"))
            return;
        if (!nItem.getBoolean("USABLE"))
            event.setCancelled(true);
    }

    /**
     * Used to stop players from placing blocks with the unusable tag
     * @param event BlockPlaceEvent to intercept the player placing the block
     */
    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item == null || item.getType().isAir() || item.getType().isEmpty())
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
