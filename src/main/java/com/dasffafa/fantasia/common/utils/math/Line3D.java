package com.dasffafa.fantasia.common.utils.math;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;

@SuppressWarnings("unused")
public class Line3D {
    private Vector3f direction;
    private BlockPos origin;
    public Line3D(BlockPos pos, Vector3f direction) {
        this.direction = direction;
        this.origin = pos;
    }

    public Line3D(BlockPos p1, BlockPos p2) {
        this.origin = p1;
        this.direction = new Vector3f(p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ());
    }
    // gen random forest model


}
