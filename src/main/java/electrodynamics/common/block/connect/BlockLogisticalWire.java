package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockLogisticalWire extends BlockWire {

	public static final HashSet<Block> WIRES = new HashSet<>();

	public BlockLogisticalWire(SubtypeWire wire) {
		super(wire);
		WIRES.add(this);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileLogisticalWire(pos, state);
	}

}
