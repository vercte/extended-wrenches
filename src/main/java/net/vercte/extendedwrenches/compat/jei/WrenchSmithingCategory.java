package net.vercte.extendedwrenches.compat.jei;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.vercte.extendedwrenches.ExtendedItems;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

public class WrenchSmithingCategory implements IRecipeCategory<WrenchMaterialSwapRecipe> {
    private static final ResourceLocation JEI_GUI_SHEET = ExtendedWrenches.asResource("textures/gui/jei.png");
    public static RecipeType<WrenchMaterialSwapRecipe> RECIPE_TYPE = RecipeType.create(ExtendedWrenches.ID, "wrench_material_swap", WrenchMaterialSwapRecipe.class);

    @Override
    public @NotNull RecipeType<WrenchMaterialSwapRecipe> getRecipeType() { return RECIPE_TYPE; }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("extendedwrenches.recipe.wrench_material_swap");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return new DoubleItemIcon(
                () -> new ItemStack(Blocks.SMITHING_TABLE),
                ExtendedItems.WRENCH_HEAD_AUGMENT::asStack
        );
    }

    @Override
    public void draw(@NotNull WrenchMaterialSwapRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(JEI_GUI_SHEET, -4, -4, 0, 0, 125, 36);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WrenchMaterialSwapRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(1, 6)
            .addIngredients(recipe.getTemplate())
            .setStandardSlotBackground();

        builder.addInputSlot(19, 6)
            .addIngredients(recipe.getBase())
            .setStandardSlotBackground();

        builder.addInputSlot(37, 6)
            .addIngredients(recipe.getAddition())
            .setStandardSlotBackground();

        IRecipeSlotBuilder outputSlot = builder.addOutputSlot(91, 2)
                .setCustomRenderer(VanillaTypes.ITEM_STACK, new OutputWrenchRenderer());

        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if(level == null) return;

        ItemStack wrench = recipe.applyTransformation(ExtendedItems.WRENCH.asStack(), level.registryAccess());
        outputSlot.addItemStack(wrench);
    }

    @Override
    public int getWidth() { return 117; }

    @Override
    public int getHeight() { return 28; }

    private static class OutputWrenchRenderer implements IIngredientRenderer<ItemStack> {
        @Override
        @NonnullDefault
        public void render(GuiGraphics guiGraphics, ItemStack ingredient) {
            RenderSystem.enableDepthTest();

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5f, 1.5f,1.5f);

            Lighting.setupForFlatItems();
            guiGraphics.renderFakeItem(ingredient, 0, 0);

            guiGraphics.pose().popPose();

            RenderSystem.disableBlend();
        }

        @Override
        public void getTooltip(ITooltipBuilder tooltip, ItemStack ingredient, @NotNull TooltipFlag tooltipFlag) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            List<Component> components = ingredient.getTooltipLines(player, tooltipFlag);
            tooltip.addAll(components);
        }

        @Override
        public int getWidth() {
            return 24;
        }

        @Override
        public int getHeight() {
            return 24;
        }

        @Override
        @NonnullDefault
        public List<Component> getTooltip(ItemStack ingredient, TooltipFlag tooltipFlag) {
            return List.of();
        }
    }
}
