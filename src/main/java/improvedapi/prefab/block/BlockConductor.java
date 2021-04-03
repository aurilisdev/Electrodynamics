package improvedapi.prefab.block;

import improvedapi.core.capability.CapabilityConductor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class BlockConductor extends Block {
    protected BlockConductor(Properties properties) {
	super(properties);
    }

    @Override
    @Deprecated
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
	super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
	worldIn.getTileEntity(pos).getCapability(CapabilityConductor.INSTANCE).ifPresent(conductor -> conductor.refresh());
    }

    @Override
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
	super.onNeighborChange(state, world, pos, neighbor);
	world.getTileEntity(pos).getCapability(CapabilityConductor.INSTANCE).ifPresent(conductor -> conductor.refresh());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }
}
