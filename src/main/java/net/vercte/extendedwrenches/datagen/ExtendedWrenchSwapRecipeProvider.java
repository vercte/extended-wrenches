package net.vercte.extendedwrenches.datagen;

import com.simibubi.create.AllItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.function.Consumer;

public class ExtendedWrenchSwapRecipeProvider extends RecipeProvider {
    private Consumer<FinishedRecipe> consumer;
    private String folder = "wrench";

    public ExtendedWrenchSwapRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        this.consumer = consumer;

        setFolder("wrench/head");
        head("amethyst_head", Tags.Items.GEMS_AMETHYST);
        head("andesite_alloy_head", AllItems.ANDESITE_ALLOY);
        head("brass_head", common("ingots/brass"));
        head("copper_head", Tags.Items.INGOTS_COPPER);
        head("diamond_head", Tags.Items.GEMS_DIAMOND);
        head("emerald_head", Tags.Items.GEMS_EMERALD);
        head("gold_head", Tags.Items.INGOTS_GOLD);
        head("iron_head", Tags.Items.INGOTS_IRON);
        head("polished_rose_quartz_head", AllItems.POLISHED_ROSE_QUARTZ);
        head("zinc_head", common("ingots/zinc"));

        setFolder("wrench/handle");
        handle("acacia_handle", Items.ACACIA_PLANKS);
        handle("bamboo_handle", Items.BAMBOO_PLANKS);
        handle("birch_handle", Items.BIRCH_PLANKS);
        handle("cherry_handle", Items.CHERRY_PLANKS);
        handle("crimson_handle", Items.CRIMSON_PLANKS);
        handle("dark_oak_handle", Items.DARK_OAK_PLANKS);
        handle("jungle_handle", Items.JUNGLE_PLANKS);
        handle("mangrove_handle", Items.MANGROVE_PLANKS);
        handle("oak_handle", Items.OAK_PLANKS);
        handle("spruce_handle", Items.SPRUCE_PLANKS);
        handle("warped_handle", Items.WARPED_PLANKS);
    }

    private void setFolder(String folder) {
        this.folder = folder;
    }

    @SuppressWarnings("DataFlowIssue")
    private TagKey<Item> common(String tag) {
        return ForgeRegistries.ITEMS.tags()
                .createOptionalTagKey(new ResourceLocation("forge", tag), Collections.emptySet());
    }

    private void head(String material, ItemLike... addition) {
        this.head(material, Ingredient.of(addition));
    }

    private void head(String material, TagKey<Item> addition) {
        this.head(material, Ingredient.of(addition));
    }

    private void head(String material, Ingredient addition) {
        this.recipe(material, ExtendedItems.WRENCH_HEAD_AUGMENT, addition, "head");
    }

    private void handle(String material, ItemLike... addition) {
        this.handle(material, Ingredient.of(addition));
    }

    private void handle(String material, Ingredient addition) {
        this.recipe(material, ExtendedItems.WRENCH_HANDLE_AUGMENT, addition, "handle");
    }

    private void recipe(String material, ItemLike template, Ingredient addition, String part) {
        ResourceLocation recipeLocation = ExtendedWrenches.asResource(this.folder + "/" + material + "_swap");
        new WrenchMaterialSwapRecipe.Builder(recipeLocation)
                .material(material)
                .template(template)
                .addition(addition)
                .part(part)
                .save(this.consumer);
    }
}
