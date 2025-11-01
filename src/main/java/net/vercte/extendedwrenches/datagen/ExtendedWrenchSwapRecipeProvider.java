package net.vercte.extendedwrenches.datagen;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
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
import net.vercte.extendedwrenches.wrench.WrenchPart;
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
        head("quartz", Items.QUARTZ);
        head("lapis_lazuli", Items.LAPIS_LAZULI);
        head("netherite_head", Items.NETHERITE_INGOT);
        head("asurine_head", AllPaletteStoneTypes.ASURINE.materialTag);
        head("crimsite_head", AllPaletteStoneTypes.CRIMSITE.materialTag);
        head("veridium_head", AllPaletteStoneTypes.VERIDIUM.materialTag);
        head("ochrum_head", AllPaletteStoneTypes.OCHRUM.materialTag);
        head("scorchia_head", AllPaletteStoneTypes.SCORCHIA.materialTag);
        head("scoria_head", AllPaletteStoneTypes.SCORIA.materialTag);
        head("spooky_head", Items.SKELETON_SKULL);

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
        handle("asurine_handle", AllPaletteStoneTypes.ASURINE.materialTag);
        handle("crimsite_handle", AllPaletteStoneTypes.CRIMSITE.materialTag);
        handle("veridium_handle", AllPaletteStoneTypes.VERIDIUM.materialTag);
        handle("ochrum_handle", AllPaletteStoneTypes.OCHRUM.materialTag);
        handle("scorchia_handle", AllPaletteStoneTypes.SCORCHIA.materialTag);
        handle("scoria_handle", AllPaletteStoneTypes.SCORIA.materialTag);

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
        cog("asurine_cog", AllPaletteStoneTypes.ASURINE.materialTag);
        cog("crimsite_cog", AllPaletteStoneTypes.CRIMSITE.materialTag);
        cog("veridium_cog", AllPaletteStoneTypes.VERIDIUM.materialTag);
        cog("ochrum_cog", AllPaletteStoneTypes.OCHRUM.materialTag);
        cog("scorchia_cog", AllPaletteStoneTypes.SCORCHIA.materialTag);
        cog("scoria_cog", AllPaletteStoneTypes.SCORIA.materialTag);
        cog("bone_cog", Items.BONE);

        setFolder("wrench/axis");
        axis("andesite_axis", Items.ANDESITE);
        axis("diorite_axis", Items.DIORITE); // never seen this block before, but I guess I'm adding it
        axis("granite_axis", Items.GRANITE);
        axis("asurine_axis", AllPaletteStoneTypes.ASURINE.materialTag);
        axis("crimsite_axis", AllPaletteStoneTypes.CRIMSITE.materialTag);
        axis("veridium_axis", AllPaletteStoneTypes.VERIDIUM.materialTag);
        axis("ochrum_axis", AllPaletteStoneTypes.OCHRUM.materialTag);
        axis("scorchia_axis", AllPaletteStoneTypes.SCORCHIA.materialTag);
        axis("scoria_axis", AllPaletteStoneTypes.SCORIA.materialTag);
        axis("bone_axis", Items.BONE);
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
        this.recipe(material, ExtendedItems.WRENCH_HEAD_AUGMENT, addition, WrenchPart.HEAD);
    }

    private void handle(String material, ItemLike... addition) {
        this.handle(material, Ingredient.of(addition));
    }

    private void handle(String material, TagKey<Item> addition) {
        this.handle(material, Ingredient.of(addition));
    }

    private void handle(String material, Ingredient addition) {
        this.recipe(material, ExtendedItems.WRENCH_HANDLE_AUGMENT, addition, WrenchPart.HANDLE);
    }

    private void cog(String material, ItemLike... addition) {
        this.cog(material, Ingredient.of(addition));
    }

    private void cog(String material, TagKey<Item> addition) {
        this.cog(material, Ingredient.of(addition));
    }

    private void cog(String material, Ingredient addition) {
        this.recipe(material, ExtendedItems.WRENCH_COG_AUGMENT, addition, WrenchPart.COG);
    }

    private void axis(String material, ItemLike... addition) { this.axis(material, Ingredient.of(addition)); }

    private void axis(String material, TagKey<Item> addition) {
        this.axis(material, Ingredient.of(addition));
    }

    private void axis(String material, Ingredient addition) {
        this.recipe(material, ExtendedItems.WRENCH_AXIS_AUGMENT, addition, WrenchPart.AXIS);
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
