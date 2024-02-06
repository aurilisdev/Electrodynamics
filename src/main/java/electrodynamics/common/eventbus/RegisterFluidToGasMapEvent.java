package electrodynamics.common.eventbus;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import electrodynamics.api.gas.Gas;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

public class RegisterFluidToGasMapEvent extends Event implements IModBusEvent {

	public final ConcurrentHashMap<Fluid, HashSet<Gas>> fluidToGasMap = new ConcurrentHashMap<>();

	public void add(@Nonnull Fluid fluid, @Nonnull Gas gas) {
		HashSet<Gas> existing = fluidToGasMap.getOrDefault(fluid, new HashSet<>());

		existing.add(gas);

		fluidToGasMap.put(fluid, existing);
	}

}
