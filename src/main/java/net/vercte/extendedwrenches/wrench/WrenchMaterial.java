package net.vercte.extendedwrenches.wrench;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.vercte.extendedwrenches.ExtendedWrenches;

public record WrenchMaterial(ResourceLocation texture, WrenchPart part) {
    public static final Codec<WrenchMaterial> CODEC = RecordCodecBuilder.create(i -> i.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(WrenchMaterial::texture),
            StringRepresentable.fromEnum(WrenchPart::values).fieldOf("part").forGetter(WrenchMaterial::part)
    ).apply(i, WrenchMaterial::new));

    public static class Builder {
        private ResourceLocation texture;
        private WrenchPart part;

        public Builder texture(ResourceLocation t) {
            this.texture = t;
            return this;
        }

        public Builder texture(String t) {
            this.texture = ExtendedWrenches.asResource(t);
            return this;
        }

        public Builder part(WrenchPart part) {
            this.part = part;
            return this;
        }

        public WrenchMaterial build() {
            return new WrenchMaterial(texture, part);
        }
    }
}
