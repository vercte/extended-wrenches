package net.vercte.extendedwrenches.datagen;

import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;
import net.vercte.extendedwrenches.wrench.WrenchPart;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ExtendedWrenchSwapRecipeProvider extends RecipeProvider {
    private RecipeOutput consumer;
    private String folder = "wrench";

    public ExtendedWrenchSwapRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
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

        setFolder("wrench/cog");
        cog("acacia_cog", Items.ACACIA_PLANKS);
        cog("bamboo_cog", Items.BAMBOO_PLANKS);
        cog("birch_cog", Items.BIRCH_PLANKS);
        cog("cherry_cog", Items.CHERRY_PLANKS);
        cog("crimson_cog", Items.CRIMSON_PLANKS);
        cog("dark_oak_cog", Items.DARK_OAK_PLANKS);
        cog("jungle_cog", Items.JUNGLE_PLANKS);
        cog("mangrove_cog", Items.MANGROVE_PLANKS);
        cog("oak_cog", Items.OAK_PLANKS);
        cog("spruce_cog", Items.SPRUCE_PLANKS);
        cog("warped_cog", Items.WARPED_PLANKS);
    }

    private void setFolder(String folder) {
        this.folder = folder;
    }

    private TagKey<Item> common(String tag) {
        return TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("c", tag));
    }

    private void head(String material, ItemLike... addition) {
        this.head(material, Ingredient.of(addition));
    }

    private void head(String material, TagKey<Item> addition) {
        this.head(material, Ingredient.of(addition));
    }

    private void head(String material, Ingredient addition) {
        this.recipe(material, ExtendedItems.WRENCH_HEAD_AUGMENT, addition, WrenchPart.HEAD);
    }

    private void handle(String material, ItemLike... addition) {
        this.handle(material, Ingredient.of(addition));
    }

    private void handle(String material, Ingredient addition) {
        this.recipe(material, ExtendedItems.WRENCH_HANDLE_AUGMENT, addition, WrenchPart.HANDLE);
    }

    private void cog(String material, ItemLike... addition) {
        this.cog(material, Ingredient.of(addition));
    }

    private void cog(String material, Ingredient addition) {
        this.recipe(material, ExtendedItems.WRENCH_COG_AUGMENT, addition, WrenchPart.COG);
    }

    private void recipe(String material, ItemLike template, Ingredient addition, WrenchPart part) {
        ResourceLocation recipeLocation = ExtendedWrenches.asResource(this.folder + "/" + material + "_swap");
        new WrenchMaterialSwapRecipe.Builder(recipeLocation)
                .material(material)
                .template(template)
                .addition(addition)
                .part(part)
                .save(this.consumer);
    }
}
