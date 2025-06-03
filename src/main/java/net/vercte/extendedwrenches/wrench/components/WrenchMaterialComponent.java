package net.vercte.extendedwrenches.wrench.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record WrenchMaterialComponent(int color) {
    public static final Codec<WrenchMaterialComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("color").forGetter(WrenchMaterialComponent::color)
            ).apply(instance, WrenchMaterialComponent::new)
    );

    public static final StreamCodec<ByteBuf, WrenchMaterialComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, WrenchMaterialComponent::color,
            WrenchMaterialComponent::new
    );
}
