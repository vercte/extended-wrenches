package net.vercte.extendedwrenches;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.vercte.extendedwrenches.wrench.ExtendedWrenchItem;

public class ExtendedItems {
    private static final CreateRegistrate REGISTRATE = ExtendedWrenches.REGISTRATE;

    public static final ItemEntry<ExtendedWrenchItem> WRENCH = REGISTRATE.item("extended_wrench", ExtendedWrenchItem::new)
            .properties(p -> p.stacksTo(1))
            .model(AssetLookup.itemModelWithPartials())
            .lang(w -> "item.extendedwrenches.nope_fake_wrench_name_i_hate_you_registrate", "Wrench")
            .tag(AllTags.AllItemTags.WRENCH.tag)
            .register();

    public static final ItemEntry<Item> WRENCH_HEAD_AUGMENT = REGISTRATE.item("wrench_head_augment", Item::new)
            .lang("Wrench Head Augment")
            .register();

    public static final ItemEntry<Item> WRENCH_HANDLE_AUGMENT = REGISTRATE.item("wrench_handle_augment", Item::new)
            .lang("Wrench Handle Augment")
            .register();

    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_WRENCH_HEAD_AUGMENT = REGISTRATE.item("incomplete_wrench_head_augment", SequencedAssemblyItem::new)
            .lang("Incomplete Wrench Head Augment")
            .register();

    public static void addToCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.getEntries().putAfter(new ItemStack(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE), WRENCH_HANDLE_AUGMENT.asStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(WRENCH_HANDLE_AUGMENT.asStack(), WRENCH_HEAD_AUGMENT.asStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static void init() {}
}
