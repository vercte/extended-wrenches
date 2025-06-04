package net.vercte.extendedwrenches.wrench.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.StringRepresentable;
import net.vercte.extendedwrenches.ExtendedWrenchesData;
import net.vercte.extendedwrenches.wrench.WrenchMaterial;
import net.vercte.extendedwrenches.wrench.WrenchPart;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public record WrenchMaterialComponent(Map<WrenchPart, Holder<WrenchMaterial>> parts) {
    public static final Codec<WrenchMaterialComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(
                            StringRepresentable.fromEnum(WrenchPart::values),
                            RegistryFixedCodec.create(ExtendedWrenchesData.WRENCH_MATERIAL)
                    ).fieldOf("parts").forGetter(WrenchMaterialComponent::parts)
            ).apply(instance, WrenchMaterialComponent::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, WrenchMaterialComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    HashMap::new,
                    ByteBufCodecs.idMapper(WrenchPart.BY_ID, WrenchPart::getId),
                    ByteBufCodecs.holderRegistry(ExtendedWrenchesData.WRENCH_MATERIAL),
                    2
            ), WrenchMaterialComponent::parts,
            WrenchMaterialComponent::new
    );

    public static WrenchMaterialComponent empty() {
        return new WrenchMaterialComponent(new HashMap<>());
    }

    public boolean partPresent(WrenchPart part) {
        return parts.containsKey(part);
    }

    @Nullable
    public Holder<WrenchMaterial> getMaterialHolder(WrenchPart part) {
        return parts.get(part);
    }

    public WrenchMaterialComponent withChangedMaterial(WrenchPart part, Holder<WrenchMaterial> material) {
        WrenchMaterialComponent newComponent = new WrenchMaterialComponent(new HashMap<>(this.parts));
        newComponent.parts.put(part, material);
        return newComponent;
    }
}
