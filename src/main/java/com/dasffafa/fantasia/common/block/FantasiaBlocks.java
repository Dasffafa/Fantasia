package com.dasffafa.fantasia.common.block;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class FantasiaBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Fantasia.MOD_ID);

    public static final RegistryObject<Block> SHIT_BLOCK = registerBlock("shit_block", ShitBlock::new,Fantasia.TAB);
    public static final RegistryObject<Item> SHIT_BLOCKITEM = registerBlockItem("shit_block", SHIT_BLOCK,Fantasia.TAB);
    public static final RegistryObject<Block> DOUBLE_BANG_BLOCK = registerBlock("double_bang_firework", DoubleBangBlock::new, Fantasia.TAB);
    public static final RegistryObject<Item> DOUBLE_BANG_BLOCKITEM = registerBlockItem("double_bang_firework", DOUBLE_BANG_BLOCK,Fantasia.TAB);

    //    public static final RegistryObject<LiquidBlock> SHIT_FLUID_BLOCK = BLOCKS.register("shit_fluid",
//            () -> new LiquidBlock(
//                    FantasiaFluids.SHIT_FLUID,
//                    BlockBehaviour.Properties.of(Material.WATER)
//                            .noCollission()
//                            .strength(100f)
//                            .noDrops()
//            )
//    );
    private static <T extends Block> RegistryObject<Block> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        return BLOCKS.register(name,block);
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return FantasiaItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
}
