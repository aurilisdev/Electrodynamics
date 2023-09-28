package electrodynamics.common.tile.pipelines.tanks.gas;

import java.util.stream.Stream;

import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileGasTankHSLA extends GenericTileGasTank {

	public TileGasTankHSLA(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_GASTANK_HSLA.get(), pos, state, SubtypeMachine.gastankhsla, 128000, 1024, 1000);
	}

	static {

		VoxelShape shape = Stream.of(
				//
				Block.box(4, 14, 4, 12, 16, 12),
				//
				Stream.of(
						//
						Block.box(0, 2, 1, 1, 12, 15),
						//
						Block.box(1, 2, 0, 15, 12, 16),
						//
						Block.box(1, 0, 2, 2, 2, 14),
						//
						Block.box(2, 0, 1, 14, 2, 15),
						//
						Block.box(14, 0, 2, 15, 2, 14),
						//
						Block.box(15, 2, 1, 16, 12, 15),
						//
						Block.box(1, 12, 2, 2, 14, 14),
						//
						Block.box(2, 12, 1, 14, 14, 15),
						//
						Block.box(14, 12, 2, 15, 14, 14)
				//
				).reduce((v1, v2) -> Shapes.or(v1, v2)).get(),
				//
				Stream.of(
						//
						Block.box(7, 14, 13, 9, 15, 14),
						//
						Block.box(13, 14, 7, 14, 15, 9),
						//
						Block.box(3, 15, 2, 13, 16, 3),
						//
						Block.box(3, 15, 13, 13, 16, 14),
						//
						Block.box(2, 15, 3, 3, 16, 13),
						//
						Block.box(13, 15, 3, 14, 16, 13),
						//
						Block.box(7, 14, 2, 9, 15, 3),
						//
						Block.box(2, 14, 7, 3, 15, 9)
				//
				).reduce((v1, v2) -> Shapes.or(v1, v2)).get()
		//
		).reduce((v1, v2) -> Shapes.or(v1, v2)).get();

		VoxelShapes.registerShape(SubtypeMachine.gastankhsla, shape, Direction.NORTH);

	}

}
