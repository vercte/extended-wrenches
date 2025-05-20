package net.vercte.extendedwrenches.wrench;

import com.google.gson.JsonObject;
import com.simibubi.create.AllItems;
import net.minecraft.client.Minecraft;
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
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import net.vercte.extendedwrenches.ExtendedWrenchesRecipeSerializers;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NonnullDefault;

import java.util.Optional;
import java.util.stream.Stream;

public class WrenchMaterialSwapRecipe implements SmithingRecipe {
    private final ResourceLocation id;
    final Ingredient template;
    final String part;

    public WrenchMaterialSwapRecipe(ResourceLocation id, Ingredient template, String part) {
        this.id = id;
        this.template = template;
        this.part = part;
    }

    @Override
    public boolean isTemplateIngredient(@NotNull ItemStack stack) {
        return template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(@NotNull ItemStack stack) {
        return stack.is(ExtendedItems.WRENCH.get()) || stack.is(AllItems.WRENCH.get());
    }

    @Override
    public boolean isAdditionIngredient(@NotNull ItemStack stack) {
        Registry<WrenchMaterial> materials = Minecraft.getInstance().level.registryAccess().registryOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL);
        return materials.stream().anyMatch(t -> t.matches(this.part, stack));
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        Registry<WrenchMaterial> materials = level.registryAccess().registryOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL);
        ItemStack addition = container.getItem(2);
        ItemStack template = container.getItem(0);

        Stream<WrenchMaterial> stream = materials.stream().filter(t -> t.matches(this.part, addition));
        Optional<WrenchMaterial> optMaterial = stream.findFirst();
        if(optMaterial.isEmpty()) return false;

        ResourceLocation location = materials.getKey(optMaterial.get());

        ItemStack stack = container.getItem(1);
        boolean isWrench = stack.is(ExtendedItems.WRENCH.get()) || stack.is(AllItems.WRENCH.get());
        return isWrench && this.template.test(template) && !ExtendedWrenchItem.hasMaterial(stack, location, part);
    }

    @Override
    @NotNull
    public ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess access) {
        Registry<WrenchMaterial> materials = access.registryOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL);
        ItemStack addition = container.getItem(2);
        Stream<WrenchMaterial> stream = materials.stream().filter(t -> t.matches(this.part, addition));
        Optional<WrenchMaterial> optMaterial = stream.findFirst();

        if(optMaterial.isEmpty()) return ItemStack.EMPTY;
        ItemStack wrench = ExtendedWrenchItem.convertWrench(container.getItem(1));
        WrenchMaterial material = optMaterial.get();
        ResourceLocation materialLocation = materials.getKey(material);

        assert materialLocation != null;
        return ExtendedWrenchItem.swapMaterial(wrench.copy(), materialLocation, material, this.part);
    }

    @Override
    @NotNull
    public ItemStack getResultItem(@NotNull RegistryAccess access) {
        return ExtendedItems.WRENCH.asStack();
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
            Ingredient base = Ingredient.fromJson(GsonHelper.getNonNull(json, "template"));
            String part = GsonHelper.getAsString(json, "part");
            return new WrenchMaterialSwapRecipe(location, base, part);
        }

        public WrenchMaterialSwapRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            Ingredient template = Ingredient.fromNetwork(buffer);
            String part = buffer.readUtf();
            return new WrenchMaterialSwapRecipe(location, template, part);
        }

        public void toNetwork(FriendlyByteBuf buffer, WrenchMaterialSwapRecipe recipe) {
            recipe.template.toNetwork(buffer);
            buffer.writeUtf(recipe.part);
        }
    }
}
