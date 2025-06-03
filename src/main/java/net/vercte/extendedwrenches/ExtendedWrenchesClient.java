package net.vercte.extendedwrenches;

import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.vercte.extendedwrenches.wrench.ExtendedWrenchItem;

@EventBusSubscriber(value = Dist.CLIENT, modid = ExtendedWrenches.ID, bus = EventBusSubscriber.Bus.MOD)
public class ExtendedWrenchesClient {
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((item, layer) -> {
            if(item.getItem() instanceof ExtendedWrenchItem) {
                if(layer != -1) return DyedItemColor.getOrDefault(item, ExtendedWrenchItem.DEFAULT_GRIP_COLOR);
            }
            return 0xffffff;
        }, ExtendedItems.WRENCH.get());
    }
}
