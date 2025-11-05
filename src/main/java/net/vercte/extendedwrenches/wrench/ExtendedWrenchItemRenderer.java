package net.vercte.extendedwrenches.wrench;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.model.BakedModelHelper;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.util.RendererReloadCache;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.StitchedSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExtendedWrenchItemRenderer extends CustomRenderedItemModelRenderer {
    private static final RendererReloadCache<ResourceLocation, TextureAtlasSprite> CACHED_TEXTURES = new RendererReloadCache<>(ExtendedWrenchItemRenderer::getAtlasSprite);
    private static final RendererReloadCache<ResourceLocation, ResourceLocation> CACHED_MATERIAL_TEXTURE_LOCATIONS = new RendererReloadCache<>(ExtendedWrenchItemRenderer::getAbsentMaterialTexture);

    public static final StitchedSprite HEAD_TEXTURE = new StitchedSprite(ExtendedWrenches.asResource("item/extended_wrench/head_materials/gold"));
    public static final StitchedSprite HANDLE_TEXTURE = new StitchedSprite(ExtendedWrenches.asResource("item/extended_wrench/handle_materials/dark_oak"));
    public static final StitchedSprite AXIS_TEXTURE = new StitchedSprite(ExtendedWrenches.asResource("item/extended_wrench/axis_materials/andesite"));
    public static final StitchedSprite COG_TEXTURE = new StitchedSprite(ExtendedWrenches.asResource("item/extended_wrench/cog_materials/spruce"));
    protected static final PartialModel GEAR = PartialModel.of(ExtendedWrenches.asResource("item/extended_wrench/gear"));

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        ResourceLocation headMaterial = getMaterialTexture(stack, WrenchPart.HEAD);
        ResourceLocation handleMaterial = getMaterialTexture(stack, WrenchPart.HANDLE);
        ResourceLocation cogMaterial = getMaterialTexture(stack, WrenchPart.COG);
        ResourceLocation axisMaterial = getMaterialTexture(stack, WrenchPart.AXIS);

        BakedModel replacedBody = generateBodyModel(model.getOriginalModel(), headMaterial, handleMaterial, axisMaterial);
        renderer.render(replacedBody, light);

        float xOffset = -1/16f;
        ms.translate(-xOffset, 0, 0);
        ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())));
        ms.translate(xOffset, 0, 0);

        BakedModel replacedCog = generateCogModel(GEAR.get(), cogMaterial);
        renderer.render(replacedCog, light);
    }

    public static BakedModel generateBodyModel(BakedModel template, @Nullable ResourceLocation headTexture, @Nullable ResourceLocation handleTexture, @Nullable ResourceLocation axisTexture) {
        TextureAtlasSprite headReplacement = HEAD_TEXTURE.get();
        if(headTexture != null) {
            headReplacement = CACHED_TEXTURES.get(headTexture);
        }

        TextureAtlasSprite handleReplacement = HANDLE_TEXTURE.get();
        if(handleTexture != null) {
            handleReplacement = CACHED_TEXTURES.get(handleTexture);
        }

        TextureAtlasSprite axisReplacement = AXIS_TEXTURE.get();
        if(axisTexture != null) {
            axisReplacement = CACHED_TEXTURES.get(axisTexture);
        }

        Map<TextureAtlasSprite, TextureAtlasSprite> map = new Reference2ReferenceOpenHashMap<>();
        map.put(HEAD_TEXTURE.get(), headReplacement);
        map.put(HANDLE_TEXTURE.get(), handleReplacement);
        map.put(AXIS_TEXTURE.get(), axisReplacement);

        return BakedModelHelper.generateModel(template, map::get);
    }

    public static BakedModel generateCogModel(BakedModel template, @Nullable ResourceLocation cogTexture) {
        TextureAtlasSprite cogReplacement = COG_TEXTURE.get();
        if(cogTexture != null) {
            cogReplacement = CACHED_TEXTURES.get(cogTexture);
        }

        Map<TextureAtlasSprite, TextureAtlasSprite> map = new Reference2ReferenceOpenHashMap<>();
        map.put(COG_TEXTURE.get(), cogReplacement);

        return BakedModelHelper.generateModel(template, map::get);
    }

    private static TextureAtlasSprite getAtlasSprite(ResourceLocation location) {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(location);
    }

    private static ResourceLocation getMaterialTexture(ItemStack stack, WrenchPart part) {
        CompoundTag display = stack.getOrCreateTagElement(ExtendedWrenchItem.TAG_DISPLAY);
        CompoundTag materialTag = display.getCompound(ExtendedWrenchItem.TAG_MATERIALS);
        String materialLocationStr = materialTag.getCompound(part.getSerializedName()).getString(ExtendedWrenchItem.TAG_MATERIAL_LOCATION);

        if(materialLocationStr.isEmpty()) return null;
        ResourceLocation materialLocation = new ResourceLocation(materialLocationStr);

        return CACHED_MATERIAL_TEXTURE_LOCATIONS.get(materialLocation);
    }

    @Nullable
    private static ResourceLocation getAbsentMaterialTexture(ResourceLocation location) {
        RegistryAccess access = getRegistryAccess();
        if(access == null) return null;

        Registry<WrenchMaterial> materials = access.registryOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL);
        Optional<WrenchMaterial> optMaterial = materials.getOptional(location);

        return optMaterial.isPresent() ? optMaterial.get().texture() : null;
    }

    @Nullable
    private static RegistryAccess getRegistryAccess() {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        if(level == null) return null;

        return level.registryAccess();
    }
}