package net.vercte.extendedwrenches.wrench;

import com.google.common.collect.Lists;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.vercte.extendedwrenches.ExtendedWrenches;
import net.vercte.extendedwrenches.ExtendedWrenchesRecipeSerializers;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WrenchDyeRecipe extends CustomRecipe {
    public WrenchDyeRecipe(ResourceLocation location, CraftingBookCategory category) {
        super(location, category);
    }

    public boolean matches(CraftingContainer container, @NotNull Level level) {
        ItemStack wrench = ItemStack.EMPTY;
        List<ItemStack> dyes = Lists.newArrayList();

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack item = container.getItem(i);
            if (!item.isEmpty()) {
                if (item.getItem() instanceof WrenchItem) {
                    if (!wrench.isEmpty()) {
                        return false;
                    }

                    wrench = item;
                } else {
                    if (!(item.getItem() instanceof DyeItem)) {
                        return false;
                    }

                    dyes.add(item);
                }
            }
        }

        return !wrench.isEmpty() && !dyes.isEmpty();
    }

    @NotNull
    public ItemStack assemble(CraftingContainer container, @NotNull RegistryAccess access) {
        List<DyeItem> dyes = Lists.newArrayList();
        ItemStack wrench = ItemStack.EMPTY;

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item instanceof WrenchItem wrenchItem) {
                    if (!wrench.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    if(wrenchItem instanceof ExtendedWrenchItem) wrench = stack.copy();
                    else wrench = ExtendedWrenches.WRENCH.asStack(1);
                } else {
                    if (!(item instanceof DyeItem dye)) {
                        return ItemStack.EMPTY;
                    }

                    dyes.add(dye);
                }
            }
        }

        if (!wrench.isEmpty() && !dyes.isEmpty()) {
            return ExtendedWrenchItem.dyeWrench(wrench, dyes);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    @NotNull
    public RecipeSerializer<?> getSerializer() { return ExtendedWrenchesRecipeSerializers.WRENCH_DYE_RECIPE.get(); }
}
