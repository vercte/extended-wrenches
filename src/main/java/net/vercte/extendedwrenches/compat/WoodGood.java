package net.vercte.extendedwrenches.compat;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.mehvahdjukaar.moonlight.api.resources.textures.Palette;
import net.mehvahdjukaar.moonlight.api.resources.textures.Respriter;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.core.misc.McMetaFile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;
import net.vercte.extendedwrenches.wrench.WrenchPart;

import java.util.List;
import java.util.function.Consumer;

public class WoodGood extends SimpleModule {
    public WoodGood(String modId) {
        super(modId, "cew", EveryCompat.MOD_ID);
        // ExtendedWrenches.LOGGER.info("WoodGood");
    }

    @Override
    public void addDynamicClientResources(Consumer<ResourceGenTask> executor) {
        super.addDynamicClientResources(executor);
        ExtendedWrenches.LOGGER.info("addDynamicClientResources");
        executor.accept((manager, sink) -> {
            for(WoodType woodType : WoodTypeRegistry.INSTANCE) {
                if(woodType.isVanilla()) continue;
                // ExtendedWrenches.LOGGER.info("woodType {}", woodType.id);
                try (
                        TextureImage cogTexture = TextureImage.open(manager,
                                ResourceLocation.fromNamespaceAndPath(ExtendedWrenches.ID, "item/extended_wrench/cog_materials/oak"));
                        TextureImage handleTexture = TextureImage.open(manager,
                                ResourceLocation.fromNamespaceAndPath(ExtendedWrenches.ID, "item/extended_wrench/handle_materials/oak"));
                        TextureImage plankTexture = TextureImage.open(manager, RPUtils.findFirstBlockTextureLocation(manager, woodType.planks))
                ) {
                    Respriter cogRespriter = Respriter.of(cogTexture);
                    Respriter handleRespriter = Respriter.of(handleTexture);

                    List<Palette> plankPalette = Palette.fromAnimatedImage(plankTexture);
                    McMetaFile plankMeta = plankTexture.getMcMeta();

                    try(TextureImage cogRecolored = cogRespriter.recolorWithAnimation(plankPalette, plankMeta);
                        TextureImage handleRecolored = handleRespriter.recolorWithAnimation(plankPalette, plankMeta)) {
                        sink.addTexture(getCogTexturePath(woodType), cogRecolored);
                        sink.addTexture(getHandleTexturePath(woodType), handleRecolored);
                    } catch (Exception e) {
                        ExtendedWrenches.LOGGER.error("Error generating texture", e);
                    }
                } catch (Exception e) {
                    ExtendedWrenches.LOGGER.error("Error generating texture", e);
                }
            }
        });
    }

    @Override
    public void addDynamicServerResources(Consumer<ResourceGenTask> executor) {
        super.addDynamicServerResources(executor);
        // ExtendedWrenches.LOGGER.info("addDynamicServerResources");
        executor.accept((manager, sink) -> {
            for(WoodType woodType : WoodTypeRegistry.INSTANCE) {
                if(woodType.isVanilla()) continue;
                // ExtendedWrenches.LOGGER.info("woodType {}", woodType.id);
                JsonObject cogMaterial = new JsonObject();
                cogMaterial.add("part", new JsonPrimitive("cog"));
                cogMaterial.add("texture", new JsonPrimitive(getCogTexturePath(woodType).toString()));
                sink.addJson(ResourceLocation.fromNamespaceAndPath(EveryCompat.MOD_ID,
                        "extendedwrenches/wrench_material/"+ woodType.getAppendableId()+"_cog"),
                        cogMaterial, ResType.JSON);
                sink.addRecipe(new WrenchMaterialSwapRecipe(
                        ResourceLocation.fromNamespaceAndPath(EveryCompat.MOD_ID,
                                woodType.getAppendableId()+"_cog"),
                        Ingredient.of(ExtendedItems.WRENCH_COG_AUGMENT),
                        Ingredient.of(woodType.planks.asItem()),
                        WrenchPart.COG
                ), ResourceLocation.fromNamespaceAndPath(EveryCompat.MOD_ID,
                        "wrench/cog/"+ woodType.getAppendableId()+"_cog_swap"));

                JsonObject handleMaterial = new JsonObject();
                handleMaterial.add("part", new JsonPrimitive("handle"));
                handleMaterial.add("texture", new JsonPrimitive(getHandleTexturePath(woodType).toString()));
                sink.addJson(ResourceLocation.fromNamespaceAndPath(EveryCompat.MOD_ID,
                                "extendedwrenches/wrench_material/"+ woodType.getAppendableId()+"_handle"),
                        handleMaterial, ResType.JSON);
                sink.addRecipe(new WrenchMaterialSwapRecipe(
                        ResourceLocation.fromNamespaceAndPath(EveryCompat.MOD_ID,
                                woodType.getAppendableId()+"_handle"),
                        Ingredient.of(ExtendedItems.WRENCH_HANDLE_AUGMENT),
                        Ingredient.of(woodType.planks.asItem()),
                        WrenchPart.HANDLE
                ), ResourceLocation.fromNamespaceAndPath(EveryCompat.MOD_ID,
                        "wrench/handle/"+ woodType.getAppendableId()+"_handle_swap"));
            }
        });
    }

    private ResourceLocation getCogTexturePath(WoodType woodType) {
        return ResourceLocation.fromNamespaceAndPath(EveryCompat.MOD_ID,
                "item/extended_wrench/cog_materials/"+ woodType.getAppendableId());
    }

    private ResourceLocation getHandleTexturePath(WoodType woodType) {
        return ResourceLocation.fromNamespaceAndPath(EveryCompat.MOD_ID,
                "item/extended_wrench/handle_materials/"+ woodType.getAppendableId());
    }
}
