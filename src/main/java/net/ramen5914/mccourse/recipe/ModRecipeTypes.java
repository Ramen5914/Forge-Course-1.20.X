package net.ramen5914.mccourse.recipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister
            .create(BuiltInRegistries.RECIPE_TYPE, MCCourseMod.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<GemEmpoweringRecipe>> GEM_EMPOWERING = RECIPE_TYPES.register(
            "gem_empowering", () -> RecipeType.simple(new ResourceLocation(MCCourseMod.MOD_ID, "gem_empowering")));

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}
