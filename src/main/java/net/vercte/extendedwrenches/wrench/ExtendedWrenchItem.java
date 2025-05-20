package net.vercte.extendedwrenches.wrench;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.vercte.extendedwrenches.ExtendedItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ExtendedWrenchItem extends WrenchItem {
    public ExtendedWrenchItem(Properties properties) {
        super(properties);
    }

    private static final String TAG_DISPLAY = "display";
    private static final String TAG_GRIP_COLOR = "grip_color";

    private static final String TAG_MATERIALS = "materials";
    private static final String TAG_MATERIAL_LOCATION = "location";
    private static final String TAG_MATERIAL_TEXTURE = "texture";

    private static final int DEFAULT_GRIP_COLOR = 0xc74f46;

    public static boolean hasCustomColor(ItemStack stack) {
        CompoundTag compoundtag = stack.getTagElement(TAG_DISPLAY);
        return compoundtag != null && compoundtag.contains(TAG_GRIP_COLOR, Tag.TAG_ANY_NUMERIC);
    }

    public static int getColor(ItemStack stack) {
        CompoundTag display = stack.getTagElement(TAG_DISPLAY);
        return display != null && display.contains(TAG_GRIP_COLOR, Tag.TAG_ANY_NUMERIC) ? display.getInt(TAG_GRIP_COLOR) : DEFAULT_GRIP_COLOR;
    }

    public static void setColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement(TAG_DISPLAY).putInt(TAG_GRIP_COLOR, color);
    }

    public static ItemStack dyeWrench(ItemStack stack, List<DyeItem> dyes) {
        ItemStack copiedWrench = ItemStack.EMPTY;
        int[] color = new int[3];
        int significant = 0;
        int amount = 0;
        Item item = stack.getItem();
        ExtendedWrenchItem wrench = null;
        if (item instanceof ExtendedWrenchItem) {
            wrench = (ExtendedWrenchItem)item;
            copiedWrench = stack.copyWithCount(1);
            if (ExtendedWrenchItem.hasCustomColor(stack)) {
                int k = ExtendedWrenchItem.getColor(copiedWrench);
                float wR = (float) (k >> 16 & 255) / 255;
                float wG = (float) (k >> 8 & 255) / 255;
                float wB = (float) (k & 255) / 255;
                significant += (int)Math.max(wR, Math.max(wG, wB)) * 255;
                color[0] += (int)(wR * 255.0F);
                color[1] += (int)(wG * 255.0F);
                color[2] += (int)(wB * 255.0F);
                amount += 1;
            }

            for (DyeItem dyeitem : dyes) {
                float[] dyeColors = dyeitem.getDyeColor().getTextureDiffuseColors();
                int dR = (int)(dyeColors[0] * 255.0F);
                int dG = (int)(dyeColors[1] * 255.0F);
                int dB = (int)(dyeColors[2] * 255.0F);
                significant += Math.max(dR, Math.max(dG, dB));
                color[0] += dR;
                color[1] += dG;
                color[2] += dB;
                amount += 1;
            }
        }

        if (wrench == null) {
            return ItemStack.EMPTY;
        } else {
            int aR = color[0] / amount;
            int aG = color[1] / amount;
            int aB = color[2] / amount;
            float f3 = (float) significant / (float) amount;
            float f4 = (float) Math.max(aR, Math.max(aG, aB));
            aR = (int) ((float) aR * f3 / f4);
            aG = (int) ((float) aG * f3 / f4);
            aB = (int) ((float) aB * f3 / f4);
            int j2 = (aR << 8) + aG;
            j2 = (j2 << 8) + aB;
            ExtendedWrenchItem.setColor(copiedWrench, j2);
            return copiedWrench;
        }
    }

    public static ItemStack swapMaterial(ItemStack stack, ResourceLocation location, WrenchMaterial material, String part) {
        CompoundTag display = stack.getOrCreateTagElement(TAG_DISPLAY);
        CompoundTag materials = display.getCompound(TAG_MATERIALS);
        CompoundTag materialData = materials.getCompound(part);
        materialData.putString(TAG_MATERIAL_LOCATION, location.toString());
        materialData.putString(TAG_MATERIAL_TEXTURE, material.texture().toString());
        materials.put(part, materialData);
        display.put(TAG_MATERIALS, materials);
        return stack;
    }

    public static boolean hasMaterial(ItemStack stack, @Nullable ResourceLocation material, String part) {
        if(material == null) return false;

        CompoundTag display = stack.getOrCreateTagElement(TAG_DISPLAY);
        CompoundTag materials = display.getCompound(TAG_MATERIALS);
        CompoundTag materialData = materials.getCompound(part);
        String materialLocation = materialData.getString(TAG_MATERIAL_LOCATION);

        if(materialLocation.isEmpty()) {
            String locString = material.toString();
            if(part.equals("head") && locString.equals("extendedwrenches:gold_head")) return true;
            if(part.equals("handle") && locString.equals("extendedwrenches:dark_oak_handle")) return true;
        }

        return materialLocation.equals(material.toString());
    }

    @Nullable
    public static ResourceLocation getMaterialTexture(ItemStack stack, String part) {
        CompoundTag display = stack.getOrCreateTagElement(TAG_DISPLAY);
        CompoundTag materials = display.getCompound(TAG_MATERIALS);
        String texture = materials.getCompound(part).getString(TAG_MATERIAL_TEXTURE);
        return !texture.isEmpty() ? new ResourceLocation(texture) : null;
    }

    public static ItemStack convertWrench(ItemStack original) {
        if(original.is(AllItems.WRENCH.get()))
            return ExtendedItems.WRENCH.asStack();
        return original;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new ExtendedWrenchItemRenderer()));
    }

    @Nonnull
    public String getDescriptionId() {
        return "item.create.wrench";
    }
}
