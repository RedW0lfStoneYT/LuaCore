package dev.selena.luacore.utils.objects;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * An example class for the {@link dev.selena.luacore.utils.RandomCollection} class
 */
@Getter
public class RewardItem {

    private final ItemStack rewardItem;
    private final String rewardName;
    private final String rewardCommand;


    /**
     * Used to setup the reward
     * @param rewardItem The item if you want it to be item
     * @param rewardName The Reward name can be used for messages
     * @param rewardCommand If you want the reward to have a command
     */
    public RewardItem(@Nullable ItemStack rewardItem, @Nullable String rewardName, @Nullable String rewardCommand) {
        this.rewardItem = rewardItem;
        this.rewardName = rewardName;
        this.rewardCommand = rewardCommand;
    }

    /**
     * Checks if the reward has an item set
     * @return True if there is an item set
     */
    public boolean isItemReward() {
        return rewardItem != null;
    }

    /**
     * Checks if the reward has a command set
     * @return True if there is a command set
     */
    public boolean isCommandReward() {
        return rewardCommand != null;
    }

}
