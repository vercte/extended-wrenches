package net.vercte.extendedwrenches;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import net.vercte.extendedwrenches.datagen.ExtendedEntriesProvider;
import net.vercte.extendedwrenches.datagen.ExtendedWrenchSwapRecipeProvider;
import net.vercte.extendedwrenches.wrench.WrenchMaterial;

import java.util.Map;

@SuppressWarnings("SameParameterValue")
public class ExtendedWrenchesData implements DataGeneratorEntrypoint {
    public static final ResourceKey<Registry<WrenchMaterial>> WRENCH_MATERIAL = key("wrench_material");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ExtendedWrenches.asResource(name));
    }

    public static void registerDatapackRegistries() {
        DynamicRegistries.registerSynced(WRENCH_MATERIAL, WrenchMaterial.CODEC);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        ExtendedEntriesProvider.addBootstraps(builder);
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        ExtendedWrenches.REGISTRATE.setupDatagen(pack, helper);

        gatherData(pack);
    }

    public static void gatherData(FabricDataGenerator.Pack pack) {
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

        pack.addProvider(ExtendedEntriesProvider::new);
        pack.addProvider(ExtendedWrenchSwapRecipeProvider::new);
    }
}
