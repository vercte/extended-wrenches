package net.vercte.extendedwrenches.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ExtendedEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ExtendedWrenchesData.WRENCH_MATERIAL, ExtendedWrenchMaterialTypes::bootstrap);

    public ExtendedEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ExtendedWrenches.ID));
    }

    @Override
    @NotNull
    public String getName() {
        return "Extended Wrenches' Generated Registry Entries";
    }
}
