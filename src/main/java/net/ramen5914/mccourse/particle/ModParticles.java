package net.ramen5914.mccourse.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister
            .create(BuiltInRegistries.PARTICLE_TYPE, MCCourseMod.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ALEXANDRITE_PARTICLES = PARTICLE_TYPES
            .register("alexandrite_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
