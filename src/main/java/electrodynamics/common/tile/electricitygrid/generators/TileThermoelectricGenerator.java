package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;

public class TileThermoelectricGenerator extends GenericTile {

	protected CachedTileOutput output;

	public SingleProperty<Boolean> hasHeat = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hasheat", false));
	public SingleProperty<Double> heatMultipler = property(new SingleProperty<>(PropertyTypes.DOUBLE, "multiplier", 0.0));
	private SingleProperty<Boolean> hasRedstoneSignal = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "redstonesignal", false));

	public TileThermoelectricGenerator(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_THERMOELECTRICGENERATOR.get(), worldPosition, blockState);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this, true, false).setOutputDirections(BlockEntityUtils.MachineDirection.TOP));
	}

	protected void tickServer(ComponentTickable tickable) {
		if (hasRedstoneSignal.getValue()) {
			return;
		}
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
		}
		Direction facing = getFacing();
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		if (tickable.getTicks() % 60 == 0) {
			Fluid fluid = level.getFluidState(worldPosition.relative(facing.getOpposite())).getType();
			hasHeat.setValue(ThermoelectricGeneratorHeatRegister.INSTANCE.isHeatSource(fluid));
			heatMultipler.setValue(ThermoelectricGeneratorHeatRegister.INSTANCE.getHeatMultiplier(fluid));
			output.update(worldPosition.relative(Direction.UP));
		}
		if (hasHeat.getValue() && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), Direction.DOWN, TransferPack.ampsVoltage(ElectroConstants.THERMOELECTRICGENERATOR_AMPERAGE * level.getFluidState(worldPosition.relative(facing.getOpposite())).getAmount() / 8.0 * heatMultipler.getValue(), electro.getVoltage()), false);
		}
	}

	@Override
	public int getComparatorSignal() {
		return hasHeat.getValue() ? 15 : 0;
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}
		hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
	}

}
