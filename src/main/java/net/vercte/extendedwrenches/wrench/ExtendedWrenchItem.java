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

    public static final int DEFAULT_GRIP_COLOR = 0xc74f46;

    public static ItemStack swapMaterial(ItemStack stack, ResourceLocation location, WrenchMaterial material, String part) {
        CompoundTag display = stack.get(TAG_DISPLAY);
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
