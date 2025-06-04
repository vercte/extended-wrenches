package net.vercte.extendedwrenches.wrench;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.model.BakedModelHelper;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.StitchedSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.vercte.extendedwrenches.ExtendedWrenches;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ExtendedWrenchItemRenderer extends CustomRenderedItemModelRenderer {
    private static final Map<String, TextureAtlasSprite> CACHED_TEXTURES = new HashMap<>();

    public static final StitchedSprite HEAD_TEXTURE = new StitchedSprite(ExtendedWrenches.asResource("item/extended_wrench/head_materials/gold"));
    public static final StitchedSprite HANDLE_TEXTURE = new StitchedSprite(ExtendedWrenches.asResource("item/extended_wrench/handle_materials/dark_oak"));
    protected static final PartialModel GEAR = PartialModel.of(ExtendedWrenches.asResource("item/extended_wrench/gear"));

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        ResourceLocation headMaterial = ExtendedWrenchItem.getMaterialTexture(stack, WrenchPart.HEAD);
        ResourceLocation handleMaterial = ExtendedWrenchItem.getMaterialTexture(stack, WrenchPart.HANDLE);

        BakedModel replaced = generateModel(model.getOriginalModel(), headMaterial, handleMaterial);
        renderer.render(replaced, light);

        float xOffset = -1/16f;
        ms.translate(-xOffset, 0, 0);
        ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())));
        ms.translate(xOffset, 0, 0);

        renderer.render(GEAR.get(), light);
    }

    public static BakedModel generateModel(BakedModel template, @Nullable ResourceLocation headTexture, @Nullable ResourceLocation handleTexture) {
        TextureAtlasSprite headReplacement = HEAD_TEXTURE.get();
        if(headTexture != null) {
            headReplacement = CACHED_TEXTURES.computeIfAbsent(headTexture.toString(), (s) -> Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(headTexture));
        }

        TextureAtlasSprite handleReplacement = HANDLE_TEXTURE.get();
        if(handleTexture != null) {
            handleReplacement = CACHED_TEXTURES.computeIfAbsent(handleTexture.toString(), (s) -> Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(handleTexture));
        }

        Map<TextureAtlasSprite, TextureAtlasSprite> map = new Reference2ReferenceOpenHashMap<>();
        map.put(HEAD_TEXTURE.get(), headReplacement);
        map.put(HANDLE_TEXTURE.get(), handleReplacement);

        return BakedModelHelper.generateModel(template, map::get);
    }

}