package net.vercte.extendedwrenches;

import com.mojang.logging.LogUtils;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

    public static final ItemEntry<ExtendedWrenchItem> WRENCH = REGISTRATE.item("extended_wrench", ExtendedWrenchItem::new)
            .properties(p -> p.stacksTo(1))
            .model(AssetLookup.itemModelWithPartials())
            .tag(AllTags.AllItemTags.WRENCH.tag)
            .register();

    public ExtendedWrenches() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(ExtendedWrenchesData::registerDatapackRegistries);
        modEventBus.addListener(ExtendedWrenchesClient::registerItemColorHandlers);

        ExtendedWrenchesRecipeSerializers.register(modEventBus);
        REGISTRATE.registerEventListeners(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
