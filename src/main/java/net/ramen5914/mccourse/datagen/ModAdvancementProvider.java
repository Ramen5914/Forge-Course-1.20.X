package net.ramen5914.mccourse.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.item.ModItems;

import java.util.Optional;
import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
        AdvancementHolder rootAdvancement = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(ModItems.ALEXANDRITE.get()),
                        Component.literal("MC Course"), Component.literal("The Power lies in the Alexandrite!"),
                        Optional.of(new ResourceLocation(MCCourseMod.MOD_ID, "textures/block/alexandrite_ore.png")), AdvancementType.TASK,
                        true, true, false))
                .addCriterion("has_alexandrite", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE.get()))
                .save(consumer, new ResourceLocation(MCCourseMod.MOD_ID, "alexandrite").toString());

        AdvancementHolder metalDetector = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(ModItems.METAL_DETECTOR.get()),
                        Component.literal("Metal Detector"), Component.literal("Batteries not included! (Actually doesn't need batteries)"),
                        Optional.empty(), AdvancementType.TASK,
                        true, true, false))
                .parent(rootAdvancement)
                .addCriterion("has_metal_detector", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.METAL_DETECTOR.get()))
                .save(consumer, new ResourceLocation(MCCourseMod.MOD_ID, "metal_detector").toString());
    }
}
