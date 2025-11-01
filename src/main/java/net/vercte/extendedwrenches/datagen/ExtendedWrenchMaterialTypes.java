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

        register(ctx, "quartz", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/quartz")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "lapis_lazuli", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/lapis_lazuli")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "netherite_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/netherite")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "asurine_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/asurine")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "crimsite_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/crimsite")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "ochrum_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/ochrum")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "veridium_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/veridium")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "scorchia_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/scorchia")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "scoria_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/scoria")
                .part(WrenchPart.HEAD)
                .build());

        register(ctx, "spooky_head", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/head_materials/spooky")
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

        register(ctx, "asurine_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/asurine")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "crimsite_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/crimsite")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "ochrum_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/ochrum")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "veridium_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/veridium")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "scorchia_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/scorchia")
                .part(WrenchPart.HANDLE)
                .build());

        register(ctx, "scoria_handle", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/handle_materials/scoria")
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

        register(ctx, "asurine_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/asurine")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "crimsite_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/crimsite")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "ochrum_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/ochrum")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "veridium_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/veridium")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "scorchia_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/scorchia")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "scoria_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/scoria")
                .part(WrenchPart.COG)
                .build());

        register(ctx, "bone_cog", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/cog_materials/bone")
                .part(WrenchPart.COG)
                .build());
        // endregion

        // region Axes (plural of axis)
        register(ctx, "andesite_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/andesite")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "diorite_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/diorite")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "granite_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/granite")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "asurine_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/asurine")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "crimsite_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/crimsite")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "ochrum_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/ochrum")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "veridium_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/veridium")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "scorchia_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/scorchia")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "scoria_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/scoria")
                .part(WrenchPart.AXIS)
                .build());

        register(ctx, "bone_axis", new WrenchMaterial.Builder()
                .texture("item/extended_wrench/axis_materials/bone")
                .part(WrenchPart.AXIS)
                .build());
        // endregion
    }

    private static void register(BootstapContext<WrenchMaterial> ctx, String name, WrenchMaterial type) {
        ctx.register(ResourceKey.create(ExtendedWrenchesData.WRENCH_MATERIAL, ExtendedWrenches.asResource(name)), type);
    }
}
