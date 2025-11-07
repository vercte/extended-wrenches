package net.vercte.extendedwrenches.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.AllItems;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.vercte.extendedwrenches.ExtendedItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(DyedItemColor.class)
public class DyedItemColorMixin {
    @ModifyExpressionValue(method = "applyDyes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0))
    private static boolean alsoIfWrench(boolean original, ItemStack stack, List<DyeItem> dyes) {
        return original || AllItems.WRENCH.isIn(stack) || ExtendedItems.WRENCH.isIn(stack);
    }
}
