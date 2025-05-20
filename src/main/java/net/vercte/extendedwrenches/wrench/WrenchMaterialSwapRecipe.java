package net.vercte.extendedwrenches.wrench;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import net.vercte.extendedwrenches.ExtendedWrenchesRecipeSerializers;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NonnullDefault;

import java.util.Optional;
import java.util.stream.Stream;

public class WrenchMaterialSwapRecipe implements SmithingRecipe {
    private final ResourceLocation id;
    final Ingredient base;
    final String part;

    public WrenchMaterialSwapRecipe(ResourceLocation id, Ingredient base, String part) {
        this.id = id;
        this.base = base;
        this.part = part;
    }

    @Override
    public boolean isTemplateIngredient(@NotNull ItemStack stack) {
        return stack.is(ExtendedWrenches.WRENCH.get());
    }

    @Override
    public boolean isBaseIngredient(@NotNull ItemStack stack) {
        return base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        Registry<WrenchMaterial> materials = level.registryAccess().registryOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL);
        ItemStack addition = container.getItem(2);
        boolean materialMatches = materials.stream().anyMatch(t -> t.matches(this.part, addition));

        return container.getItem(0).is(ExtendedWrenches.WRENCH.get()) && materialMatches;
    }

    @Override
    @NotNull
    public ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess access) {
        Registry<WrenchMaterial> materials = access.registryOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL);
        ItemStack addition = container.getItem(2);
        Stream<WrenchMaterial> stream = materials.stream().filter(t -> t.matches(this.part, addition));
        Optional<WrenchMaterial> optMaterial = stream.findFirst();

        if(optMaterial.isEmpty()) return ItemStack.EMPTY;
        ItemStack wrench = container.getItem(0);
        WrenchMaterial material = optMaterial.get();
        ResourceLocation materialLocation = materials.getKey(material);

        assert materialLocation != null;
        return ExtendedWrenchItem.swapMaterial(wrench, materialLocation, this.part);
    }

    @Override
    @NotNull
    public ItemStack getResultItem(@NotNull RegistryAccess access) {
        return ExtendedWrenches.WRENCH.asStack();
    }

    @Override
    @NotNull
    public ResourceLocation getId() { return id; }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer() { return ExtendedWrenchesRecipeSerializers.WRENCH_MATERIAL_SWAP.get(); }

    @NonnullDefault
    public static class Serializer implements RecipeSerializer<WrenchMaterialSwapRecipe> {
        public WrenchMaterialSwapRecipe fromJson(ResourceLocation location, JsonObject json) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getNonNull(json, "base"));
            String part = GsonHelper.getAsString(json, "part");
            return new WrenchMaterialSwapRecipe(location, base, part);
        }

        public WrenchMaterialSwapRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            Ingredient base = Ingredient.fromNetwork(buffer);
            String part = buffer.readUtf();
            return new WrenchMaterialSwapRecipe(location, base, part);
        }

        public void toNetwork(FriendlyByteBuf buffer, WrenchMaterialSwapRecipe recipe) {
            recipe.base.toNetwork(buffer);
            buffer.writeUtf(recipe.part);
        }
    }
}
