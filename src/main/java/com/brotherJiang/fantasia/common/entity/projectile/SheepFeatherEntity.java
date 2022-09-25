package com.brotherJiang.fantasia.common.entity.projectile;

import com.brotherJiang.fantasia.common.entity.FantasiaEntities;
import com.brotherJiang.fantasia.common.item.FantasiaItems;
import com.brotherJiang.fantasia.common.utils.ModDamageSources;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class SheepFeatherEntity extends ProjectileBase implements IRendersAsItem {


    public SheepFeatherEntity(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), x, y, z, worldIn);
    }

    public SheepFeatherEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity thrower, World world) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), thrower, world);
    }

    public SheepFeatherEntity(World world) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), world);
    }

    public SheepFeatherEntity(World world, LivingEntity owner) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), owner, world);
        BlockPos pos = getPosition();
        this.setPosition(pos.getX(),pos.getY() + owner.getHeight()*0.7, pos.getZ());
        this.setBreakAbility(1.0F);
    }

    @Override
    public @NotNull IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @NotNull
    @Override
    protected IParticleData getParticle() {
        return ParticleTypes.SMOKE;
    }

    @Override
    protected void affectEntity(Entity entity) {
        entity.attackEntityFrom(ModDamageSources.sheep_feather, 6);
        entity.setFire(10);
    }

    @Override
    protected void affectBlock(World world, BlockPos pos) {
        for (int i=1; i<3; i++){
            BlockPos tryToIgnitePos = this.getPosition().add(new Vector3i(
                    rand.nextInt(2) - 1,
                    rand.nextInt(2) - 1,
                    rand.nextInt(2) - 1).up());
            if (world.getBlockState(tryToIgnitePos).matchesBlock(Blocks.AIR) && !world.getBlockState(tryToIgnitePos.down()).matchesBlock(Blocks.AIR)){
                world.setBlockState(tryToIgnitePos, Blocks.FIRE.getDefaultState());
            }
        }
    }

    @Override
    public boolean isBurning() {
        return true;
    }

    @Override
    public @NotNull ItemStack getItem() {
        return new ItemStack(FantasiaItems.SHEEP_FEATHER.get(),1);
    }
}
