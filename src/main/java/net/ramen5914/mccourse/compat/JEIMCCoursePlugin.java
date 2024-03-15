package net.ramen5914.mccourse.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.ModBlocks;
import net.ramen5914.mccourse.item.ModItems;
import net.ramen5914.mccourse.recipe.GemEmpoweringRecipe;
import net.ramen5914.mccourse.recipe.ModRecipeTypes;
import net.ramen5914.mccourse.screen.GemEmpoweringStationScreen;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIMCCoursePlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MCCourseMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new GemEmpoweringRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<GemEmpoweringRecipe> gemEmpoweringRecipes = new ArrayList<>();
        recipeManager.getAllRecipesFor(ModRecipeTypes.GEM_EMPOWERING.get()).forEach(holder -> gemEmpoweringRecipes.add(holder.value()));
        registration.addRecipes(GemEmpoweringRecipeCategory.GEM_EMPOWERING_TYPE, gemEmpoweringRecipes);

        registration.addIngredientInfo(new ItemStack(ModItems.METAL_DETECTOR.get()), VanillaTypes.ITEM_STACK, Component.literal("This item scans for ore blocks in a column below the block you right click on."));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(GemEmpoweringStationScreen.class, 85, 30, 7, 26,
                GemEmpoweringRecipeCategory.GEM_EMPOWERING_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.GEM_EMPOWERING_STATION.get()), GemEmpoweringRecipeCategory.GEM_EMPOWERING_TYPE);
    }
}
