package net.ramen5914.mccourse.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.ModBlocks;

public class ModVillagers {
        public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, MCCourseMod.MOD_ID);
        public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, MCCourseMod.MOD_ID);

        public static final DeferredHolder<PoiType, PoiType> SOUND_POI = POI_TYPES.register("sound_poi",
                        () -> new PoiType(ImmutableSet
                                .copyOf(ModBlocks.SOUND_BLOCK.get()
                                        .getStateDefinition().getPossibleStates()),
                                1,
                                1));

        public static final DeferredHolder<VillagerProfession, VillagerProfession> SOUND_MASTER = VILLAGER_PROFESSIONS
                        .register("soundmaster", () -> new VillagerProfession("soundmaster",
                                        x -> x.value() == SOUND_POI.get(), x -> x.value() == SOUND_POI.get(),
                                        ImmutableSet.of(), ImmutableSet.of(),
                                        SoundEvents.VILLAGER_WORK_TOOLSMITH));

        public static void register(IEventBus eventBus) {
                POI_TYPES.register(eventBus);
                VILLAGER_PROFESSIONS.register(eventBus);
        }
}
