package net.ramen5914.mccourse.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.ModBlocks;

public class ModBlockEntities {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
                        .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MCCourseMod.MOD_ID);

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GemEmpoweringStationBlockEntity>> GEM_EMPOWERING_STATION_BE_TYPE =
                BLOCK_ENTITIES.register("gem_empowering_station_block_entity",
                        () -> BlockEntityType.Builder.of(
                                GemEmpoweringStationBlockEntity::new,
                                        ModBlocks.GEM_EMPOWERING_STATION.get())
                                .build(null));

        public static void register(IEventBus eventBus) {
                BLOCK_ENTITIES.register(eventBus);
        }
}
