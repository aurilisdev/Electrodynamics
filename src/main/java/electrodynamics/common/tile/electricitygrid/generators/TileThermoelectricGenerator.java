package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileThermoelectricGenerator extends GenericTile {

	protected CachedTileOutput output;

	public Property<Boolean> hasHeat = property(new Property<>(PropertyType.Boolean, "hasheat", false));
	public Property<Double> heatMultipler = property(new Property<>(PropertyType.Double, "multiplier", 0.0));
	private Property<Boolean> hasRedstoneSignal = property(new Property<>(PropertyType.Boolean, "redstonesignal", false));

	public TileThermoelectricGenerator(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_THERMOELECTRICGENERATOR.get(), worldPosition, blockState);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this, true, false).setOutputDirections(Direction.UP));
	}

	protected void tickServer(ComponentTickable tickable) {
		if (hasRedstoneSignal.get()) {
			return;
		}
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
		}
		Direction facing = getFacing();
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		if (tickable.getTicks() % 60 == 0) {
			Fluid fluid = level.getFluidState(worldPosition.relative(facing.getOpposite())).getType();
			hasHeat.set(ThermoelectricGeneratorHeatRegister.INSTANCE.isHeatSource(fluid));
			heatMultipler.set(ThermoelectricGeneratorHeatRegister.INSTANCE.getHeatMultiplier(fluid));
			output.update(worldPosition.relative(Direction.UP));
		}
		if (hasHeat.get() && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), Direction.UP, TransferPack.ampsVoltage(Constants.THERMOELECTRICGENERATOR_AMPERAGE * level.getFluidState(worldPosition.relative(facing.getOpposite())).getAmount() / 16.0 * heatMultipler.get(), electro.getVoltage()), false);
		}
	}

	@Override
	public int getComparatorSignal() {
		return hasHeat.get() ? 15 : 0;
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}
		hasRedstoneSignal.set(level.hasNeighborSignal(getBlockPos()));
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.125, 0.4375, 0.875, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.875, 0.25, 0.75, 1, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.125, 0.875, 0.03125, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.0625, 0.9375, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.5625, 0.0625, 0.375, 0.75, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.0625, 0.375, 0.4375, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.5625, 0.125, 0.75, 0.75, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.25, 0.125, 0.75, 0.4375, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0.0625, 0.6875, 0.6875, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.3125, 0.0625, 0.6875, 0.375, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.625, 0, 0.6875, 0.6875, 0.0625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.3125, 0, 0.6875, 0.375, 0.0625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.125, 0.15625, 0.84375, 0.875, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.46875, 0.140625, 0.859375, 0.53125, 0.671875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.6875, 0.796875, 0.8125, 0.71875, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.875, 0.540625, 0.4125, 0.8875, 0.571875, 0.50625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.734375, 0.796875, 0.5625, 0.765625, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.59375, 0.796875, 0.8125, 0.625, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.640625, 0.796875, 0.5625, 0.671875, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.515625, 0.796875, 0.8125, 0.546875, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.5625, 0.796875, 0.5625, 0.59375, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.421875, 0.796875, 0.8125, 0.453125, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.46875, 0.796875, 0.5625, 0.5, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.34375, 0.796875, 0.8125, 0.375, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.390625, 0.796875, 0.5625, 0.421875, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.25, 0.796875, 0.8125, 0.28125, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.296875, 0.796875, 0.5625, 0.328125, 0.953125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.734375, 0.09375, 0.90625, 0.78125, 0.21875, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.734375, 0.0625, 0.375, 0.78125, 0.09375, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.0625, 0.375, 0.734375, 0.09375, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.09375, 0.3125, 0.75, 0.15625, 0.5), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.796875, 0.703125, 0.6875, 0.828125, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.65625, 0.625, 0.75, 0.84375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.46875, 0.3125, 0.875, 0.71875, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.63125, 0.715625, 0.65, 0.68125, 0.840625, 0.7125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.15625, 0.140625, 0.859375, 0.21875, 0.671875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.15625, 0.8125, 0.75, 0.1875, 0.921875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.809375, 0.8125, 0.75, 0.840625, 0.921875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.515625, 0.1875, 0.765625, 0.796875, 0.8125, 0.984375), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.thermoelectricgenerator, shape, Direction.EAST);
	}
}
