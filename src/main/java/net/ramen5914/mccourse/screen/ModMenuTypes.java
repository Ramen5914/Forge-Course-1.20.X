package net.ramen5914.mccourse.screen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ramen5914.mccourse.MCCourseMod;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(BuiltInRegistries.MENU,
            MCCourseMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<GemEmpoweringStationMenu>> GEM_EMPOWERING_MENU = MENU_TYPE.register("gem_empowering_menu",
            () -> IMenuTypeExtension.create(GemEmpoweringStationMenu::new));

//    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
//        return MENUS.register(name, () -> IForgeMenuType.create(factory));
//    }

    public static void register(IEventBus eventBus) {
        MENU_TYPE.register(eventBus);
    }
}
