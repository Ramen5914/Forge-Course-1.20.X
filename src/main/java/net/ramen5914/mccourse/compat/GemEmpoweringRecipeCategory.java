package net.ramen5914.mccourse.compat;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.ModBlocks;
import net.ramen5914.mccourse.recipe.GemEmpoweringRecipe;
import org.jetbrains.annotations.NotNull;

public class GemEmpoweringRecipeCategory implements IRecipeCategory<GemEmpoweringRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MCCourseMod.MOD_ID, "gem_empowering");
    public static final ResourceLocation TEXTURE = new ResourceLocation(MCCourseMod.MOD_ID, "textures/gui/container/gem_empowering_station_gui.png");
    public static final ResourceLocation ARROW = new ResourceLocation(MCCourseMod.MOD_ID, "textures/gui/sprites/container/gem_empowering_station/empowering_progress.png");
    public static final ResourceLocation ENERGY_BAR = new ResourceLocation(MCCourseMod.MOD_ID, "textures/gui/sprites/container/gem_empowering_station/energy_bar.png");

    public static final RecipeType<GemEmpoweringRecipe> GEM_EMPOWERING_TYPE = new RecipeType<>(UID, GemEmpoweringRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final int regularCookTime = 200;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    private final LoadingCache<Integer, IDrawableAnimated> cachedEnergyBars;

    public GemEmpoweringRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 25, 10, 140, 66);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.GEM_EMPOWERING_STATION.get()));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return helper.drawableBuilder(ARROW, 0, 0, 8, 26)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.TOP, false);
                    }
                });

        this.cachedEnergyBars = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        int v = Mth.ceil((cookTime * 100f / 64000) * 64);

                        return helper.drawableBuilder(ENERGY_BAR, 0, 64 - v, 8, v)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.TOP, true);
                    }
                });
    }

    protected IDrawableAnimated getArrow(GemEmpoweringRecipe pRecipe) {
        int cookTime = pRecipe.getCookingTime();
        if (cookTime <= 0) {
            cookTime = regularCookTime;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }

    private IDrawableAnimated getEnergyBar(GemEmpoweringRecipe pRecipe) {
        int cookTime = pRecipe.getCookingTime();
        if (cookTime <= 0) {
            cookTime = regularCookTime;
        }
        return this.cachedEnergyBars.getUnchecked(cookTime);
    }

    protected void drawCookTime(GemEmpoweringRecipe pRecipe, GuiGraphics guiGraphics) {
        int cookTime = pRecipe.getCookingTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            guiGraphics.drawString(fontRenderer, timeString.getString(), 68f, 29.5f, 0xFF808080, false);
        }
    }

    @Override
    public @NotNull RecipeType<GemEmpoweringRecipe> getRecipeType() {
        return GEM_EMPOWERING_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Gem Infusing Station");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(GemEmpoweringRecipe pRecipe, IRecipeSlotsView recipeSlotsView, GuiGraphics pGuiGraphics, double mouseX, double mouseY) {
        IDrawableAnimated arrow = getArrow(pRecipe);
        IDrawableAnimated energyBar = getEnergyBar(pRecipe);
        
        arrow.draw(pGuiGraphics, 60, 20);

        int v = Mth.ceil((pRecipe.getCookingTime() * 100f / 64000) * 64);
        energyBar.draw(pGuiGraphics, 131, 1 + (64 - v));

        drawCookTime(pRecipe, pGuiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GemEmpoweringRecipe pRecipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 1).addIngredients(pRecipe.getIngredient());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 49).addItemStack(pRecipe.getResultItem());
    }
}
