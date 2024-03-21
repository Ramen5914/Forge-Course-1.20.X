package net.ramen5914.mccourse.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.entity.GemEmpoweringStationBlockEntity;
import net.ramen5914.mccourse.util.MouseUtil;

import java.util.List;
import java.util.Optional;

public class GemEmpoweringStationScreen extends AbstractContainerScreen<GemEmpoweringStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MCCourseMod.MOD_ID, "textures/gui/container/gem_empowering_station_gui.png");
    private final GemEmpoweringStationBlockEntity blockEntity = menu.blockEntity;

    public GemEmpoweringStationScreen(GemEmpoweringStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltip(pGuiGraphics, pMouseX, pMouseY, x, y);
    }

    private void renderEnergyAreaTooltip(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 11, 8, 64)) {
            pGuiGraphics.renderTooltip(this.font, List.of(Component.literal(blockEntity.getEnergyStorage().getEnergyStored() + " / " + blockEntity.getEnergyStorage().getMaxEnergyStored() + " FE")),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderEnergyBar(guiGraphics, x, y);
        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderEnergyBar(GuiGraphics pGuiGraphics, int x, int y) {
        if (blockEntity.getEnergyStorage().getEnergyStored() > 0) {
            int l = Mth.ceil(menu.getEnergyLevel() * 64f);
            pGuiGraphics.blit(TEXTURE, x + 156, y + 75 - l, 184, 64-l, 8, l);
        }
    }

    private void renderProgressArrow(GuiGraphics pGuiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            int pixelsToRender = Mth.ceil(menu.getEmpoweringProgress() * 26f);
            pGuiGraphics.blit(TEXTURE, x + 85, y + 30, 176, 0, 8, pixelsToRender);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
