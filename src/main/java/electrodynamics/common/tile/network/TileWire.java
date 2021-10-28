package electrodynamics.common.tile.network;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.generic.GenericTileWire;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileWire extends GenericTileWire {
    public double transmit = 0;

    public TileWire() {
	super(DeferredRegisters.TILE_WIRE.get());
    }

    public TileWire(BlockEntityType<TileLogisticalWire> tileEntityType) {
	super(tileEntityType);
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
    public CompoundTag save(CompoundTag compound) {
	compound.putInt("ord", getWireType().ordinal());
	return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
	super.load(state, compound);
	wire = SubtypeWire.values()[compound.getInt("ord")];
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
