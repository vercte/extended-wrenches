package net.vercte.extendedwrenches;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vercte.extendedwrenches.wrench.WrenchDyeRecipe;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;

public class ExtendedWrenchesRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExtendedWrenches.ID);

    public static final RegistryObject<RecipeSerializer<WrenchDyeRecipe>> WRENCH_DYE_RECIPE = RECIPE_SERIALIZERS.register("wrench_dye", () -> new SimpleCraftingRecipeSerializer<>(WrenchDyeRecipe::new));
    public static final RegistryObject<RecipeSerializer<WrenchMaterialSwapRecipe>> WRENCH_MATERIAL_SWAP = RECIPE_SERIALIZERS.register("wrench_material_swap", WrenchMaterialSwapRecipe.Serializer::new);

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}
