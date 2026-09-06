package electrodynamics.common.tile.pipelines.gas.gastransformer.thermoelectricmanipulator;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.inventory.container.tile.ContainerThermoelectricManipulator;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.common.tile.pipelines.gas.gastransformer.IMultiblockGasTransformer;
import electrodynamics.common.tile.pipelines.gas.gastransformer.TileGasTransformerSideBlock;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import voltaic.Voltaic;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.GasTank;
import voltaic.api.gas.IGasHandler;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.components.utils.IComponentFluidHandler;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileAdvancedThermoelectricManipulator extends GenericTileThermoelectricManipulator
	implements IMultiblockGasTransformer {

    public boolean hasBeenDestroyed = false;

    public TileAdvancedThermoelectricManipulator(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_ADVANCED_THERMOELECTRIC_MANIPULATOR.get(), worldPos, blockState);
    }

    @Override
    public void tickClient(Level level, ComponentTickable tickable) {
	ElectrodynamicsBlockStates.ManipulatorHeatingStatus status = getBlockState()
		.getValue(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS);
	if (status == ElectrodynamicsBlockStates.ManipulatorHeatingStatus.OFF)
	    return;
	if (level.random.nextDouble() < 0.5) {

	    Direction direction = getFacing();

	    double axisShift = Voltaic.RANDOM.nextDouble(0.25) + 0.1;
	    double otherShift = Voltaic.RANDOM.nextDouble(0.63) + 0.1;
	    double yShift = Voltaic.RANDOM.nextDouble();

	    double xShift = direction.getAxis() == Direction.Axis.X
		    ? direction.getStepX() * (direction.getStepX() < 0 ? -1 + otherShift : otherShift)
		    : direction.getStepZ() < 0 ? 1 - axisShift : axisShift;
	    double zShift = direction.getAxis() == Direction.Axis.Z
		    ? direction.getStepZ() * (direction.getStepZ() < 0 ? -1 + otherShift : otherShift)
		    : direction.getStepX() < 0 ? axisShift : 1 - axisShift;

	    ParticleOptions particle;

	    if (status == ElectrodynamicsBlockStates.ManipulatorHeatingStatus.HEAT) {
		particle = ParticleTypes.SMOKE;
	    } else {
		particle = ParticleTypes.SNOWFLAKE;
	    }

	    level.addParticle(particle, worldPosition.getX() + xShift, worldPosition.getY() + yShift,
		    worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);
	    // level.addParticle(ParticleTypes.FLAME, worldPosition.getX() + xShift,
	    // worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D,
	    // 0.0D);

	}
    }

    @Override
    public ComponentContainerProvider getContainerProvider() {
	return new ComponentContainerProvider("advancedthermoelectricmanipulator", this)
		.createMenu((id, inv) -> new ContainerThermoelectricManipulator(id, inv,
			requireComponent(IComponentType.Inventory), getCoordsArray()));
    }

    @Override
    public double getUsagePerTick() {
	return ElectrodynamicsConfig.INSTANCE.ADVANCED_THERMOELECTRIC_MANIPULATOR_USAGE_PER_TICK.get();
    }

    @Override
    public int getConversionRate() {
	return ElectrodynamicsConfig.INSTANCE.ADVANCED_THERMOELECTRIC_MANIPULATOR_CONVERSION_RATE.get();
    }

    @Override
    public void updateAddonTanks(int count, boolean isLeft) {
	ComponentGasHandlerMulti handler = requireComponent(IComponentType.GasHandler);
	ComponentFluidHandlerMulti multi = requireComponent(IComponentType.FluidHandler);
	if (isLeft) {
	    multi.getInputTanks()[0]
		    .setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_INPUT_CAPACITY.get()
			    + ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * count);
	    handler.getInputTanks()[0]
		    .setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_INPUT_CAPACITY.get()
			    + ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * count);
	} else {
	    multi.getOutputTanks()[0]
		    .setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY.get()
			    + ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * count);
	    handler.getOutputTanks()[0]
		    .setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY.get()
			    + ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * count);
	}
    }

    @Override
    public boolean hasBeenDestroyed() {
	return hasBeenDestroyed;
    }

    @Override
    public void onPlace(Level level, BlockState oldState, boolean isMoving) {
	super.onPlace(level, oldState, isMoving);
	if (level.isClientSide)
	    return;
	Direction facing = getFacing();

	BlockEntity left = level
		.getBlockEntity(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.EAST)));
	BlockEntity right = level
		.getBlockEntity(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.WEST)));

	if (left != null && right != null && left instanceof TileGasTransformerSideBlock leftTile
		&& right instanceof TileGasTransformerSideBlock rightTile) {
	    leftTile.setOwnerPos(getBlockPos());
	    leftTile.setIsLeft();
	    leftTile.setChanged();
	    rightTile.setOwnerPos(getBlockPos());
	    rightTile.setChanged();
	}
    }

    @Override
    public void onBlockDestroyed(Level level) {
	if (level.isClientSide || hasBeenDestroyed)
	    return;
	hasBeenDestroyed = true;
	Direction facing = getFacing();
	level.destroyBlock(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.WEST)), false);
	level.destroyBlock(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.EAST)), false);
    }

    @Override
    public void outputToPipe(ComponentProcessor processor, ComponentGasHandlerMulti gasHandler, Direction facing) {
	Level level = this.level;
	if (level == null)
	    return;

	Direction direction = BlockEntityUtils.getRelativeSide(facing,
		BlockEntityUtils.MachineDirection.LEFT.mappedDir);// opposite of west is east
	BlockPos face = getBlockPos().relative(direction, 2);
	BlockEntity faceTile = level.getBlockEntity(face);
	if (faceTile != null) {

	    IGasHandler handler = level.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK,
		    faceTile.getBlockPos(), faceTile.getBlockState(), faceTile, direction.getOpposite());

	    if (handler != null) {

		GasTank gasTank = gasHandler.getOutputTanks()[0];
		for (int i = 0; i < handler.getTanks(); i++) {
		    GasStack tankGas = gasTank.getGas();
		    int amtAccepted = handler.fill(tankGas, GasAction.EXECUTE);
		    GasStack taken = new GasStack(tankGas.getGas(), amtAccepted, tankGas.getTemperature(),
			    tankGas.getPressure());
		    gasTank.drain(taken, GasAction.EXECUTE);
		}

	    }

	}

	ComponentFluidHandlerMulti fluidHandler = requireComponent(IComponentType.FluidHandler);

	face = getBlockPos().relative(direction).relative(Direction.DOWN);
	faceTile = level.getBlockEntity(face);
	if (faceTile != null) {

	    IFluidHandler handler = level.getCapability(Capabilities.FluidHandler.BLOCK, faceTile.getBlockPos(),
		    faceTile.getBlockState(), faceTile, Direction.UP);

	    if (handler != null) {

		FluidTank fluidTank = fluidHandler.getOutputTanks()[0];
		FluidStack tankFluid = fluidTank.getFluid();
		int amtAccepted = handler.fill(tankFluid, IFluidHandler.FluidAction.EXECUTE);
		FluidStack taken = new FluidStack(tankFluid.getFluid(), amtAccepted);
		fluidTank.drain(taken, IFluidHandler.FluidAction.EXECUTE);

	    }

	}
    }

    @Override
    public void updateLit(boolean isHeating, Direction facing) {
	Level level = this.level;
	if (level == null)
	    return;

	if (BlockEntityUtils.isLit(this) ^ isHeating) {
	    BlockEntityUtils.updateLit(this, isHeating);
	    BlockEntity left = level
		    .getBlockEntity(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.EAST)));
	    BlockEntity right = level
		    .getBlockEntity(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.WEST)));
	    if (left != null && left instanceof TileGasTransformerSideBlock leftTile && right != null
		    && right instanceof TileGasTransformerSideBlock rightTile) {
		BlockEntityUtils.updateLit(leftTile, isHeating);
		BlockEntityUtils.updateLit(rightTile, isHeating);
	    }
	}
    }

    @Override
    public IComponentFluidHandler getFluidHandler() {
	return new ComponentFluidHandlerMulti.ComponentFluidHandlerMultiBiDirec(this)
		.setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM)
		.setInputTanks(1, arr(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_INPUT_CAPACITY.get()))
		.setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM)
		.setOutputTanks(1, arr(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY.get()));
    }

    @Override
    public int getHeatTransfer() {
	return ElectrodynamicsConfig.INSTANCE.ADVANCED_THERMOELECTRIC_MANIPULATOR_HEAT_TRANSFER.get();
    }
}
