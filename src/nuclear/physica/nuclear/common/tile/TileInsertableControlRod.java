package physica.nuclear.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import physica.api.core.abstraction.Face;
import physica.api.core.inventory.IGuiInterface;
import physica.core.common.integration.IComputerIntegration;
import physica.library.inventory.ContainerBase;
import physica.library.location.GridLocation;
import physica.library.network.IPacket;
import physica.library.network.netty.PacketSystem;
import physica.library.network.packet.PacketTile;
import physica.library.tile.TileBaseRotateable;
import physica.nuclear.client.gui.GuiInsertableControlRod;

public class TileInsertableControlRod extends TileBaseRotateable implements IGuiInterface, IComputerIntegration {

	public static final int	CONTROL_ROD_PACKET_ID	= 8;
	private int				insertion				= 100;

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return super.getRenderBoundingBox().expand(1, 1, 1);
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		Face facing = getFacing().getOpposite();
		GridLocation loc = getLocation();
		TileEntity tile = World().getTileEntity(loc.xCoord + facing.offsetX, loc.yCoord + facing.offsetY, loc.zCoord + facing.offsetZ);
		if (!(tile instanceof TileFissionReactor))
		{
			World().spawnEntityInWorld(new EntityItem(World(), loc.xCoord + 0.5, loc.yCoord + 0.5, loc.zCoord + 0.5, new ItemStack(getBlockType())));
			getLocation().setBlockAir(World());

		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiInsertableControlRod(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerBase<>(player, this, null);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("insertion", insertion);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		insertion = tag.getInteger("insertion");
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeClientGuiPacket(dataList, player);
		dataList.add(insertion);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readClientGuiPacket(buf, player);
		insertion = buf.readInt();
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(insertion);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readSynchronizationPacket(buf, player);
		insertion = buf.readInt();
	}

	public int getInsertion()
	{
		return insertion;
	}

	public void setInsertion(int value)
	{
		insertion = Math.max(0, Math.min(100, value));
	}

	public void actionPerformed(int amount, Side side)
	{
		if (side == Side.CLIENT)
		{
			GridLocation loc = getLocation();
			PacketSystem.INSTANCE.sendToServer(new PacketTile("", CONTROL_ROD_PACKET_ID, loc.xCoord, loc.yCoord, loc.zCoord, amount));
		}
		insertion = Math.max(0, Math.min(100, insertion + amount));
	}

	@Override
	public void readCustomPacket(int id, EntityPlayer player, Side side, IPacket type)
	{
		if (id == CONTROL_ROD_PACKET_ID && side.isServer() && type instanceof PacketTile)
		{
			actionPerformed(((PacketTile) type).customInteger, side);
		}
	}

	@Override
	public String getComponentName() {
		return "fission_control_rod";
	}

	@Override
	public String[] methods()
	{
		return new String[]{"setInsertion", "getInsertion"};
	}

	@Override
	public Object[] invoke(int method, Object[] args) throws Exception
	{
		switch (method) {
			case 0:
				if (args.length <= 0) {
					return new Object[]{"1st argument is required and must be a number."};
				} else if (!(args[0] instanceof Double)) {
					return new Object[]{"Expected integer for 1st argument, got "+args[0].getClass().getName()};
				}
				this.setInsertion(((Double) args[0]).intValue());
				return new Object[]{};
			case 1:
				return new Object[]{this.getInsertion()};
			default:
				throw new NoSuchMethodException();
		}
	}
}
