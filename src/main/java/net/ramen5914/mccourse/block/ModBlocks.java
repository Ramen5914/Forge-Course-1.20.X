package net.ramen5914.mccourse.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.custom.AlexandriteLampBlock;
import net.ramen5914.mccourse.block.custom.GemEmpoweringStationBlock;
import net.ramen5914.mccourse.block.custom.KohlrabiCropBlock;
import net.ramen5914.mccourse.block.custom.SoundBlock;
import net.ramen5914.mccourse.fluid.ModFluids;
import net.ramen5914.mccourse.item.ModItems;
import net.ramen5914.mccourse.sound.ModSounds;

import java.util.Optional;
import java.util.function.Function;

public class ModBlocks {
//        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MCCourseMod.MOD_ID);
        public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MCCourseMod.MOD_ID);

        public static final DeferredBlock<Block> ALEXANDRITE_BLOCK = registerSimpleBlock("alexandrite_block",
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.METAL)
                        .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                        .requiresCorrectToolForDrops()
                        .strength(5.0F, 6.0F)
                        .sound(SoundType.METAL));

        public static final DeferredBlock<Block> RAW_ALEXANDRITE_BLOCK = registerSimpleBlock("raw_alexandrite_block",
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_BLOCK));

        public static final DeferredBlock<Block> ALEXANDRITE_ORE = registerBlock("alexandrite_ore",
                properties -> new DropExperienceBlock(
                        UniformInt.of(2, 5), properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.DEEPSLATE)
                        .strength(5f)
                        .requiresCorrectToolForDrops());

        public static final DeferredBlock<Block> DEEPSLATE_ALEXANDRITE_ORE = registerBlock("deepslate_alexandrite_ore",
                properties -> new DropExperienceBlock(
                        UniformInt.of(3, 7), properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.DEEPSLATE)
                        .strength(5f)
                        .requiresCorrectToolForDrops());

        public static final DeferredBlock<Block> END_STONE_ALEXANDRITE_ORE = registerBlock("end_stone_alexandrite_ore",
                properties -> new DropExperienceBlock(
                        UniformInt.of(5, 8), properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.END_STONE)
                        .strength(5f).requiresCorrectToolForDrops());

        public static final DeferredBlock<Block> NETHER_ALEXANDRITE_ORE = registerBlock("nether_alexandrite_ore",
                properties -> new DropExperienceBlock(
                        UniformInt.of(3, 6), properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.NETHERRACK)
                        .strength(5f).requiresCorrectToolForDrops());

        public static final DeferredBlock<Block> SOUND_BLOCK = registerBlock("sound_block",
                SoundBlock::new,
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_BLOCK));

        public static final DeferredBlock<Block> ALEXANDRITE_STAIRS = registerBlock("alexandrite_stairs",
                properties -> new StairBlock(
                        () -> ModBlocks.ALEXANDRITE_BLOCK.get().defaultBlockState(), properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.GRANITE_STAIRS)
                        .sound(SoundType.METAL));

        public static final DeferredBlock<Block> ALEXANDRITE_SLAB = registerBlock("alexandrite_slab",
                SlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(
                        Blocks.GRANITE_SLAB)
                        .sound(SoundType.METAL));

        public static final DeferredBlock<Block> ALEXANDRITE_PRESSURE_PLATE = registerBlock("alexandrite_pressure_plate",
                properties -> new PressurePlateBlock(BlockSetType.IRON, properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE));

        public static final DeferredBlock<Block> ALEXANDRITE_BUTTON = registerBlock("alexandrite_button",
                properties -> new ButtonBlock(
                        BlockSetType.IRON,
                        5, properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.STONE_BUTTON));

        public static final DeferredBlock<Block> ALEXANDRITE_FENCE = registerBlock("alexandrite_fence",
                FenceBlock::new,
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.OAK_FENCE));

        public static final DeferredBlock<Block> ALEXANDRITE_FENCE_GATE = registerBlock("alexandrite_fence_gate",
                properties -> new FenceGateBlock(
                        Optional.of(WoodType.OAK),
                        properties,
                        Optional.of(SoundEvents.FENCE_GATE_OPEN),
                        Optional.of(SoundEvents.FENCE_GATE_CLOSE)),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.OAK_FENCE_GATE));

        public static final DeferredBlock<Block> ALEXANDRITE_WALL = registerBlock("alexandrite_wall",
                WallBlock::new,
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.GRANITE_WALL));

        public static final DeferredBlock<Block> ALEXANDRITE_DOOR = registerBlock("alexandrite_door",
                properties -> new DoorBlock(
                        BlockSetType.IRON, properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_DOOR));

        public static final DeferredBlock<Block> ALEXANDRITE_TRAPDOOR = registerBlock("alexandrite_trapdoor",
                properties -> new TrapDoorBlock(
                        BlockSetType.IRON, properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_TRAPDOOR));

        public static final DeferredBlock<Block> ALEXANDRITE_LAMP = registerBlock("alexandrite_lamp",
                AlexandriteLampBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_BLUE)
                        .sound(ModSounds.ALEXANDRITE_LAMP_SOUNDS)
                        .lightLevel(state -> state.getValue(AlexandriteLampBlock.CLICKED) ? 15 : 0)
                        .strength(1f));

        public static final DeferredBlock<Block> KOHLRABI_CROP = BLOCKS.registerBlock("kohlrabi_crop",
                KohlrabiCropBlock::new,
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.WHEAT)
                        .noCollission()
                        .noOcclusion());

        public static final DeferredBlock<Block> SNAPDRAGON = registerBlock("snapdragon",
                properties -> new FlowerBlock(
                        MobEffects.BLINDNESS,
                        6, properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.ALLIUM));

        public static final DeferredBlock<Block> POTTED_SNAPDRAGON = registerBlock("potted_snapdragon",
                properties -> new FlowerPotBlock(
                        () -> (FlowerPotBlock) Blocks.FLOWER_POT,
                        SNAPDRAGON, properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.POTTED_ALLIUM));

        public static final DeferredBlock<Block> GEM_EMPOWERING_STATION = registerBlock("gem_empowering_station",
                 GemEmpoweringStationBlock::new,
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_BLOCK)
                        .noOcclusion());

        public static final DeferredBlock<LiquidBlock> SOAP_WATER_BLOCK = BLOCKS.registerBlock("soap_water_block",
                properties -> new LiquidBlock(
                        ModFluids.SOURCE_SOAP_WATER, properties),
                BlockBehaviour.Properties
                        .ofFullCopy(Blocks.WATER)
                        .noLootTable());

        private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends T> func, BlockBehaviour.Properties props) {
                DeferredBlock<T> createdBlock = BLOCKS.registerBlock(name, func, props);
                registerSimpleBlockItem(name, createdBlock);

                return createdBlock;
        }

        private static DeferredBlock<Block> registerSimpleBlock(String name, BlockBehaviour.Properties props) {
                DeferredBlock<Block> createdBlock = BLOCKS.registerSimpleBlock(name, props);
                registerSimpleBlockItem(name, createdBlock);

                return createdBlock;
        }

        private static <T extends Block> DeferredItem<BlockItem> registerSimpleBlockItem(String name, DeferredBlock<T> block) {
                return ModItems.ITEMS.registerSimpleBlockItem(name, block);
        }

        public static void register(IEventBus eventBus) {
                BLOCKS.register(eventBus);
        }
}
