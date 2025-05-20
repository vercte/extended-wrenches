package net.vercte.extendedwrenches.wrench;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.vercte.extendedwrenches.ExtendedWrenches;

import java.util.ArrayList;
import java.util.List;

public record WrenchMaterial(List<HolderSet<Item>> items, ResourceLocation texture, String part) {
    public static final Codec<WrenchMaterial> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.list(RegistryCodecs.homogeneousList(Registries.ITEM)).fieldOf("items").forGetter(WrenchMaterial::items),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(WrenchMaterial::texture),
            Codec.STRING.fieldOf("part").forGetter(WrenchMaterial::part)
    ).apply(i, WrenchMaterial::new));

    @SuppressWarnings("deprecation")
    boolean matches(String part, ItemStack stack) {
        return this.part.equals(part) && items().stream().anyMatch(i -> i.contains(stack.getItem().builtInRegistryHolder()));
    }

    public static class Builder {
        private final List<HolderSet<Item>> itemSets = new ArrayList<>();
        private ResourceLocation texture;
        private String part;

        @SuppressWarnings("deprecation")
        public Builder item(Item... items) {
            List<Holder<Item>> holders = new ArrayList<>();
            for (Item item : items)
                holders.add(item.builtInRegistryHolder());
            itemSets.add(HolderSet.direct(holders));
            return this;
        }

        public Builder texture(ResourceLocation t) {
            this.texture = t;
            return this;
        }

        public Builder texture(String t) {
            this.texture = ExtendedWrenches.asResource(t);
            return this;
        }

        public Builder part(String part) {
            this.part = part;
            return this;
        }

        public WrenchMaterial build() {
            return new WrenchMaterial(itemSets, texture, part);
        }
    }
}
