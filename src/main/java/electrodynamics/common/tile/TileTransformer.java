package electrodynamics.common.tile;

import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileTransformer extends GenericTile {
	public CachedTileOutput output;
	public TransferPack lastTransfer = TransferPack.EMPTY;
	public boolean locked = false;

	public TileTransformer(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_TRANSFORMER.get(), worldPosition, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeOutput(Direction.SOUTH).relativeInput(Direction.NORTH).voltage(-1));
	}

	protected TransferPack receivePower(TransferPack transfer, boolean debug) {
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (locked) {
			return TransferPack.EMPTY;
		}
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing));
		}
		boolean shouldUpgrade = ((BlockMachine) getBlockState().getBlock()).machine == SubtypeMachine.upgradetransformer;
		double resultVoltage = Mth.clamp(transfer.getVoltage() * (shouldUpgrade ? 2 : 0.5), 15.0, 61440.0);
		locked = true;
		TransferPack returner = ElectricityUtils.receivePower(output.getSafe(), facing.getOpposite(), TransferPack.joulesVoltage(transfer.getJoules() * Constants.TRANSFORMER_EFFICIENCY, resultVoltage), debug);
		locked = false;
		lastTransfer = returner;
		return returner;
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.25, 0.0625, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.0625, 1, 0.3125, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.875, 0.625, 0.84375, 0.9375, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.875, 0.1875, 0.84375, 0.9375, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.6875, 0.15625, 0.875, 0.78125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.6875, 0.59375, 0.875, 0.78125, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.6875, 0.59375, 0.375, 0.84375, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.6875, 0.15625, 0.375, 0.84375, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.59375, 0.375, 0.53125, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.15625, 0.375, 0.53125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.4375, 0.15625, 0.875, 0.53125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.4375, 0.59375, 0.875, 0.53125, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.640625, 0.53125, 0.171875, 0.859375, 0.6875, 0.390625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.640625, 0.53125, 0.609375, 0.859375, 0.6875, 0.828125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.140625, 0.53125, 0.609375, 0.359375, 0.6875, 0.828125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.140625, 0.53125, 0.171875, 0.359375, 0.6875, 0.390625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.3125, 0.625, 0.34375, 0.875, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.3125, 0.1875, 0.34375, 0.875, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65625, 0.3125, 0.625, 0.84375, 0.875, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65625, 0.3125, 0.1875, 0.84375, 0.875, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0.3125, 0.25, 1, 0.75, 0.75), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.downgradetransformer, shape, Direction.EAST);
		shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.25, 0.0625, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.0625, 1, 0.3125, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.875, 0.625, 0.84375, 0.9375, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.875, 0.1875, 0.84375, 0.9375, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.6875, 0.59375, 0.375, 0.78125, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.375, 0.59375, 0.875, 0.53125, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.375, 0.15625, 0.875, 0.53125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.6875, 0.59375, 0.875, 0.84375, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.6875, 0.15625, 0.875, 0.84375, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.6875, 0.15625, 0.375, 0.78125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.59375, 0.375, 0.53125, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.15625, 0.375, 0.53125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.140625, 0.53125, 0.609375, 0.359375, 0.6875, 0.828125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.640625, 0.53125, 0.609375, 0.859375, 0.6875, 0.828125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.640625, 0.53125, 0.171875, 0.859375, 0.6875, 0.390625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.140625, 0.53125, 0.171875, 0.359375, 0.6875, 0.390625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.3125, 0.625, 0.34375, 0.875, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.3125, 0.1875, 0.34375, 0.875, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65625, 0.3125, 0.625, 0.84375, 0.875, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65625, 0.3125, 0.1875, 0.84375, 0.875, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0.3125, 0.25, 1, 0.75, 0.75), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.upgradetransformer, shape, Direction.EAST);
	}
}
