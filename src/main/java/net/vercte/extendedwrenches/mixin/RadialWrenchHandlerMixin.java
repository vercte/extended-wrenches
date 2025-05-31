package net.vercte.extendedwrenches.mixin;

import com.simibubi.create.content.contraptions.wrench.RadialWrenchHandler;
import com.simibubi.create.content.contraptions.wrench.RadialWrenchMenu;
import net.createmod.catnip.gui.ScreenOpener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.vercte.extendedwrenches.ExtendedItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RadialWrenchHandler.class)
public class RadialWrenchHandlerMixin {
    @Inject(method = "onKeyInput", at = @At(value = "RETURN",ordinal = 5))
    private static void onKeyInput(int key, boolean pressed, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();

        ClientLevel level = mc.level;
        LocalPlayer player = mc.player;
        assert player != null;
        assert level != null;

        ItemStack heldItem = player.getMainHandItem();
        if(heldItem.getItem() == ExtendedItems.WRENCH.get()) {
            HitResult objectMouseOver = mc.hitResult;
            if (!(objectMouseOver instanceof BlockHitResult blockHitResult))
                return;

            BlockState state = level.getBlockState(blockHitResult.getBlockPos());

            RadialWrenchMenu.tryCreateFor(state, blockHitResult.getBlockPos(), level).ifPresent(ScreenOpener::open);
        }
    }
}
