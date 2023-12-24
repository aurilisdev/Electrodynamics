package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockLogisticalWire extends BlockWire {

	public static final HashSet<Block> WIRES = new HashSet<>();

	public BlockLogisticalWire(SubtypeWire wire) {
		super(wire);
		WIRES.add(this);
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileLogisticalWire();
	}

}
