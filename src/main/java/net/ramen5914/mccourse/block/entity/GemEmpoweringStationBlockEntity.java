package net.ramen5914.mccourse.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.ramen5914.mccourse.item.ModItems;
import net.ramen5914.mccourse.recipe.GemEmpoweringRecipe;
import net.ramen5914.mccourse.recipe.ModRecipeTypes;
import net.ramen5914.mccourse.screen.GemEmpoweringStationMenu;
import net.ramen5914.mccourse.util.InventoryDirectionEntry;
import net.ramen5914.mccourse.util.InventoryDirectionWrapper;
import net.ramen5914.mccourse.util.ModEnergyStorage;
import net.ramen5914.mccourse.util.WrappedHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class GemEmpoweringStationBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            super.onContentsChanged(slot);
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
    };

    private static final int INPUT_SLOT = 0;
    private static final int FLUID_INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int ENERGY_ITEM_SLOT = 3;

    private final Map<Direction, Lazy<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.NORTH, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.WEST, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.UP, INPUT_SLOT, true)).directionsMap;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();

    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(64000, 200) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    public GemEmpoweringStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GEM_EMPOWERING_STATION_BE_TYPE.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> GemEmpoweringStationBlockEntity.this.progress;
                    case 1 -> GemEmpoweringStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> GemEmpoweringStationBlockEntity.this.progress = pValue;
                    case 1 -> GemEmpoweringStationBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public IEnergyStorage getEnergyStorage() {
        return this.ENERGY_STORAGE;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Gem Empowering Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GemEmpoweringStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }


//    @Override
//    public @NotNull <T> Lazy<T> getCapability(@NotNull BlockCapability<T, T> cap, @Nullable Direction side) {
//        if (cap.equals(Capabilities.EnergyStorage.BLOCK)) {
//            return lazyEnergyHandler.cast();
//        }
//
//        if (cap == ForgeCapabilities.ITEM_HANDLER) {
//            if (side == null) {
//                return lazyItemHandler.cast();
//            }
//
//            if (directionWrappedHandlerMap.containsKey(side)) {
//                Direction localDir = this.getBlockState().getValue(GemEmpoweringStationBlock.FACING);
//                if (side == Direction.DOWN || side == Direction.UP) {
//                    return directionWrappedHandlerMap.get(side).cast();
//                }
//
//                return switch (localDir) {
//                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
//                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
//                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
//                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
//                };
//            }
//        }
//
//        return super.getCapability(cap, side);
//    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("gem_empowering_station.progress", progress);
        pTag.putInt("energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("gem_empowering_station.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("energy"));
    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {
        fillUpOnEnergy();

        if (isOutputSlotEmptyOrReceivable()) {
            if (hasRecipe()) {
                increaseCraftingProcess();
                extractEnergy();

                if (hasProgressFinished()) {
                    craftItem();
                    this.progress = 0;
                }
                setChanged(level, pPos, pState);
            } else {
                this.progress = 0;
            }
        } else {
            this.progress = 0;
        }
    }

    private void extractEnergy() {
        this.ENERGY_STORAGE.extractEnergy(50, false);
    }

    private void fillUpOnEnergy() {
        if (hasEnergyItemInSlot(ENERGY_ITEM_SLOT)) {
            if ((this.ENERGY_STORAGE.getMaxEnergyStored() - this.ENERGY_STORAGE.getEnergyStored()) >= 200) {
                this.ENERGY_STORAGE.receiveEnergy(200, false);

                this.itemHandler.extractItem(ENERGY_ITEM_SLOT, 1, false);
            }
        }
    }

    private boolean hasEnergyItemInSlot(int energyItemSlot) {
        return !this.itemHandler.getStackInSlot(energyItemSlot).isEmpty() &&
                this.itemHandler.getStackInSlot(energyItemSlot).getItem() == ModItems.KOHLRABI.get();
    }

    private void craftItem() {
        Optional<RecipeHolder<GemEmpoweringRecipe>> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.map(gemEmpoweringRecipeRecipeHolder -> gemEmpoweringRecipeRecipeHolder.value().getResultItem(this.getLevel().registryAccess())).orElse(ItemStack.EMPTY);

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(resultItem.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + resultItem.getCount()));
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<GemEmpoweringRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack resultItem = recipe.get().value().getResultItem(this.getLevel().registryAccess());

        this.maxProgress = recipe.get().value().getCookingTime();

        return canInsertAmountIntoOutputSlot(resultItem.getCount())
                && canInsertItemIntoOutputSlot(resultItem.getItem())
                && hasEnoughEnergyToCraft();
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.ENERGY_STORAGE.getEnergyStored() >= 100;
    }

    private Optional<RecipeHolder<GemEmpoweringRecipe>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GEM_EMPOWERING.get(), inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize() >=
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() < this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.DOWN) {
            return new int[]{OUTPUT_SLOT};
        } else if (pSide == Direction.WEST) {
            return new int[]{FLUID_INPUT_SLOT};
        } else if (pSide == Direction.EAST) {
            return new int[]{ENERGY_ITEM_SLOT};
        } else {
            return new int[]{INPUT_SLOT};
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pStack, @Nullable Direction pDirection) {
        if (pIndex == 2) {
            return false;
        } else if (pIndex != 1) {
            return true;
        } else {
            ItemStack itemstack = (ItemStack) this.items.get(1);
            return CommonHooks.getBurnTime(pStack, this.recipeType) > 0 || pStack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        if (pDirection == Direction.DOWN && pIndex == 1) {
            return pStack.is(Items.WATER_BUCKET) || pStack.is(Items.BUCKET);
        } else {
            return true;
        }
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return null;
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {

    }

    @Override
    public boolean stillValid(Player player) {
        this.getLevel().invalidateCapabilities(this.getBlockPos());
        return true;
    }

    @Override
    public void clearContent() {

    }
}