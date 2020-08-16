package electrodynamics.common.tile.generic;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;

public abstract class GenericTileBase extends TileEntity implements INameable {

	private ITextComponent customName;

	public GenericTileBase(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		customName = ITextComponent.Serializer.func_240643_a_(compound.getString("CustomName"));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (customName != null) {
			compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
		}
		return compound;
	}

	@Override
	public void markDirty() {
		super.markDirty();
		sendPacket();
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT tag = new CompoundNBT();
		writePacket(tag);
		return new SUpdateTileEntityPacket(pos, 1, tag);
	}

	public void writePacket(CompoundNBT nbt) {
		write(nbt);
	}

	public void readPacket(CompoundNBT nbt) {
		read(getBlockState(), nbt);
	}

	public void sendPacket() {
		world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		readPacket(pkt.getNbtCompound());
	}

	@Override
	public ITextComponent getCustomName() {
		return customName;
	}

	@Override
	public ITextComponent getName() {
		return getCustomName();
	}

}
