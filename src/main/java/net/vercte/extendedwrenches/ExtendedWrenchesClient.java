package net.vercte.extendedwrenches;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.vercte.extendedwrenches.wrench.ExtendedWrenchItem;

public class ExtendedWrenchesClient implements ClientModInitializer {
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((item, layer) -> {
            if(item.getItem() instanceof ExtendedWrenchItem) {
                if(layer != -1) return ExtendedWrenchItem.getColor(item);
            }
            return 0xffffff;
        }, ExtendedItems.WRENCH.get());
    }
}
