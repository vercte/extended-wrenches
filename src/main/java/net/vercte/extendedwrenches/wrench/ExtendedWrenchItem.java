package net.vercte.extendedwrenches.wrench;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import net.vercte.extendedwrenches.wrench.components.WrenchMaterialComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ExtendedWrenchItem extends WrenchItem {
    public ExtendedWrenchItem(Properties properties) {
        super(properties);
    }

    public static final int DEFAULT_GRIP_COLOR = -0x38b0b9;

    public static ItemStack swapMaterial(ItemStack stack, WrenchPart part, Holder<WrenchMaterial> holder) {
        WrenchMaterialComponent materials = stack.getOrDefault(ExtendedWrenchesData.WRENCH_MATERIAL_COMPONENT, WrenchMaterialComponent.empty());
        stack.set(ExtendedWrenchesData.WRENCH_MATERIAL_COMPONENT, materials.withChangedMaterial(part, holder));
        return stack;
    }

    public static boolean hasMaterial(ItemStack stack, WrenchPart part, @Nullable ResourceLocation material) {
        if(material == null) return false;

        WrenchMaterialComponent materials = stack.getOrDefault(ExtendedWrenchesData.WRENCH_MATERIAL_COMPONENT, WrenchMaterialComponent.empty());
        Holder<WrenchMaterial> holderMaterial = materials.getMaterialHolder(part);
        if(holderMaterial != null && holderMaterial.is(material)) return true;

        if(!materials.partPresent(part)) {
            String locString = material.toString();
            if(part == WrenchPart.HEAD && locString.equals("extendedwrenches:gold_head")) return true;
            if(part == WrenchPart.HANDLE && locString.equals("extendedwrenches:dark_oak_handle")) return true;
            return part == WrenchPart.COG && locString.equals("extendedwrenches:spruce");
        }

        return false;
    }

    @Nullable
    public static ResourceLocation getMaterialTexture(ItemStack stack, WrenchPart part) {
        WrenchMaterialComponent materials = stack.getOrDefault(ExtendedWrenchesData.WRENCH_MATERIAL_COMPONENT, WrenchMaterialComponent.empty());
        Holder<WrenchMaterial> holderMaterial = materials.getMaterialHolder(part);
        if(holderMaterial == null) return null;
        return holderMaterial.value().texture();
    }

    public static ItemStack convertWrench(ItemStack original) {
        if(original.is(AllItems.WRENCH.get()))
            return ExtendedItems.WRENCH.asStack();
        return original.copy();
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
