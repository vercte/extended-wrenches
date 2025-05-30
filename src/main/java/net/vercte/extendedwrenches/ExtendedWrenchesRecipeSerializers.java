package net.vercte.extendedwrenches;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vercte.extendedwrenches.wrench.WrenchDyeRecipe;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;

import java.util.function.Supplier;

public class ExtendedWrenchesRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ExtendedWrenches.ID);

    public static final Supplier<RecipeSerializer<WrenchDyeRecipe>> WRENCH_DYE_RECIPE = RECIPE_SERIALIZERS.register("wrench_dye", () -> new SimpleCraftingRecipeSerializer<>(WrenchDyeRecipe::new));
    public static final Supplier<RecipeSerializer<WrenchMaterialSwapRecipe>> WRENCH_MATERIAL_SWAP = RECIPE_SERIALIZERS.register("wrench_material_swap", WrenchMaterialSwapRecipe.Serializer::new);

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}
