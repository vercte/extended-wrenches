package net.vercte.extendedwrenches;

import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.vercte.extendedwrenches.wrench.ExtendedWrenchItem;

public class ExtendedWrenchesClient {
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((item, layer) -> {
            if(item.getItem() instanceof ExtendedWrenchItem) {
                if(layer != -1) return ExtendedWrenchItem.getColor(item);
            }
            return 0xffffff;
        }, ExtendedItems.WRENCH.get());
    }
}
