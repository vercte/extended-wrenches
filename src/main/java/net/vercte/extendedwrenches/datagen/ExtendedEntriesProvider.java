package net.vercte.extendedwrenches.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ExtendedEntriesProvider extends FabricDynamicRegistryProvider {
    public ExtendedEntriesProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL));
    }

    public static void addBootstraps(RegistrySetBuilder builder) {
        builder.add(ExtendedWrenchesData.WRENCH_MATERIAL, ExtendedWrenchMaterialTypes::bootstrap);
    }

    @Override
    @NotNull
    public String getName() {
        return "Extended Wrenches' Generated Registry Entries";
    }
}
