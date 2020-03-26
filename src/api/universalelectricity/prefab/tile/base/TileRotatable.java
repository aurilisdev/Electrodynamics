package universalelectricity.prefab.tile.base;

import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import physica.core.common.network.PacketHandler;
import physica.core.common.network.PacketTileEntity;
import universalelectricity.api.tile.ITileNetwork;
import universalelectricity.api.tile.ITileRotatable;

public class TileRotatable extends TileBase implements ITileNetwork, ITileRotatable {
	private static final String NBT_FACING = "facing";

	protected EnumFacing facing = EnumFacing.NORTH;

	public TileRotatable() {
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey(NBT_FACING)) {
			facing = EnumFacing.byIndex(tag.getInteger(NBT_FACING));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if (facing != null) {
			tag.setInteger(NBT_FACING, facing.ordinal());
		}
		return tag;
	}

	@Override
	public void handlePacketData(ByteBuf dataStream) {
		if (world.isRemote) {
			facing = EnumFacing.byIndex(dataStream.readInt());
		}
	}

	@Override
	public List<Object> getPacketData(List<Object> objects) {
		objects.add(facing.ordinal());

		return objects;
	}

	@Override
	public boolean canSetFacing(EnumFacing facing) {
		return Arrays.asList(EnumFacing.HORIZONTALS).contains(facing);
	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	@Override
	public void setFacing(EnumFacing facing) {
		this.facing = facing;
		PacketHandler.instance.sendToReceivers(new PacketTileEntity(this), this);
	}
}