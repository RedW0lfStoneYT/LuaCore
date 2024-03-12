package dev.selena.luacore.nms;

import dev.selena.luacore.nms.v1_20_R2.NMS_1_20_R2;
import dev.selena.luacore.nms.v1_20_R3.NMS_1_20_R3;
import lombok.Getter;

/**
 * Used for NMS reflections
 */
public enum Version {

    /**
     * Used for 1.20.3-1.20.4
     */
    v1_20_R3(new NMS_1_20_R3(), 1203),
    /**
     * Used for 1.20.2
     */
    v1_20_R2(new NMS_1_20_R2(), 1202),
    /**
     * Used for any other versions
     */
    NOT_SUPPORTED(null, 0);
    // TODO add 1.20-1.20.1 because I guess I forgot that?

    @Getter
    private final INMSVersionClass clazz;
    @Getter
    private final int versionNumber;

    /**
     * NMS version reflections enum
     * @param versionClass The main class for the NMS version
     * @param versionNumber The Unique number for the version (i.e., 1203)
     */
    Version(INMSVersionClass versionClass, int versionNumber) {
        this.clazz = versionClass;
        this.versionNumber = versionNumber;
    }
}
