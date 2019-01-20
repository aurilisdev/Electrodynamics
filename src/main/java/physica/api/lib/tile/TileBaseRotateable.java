package physica.api.lib.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.base.IRotatable;

public abstract class TileBaseRotateable extends TileBase implements IRotatable {

	private ForgeDirection facing = ForgeDirection.NORTH;

	@Override
	public ForgeDirection getFacing() {
		return facing;
	}

	@Override
	public void setFacing(ForgeDirection facing) {
		this.facing = facing;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("facing", facing.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setFacing(ForgeDirection.getOrientation(nbt.getInteger("facing")));
	}

	@Override
	protected void writeDescriptionPacket(List<Object> dataList, EntityPlayer player) {
		super.writeDescriptionPacket(dataList, player);
		dataList.add(facing.ordinal());
	}

	@Override
	protected void readDescriptionPacket(ByteBuf buf, EntityPlayer player) {
		super.readDescriptionPacket(buf, player);
		setFacing(ForgeDirection.getOrientation(buf.readInt()));
	}
}
