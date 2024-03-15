package net.ramen5914.mccourse.painting;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;

public class ModPaintings {
        public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister
                        .create(BuiltInRegistries.PAINTING_VARIANT, MCCourseMod.MOD_ID);

        public static final DeferredHolder<PaintingVariant, PaintingVariant> SAW_THEM = PAINTING_VARIANTS.register("saw_them",
                        () -> new PaintingVariant(16, 16));
        public static final DeferredHolder<PaintingVariant, PaintingVariant> SHRIMP = PAINTING_VARIANTS.register("shrimp",
                        () -> new PaintingVariant(32, 16));
        public static final DeferredHolder<PaintingVariant, PaintingVariant> WORLD = PAINTING_VARIANTS.register("world",
                        () -> new PaintingVariant(32, 32));

        public static void register(IEventBus eventBus) {
                PAINTING_VARIANTS.register(eventBus);
        }
}
