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
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

public class LuaEntity {

    @Getter
    private RandomCollection<ItemStack> drops;

    @Getter
    private Entity spigotEntity;
    @Getter
    private boolean luaEntity;

    public LuaEntity(Entity entity) {
        spigotEntity = entity;
        NBT.modifyPersistentData(entity, nbtData -> {
            ReadWriteNBT compound = nbtData.getCompound(LuaCore.getCompountName());
            if (!isLuaEntity(entity))
                return;
            if (!compound.hasTag("EntityDrops")) {
                LuaMessageUtils.verboseMessage("No Custom Drops");
                return;
            }
            luaEntity = true;
            Type tokenType = new TypeToken<RandomCollection<ItemBuilder>>(){}.getType(); // THEN THIS MK SEE WHAT WORKS SILLY
            setDrops(NBTUtils.getEntityNBTContent(tokenType, "EntityDrops", compound));
        });
    }

    private void setDrops(RandomCollection<ItemBuilder> collection) {
        LuaMessageUtils.json_dump("Collection:", collection);
        drops = collection.cloneTo(ItemStack.class, ItemBuilder::build);
        LuaMessageUtils.json_dump("Drops:", drops);
    }

    public static boolean isLuaEntity(Entity entity) {
        AtomicBoolean returnValue = new AtomicBoolean(true);
        NBT.modifyPersistentData(entity, nbtData -> {
            ReadWriteNBT compound = nbtData.getCompound(LuaCore.getCompountName());
            if (compound == null) {
                LuaMessageUtils.verboseMessage("No compound");
                returnValue.set(false);
            }
            if (!compound.getBoolean("LuaCoreEntity")) {
                LuaMessageUtils.verboseMessage("Not a LuaCoreEntity");
                returnValue.set(false);
            }
        });
        return returnValue.get();
    }

}
