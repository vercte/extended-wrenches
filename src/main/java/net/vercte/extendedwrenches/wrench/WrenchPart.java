package net.vercte.extendedwrenches.wrench;

import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.IntFunction;

@SuppressWarnings("unused")
public enum WrenchPart implements StringRepresentable {
    HEAD(0),
    HANDLE(1),
    COG(2);

    private final int id;
    WrenchPart(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public static final IntFunction<WrenchPart> BY_ID =
            ByIdMap.continuous(
                    WrenchPart::getId,
                    WrenchPart.values(),
                    ByIdMap.OutOfBoundsStrategy.ZERO
            );

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    @Nullable
    public static WrenchPart getFromString(String part) {
        for(WrenchPart p : WrenchPart.values()) {
            if(p.getSerializedName().equals(part)) return p;
        }
        return null;
    }
}
