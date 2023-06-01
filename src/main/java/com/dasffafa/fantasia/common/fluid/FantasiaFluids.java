package com.dasffafa.fantasia.common.fluid;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.block.FantasiaBlocks;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FantasiaFluids {
    public static final ResourceLocation STILL_OIL_TEXTURE = new ResourceLocation("block/water_still");
    public static final ResourceLocation FLOWING_OIL_TEXTURE = new ResourceLocation("block/water_flow");
    public static final ResourceLocation OVERLAY_OIL_TEXTURE = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Fantasia.MOD_ID);

    public static final RegistryObject<FlowingFluid> SHIT_FLUID = FLUIDS.register("shit_fluid", () -> new ForgeFlowingFluid.Source(
            FantasiaFluids.SHIT_FLUID_PROPERTIES
    ));
    public static final RegistryObject<FlowingFluid> SHIT_FLOWING = FLUIDS.register("shit_flowing", () -> new ForgeFlowingFluid.Flowing(
            FantasiaFluids.SHIT_FLUID_PROPERTIES
    ));

    public static final ForgeFlowingFluid.Properties SHIT_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            SHIT_FLUID,
            SHIT_FLOWING,
            FluidAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE)
                    .density(80)
                    .temperature(300)
                    .color(0xff4c2724)
                    .viscosity(120)
                    .luminosity(2)
//                    .sound(FantasiaSounds.PUJIPA.get())
    )
            .slopeFindDistance(1)
            .levelDecreasePerBlock(3)
            .bucket(FantasiaItems.SHIT_BUCKET)
            .block(FantasiaBlocks.SHIT_FLUID_BLOCK);
}

