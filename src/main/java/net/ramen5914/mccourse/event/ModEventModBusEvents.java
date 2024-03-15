package net.ramen5914.mccourse.event;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.entity.ModBlockEntities;
import net.ramen5914.mccourse.item.ModItems;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = MCCourseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventModBusEvents {
  @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.GEM_EMPOWERING_STATION_BE_TYPE.get(),
                (be, side) -> new ItemStackHandler(4) {
                    @Override
                    protected void onContentsChanged(int slot) {
                        be.setChanged();
                    }

                    @Override
                    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                        return switch (slot) {
                            case 0, 1 -> true;
                            case 2 -> false;
                            case 3 -> stack.getItem() == ModItems.KOHLRABI.get();
                            default -> super.isItemValid(slot, stack);
                        };
                    }
                }
        );
    }
}
