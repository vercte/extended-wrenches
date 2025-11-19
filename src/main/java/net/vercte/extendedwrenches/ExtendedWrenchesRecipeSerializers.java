package net.vercte.extendedwrenches;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.vercte.extendedwrenches.wrench.WrenchDyeRecipe;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;

import java.util.function.Supplier;

public class ExtendedWrenchesRecipeSerializers {
    public static final RecipeSerializer<WrenchDyeRecipe> WRENCH_DYE_RECIPE = register("wrench_dye", () -> new SimpleCraftingRecipeSerializer<>(WrenchDyeRecipe::new));
    public static final RecipeSerializer<WrenchMaterialSwapRecipe> WRENCH_MATERIAL_SWAP = register("wrench_material_swap", WrenchMaterialSwapRecipe.Serializer::new);

    private static <T extends Recipe<R>, R extends Container> RecipeSerializer<T> register(String id, Supplier<RecipeSerializer<T>> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ExtendedWrenches.asResource(id), serializer.get());
    }

    public static void init() {}
}
