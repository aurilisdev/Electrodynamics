package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.api.network.cable.type.IFluidPipe;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.tile.pipelines.fluids.TileFluidPipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.state.EnumProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class BlockFluidPipe extends AbstractRefreshingConnectBlock {

	public static final HashSet<Block> PIPESET = new HashSet<>();

	public final SubtypeFluidPipe pipe;

	public BlockFluidPipe(SubtypeFluidPipe pipe) {
		super(Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).strength(0.15f).dynamicShape().noOcclusion().harvestTool(ToolType.PICKAXE).harvestLevel(1), 3);
		this.pipe = pipe;
		PIPESET.add(this);
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) {
		return new TileFluidPipe();
	}

	@Override
	public BlockState refreshConnections(BlockState otherState, TileEntity tile, BlockState state, Direction dir) {
		EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(dir);
		if (tile instanceof IFluidPipe) {
			return state.setValue(property, EnumConnectType.WIRE);
		}
		if (FluidUtilities.isFluidReceiver(tile, dir.getOpposite())) {
			return state.setValue(property, EnumConnectType.INVENTORY);
		}
		if (state.hasProperty(property)) {
			return state.setValue(property, EnumConnectType.NONE);
		}
		return state;
	}

	@Override
	public IRefreshableCable getCableIfValid(TileEntity tile) {
		if (tile instanceof IFluidPipe) {
			return (IFluidPipe) tile;
		}
		return null;
	}

}
