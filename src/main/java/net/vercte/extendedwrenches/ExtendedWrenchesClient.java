package net.vercte.extendedwrenches;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vercte.extendedwrenches.wrench.ExtendedWrenchItem;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ExtendedWrenches.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtendedWrenchesClient {
    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((item, layer) -> {
            if(item.getItem() instanceof ExtendedWrenchItem) {
                if(layer != -1) return ExtendedWrenchItem.getColor(item);
            }
            return 0xffffff;
        }, ExtendedItems.WRENCH.get());
    }
}
