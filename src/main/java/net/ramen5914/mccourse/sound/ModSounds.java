package net.ramen5914.mccourse.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;

public class ModSounds {
        public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
                        .create(BuiltInRegistries.SOUND_EVENT, MCCourseMod.MOD_ID);

        public static final DeferredHolder<SoundEvent, SoundEvent> METAL_DETECTOR_FOUND_ORE = registerSoundEvents(
                        "metal_detector_found_ore");

        public static final DeferredHolder<SoundEvent, SoundEvent> ALEXANDRITE_LAMP_BREAK = registerSoundEvents(
                        "alexandrite_lamp_break");
        public static final DeferredHolder<SoundEvent, SoundEvent> ALEXANDRITE_LAMP_STEP = registerSoundEvents(
                        "alexandrite_lamp_step");
        public static final DeferredHolder<SoundEvent, SoundEvent> ALEXANDRITE_LAMP_PLACE = registerSoundEvents(
                        "alexandrite_lamp_place");
        public static final DeferredHolder<SoundEvent, SoundEvent> ALEXANDRITE_LAMP_HIT = registerSoundEvents(
                        "alexandrite_lamp_hit");
        public static final DeferredHolder<SoundEvent, SoundEvent> ALEXANDRITE_LAMP_FALL = registerSoundEvents(
                        "alexandrite_lamp_fall");

        public static final DeferredSoundType ALEXANDRITE_LAMP_SOUNDS = new DeferredSoundType(1f, 1f,
                        ModSounds.ALEXANDRITE_LAMP_BREAK, ModSounds.ALEXANDRITE_LAMP_STEP,
                        ModSounds.ALEXANDRITE_LAMP_PLACE,
                        ModSounds.ALEXANDRITE_LAMP_HIT, ModSounds.ALEXANDRITE_LAMP_FALL);

        public static final DeferredHolder<SoundEvent, SoundEvent> BAR_BRAWL = registerSoundEvents("bar_brawl");

        private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
                ResourceLocation id = new ResourceLocation(MCCourseMod.MOD_ID, name);
                return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
        }

        public static void register(IEventBus eventBus) {
                SOUND_EVENTS.register(eventBus);
        }
}
