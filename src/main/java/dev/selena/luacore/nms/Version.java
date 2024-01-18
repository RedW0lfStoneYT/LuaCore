package dev.selena.luacore.nms;

import dev.selena.luacore.nms.v1_20_R2.NMS_1_20_R2;
import dev.selena.luacore.nms.v1_20_R3.NMS_1_20_R3;
import lombok.Getter;

public enum Version {

    v1_20_R3(new NMS_1_20_R3(), 1203),
    v1_20_R2(new NMS_1_20_R2(), 1202),
    NOT_SUPPORTED(null, 0);


    @Getter
    private final INMSVersionClass clazz;
    @Getter
    private final int versionNumber;

    Version(INMSVersionClass versionClass, int versionNumber) {
        this.clazz = versionClass;
        this.versionNumber = versionNumber;
    }
}
