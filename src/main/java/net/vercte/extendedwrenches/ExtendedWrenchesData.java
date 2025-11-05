package net.vercte.extendedwrenches;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.vercte.extendedwrenches.datagen.ExtendedEntriesProvider;
import net.vercte.extendedwrenches.datagen.ExtendedWrenchSwapRecipeProvider;
import net.vercte.extendedwrenches.wrench.WrenchMaterial;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public class ExtendedWrenchesData {
    public static final ResourceKey<Registry<WrenchMaterial>> WRENCH_MATERIAL = key("wrench_material");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ExtendedWrenches.asResource(name));
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

        ExtendedWrenches.REGISTRATE.addDataGenerator(ProviderType.LANG, lang -> {
            String interfacePath = "assets/extendedwrenches/lang/default/interface.json";
            JsonElement jsonElement = FilesHelper.loadJsonResource(interfacePath);
            if (jsonElement == null) {
                throw new IllegalStateException(String.format("Could not find interface lang file: %s", interfacePath));
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().getAsString();
                lang.add(key, value);
            }
        });

        if(event.includeServer()) {
            generator.addProvider(true, new ExtendedEntriesProvider(output, lookupProvider));
            generator.addProvider(true, new ExtendedWrenchSwapRecipeProvider(output));
        }
    }
}
