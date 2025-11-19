package net.vercte.extendedwrenches.compat;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.every_compat.misc.HardcodedBlockType;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.mehvahdjukaar.moonlight.api.resources.textures.Palette;
import net.mehvahdjukaar.moonlight.api.resources.textures.Respriter;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.core.misc.McMetaFile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenches;

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
        // ExtendedWrenches.LOGGER.info("addDynamicClientResources");
        executor.accept((manager, sink) -> {
            for(WoodType woodType : WoodTypeRegistry.INSTANCE) {
                if(HardcodedBlockType.isKnownVanillaWood(woodType)) continue;
                // ExtendedWrenches.LOGGER.info("woodType {}", woodType.id);
                try (
                        TextureImage cogTexture = TextureImage.open(manager,
                                ExtendedWrenches.asResource("item/extended_wrench/cog_materials/oak"));
                        TextureImage handleTexture = TextureImage.open(manager,
                                ExtendedWrenches.asResource("item/extended_wrench/handle_materials/oak"));
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
                if(HardcodedBlockType.isKnownVanillaWood(woodType)) continue;
                // ExtendedWrenches.LOGGER.info("woodType {}", woodType.id);
                JsonObject cogMaterial = new JsonObject();
                cogMaterial.add("part", new JsonPrimitive("cog"));
                cogMaterial.add("texture", new JsonPrimitive(getCogTexturePath(woodType).toString()));
                sink.addJson(new ResourceLocation(EveryCompat.MOD_ID,
                        "extendedwrenches/wrench_material/"+ woodType.getAppendableId()+"_cog"),
                        cogMaterial, ResType.JSON);

                JsonObject additionItem = new JsonObject();
                additionItem.add("item", new JsonPrimitive(BuiltInRegistries.ITEM.getKey(woodType.planks.asItem()).toString()));

                JsonObject cogRecipe = new JsonObject();
                cogRecipe.add("type", new JsonPrimitive("extendedwrenches:wrench_material_swap"));
                cogRecipe.add("addition", additionItem);
                cogRecipe.add("material", new JsonPrimitive(EveryCompat.MOD_ID+":"+woodType.getAppendableId()+"_cog"));
                cogRecipe.add("part", new JsonPrimitive("cog"));
                JsonObject cogTemplateItem = new JsonObject();
                cogTemplateItem.add("item", new JsonPrimitive(BuiltInRegistries.ITEM.getKey(ExtendedItems.WRENCH_COG_AUGMENT.get()).toString()));
                cogRecipe.add("template", cogTemplateItem);
                sink.addJson(new ResourceLocation(EveryCompat.MOD_ID,
                        "wrench/cog/"+ woodType.getAppendableId()+"_cog_swap"),
                        cogRecipe, ResType.RECIPES);



                JsonObject handleMaterial = new JsonObject();
                handleMaterial.add("part", new JsonPrimitive("handle"));
                handleMaterial.add("texture", new JsonPrimitive(getHandleTexturePath(woodType).toString()));
                sink.addJson(new ResourceLocation(EveryCompat.MOD_ID,
                                "extendedwrenches/wrench_material/"+ woodType.getAppendableId()+"_handle"),
                        handleMaterial, ResType.JSON);

                JsonObject handleRecipe = new JsonObject();
                handleRecipe.add("type", new JsonPrimitive("extendedwrenches:wrench_material_swap"));
                handleRecipe.add("addition", additionItem);
                handleRecipe.add("material", new JsonPrimitive(EveryCompat.MOD_ID+":"+woodType.getAppendableId()+"_handle"));
                handleRecipe.add("part", new JsonPrimitive("handle"));
                JsonObject handleTemplateItem = new JsonObject();
                handleTemplateItem.add("item", new JsonPrimitive(BuiltInRegistries.ITEM.getKey(ExtendedItems.WRENCH_HANDLE_AUGMENT.get()).toString()));
                handleRecipe.add("template", handleTemplateItem);
                sink.addJson(new ResourceLocation(EveryCompat.MOD_ID,
                                "wrench/handle/"+ woodType.getAppendableId()+"_handle_swap"),
                        handleRecipe, ResType.RECIPES);
            }
        });
    }

    private ResourceLocation getCogTexturePath(WoodType woodType) {
        return new ResourceLocation(EveryCompat.MOD_ID,
                "item/extended_wrench/cog_materials/"+ woodType.getAppendableId());
    }

    private ResourceLocation getHandleTexturePath(WoodType woodType) {
        return new ResourceLocation(EveryCompat.MOD_ID,
                "item/extended_wrench/handle_materials/"+ woodType.getAppendableId());
    }
}
