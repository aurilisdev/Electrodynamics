package electrodynamics.common.tile;

import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileMultiSubnode extends GenericTile {
	public Property<BlockPos> nodePos = property(new Property<BlockPos>(PropertyType.BlockPos, "nodePos", BlockPos.ZERO));
	public VoxelShape shapeCache;

	public TileMultiSubnode(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_MULTI.get(), worldPosition, blockState);
		addComponent(new ComponentPacketHandler());
	}

	public VoxelShape getShape() {
		if (shapeCache != null) {
			return shapeCache;
		}
		if (nodePos.get() != null) {
			BlockEntity tile = level.getBlockEntity(nodePos.get());
			if (tile instanceof IMultiblockTileNode node) {
				BlockPos tp = tile.getBlockPos();
				BlockPos offset = new BlockPos(worldPosition.getX() - tp.getX(), worldPosition.getY() - tp.getY(), worldPosition.getZ() - tp.getZ());
				for (Subnode sub : node.getSubNodes()) {
					if (offset.equals(sub.pos)) {
						shapeCache = sub.shape;
						return shapeCache;
					}
				}
			}
		}
		return Shapes.block();
	}

}
