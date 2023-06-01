package com.dasffafa.fantasia.common.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class Vec3Helper {
    public static float length(Vec3 vec3) {
        return Mth.sqrt((float) (Math.pow(vec3.x, 2) + Math.pow(vec3.y, 2) + Math.pow(vec3.z, 2)));
    }
}
