package net.vercte.extendedwrenches.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.block.Blocks;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@JeiPlugin
public class ExtendedJEI implements IModPlugin {
    @Override @NotNull
    public ResourceLocation getPluginUid() {
        return ExtendedWrenches.asResource("default");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WrenchSmithingCategory());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(WrenchSmithingCategory.RECIPE_TYPE, getAllSwapRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(WrenchSmithingCategory.RECIPE_TYPE, Blocks.SMITHING_TABLE);
    }

    public List<WrenchMaterialSwapRecipe> getAllSwapRecipes() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        assert level != null;

        List<WrenchMaterialSwapRecipe> recipes = new ArrayList<>();
        Stream<SmithingRecipe> smithingRecipeStream = level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING).stream();
        smithingRecipeStream.forEach(r -> {
            if(r instanceof WrenchMaterialSwapRecipe sr) recipes.add(sr);
        });

        return recipes;
    }
}
