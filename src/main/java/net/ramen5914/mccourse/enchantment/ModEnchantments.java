package net.ramen5914.mccourse.enchantment;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;

import java.util.function.Supplier;

public class ModEnchantments {
        public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
                        .create(BuiltInRegistries.ENCHANTMENT, MCCourseMod.MOD_ID);

        public static final Supplier<LightningStrikerEnchantment> LIGHTNING_STRIKER =
                ENCHANTMENTS.register("lightning_striker", () -> new LightningStrikerEnchantment(Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

        public static void register(IEventBus eventBus) {
                ENCHANTMENTS.register(eventBus);
        }
}
