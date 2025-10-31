package electrodynamics.common.tile.pipelines.gas.gastransformer.thermoelectricmanipulator;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.inventory.container.tile.ContainerThermoelectricManipulator;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.common.tile.pipelines.gas.gastransformer.IAddonTankManager;
import electrodynamics.common.tile.pipelines.gas.gastransformer.TileGasTransformerAddonTank;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.components.utils.IComponentFluidHandler;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileBasicThermoelectricManipulator extends GenericTileThermoelectricManipulator
	implements IAddonTankManager {
    public TileBasicThermoelectricManipulator(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_THERMOELECTRIC_MANIPULATOR.get(), worldPos, blockState);
    }

    @Override
    public void outputToPipe(ComponentProcessor processor, ComponentGasHandlerMulti multi, Direction facing) {
	processor.outputToFluidPipe();
	processor.outputToGasPipe();
    }

    @Override
    public void updateLit(boolean isHeating, Direction facing) {
	if (BlockEntityUtils.isLit(this) ^ isHeating) {
	    BlockEntityUtils.updateLit(this, isHeating);
	}
    }

    @Override
    public IComponentFluidHandler getFluidHandler() {
	return new ComponentFluidHandlerMulti(this)
		.setInputTanks(1, ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_INPUT_CAPACITY.get())
		.setInputDirections(BlockEntityUtils.MachineDirection.BACK)
		.setOutputTanks(1, ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY.get())
		.setOutputDirections(BlockEntityUtils.MachineDirection.FRONT);
    }

    @Override
    public int getHeatTransfer() {
	return ElectrodynamicsConfig.INSTANCE.THERMOELECTRIC_MANIPULATOR_HEAT_TRANSFER.get();
    }

    @Override
    public ComponentContainerProvider getContainerProvider() {
	return new ComponentContainerProvider("thermoelectricmanipulator", this)
		.createMenu((id, inv) -> new ContainerThermoelectricManipulator(id, inv,
			getComponent(IComponentType.Inventory), getCoordsArray()));
    }

    @Override
    public double getUsagePerTick() {
	return ElectrodynamicsConfig.INSTANCE.THERMOELECTRIC_MANIPULATOR_USAGE_PER_TICK.get();
    }

    @Override
    public int getConversionRate() {
	return ElectrodynamicsConfig.INSTANCE.THERMOELECTRIC_MANIPULATOR_CONVERSION_RATE.get();
    }

    @Override
    public void tickClient(ComponentTickable tickable) {
	ElectrodynamicsBlockStates.ManipulatorHeatingStatus status = getBlockState()
		.getValue(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS);
	if (status == ElectrodynamicsBlockStates.ManipulatorHeatingStatus.OFF) {
	    return;
	}
	if (level.random.nextDouble() < 0.5) {

	    // TODO particles

	}
    }

    @Override
    public void updateTankCount() {
	BlockPos abovePos = getBlockPos().above();
	BlockState aboveState = getLevel().getBlockState(abovePos);
	BlockEntity aboveTile;
	int tankCount = 0;
	for (int i = 0; i < ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_LIMIT.get(); i++) {
	    if (!aboveState.is(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK)) {
		break;
	    }
	    aboveTile = getLevel().getBlockEntity(abovePos);
	    if ((aboveTile == null) || !(aboveTile instanceof TileGasTransformerAddonTank tank)) {
		break;
	    }
	    abovePos = abovePos.above();
	    aboveState = getLevel().getBlockState(abovePos);
	    tank.setOwnerPos(getBlockPos());
	    tankCount++;
	}
	ComponentGasHandlerMulti handler = getComponent(IComponentType.GasHandler);
	ComponentFluidHandlerMulti multi = getComponent(IComponentType.FluidHandler);
	multi.getInputTanks()[0].setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_INPUT_CAPACITY.get()
		+ ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * tankCount);
	handler.getInputTanks()[0].setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_INPUT_CAPACITY.get()
		+ ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * tankCount);
	multi.getOutputTanks()[0].setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY.get()
		+ ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * tankCount);
	handler.getOutputTanks()[0]
		.setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY.get()
			+ ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * tankCount);

    }
}
