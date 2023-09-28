package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.api.network.cable.type.IFluidPipe;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.common.tile.pipelines.fluids.TileFluidPipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class BlockFluidPipe extends AbstractRefreshingConnectBlock {

	public static final HashSet<Block> PIPESET = new HashSet<>();

	public final SubtypeFluidPipe pipe;

	public BlockFluidPipe(SubtypeFluidPipe pipe) {
		super(Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).strength(0.15f).dynamicShape().noOcclusion(), 3);
		this.pipe = pipe;
		PIPESET.add(this);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileFluidPipe(pos, state);
	}

	@Override
	public BlockState refreshConnections(BlockState otherState, BlockEntity tile, BlockState state, Direction dir) {
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
	public IRefreshableCable getCableIfValid(BlockEntity tile) {
		if (tile instanceof IFluidPipe pipe) {
			return pipe;
		}
		return null;
	}

}
