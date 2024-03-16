package dev.selena.luacore.utils.nbt;

import lombok.Getter;

public enum NBTConstants {
    UNSTACKABLE("UNSTACKABLE"),
    UNUSABLE("UNSUABLE");

    @Getter
    private String nbtString;
    NBTConstants(String nbtString) {
        this.nbtString = nbtString;
    }
}
