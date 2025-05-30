package net.vercte.extendedwrenches.mixin;

import com.simibubi.create.content.equipment.wrench.WrenchItem;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import net.vercte.extendedwrenches.ExtendedItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Mixin 'borrowed' from @noregard in the Create mod discord's #devchat
@Mixin(ItemProviderEntry.class)
public abstract class ItemProviderEntryMixin<T extends ItemLike> extends RegistryEntry<T> {
    public ItemProviderEntryMixin(AbstractRegistrate<?> owner, RegistryObject<T> delegate) {
        super(owner, delegate);
    }

    @Inject(method = "isIn", at = @At("HEAD"), cancellable = true)
    public void isIn(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() == ExtendedItems.WRENCH.get()
                && this.get().asItem() instanceof WrenchItem) {
            cir.setReturnValue(true);
        }
    }
}
