package net.vercte.extendedwrenches.wrench;

import com.google.common.collect.Lists;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.vercte.extendedwrenches.ExtendedWrenchesRecipeSerializers;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WrenchDyeRecipe extends CustomRecipe {
    public WrenchDyeRecipe(CraftingBookCategory category) {
        super(category);
    }


    public boolean matches(CraftingInput input, @NotNull Level level) {
        ItemStack wrench = ItemStack.EMPTY;
        List<ItemStack> dyes = Lists.newArrayList();

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WrenchItem) {
                    if (!wrench.isEmpty()) {
                        return false;
                    }

                    wrench = stack;
                } else {
                    if (!(stack.getItem() instanceof DyeItem)) {
                        return false;
                    }

                    dyes.add(stack);
                }
            }
        }

        return !wrench.isEmpty() && !dyes.isEmpty();
    }

    @NotNull
    public ItemStack assemble(CraftingInput input, @NotNull HolderLookup.Provider registries) {
        List<DyeItem> dyes = Lists.newArrayList();
        ItemStack wrench = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WrenchItem) {
                    if (!wrench.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    wrench = ExtendedWrenchItem.convertWrench(stack);
                } else {
                    if (!(stack.getItem() instanceof DyeItem dyeitem)) {
                        return ItemStack.EMPTY;
                    }

                    dyes.add(dyeitem);
                }
            }
        }

        return !wrench.isEmpty() && !dyes.isEmpty() ? DyedItemColor.applyDyes(wrench, dyes) : ItemStack.EMPTY;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer() {
        return ExtendedWrenchesRecipeSerializers.WRENCH_DYE.get();
    }
}
