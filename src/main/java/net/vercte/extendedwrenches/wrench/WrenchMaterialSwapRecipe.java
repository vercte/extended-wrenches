package net.vercte.extendedwrenches.wrench;

import com.google.gson.JsonObject;
import com.simibubi.create.AllItems;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import net.vercte.extendedwrenches.ExtendedWrenchesRecipeSerializers;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NonnullDefault;

import java.util.Optional;
import java.util.function.Consumer;

public class WrenchMaterialSwapRecipe implements SmithingRecipe {
    private final ResourceLocation id;
    private final ResourceLocation materialLocation;
    final Ingredient template;
    final Ingredient addition;
    final WrenchPart part;

    public WrenchMaterialSwapRecipe(ResourceLocation id, ResourceLocation material, Ingredient template, Ingredient addition, WrenchPart part) {
        this.id = id;
        this.materialLocation = material;
        this.template = template;
        this.addition = addition;
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
        return addition.test(stack);
    }

    public Ingredient getTemplate() { return this.template; }
    public Ingredient getBase() { return Ingredient.of(AllItems.WRENCH, ExtendedItems.WRENCH); }
    public Ingredient getAddition() { return this.addition; }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        ItemStack template = container.getItem(0);
        ItemStack addition = container.getItem(2);

        ItemStack stack = container.getItem(1);
        boolean isWrench = stack.is(ExtendedItems.WRENCH.get()) || stack.is(AllItems.WRENCH.get());
        return isWrench &&
                this.template.test(template) && this.addition.test(addition) &&
                !ExtendedWrenchItem.hasMaterial(stack, part, materialLocation);
    }

    @Override
    @NotNull
    public ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess access) {
        Registry<WrenchMaterial> materials = access.registryOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL);
        Optional<WrenchMaterial> optMaterial = materials.getOptional(materialLocation);

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
            Ingredient addition = Ingredient.fromJson(GsonHelper.getNonNull(json, "addition"));
            ResourceLocation materialLocation = ResourceLocation.parse(GsonHelper.getAsString(json, "material"));
            WrenchPart part = WrenchPart.getFromString(GsonHelper.getAsString(json, "part"));
            return new WrenchMaterialSwapRecipe(location, materialLocation, base, addition, part);
        }

        public WrenchMaterialSwapRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            Ingredient template = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            ResourceLocation materialLocation = buffer.readResourceLocation();
            String part = buffer.readUtf();
            return new WrenchMaterialSwapRecipe(location, materialLocation, template, addition, WrenchPart.getFromString(part));
        }

        public void toNetwork(FriendlyByteBuf buffer, WrenchMaterialSwapRecipe recipe) {
            recipe.template.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeResourceLocation(recipe.materialLocation);
            buffer.writeUtf(recipe.part.getSerializedName());
        }
    }

    public static class Builder implements FinishedRecipe{
        private final ResourceLocation id;
        private ResourceLocation materialLocation;
        private Ingredient template;
        private Ingredient addition;
        private WrenchPart part;

        public Builder(ResourceLocation id) {
            this.id = id;
        }

        public Builder material(String id) {
            this.materialLocation = ExtendedWrenches.asResource(id);
            return this;
        }

        public Builder template(ItemLike template) {
            this.template = Ingredient.of(template);
            return this;
        }

        public Builder addition(TagKey<Item> addition) {
            return this.addition(Ingredient.of(addition));
        }

        public Builder addition(ItemLike addition) {
            return this.addition(Ingredient.of(addition));
        }

        public Builder addition(Ingredient addition) {
            this.addition = addition;
            return this;
        }

        public Builder part(WrenchPart part) {
            this.part = part;
            return this;
        }

        public void save(Consumer<FinishedRecipe> consumer) {
            consumer.accept(this);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("material", materialLocation.toString());
            json.add("template", template.toJson());
            json.add("addition", addition.toJson());
            json.addProperty("part", part.getSerializedName());
        }

        @Override
        @NotNull
        public ResourceLocation getId() { return this.id; }

        @Override
        @NotNull
        public RecipeSerializer<?> getType() { return ExtendedWrenchesRecipeSerializers.WRENCH_MATERIAL_SWAP.get(); }

        @Override
        public JsonObject serializeAdvancement() { return null; }

        @Override
        public ResourceLocation getAdvancementId() { return null; }
    }
}
