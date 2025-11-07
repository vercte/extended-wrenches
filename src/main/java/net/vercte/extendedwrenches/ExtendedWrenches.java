package net.vercte.extendedwrenches;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vercte.extendedwrenches.compat.ModCompat;
import net.vercte.extendedwrenches.wrench.ExtendedWrenchItem;
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

        modEventBus.addListener(ExtendedWrenches::initExtra);
        modEventBus.addListener(ExtendedItems::addToCreative);
        modEventBus.addListener(ExtendedWrenchesData::registerDatapackRegistries);
        modEventBus.addListener(EventPriority.LOWEST, ExtendedWrenchesData::gatherData);

        ExtendedWrenchesRecipeSerializers.register(modEventBus);
        REGISTRATE.registerEventListeners(modEventBus);
    }

    public static void initExtra(final FMLCommonSetupEvent event) {
        CauldronInteraction.WATER.put(ExtendedItems.WRENCH.get(), (state, level, pos, player, hand, stack) -> {
            if (!stack.is(ExtendedItems.WRENCH.get())) {
                return InteractionResult.PASS;
            } else if (!ExtendedWrenchItem.hasCustomColor(stack)) {
                return InteractionResult.PASS;
            } else {
                if (!level.isClientSide) {
                    ExtendedWrenchItem.clearColor(stack);
                    player.awardStat(Stats.CLEAN_ARMOR);
                    LayeredCauldronBlock.lowerFillLevel(state, level, pos);
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        });
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
