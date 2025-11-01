package net.vercte.extendedwrenches;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vercte.extendedwrenches.compat.ModCompat;
import org.slf4j.Logger;

@Mod(ExtendedWrenches.ID)
public class ExtendedWrenches {
    public static final String ID = "extendedwrenches";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(item))));

    public ExtendedWrenches() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ExtendedItems.init();
        everyCompatModule();

        modEventBus.addListener(ExtendedItems::addToCreative);
        modEventBus.addListener(ExtendedWrenchesData::registerDatapackRegistries);
        modEventBus.addListener(EventPriority.LOWEST, ExtendedWrenchesData::gatherData);

        ExtendedWrenchesRecipeSerializers.register(modEventBus);
        REGISTRATE.registerEventListeners(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }

    private static void everyCompatModule() {
        try {
            if (ModList.get().isLoaded("everycomp")) {
                ModCompat.init();
                LOGGER.debug("Every Compat found, starting compatibility");
            } else {
                LOGGER.debug("Every Compat is not installed");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to start EveryComp module", e);
        }
    }
}
