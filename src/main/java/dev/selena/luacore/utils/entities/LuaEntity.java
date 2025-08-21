package dev.selena.luacore.utils.entities;

import com.google.gson.reflect.TypeToken;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import dev.selena.luacore.LuaCore;
import dev.selena.luacore.utils.RandomCollection;
import dev.selena.luacore.utils.items.ItemBuilder;
import dev.selena.luacore.utils.nbt.NBTUtils;
import dev.selena.luacore.utils.text.LuaMessageUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Used for handling of LuaCore Entities
 */
public class LuaEntity {

    @Getter
    private RandomCollection<ItemStack> drops;
    @Getter
    private Entity spigotEntity;
    @Getter
    private boolean luaEntity;
    private String owningPlugin;
    @Getter
    private boolean useVanillaDrops;

    /**
     * used for setting up stuff for the LuaEntity
     * @param entity The entity you want to parse
     */
    public LuaEntity(Entity entity) {
        spigotEntity = entity;
        NBT.modifyPersistentData(entity, nbtData -> {
            ReadWriteNBT compound = nbtData.getCompound(LuaCore.getCompountName());
            owningPlugin = nbtData.getString("OwningPlugin");
            if (!isLuaEntity(entity))
                return;
            if (!compound.hasTag("EntityDrops")) {
                LuaMessageUtils.verboseMessage("No Custom Drops");
                return;
            }
            luaEntity = true;
            if (compound.hasTag("VanillaDrops")) {
                useVanillaDrops = compound.getBoolean("VanillaDrops");
                if (useVanillaDrops) {
                    return;
                }
            } else {
                useVanillaDrops = false;
            }
            Type dropsType = TypeToken.getParameterized(RandomCollection.class, ItemBuilder.class).getType();
            setDrops(NBTUtils.getEntityNBTContent(dropsType, "EntityDrops", compound));

        });
    }

    private void setDrops(RandomCollection<ItemBuilder> collection) {
        drops = collection.cloneTo(ItemStack.class, ItemBuilder::build);
    }

    /**
     * Used for seeing if an entity is a LuaEntity (This is plugin-specific)
     * @param entity The entity you want to check
     * @return True if contains the "LuaCoreEntity" NBT for your plugin
     */
    public static boolean isLuaEntity(Entity entity) {
        AtomicBoolean returnValue = new AtomicBoolean(true);
        NBT.modifyPersistentData(entity, nbtData -> {
            ReadWriteNBT compound = nbtData.getCompound(LuaCore.getCompountName());
            if (compound == null) {
                LuaMessageUtils.verboseMessage("No compound");
                returnValue.set(false);
                return;
            }
            if (!compound.getBoolean("LuaCoreEntity")) {
                LuaMessageUtils.verboseMessage("Not a LuaCoreEntity");
                returnValue.set(false);
            }
        });
        return returnValue.get();
    }

    /**
     * Gets the owning plugin of the entity
     * @return The owning plugin of the entity
     */
    public Plugin getOwningPlugin() {
        if (owningPlugin == null) {
            LuaMessageUtils.verboseWarn("Owning plugin is null for " + spigotEntity.getType().name());
            return null;
        }
        Plugin plugin = Bukkit.getPluginManager().getPlugin(owningPlugin);
        if (plugin == null) {
            LuaMessageUtils.verboseWarn("Owning plugin is null for " + spigotEntity.getType().name() + " - " + owningPlugin);
        }
        return plugin;
    }

}
