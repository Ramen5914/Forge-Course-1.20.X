package net.ramen5914.mccourse.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.ramen5914.mccourse.block.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GemEmpoweringRecipe implements Recipe<SimpleContainer> {
    private final RecipeType<?> recipeType = ModRecipeTypes.GEM_EMPOWERING.get();
    private final String group;
    public final Ingredient ingredient;
    public final ItemStack result;
    private final int cookingTime;

    public GemEmpoweringRecipe(String pGroup, Ingredient pIngredient, ItemStack pResult, int cookingTime) {
        this.group = pGroup;
        this.ingredient = pIngredient;
        this.result = pResult;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean showNotification() {
        return Recipe.super.showNotification();
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.GEM_EMPOWERING_STATION.get());
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.GEM_EMPOWERING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return this.recipeType;
    }

    @Override
    public boolean matches(SimpleContainer pInv, Level level) {
        return this.ingredient.test(pInv.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(this.ingredient);
    }

    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return this.result;
    }

    public @NotNull String getGroup() {
        return this.group;
    }

    public RecipeType<?> getRecipeType() {
        return this.recipeType;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResultItem() {
        return this.result;
    }

    public static class Serializer implements RecipeSerializer<GemEmpoweringRecipe> {
        public static final Serializer INSTANCE = new Serializer();
//        public static final ResourceLocation ID =
//                new ResourceLocation(MCCourseMod.MOD_ID,"gem_empowering");

        public static final Codec<GemEmpoweringRecipe> CODEC = RecordCodecBuilder.create(builder -> builder
                .group(
                        ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(GemEmpoweringRecipe::getGroup),
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(GemEmpoweringRecipe::getIngredient),
                        ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(GemEmpoweringRecipe::getResultItem),
                        // Vanilla uses "cookingtime" even thought I'd rather use "cooking_time". Whatever ig
                        Codec.INT.fieldOf("cookingtime").orElse(100).forGetter(GemEmpoweringRecipe::getCookingTime)
                )
                .apply(builder, GemEmpoweringRecipe::new));

        public Serializer() {

        }

        public @NotNull Codec<GemEmpoweringRecipe> codec() {
            return CODEC;
        }

        public void toNetwork(FriendlyByteBuf pBuffer, GemEmpoweringRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeVarInt(pRecipe.getCookingTime());
        }

        public @Nullable GemEmpoweringRecipe fromNetwork(FriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            int cookingTime = pBuffer.readVarInt();
            return new GemEmpoweringRecipe(group, ingredient, result, cookingTime);
        }
    }
}
