package net.ramen5914.mccourse.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ramen5914.mccourse.MCCourseMod;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MCCourseMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<GemEmpoweringRecipe>> GEM_EMPOWERING_SERIALIZER =
            RECIPE_SERIALIZERS.register("gem_empowering", () -> GemEmpoweringRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
