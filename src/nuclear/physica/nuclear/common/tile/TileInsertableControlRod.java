package physica.nuclear.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.IGuiInterface;
import physica.library.inventory.ContainerBase;
import physica.library.network.IPacket;
import physica.library.network.netty.PacketSystem;
import physica.library.network.packet.PacketTile;
import physica.library.tile.TileBaseRotateable;
import physica.nuclear.client.gui.GuiInsertableControlRod;

public class TileInsertableControlRod extends TileBaseRotateable implements IGuiInterface {

	public static final int CONTROL_ROD_PACKET_ID = 8;
	private int insertion = 100;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return super.getRenderBoundingBox().expand(1, 1, 1);
	}

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		ForgeDirection facing = getFacing().getOpposite();
		TileEntity tile = worldObj.getTileEntity(xCoord + facing.offsetX, yCoord + facing.offsetY, zCoord + facing.offsetZ);
		if (!(tile instanceof TileFissionReactor)) {
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, new ItemStack(getBlockType())));
			getLocation().setBlockAir(worldObj);

		}
	}

	@Override
	public GuiScreen getClientGuiElement(int id, EntityPlayer player) {
		return new GuiInsertableControlRod(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player) {
		ContainerBase<TileInsertableControlRod> base = new ContainerBase<TileInsertableControlRod>(player, this);
		base.addDefaultPlayerInventory(player, 0);
		return base;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("insertion", insertion);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		insertion = tag.getInteger("insertion");
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player) {
		super.writeClientGuiPacket(dataList, player);
		dataList.add(insertion);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readClientGuiPacket(buf, player);
		insertion = buf.readInt();
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player) {
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(insertion);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player) {
		super.readSynchronizationPacket(buf, player);
		insertion = buf.readInt();
	}

	public int getInsertion() {
		return insertion;
	}

	public void actionPerformed(int amount, Side side) {
		if (side == Side.CLIENT) {
			PacketSystem.INSTANCE.sendToServer(new PacketTile("", CONTROL_ROD_PACKET_ID, xCoord, yCoord, zCoord, amount));
		}
		insertion = Math.max(0, Math.min(100, insertion + amount));
	}

	@Override
	public void readCustomPacket(int id, EntityPlayer player, Side side, IPacket type) {
		if (id == CONTROL_ROD_PACKET_ID && side.isServer() && type instanceof PacketTile) {
			actionPerformed(((PacketTile) type).customInteger, side);
		}
	}

}
