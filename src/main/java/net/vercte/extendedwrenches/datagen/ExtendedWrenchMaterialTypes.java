package net.vercte.extendedwrenches.datagen;

import com.simibubi.create.AllItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import net.vercte.extendedwrenches.wrench.WrenchMaterial;
import net.minecraft.data.worldgen.BootstapContext;

public class ExtendedWrenchMaterialTypes {

    public static void bootstrap(BootstapContext<WrenchMaterial> ctx) {

        // region Heads
        register(ctx, "gold_head", new WrenchMaterial.Builder()
                .item(Items.GOLD_INGOT)
                .texture("item/extended_wrench/head_materials/gold")
                .part("head")
                .build());

        register(ctx, "amethyst_head", new WrenchMaterial.Builder()
                .item(Items.AMETHYST_SHARD, Items.AMETHYST_BLOCK)
                .texture("item/extended_wrench/head_materials/amethyst")
                .part("head")
                .build());

        register(ctx, "andesite_alloy_head", new WrenchMaterial.Builder()
                .item(AllItems.ANDESITE_ALLOY.get())
                .texture("item/extended_wrench/head_materials/andesite_alloy")
                .part("head")
                .build());

        register(ctx, "brass_head", new WrenchMaterial.Builder()
                .item(AllItems.BRASS_INGOT.get())
                .texture("item/extended_wrench/head_materials/brass")
                .part("head")
                .build());

        register(ctx, "copper_head", new WrenchMaterial.Builder()
                .item(Items.COPPER_INGOT)
                .texture("item/extended_wrench/head_materials/copper")
                .part("head")
                .build());

        register(ctx, "diamond_head", new WrenchMaterial.Builder()
                .item(Items.DIAMOND)
                .texture("item/extended_wrench/head_materials/diamond")
                .part("head")
                .build());

        register(ctx, "emerald_head", new WrenchMaterial.Builder()
                .item(Items.EMERALD)
                .texture("item/extended_wrench/head_materials/emerald")
                .part("head")
                .build());

        register(ctx, "iron_head", new WrenchMaterial.Builder()
                .item(Items.IRON_INGOT)
                .texture("item/extended_wrench/head_materials/iron")
                .part("head")
                .build());

        register(ctx, "polished_rose_quartz_head", new WrenchMaterial.Builder()
                .item(AllItems.POLISHED_ROSE_QUARTZ.get())
                .texture("item/extended_wrench/head_materials/polished_rose_quartz")
                .part("head")
                .build());

        register(ctx, "zinc_head", new WrenchMaterial.Builder()
                .item(AllItems.ZINC_INGOT.get())
                .texture("item/extended_wrench/head_materials/zinc")
                .part("head")
                .build());

        // endregion

        // region Handles
        register(ctx, "oak_handle", new WrenchMaterial.Builder()
                .item(Items.OAK_PLANKS)
                .texture("item/extended_wrench/handle_materials/oak")
                .part("handle")
                .build());

        register(ctx, "spruce_handle", new WrenchMaterial.Builder()
                .item(Items.SPRUCE_PLANKS)
                .texture("item/extended_wrench/handle_materials/spruce")
                .part("handle")
                .build());

        register(ctx, "dark_oak_handle", new WrenchMaterial.Builder()
                .item(Items.DARK_OAK_PLANKS)
                .texture("item/extended_wrench/handle_materials/dark_oak")
                .part("handle")
                .build());

        register(ctx, "birch_handle", new WrenchMaterial.Builder()
                .item(Items.BIRCH_PLANKS)
                .texture("item/extended_wrench/handle_materials/birch")
                .part("handle")
                .build());

        register(ctx, "jungle_handle", new WrenchMaterial.Builder()
                .item(Items.JUNGLE_PLANKS)
                .texture("item/extended_wrench/handle_materials/jungle")
                .part("handle")
                .build());

        register(ctx, "cherry_handle", new WrenchMaterial.Builder()
                .item(Items.CHERRY_PLANKS)
                .texture("item/extended_wrench/handle_materials/cherry")
                .part("handle")
                .build());

        register(ctx, "mangrove_handle", new WrenchMaterial.Builder()
                .item(Items.MANGROVE_PLANKS)
                .texture("item/extended_wrench/handle_materials/mangrove")
                .part("handle")
                .build());

        register(ctx, "acacia_handle", new WrenchMaterial.Builder()
                .item(Items.ACACIA_PLANKS)
                .texture("item/extended_wrench/handle_materials/acacia")
                .part("handle")
                .build());

        register(ctx, "crimson_handle", new WrenchMaterial.Builder()
                .item(Items.CRIMSON_PLANKS)
                .texture("item/extended_wrench/handle_materials/crimson")
                .part("handle")
                .build());

        register(ctx, "warped_handle", new WrenchMaterial.Builder()
                .item(Items.WARPED_PLANKS)
                .texture("item/extended_wrench/handle_materials/warped")
                .part("handle")
                .build());

        register(ctx, "bamboo_handle", new WrenchMaterial.Builder()
                .item(Items.BAMBOO_PLANKS)
                .texture("item/extended_wrench/handle_materials/bamboo")
                .part("handle")
                .build());
        // endregion
    }

    private static void register(BootstapContext<WrenchMaterial> ctx, String name, WrenchMaterial type) {
        ctx.register(ResourceKey.create(ExtendedWrenchesData.WRENCH_MATERIAL, ExtendedWrenches.asResource(name)), type);
    }
}
