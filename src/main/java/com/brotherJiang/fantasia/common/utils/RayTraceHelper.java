/*
 * Copyright (c) 2021/7/30 下午10:02.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/bj_magic2.
 */
package com.brotherJiang.fantasia.common.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class RayTraceHelper {
    private RayTraceHelper() {
    }

    public static Predicate<Entity> getSelector() {
        return entity -> (entity instanceof LivingEntity && !(entity instanceof ArmorStandEntity));
    }

    @Nonnull
    public static BlockRayTraceResult rayTraceBlock(World world, Entity tracer, double range) {
      float f = tracer.rotationPitch;
      float f1 = tracer.rotationYaw;
      Vector3d vector3d = tracer.getEyePosition(1.0F);
      float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
      float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
      float f6 = f3 * f4;
      float f7 = f2 * f4;
      Vector3d vector3d1 = vector3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
      return world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, tracer));
    }

    @Nullable
    public static EntityRayTraceResult rayTraceEntity(Entity tracer, int range) {
        Vector3d origin = tracer.getEyePosition(1);
        Vector3d endpoint = origin.add(tracer.getLookVec().scale(range));
        AxisAlignedBB shooterBoundingBox = tracer.getBoundingBox().grow(range, range, range);
        return ProjectileHelper.rayTraceEntities(tracer, origin, endpoint, shooterBoundingBox, getSelector(), range);
    }

    @Nullable
    public static RayTraceResult rayTrace(Entity tracer, int range) {
        EntityRayTraceResult entityRayTrace = rayTraceEntity(tracer, range);
        if (entityRayTrace != null) {
            return entityRayTrace;
        } else {
            BlockRayTraceResult blockRayTrace = rayTraceBlock(tracer.world, tracer, range);
            return blockRayTrace.getType().equals(RayTraceResult.Type.MISS) ? null : blockRayTrace;
        }

    }

}