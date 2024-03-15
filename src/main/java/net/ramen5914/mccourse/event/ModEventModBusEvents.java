package net.ramen5914.mccourse.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.server.command.ConfigCommand;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.ModBlocks;
import net.ramen5914.mccourse.block.entity.ModBlockEntities;
import net.ramen5914.mccourse.command.ReturnHomeCommand;
import net.ramen5914.mccourse.command.SetHomeCommand;
import net.ramen5914.mccourse.item.ModItems;
import net.ramen5914.mccourse.item.custom.HammerItem;
import net.ramen5914.mccourse.villager.ModVillagers;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MCCourseMod.MOD_ID, bus = ModEventBusSubscriber.Bus.MOD)
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
