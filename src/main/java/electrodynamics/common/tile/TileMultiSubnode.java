package electrodynamics.common.tile;

import electrodynamics.api.multiblock.parent.IMultiblockParentTile;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class TileMultiSubnode extends GenericTile {

	public final Property<Location> parentPos = property(new Property<>(PropertyType.Location, "node", Location.ZERO));
	public final Property<Integer> nodeIndex = property(new Property<>(PropertyType.Integer, "nodeIndex", 0));

	public VoxelShape shapeCache;

	public TileMultiSubnode() {
		super(ElectrodynamicsBlockTypes.TILE_MULTI.get());
		addComponent(new ComponentPacketHandler(this));
	}

	public void setData(BlockPos parentPos, int subnodeIndex) {

		this.parentPos.set(new Location(parentPos));
		nodeIndex.set(subnodeIndex);

		setChanged();

	}

	public VoxelShape getShape() {

		if (shapeCache != null) {
			return shapeCache;
		}

		TileEntity tile = level.getBlockEntity(parentPos.get().toBlockPos());
		if (tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile node = (IMultiblockParentTile) tile;
			return shapeCache = node.getSubNodes()[nodeIndex.get()].getShape(node.getFacingDirection());

		}

		return VoxelShapes.block();
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		TileEntity tile = level.getBlockEntity(parentPos.get().toBlockPos());
		if (tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile node = (IMultiblockParentTile) tile;
			node.onSubnodeNeighborChange(this, neighbor, blockStateTrigger);
		}
	}

	@Override
	public ActionResultType use(PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity tile = level.getBlockEntity(parentPos.get().toBlockPos());
		if (tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile node = (IMultiblockParentTile) tile;
			return node.onSubnodeUse(player, handIn, hit, this);
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		super.onPlace(oldState, isMoving);
		TileEntity tile = level.getBlockEntity(parentPos.get().toBlockPos());
		if (tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile node = (IMultiblockParentTile) tile;
			node.onSubnodePlace(this, oldState, isMoving);
		}
	}

	@Override
	public int getComparatorSignal() {
		TileEntity tile = level.getBlockEntity(parentPos.get().toBlockPos());
		if (tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile node = (IMultiblockParentTile) tile;
			return node.getSubdnodeComparatorSignal(this);
		}
		return 0;
	}

	@Override
	public void onBlockDestroyed() {
		TileEntity tile = level.getBlockEntity(parentPos.get().toBlockPos());
		if (tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile node = (IMultiblockParentTile) tile;
			node.onSubnodeDestroyed(this);
		}
		super.onBlockDestroyed();
	}

	@Override
	public int getDirectSignal(Direction dir) {
		TileEntity tile = level.getBlockEntity(parentPos.get().toBlockPos());
		if (tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile node = (IMultiblockParentTile) tile;
			return node.getDirectSignal(this, dir);
		}
		return 0;
	}

	@Override
	public int getSignal(Direction dir) {
		TileEntity tile = level.getBlockEntity(parentPos.get().toBlockPos());
		if (tile instanceof IMultiblockParentTile) {
			IMultiblockParentTile node = (IMultiblockParentTile) tile;
			return node.getSignal(this, dir);
		}
		return 0;
	}

}
