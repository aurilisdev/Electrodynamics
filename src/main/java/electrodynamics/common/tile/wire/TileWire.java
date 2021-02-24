package electrodynamics.common.tile.wire;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.generic.GenericTileWire;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class TileWire extends GenericTileWire {
    public double transmit = 0;

    public TileWire() {
	super(DeferredRegisters.TILE_WIRE.get());
    }

    public TileWire(TileEntityType<?> tile) {
	super(tile);
    }

    public SubtypeWire wire = null;

    @Override
    public SubtypeWire getWireType() {
	if (wire == null) {
	    wire = ((BlockWire) getBlockState().getBlock()).wire;
	}
	return wire;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putInt("ord", getWireType().ordinal());
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	wire = SubtypeWire.values()[compound.getInt("ord")];
    }

    @Override
    public CompoundNBT createUpdateTag() {
	CompoundNBT nbt = super.createUpdateTag();
	nbt.putDouble("transmit", transmit);
	return nbt;
    }

    @Override
    public void handleUpdatePacket(CompoundNBT nbt) {
	super.handleUpdatePacket(nbt);
	transmit = nbt.getDouble("transmit");
    }
}
