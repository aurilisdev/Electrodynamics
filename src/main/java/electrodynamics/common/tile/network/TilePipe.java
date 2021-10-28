package electrodynamics.common.tile.network;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.connect.BlockPipe;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.tile.generic.GenericTilePipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TilePipe extends GenericTilePipe {
    public double transmit = 0;

    public TilePipe() {
	super(DeferredRegisters.TILE_PIPE.get());
    }

    public SubtypePipe pipe = null;

    @Override
    public SubtypePipe getPipeType() {
	if (pipe == null) {
	    pipe = ((BlockPipe) getBlockState().getBlock()).pipe;
	}
	return pipe;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
	compound.putInt("ord", getPipeType().ordinal());
	return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
	super.load(state, compound);
	pipe = SubtypePipe.values()[compound.getInt("ord")];
    }

    @Override
    protected void writeCustomPacket(CompoundTag nbt) {
	nbt.putDouble("transmit", transmit);
    }

    @Override
    protected void readCustomPacket(CompoundTag nbt) {
	transmit = nbt.getDouble("transmit");
    }
}
