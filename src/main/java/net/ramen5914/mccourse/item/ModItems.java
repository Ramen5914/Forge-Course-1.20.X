package net.ramen5914.mccourse.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;
import net.ramen5914.mccourse.block.ModBlocks;
import net.ramen5914.mccourse.fluid.ModFluids;
import net.ramen5914.mccourse.item.custom.*;
import net.ramen5914.mccourse.sound.ModSounds;

public class ModItems {
        public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MCCourseMod.MOD_ID);

        public static final DeferredItem<Item> ALEXANDRITE = ITEMS.registerSimpleItem("alexandrite");

        public static final DeferredItem<Item> RAW_ALEXANDRITE = ITEMS.registerSimpleItem("raw_alexandrite");

        public static final DeferredItem<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
                        () -> new MetalDetectorItem(new Item.Properties()
                                        .durability(512)));

        public static final DeferredItem<Item> KOHLRABI = ITEMS.registerSimpleItem("kohlrabi",
                        new Item.Properties().food(new FoodProperties.Builder()
                                .nutrition(3).saturationMod(0.25f)
                                .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.1f)
                                .build()));

        public static final DeferredItem<Item> PEAT_BRICK = ITEMS.register("peat_brick",
                () -> new FuelItem(new Item.Properties(), 200));

        public static final DeferredItem<Item> ALEXANDRITE_SWORD = ITEMS.register("alexandrite_sword",
                        () -> new SlowingSwordItem(ModToolTiers.ALEXANDRITE, 2, 3f,
                                        new Item.Properties().durability(256)));

        public static final DeferredItem<Item> ALEXANDRITE_PICKAXE = ITEMS.register("alexandrite_pickaxe",
                        () -> new PickaxeItem(ModToolTiers.ALEXANDRITE, 1, 2f,
                                        new Item.Properties().durability(256)));
        public static final DeferredItem<Item> ALEXANDRITE_AXE = ITEMS.register("alexandrite_axe",
                        () -> new AxeItem(ModToolTiers.ALEXANDRITE, 2, 3f,
                                        new Item.Properties().durability(256)));
        public static final DeferredItem<Item> ALEXANDRITE_SHOVEL = ITEMS.register("alexandrite_shovel",
                        () -> new ShovelItem(ModToolTiers.ALEXANDRITE, 2, 3f,
                                        new Item.Properties().durability(256)));
        public static final DeferredItem<Item> ALEXANDRITE_HOE = ITEMS.register("alexandrite_hoe",
                        () -> new HoeItem(ModToolTiers.ALEXANDRITE, 2, 3f,
                                        new Item.Properties().durability(256)));

        public static final DeferredItem<Item> ALEXANDRITE_PAXEL = ITEMS.register("alexandrite_paxel",
                        () -> new PaxelItem(ModToolTiers.ALEXANDRITE, 2, 3f,
                                        new Item.Properties().durability(256)));
        public static final DeferredItem<Item> ALEXANDRITE_HAMMER = ITEMS.register("alexandrite_hammer",
                        () -> new HammerItem(ModToolTiers.ALEXANDRITE, 2, 3f,
                                        new Item.Properties().durability(256)));

        public static final DeferredItem<Item> ALEXANDRITE_HELMET = ITEMS.register("alexandrite_helmet",
                        () -> new ModArmorItem(ModArmorMaterials.ALEXANDRITE, ArmorItem.Type.HELMET,
                                        new Item.Properties()));
        public static final DeferredItem<Item> ALEXANDRITE_CHESTPLATE = ITEMS.register("alexandrite_chestplate",
                        () -> new ModArmorItem(ModArmorMaterials.ALEXANDRITE, ArmorItem.Type.CHESTPLATE,
                                        new Item.Properties()));
        public static final DeferredItem<Item> ALEXANDRITE_LEGGINGS = ITEMS.register("alexandrite_leggings",
                        () -> new ModArmorItem(ModArmorMaterials.ALEXANDRITE, ArmorItem.Type.LEGGINGS,
                                        new Item.Properties()));
        public static final DeferredItem<Item> ALEXANDRITE_BOOTS = ITEMS.register("alexandrite_boots",
                        () -> new ModArmorItem(ModArmorMaterials.ALEXANDRITE, ArmorItem.Type.BOOTS,
                                        new Item.Properties()));

        public static final DeferredItem<Item> ALEXANDRITE_HORSE_ARMOR = ITEMS.register("alexandrite_horse_armor",
                        () -> new HorseArmorItem(12, new ResourceLocation(MCCourseMod.MOD_ID,
                                        "textures/entity/horse/armor/horse_armor_" + "alexandrite" + ".png"),
                                        new Item.Properties()));

        public static final DeferredItem<Item> DATA_TABLET = ITEMS.register("data_tablet",
                        () -> new DataTabletItem(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<BlockItem> KOHLRABI_SEEDS = ITEMS.registerSimpleBlockItem("kohlrabi_seeds", ModBlocks.KOHLRABI_CROP);

        public static final DeferredItem<Item> BAR_BRAWL_RECORD = ITEMS.register("bar_brawl_record",
                        () -> new RecordItem(4, ModSounds.BAR_BRAWL, new Item.Properties().stacksTo(1), 2440));

        public static final DeferredItem<Item> RADIATION_STAFF = ITEMS.register("radiation_staff",
                        () -> new Item(new Item.Properties()
                                        .durability(1024)));

        public static final DeferredItem<Item> ALEXANDRITE_BOW = ITEMS.register("alexandrite_bow",
                        () -> new BowItem(new Item.Properties()
                                        .durability(500)));

        public static final DeferredItem<Item> ALEXANDRITE_SHIELD = ITEMS.register("alexandrite_shield",
                        () -> new ShieldItem(new Item.Properties()
                                        .durability(500)));

        public static final DeferredItem<Item> SOAP_WATER_BUCKET = ITEMS.register("soap_water_bucket",
                        () -> new BucketItem(ModFluids.SOURCE_SOAP_WATER, new Item.Properties()
                                        .craftRemainder(Items.BUCKET)
                                        .stacksTo(1)));

        public static void register(IEventBus eventBus) {
                ITEMS.register(eventBus);
        }
}
