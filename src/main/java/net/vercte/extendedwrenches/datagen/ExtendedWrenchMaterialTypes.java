package net.vercte.extendedwrenches.datagen;

import net.minecraft.resources.ResourceKey;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import net.vercte.extendedwrenches.wrench.WrenchMaterial;
import net.minecraft.data.worldgen.BootstapContext;
import net.vercte.extendedwrenches.wrench.WrenchPart;

public class ExtendedWrenchMaterialTypes {

    public static void bootstrap(BootstapContext<WrenchMaterial> ctx) {

        // region Heads
        register(ctx, "gold_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/gold")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "amethyst_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/amethyst")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "andesite_alloy_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/andesite_alloy")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "brass_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/brass")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "copper_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/copper")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "diamond_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/diamond")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "emerald_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/emerald")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "iron_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/iron")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "polished_rose_quartz_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/polished_rose_quartz")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "zinc_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/zinc")
                .part(WrenchPart.HEAD)
                .build());

        // endregion

        // region Handles
        register(ctx, "oak_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/oak")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "spruce_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/spruce")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "dark_oak_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/dark_oak")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "birch_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/birch")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "jungle_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/jungle")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "cherry_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/cherry")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "mangrove_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/mangrove")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "acacia_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/acacia")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "crimson_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/crimson")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "warped_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/warped")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "bamboo_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/bamboo")
                .part(WrenchPart.HANDLE)
                .build());
        // endregion

        // region Cogs
        register(ctx, "oak_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/oak")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "spruce_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/spruce")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "dark_oak_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/dark_oak")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "birch_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/birch")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "jungle_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/jungle")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "cherry_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/cherry")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "mangrove_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/mangrove")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "acacia_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/acacia")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "crimson_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/crimson")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "warped_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/warped")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "bamboo_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/bamboo")
                .part(WrenchPart.COG)
                .build());
        // endregion
    }

    private static void register(BootstapContext<WrenchMaterial> ctx, String name, WrenchMaterial type) {
        ctx.register(ResourceKey.create(ExtendedWrenchesData.WRENCH_MATERIAL, ExtendedWrenches.asResource(name)), type);
    }
}
