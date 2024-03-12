package net.ramen5914.mccourse.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ramen5914.mccourse.MCCourseMod;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MCCourseMod.MOD_ID);

    public static final RegistryObject<RecipeType<GemEmpoweringRecipe>> GEM_EMPOWERING = RECIPE_TYPES.register(
            "gem_empowering", () -> RecipeType.simple(new ResourceLocation(MCCourseMod.MOD_ID, "gem_empowering"))
    );

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}
