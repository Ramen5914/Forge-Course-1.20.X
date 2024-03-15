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
