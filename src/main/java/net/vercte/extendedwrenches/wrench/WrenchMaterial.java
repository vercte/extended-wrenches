package net.vercte.extendedwrenches.wrench;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record WrenchMaterial(ItemStack item, ResourceLocation texture, String part) {
    public static final Codec<WrenchMaterial> CODEC = RecordCodecBuilder.create(i -> i.group(
            ItemStack.CODEC.fieldOf("item").forGetter(WrenchMaterial::item),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(WrenchMaterial::texture),
            Codec.STRING.fieldOf("part").forGetter(WrenchMaterial::part)
    ).apply(i, WrenchMaterial::new));

    boolean matches(String part, ItemStack stack) {
        return this.part.equals(part) && Ingredient.of(item).test(stack);
    }
}
