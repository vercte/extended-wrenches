package net.vercte.extendedwrenches;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.vercte.extendedwrenches.datagen.ExtendedEntriesProvider;
import net.vercte.extendedwrenches.wrench.WrenchMaterial;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public class ExtendedWrenchesData {
    public static final ResourceKey<Registry<WrenchMaterial>> WRENCH_MATERIAL = key("wrench_material");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ExtendedWrenches.asResource(name));
    }

    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        ExtendedWrenches.LOGGER.info("{}", WRENCH_MATERIAL);
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
        }
    }
}
