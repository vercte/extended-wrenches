package net.vercte.extendedwrenches.compat;

import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.vercte.extendedwrenches.wrench.WrenchMaterialSwapRecipe;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class WrenchMaterialSwapCategoryExtension implements ISmithingCategoryExtension<WrenchMaterialSwapRecipe> {
    @Override
    public void setTemplate(WrenchMaterialSwapRecipe recipe, IIngredientAcceptor acceptor) {
        acceptor.addIngredients(recipe.getTemplate());
    }

    @Override
    public void setBase(WrenchMaterialSwapRecipe recipe, IIngredientAcceptor acceptor) {
        acceptor.addIngredients(recipe.getBase());
    }

    @Override
    public void setAddition(WrenchMaterialSwapRecipe recipe, IIngredientAcceptor acceptor) {
        acceptor.addIngredients(recipe.getAddition());
    }

    @Override
    public void setOutput(WrenchMaterialSwapRecipe recipe, IIngredientAcceptor acceptor) {
        Ingredient templateIngredient = recipe.getTemplate();
        Ingredient baseIngredient = recipe.getBase();
        Ingredient additionIngredient = recipe.getAddition();
        ItemStack[] additions = additionIngredient.getItems();
        if (additions.length != 0) {
            ItemStack addition = additions[0];

            for(ItemStack template : templateIngredient.getItems()) {
                for(ItemStack base : baseIngredient.getItems()) {
                    Container recipeInput = createInput(template, base, addition);
                    ItemStack output = ExtendedJEI.assembleRecipe(recipeInput, recipe);
                    acceptor.addItemStack(output);
                }
            }
        }
    }

    private static Container createInput(ItemStack template, ItemStack base, ItemStack addition) {
        Container container = new SimpleContainer(3);
        container.setItem(0, template);
        container.setItem(1, base);
        container.setItem(2, addition);
        return container;
    }
}
