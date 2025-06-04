package net.vercte.extendedwrenches.wrench;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.AllItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import net.vercte.extendedwrenches.ExtendedWrenchesRecipeSerializers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrenchMaterialSwapRecipe implements SmithingRecipe {
    private final ResourceLocation materialLocation;
    final Ingredient template;
    final Ingredient addition;
    final WrenchPart part;

    public WrenchMaterialSwapRecipe(ResourceLocation material, Ingredient template, Ingredient addition, WrenchPart part) {
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

    public ResourceLocation getMaterialLocation() { return this.materialLocation; }
    public Ingredient getTemplate() { return this.template; }
    public Ingredient getBase() { return Ingredient.of(AllItems.WRENCH, ExtendedItems.WRENCH); }
    public Ingredient getAddition() { return this.addition; }
    public WrenchPart getPart() { return this.part; }

    @Override
    public boolean matches(@NotNull SmithingRecipeInput input, @NotNull Level level) {
        ItemStack template = input.getItem(0);
        ItemStack addition = input.getItem(2);

        ItemStack stack = input.getItem(1);
        boolean isWrench = stack.is(ExtendedItems.WRENCH.get()) || stack.is(AllItems.WRENCH.get());
        return isWrench &&
                this.template.test(template) && this.addition.test(addition) &&
                !ExtendedWrenchItem.hasMaterial(stack, part, materialLocation);
    }

    @Override
    @NotNull
    public ItemStack assemble(@NotNull SmithingRecipeInput input, @NotNull HolderLookup.Provider provider) {
        ResourceKey<WrenchMaterial> materialKey = ResourceKey.create(ExtendedWrenchesData.WRENCH_MATERIAL, materialLocation);
        HolderLookup.RegistryLookup<WrenchMaterial> materials = provider.lookupOrThrow(ExtendedWrenchesData.WRENCH_MATERIAL);
        Optional<Holder.Reference<WrenchMaterial>> optMaterial = materials.get(materialKey);

        if(optMaterial.isEmpty()) return ItemStack.EMPTY;
        ItemStack wrench = ExtendedWrenchItem.convertWrench(input.getItem(1));

        return ExtendedWrenchItem.swapMaterial(wrench.copy(), this.part, optMaterial.get());
    }

    @Override
    @NotNull
    public ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        return ExtendedItems.WRENCH.asStack();
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer() { return ExtendedWrenchesRecipeSerializers.WRENCH_MATERIAL_SWAP.get(); }

    public static class Serializer implements RecipeSerializer<WrenchMaterialSwapRecipe> {
        public static final MapCodec<WrenchMaterialSwapRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                ResourceLocation.CODEC.fieldOf("material").forGetter(WrenchMaterialSwapRecipe::getMaterialLocation),
                Ingredient.CODEC.fieldOf("template").forGetter(WrenchMaterialSwapRecipe::getTemplate),
                Ingredient.CODEC.fieldOf("addition").forGetter(WrenchMaterialSwapRecipe::getAddition),
                StringRepresentable.fromEnum(WrenchPart::values).fieldOf("part").forGetter(WrenchMaterialSwapRecipe::getPart)
        ).apply(inst, WrenchMaterialSwapRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, WrenchMaterialSwapRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ResourceLocation.STREAM_CODEC, WrenchMaterialSwapRecipe::getMaterialLocation,
                        Ingredient.CONTENTS_STREAM_CODEC, WrenchMaterialSwapRecipe::getTemplate,
                        Ingredient.CONTENTS_STREAM_CODEC, WrenchMaterialSwapRecipe::getAddition,
                        ByteBufCodecs.idMapper(WrenchPart.BY_ID, WrenchPart::getId), WrenchMaterialSwapRecipe::getPart,
                        WrenchMaterialSwapRecipe::new
                );

        @Override
        @NotNull
        public MapCodec<WrenchMaterialSwapRecipe> codec() {
            return CODEC;
        }

        @Override
        @NotNull
        public StreamCodec<RegistryFriendlyByteBuf, WrenchMaterialSwapRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder {
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

        @Override
        @NotNull
        public RecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
            return this;
        }

        @Override
        @NotNull
        public RecipeBuilder group(@Nullable String s) {
            return this;
        }

        @Override
        @NotNull
        public Item getResult() {
            return ExtendedItems.WRENCH.asItem();
        }

        @Override
        public void save(@NotNull RecipeOutput output, @NotNull ResourceLocation location) {
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(AdvancementRequirements.Strategy.OR);
            WrenchMaterialSwapRecipe recipe = new WrenchMaterialSwapRecipe(this.materialLocation, this.template, this.addition, this.part);
            output.accept(location, recipe, advancement.build(id.withPrefix("recipes/")));
        }

        public void save(@NotNull RecipeOutput output) {
            this.save(output, this.id);
        }
    }
}
