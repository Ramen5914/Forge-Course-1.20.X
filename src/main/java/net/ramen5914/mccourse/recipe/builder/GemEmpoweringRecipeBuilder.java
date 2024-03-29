package net.ramen5914.mccourse.recipe.builder;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.AdvancementRewards.Builder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.recipe.GemEmpoweringRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GemEmpoweringRecipeBuilder implements RecipeBuilder {
    private final Item result;
    @Nullable
    private final Integer count;
    private final Ingredient ingredient;
    private final int cookingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    private GemEmpoweringRecipeBuilder(ItemStack pResult, Ingredient pIngredient, int pCookingTime) {
        this.result = pResult.getItem();
        this.count = pResult.getCount();
        this.ingredient = pIngredient;
        this.cookingTime = pCookingTime;
    }

    public static GemEmpoweringRecipeBuilder gemEmpowering(Ingredient pIngredient, ItemStack pResult, int pCookingTime) {
        return new GemEmpoweringRecipeBuilder(pResult, pIngredient, pCookingTime);
    }

    public static GemEmpoweringRecipeBuilder gemEmpowering(Ingredient pIngredient, ItemLike pResult, int pCookingTime) {
        return new GemEmpoweringRecipeBuilder(new ItemStack(pResult), pIngredient, pCookingTime);
    }

    public @NotNull GemEmpoweringRecipeBuilder unlockedBy(@NotNull String pName, @NotNull Criterion<?> pCriterion) {
        this.criteria.put(pName, pCriterion);
        return this;
    }

    public @NotNull GemEmpoweringRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    @Override
    public void save(@NotNull RecipeOutput pRecipeOutput) {
        RecipeBuilder.super.save(pRecipeOutput);
    }

    @Override
    public void save(@NotNull RecipeOutput pRecipeOutput, @NotNull String pId) {
        save(pRecipeOutput, new ResourceLocation(MCCourseMod.MOD_ID, pId));
    }

    public void save(RecipeOutput pRecipeOutput, @NotNull ResourceLocation pId) {
        this.ensureValid(pId);
        Advancement.Builder advancementBuilder = pRecipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId)).rewards(Builder.recipe(pId)).requirements(Strategy.OR);
        Objects.requireNonNull(advancementBuilder);
        this.criteria.forEach(advancementBuilder::addCriterion);
        GemEmpoweringRecipe recipe = new GemEmpoweringRecipe(Objects.requireNonNullElse(this.group, ""), this.ingredient, new ItemStack(this.result, Objects.requireNonNullElse(this.count, 1)), this.cookingTime);
        pRecipeOutput.accept(pId, recipe, advancementBuilder.build(pId.withPrefix("recipes/gem_empowering/")));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}
