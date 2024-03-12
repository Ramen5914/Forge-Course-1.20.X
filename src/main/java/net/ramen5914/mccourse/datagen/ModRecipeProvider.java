package net.ramen5914.mccourse.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.ModBlocks;
import net.ramen5914.mccourse.item.ModItems;
import net.ramen5914.mccourse.recipe.builder.GemEmpoweringRecipeBuilder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ALEXANDRITE_SMELTABLES = List.of(
            ModBlocks.ALEXANDRITE_ORE.get(),
            ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(),
            ModBlocks.NETHER_ALEXANDRITE_ORE.get(),
            ModBlocks.END_STONE_ALEXANDRITE_ORE.get()
    );

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .group("alexandrite")
                .define('A', ModItems.ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", has(ModItems.ALEXANDRITE.get()))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 9)
                .requires(ModBlocks.ALEXANDRITE_BLOCK.get())
                .group("alexandrite")
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(pRecipeOutput);

        customNineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, ModItems.RAW_ALEXANDRITE.get(), RecipeCategory.MISC, ModBlocks.RAW_ALEXANDRITE_BLOCK.get(), "mccourse:raw_alexandrite_block", "alexandrite", "mccourse:raw_alexandrite", "alexandrite");
        oreSmelting(pRecipeOutput, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 200, "alexandrite");
        oreBlasting(pRecipeOutput, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 100, "alexandrite");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_ALEXANDRITE.get()), RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 200)
                .group("alexandrite")
                .unlockedBy("has_raw_alexandrite", has(ModItems.RAW_ALEXANDRITE.get()))
                .save(pRecipeOutput, "alexandrite_from_smelting");

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_ALEXANDRITE.get()), RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 100)
                .group("alexandrite")
                .unlockedBy("has_raw_alexandrite", has(ModItems.RAW_ALEXANDRITE.get()))
                .save(pRecipeOutput, "alexandrite_from_blasting");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_BLOCK.get())
                .requires(ModBlocks.ALEXANDRITE_SLAB.get())
                .requires(ModBlocks.ALEXANDRITE_SLAB.get())
                .unlockedBy("has_alexandrite_slab", has(ModBlocks.ALEXANDRITE_SLAB.get()))
                .save(pRecipeOutput, "mccourse:alexandrite_block_from_slabs");

        slab(pRecipeOutput, RecipeCategory.MISC, ModBlocks.ALEXANDRITE_SLAB.get(), ModBlocks.ALEXANDRITE_BLOCK.get());

        GemEmpoweringRecipeBuilder.gemEmpowering(Ingredient.of(ModItems.RAW_ALEXANDRITE.get()), new ItemStack(ModItems.ALEXANDRITE.get(), 3), 200)
                .unlockedBy("has_raw_alexandrite", has(ModItems.RAW_ALEXANDRITE.get()))
                .group("alexandrite")
                .save(pRecipeOutput, "alexandrite_from_gem_empowering");

        GemEmpoweringRecipeBuilder.gemEmpowering(Ingredient.of(Items.STICK), new ItemStack(Items.BLAZE_ROD, 7), 100)
                .unlockedBy("has_stick", has(Items.STICK))
                .save(pRecipeOutput, "blaze_rod_from_gem_empowering");

        GemEmpoweringRecipeBuilder.gemEmpowering(Ingredient.of(Items.GREEN_DYE), Items.EMERALD, 500)
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(pRecipeOutput, "emerald_from_gem_empowering");
    }

    protected static void customNineBlockStorageRecipes(RecipeOutput pRecipeOutput, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked, String pPackedName, @Nullable String pPackedGroup, String pUnpackedName, @Nullable String pUnpackedGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpackedCategory, pUnpacked, 9).requires(pPacked).group(pUnpackedGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pRecipeOutput, new ResourceLocation(pUnpackedName));
        ShapedRecipeBuilder.shaped(pPackedCategory, pPacked).define('#', pUnpacked).pattern("###").pattern("###").pattern("###").group(pPackedGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pRecipeOutput, new ResourceLocation(pPackedName));
    }

    protected static void oreSmelting(@NotNull RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput pRecipeOutput, RecipeSerializer<T> pRecipeSerializer, AbstractCookingRecipe.Factory<T> pRecipeType, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pFileEnding) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pRecipeSerializer, pRecipeType).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, MCCourseMod.MOD_ID + ":" + (pResult) + pFileEnding + "_" + getItemName(itemlike));
        }
    }
}
