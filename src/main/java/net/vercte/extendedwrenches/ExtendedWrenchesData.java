package net.vercte.extendedwrenches;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vercte.extendedwrenches.datagen.ExtendedEntriesProvider;
import net.vercte.extendedwrenches.datagen.ExtendedWrenchSwapRecipeProvider;
import net.vercte.extendedwrenches.wrench.WrenchMaterial;
import net.vercte.extendedwrenches.wrench.components.WrenchMaterialComponent;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public class ExtendedWrenchesData {
    public static final DeferredRegister.DataComponents COMPONENT_REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ExtendedWrenches.ID);

    public static final ResourceKey<Registry<WrenchMaterial>> WRENCH_MATERIAL = key("wrench_material");

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WrenchMaterialComponent>> WRENCH_MATERIAL_COMPONENT = COMPONENT_REGISTRAR.registerComponentType(
            "wrench_material",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(WrenchMaterialComponent.CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(WrenchMaterialComponent.STREAM_CODEC)
    );

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ExtendedWrenches.asResource(name));
    }

    public static void register(IEventBus bus) {
        COMPONENT_REGISTRAR.register(bus);
    }

    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                WRENCH_MATERIAL,
                WrenchMaterial.CODEC,
                WrenchMaterial.CODEC
        );
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        if(event.includeServer()) {
            generator.addProvider(true, new ExtendedEntriesProvider(output, lookupProvider));
            generator.addProvider(true, new ExtendedWrenchSwapRecipeProvider(output, lookupProvider));
        }
    }
}
