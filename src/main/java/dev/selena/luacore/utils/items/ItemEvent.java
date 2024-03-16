package dev.selena.luacore.utils.items;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

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
        if (item == null || item.getType().isAir())
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
        if (item == null || item.getType().isAir())
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
    public void itemSpawn(ItemSpawnEvent event) {
        Item itemEnt = event.getEntity();
        ItemStack itemStack = itemEnt.getItemStack();
        if (itemStack.getType().isAir())
            return;
        NBT.modify(itemStack, itemNbt -> {
           if (!itemNbt.hasCustomNbtData()) {
               LuaMessageUtils.verboseMessage("No custom NBT");
               return;
           }
           if (!itemNbt.hasTag("UNSTACKABLE")) {
               LuaMessageUtils.verboseMessage("No USABLE tag");
               return;
           }
           if (itemStack.getAmount() > 1) {
               for (int i = 1; i < itemStack.getAmount(); i++) {
                   ItemStack newItem = itemStack.clone();
                   newItem.setAmount(1);
                   itemEnt.getLocation().getWorld().dropItem(itemEnt.getLocation(), newItem).setVelocity(itemEnt.getVelocity());
               }
               itemStack.setAmount(1);
           }
           itemNbt.setUUID("UNSTACKABLE", UUID.randomUUID());
        });
        event.getEntity().setItemStack(itemStack);
    }
}
