package net.ramen5914.mccourse.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SlimeyEffect extends MobEffect {
    public SlimeyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.horizontalCollision) {
            Vec3 initialVec = pLivingEntity.getDeltaMovement();
            Vec3 climbVec = new Vec3(initialVec.x, 0.20, initialVec.z);
            pLivingEntity.setDeltaMovement(climbVec.x * 0.91d, climbVec.y * 0.98d, climbVec.z * 0.91d);


        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
