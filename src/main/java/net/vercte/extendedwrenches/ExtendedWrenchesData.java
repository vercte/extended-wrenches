package net.vercte.extendedwrenches;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.vercte.extendedwrenches.wrench.WrenchMaterial;

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
}
