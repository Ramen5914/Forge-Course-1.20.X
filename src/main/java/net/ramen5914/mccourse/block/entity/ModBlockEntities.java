package net.ramen5914.mccourse.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MCCourseMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<GemEmpoweringStationBlockEntity>> GEM_EMPOWERING_STATION_BE =
            BLOCK_ENTITIES.register("gem_empowering_station_block_entity",
                    () -> BlockEntityType.Builder.of(GemEmpoweringStationBlockEntity::new, ModBlocks.GEM_EMPOWERING_STATION.get())
                            .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
