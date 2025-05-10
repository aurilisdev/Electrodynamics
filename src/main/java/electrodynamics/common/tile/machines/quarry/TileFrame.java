package electrodynamics.common.tile.machines.quarry;

import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileFrame extends TileEntity {

	public BlockPos ownerQuarryPos = null;

	private static final String KEY = "quarrypos";

	public TileFrame() {
		super(ElectrodynamicsTiles.TILE_QUARRY_FRAME.get());
	}

	public void purposefullyDestroyed() {
		if (ownerQuarryPos == null) {
			return;
		}
		TileEntity entity = level.getBlockEntity(ownerQuarryPos);
		if (entity != null && entity instanceof TileQuarry) {
			((TileQuarry) entity).addBrokenFrame(getBlockPos(), getBlockState());
		}
	}

	public void setQuarryPos(BlockPos pos) {
		ownerQuarryPos = pos;
		setChanged();
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		if (ownerQuarryPos != null) {
			BlockPos.CODEC.encodeStart(NBTDynamicOps.INSTANCE, ownerQuarryPos).result().ifPresent(nbt -> tag.put(KEY, nbt));;
		}
		return super.save(tag);
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		super.load(state, tag);
		if (tag.contains(KEY)) {
			BlockPos.CODEC.decode(NBTDynamicOps.INSTANCE, tag.get(KEY)).result().ifPresent(pair -> ownerQuarryPos = pair.getFirst());
		}
	}

}
