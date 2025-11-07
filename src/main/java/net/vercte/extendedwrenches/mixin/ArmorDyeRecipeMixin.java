package net.vercte.extendedwrenches.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ArmorDyeRecipe;
import net.vercte.extendedwrenches.wrench.ExtendedWrenchItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorDyeRecipe.class)
public class ArmorDyeRecipeMixin {
    @WrapOperation(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack convertWrench(ItemStack stack, Operation<ItemStack> original) {
        if(stack.is(AllItems.WRENCH)) {
            return ExtendedWrenchItem.convertWrench(original.call(stack));
        }

        return original.call(stack);
    }
}
