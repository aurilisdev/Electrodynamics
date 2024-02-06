package electrodynamics.prefab.tile.components.utils;

import org.jetbrains.annotations.Nullable;

import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.prefab.tile.components.CapabilityInputType;
import electrodynamics.prefab.tile.components.IComponent;
import net.minecraft.core.Direction;

public interface IComponentGasHandler extends IComponent {

	public static final int TANK_MULTIPLIER = 1000;

	PropertyGasTank[] getInputTanks();

	PropertyGasTank[] getOutputTanks();
	
	@Nullable
	public IGasHandler getCapability(@Nullable Direction direction, CapabilityInputType mode);

}
