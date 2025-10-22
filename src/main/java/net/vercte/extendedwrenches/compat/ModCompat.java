package net.vercte.extendedwrenches.compat;

import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;
import net.vercte.extendedwrenches.ExtendedWrenches;

public class ModCompat {
    public static void init() {
        EveryCompatAPI.registerModule(new WoodGood(ExtendedWrenches.ID));
    }
}
