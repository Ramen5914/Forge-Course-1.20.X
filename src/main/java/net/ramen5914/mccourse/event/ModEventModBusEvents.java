package net.ramen5914.mccourse.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.entity.ModBlockEntities;

@Mod.EventBusSubscriber(modid = MCCourseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventModBusEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.GEM_EMPOWERING_STATION_BE_TYPE.get(),
                (be, side) -> be.getItemHandler()
        );

        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                ModBlockEntities.GEM_EMPOWERING_STATION_BE_TYPE.get(),
                (be, side) -> be.getEnergyStorage()
        );
    }
}
