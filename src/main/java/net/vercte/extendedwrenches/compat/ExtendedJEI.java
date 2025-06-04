package net.vercte.extendedwrenches.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ExtendedJEI implements IModPlugin {
    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return ExtendedWrenches.asResource("default");
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getSmithingCategory().addExtension(WrenchMaterialSwapRecipe.class, new WrenchMaterialSwapCategoryExtension());
    }


    public static <I extends RecipeInput> ItemStack assembleRecipe(I input, Recipe<I> recipe) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new NullPointerException("level must not be null.");
        } else {
            RegistryAccess registryAccess = level.registryAccess();
            return recipe.assemble(input, registryAccess);
        }
    }
}
