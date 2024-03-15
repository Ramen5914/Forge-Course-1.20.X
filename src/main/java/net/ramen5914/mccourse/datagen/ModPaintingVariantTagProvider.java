package net.ramen5914.mccourse.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PaintingVariantTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.ramen5914.mccourse.MCCourseMod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModPaintingVariantTagProvider extends PaintingVariantTagsProvider {
    public ModPaintingVariantTagProvider(PackOutput pPackOutput, CompletableFuture<HolderLookup.Provider> pFuture, @Nullable ExistingFileHelper pExistingFileHelper) {
        super(pPackOutput, pFuture, MCCourseMod.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(PaintingVariantTags.PLACEABLE)
                .addOptional(new ResourceLocation(MCCourseMod.MOD_ID, "saw_them"))
                .addOptional(new ResourceLocation(MCCourseMod.MOD_ID, "shrimp"))
                .addOptional(new ResourceLocation(MCCourseMod.MOD_ID, "world"));
    }
}
