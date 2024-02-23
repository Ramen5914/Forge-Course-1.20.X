package net.ramen5914.mccourse.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.ramen5914.mccourse.MCCourseMod;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            MCCourseMod.MOD_ID);
    
    

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
