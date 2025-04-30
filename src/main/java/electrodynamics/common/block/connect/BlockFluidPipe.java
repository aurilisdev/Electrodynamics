package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.tile.pipelines.fluid.GenericTileFluidPipe;
import electrodynamics.common.tile.pipelines.fluid.TileFluidPipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.network.cable.type.IFluidPipe;
import voltaic.common.block.connect.AbstractRefreshingConnectBlock;
import voltaic.common.block.connect.EnumConnectType;
import voltaic.common.network.utils.FluidUtilities;

public class BlockFluidPipe extends AbstractRefreshingConnectBlock<GenericTileFluidPipe> {

	public static final HashSet<Block> PIPESET = new HashSet<>();

    public final IFluidPipe pipe;

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
    public EnumConnectType getConnection(BlockState otherState, BlockEntity otherTile, GenericTileFluidPipe thisConductor, Direction dir) {
        EnumConnectType connection = EnumConnectType.NONE;
        if (otherTile instanceof GenericTileFluidPipe) {
            connection = EnumConnectType.WIRE;
        } else if (FluidUtilities.isFluidReceiver(otherTile, dir.getOpposite())) {
            connection = EnumConnectType.INVENTORY;
        }
        return connection;
    }

    @Override
    public GenericTileFluidPipe getCableIfValid(BlockEntity tile) {
        if (tile instanceof GenericTileFluidPipe pipe) {
            return pipe;
        }
        return null;
    }

}
