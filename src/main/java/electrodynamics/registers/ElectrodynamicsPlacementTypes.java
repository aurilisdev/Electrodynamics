// electrodynamics/registers/ElectrodynamicsPlacementTypes.java
package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.common.world.placement.ConfigScaledCount;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElectrodynamicsPlacementTypes {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_TYPES = DeferredRegister
	    .create(Registries.PLACEMENT_MODIFIER_TYPE, Electrodynamics.ID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ConfigScaledCount>> CONFIG_SCALED_COUNT = PLACEMENT_TYPES
	    .register("config_scaled_count", () -> () -> ConfigScaledCount.CODEC);

}
