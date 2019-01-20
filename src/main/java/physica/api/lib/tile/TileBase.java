package physica.api.lib.tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import physica.api.base.IGuiInterface;
import physica.api.base.IPlayerUsing;
import physica.api.network.IPacket;
import physica.api.network.IPacketReciever;
import physica.api.network.netty.PacketSystem;
import physica.api.network.packet.PacketTile;

public abstract class TileBase extends TileEntity implements IPacketReciever, IPlayerUsing {
	public static final int			DESC_PACKET_ID		= 1000;
	public static final int			GUI_PACKET_ID		= 1001;

	private int						_ticksRunning		= 0;
	private boolean					_syncClientNextTick	= true;

	private ArrayList<EntityPlayer>	playersUsingGUI		= new ArrayList();

	protected void firstTick() {
	}

	@Override
	public Collection<EntityPlayer> getPlayersUsingGui() {
		return playersUsingGUI;
	}

	protected boolean isClient() {
		return worldObj != null && worldObj.isRemote;
	}

	protected boolean isServer() {
		return worldObj != null && !worldObj.isRemote;
	}

	@Override
	public boolean read(ByteBuf buf, int id, EntityPlayer player, IPacket type) {
		if (isClient())
		{
			if (id == DESC_PACKET_ID)
			{
				readDescriptionPacket(buf, player);
				return true;
			} else if (id == GUI_PACKET_ID)
			{
				readGuiPacket(buf, player);
				return true;
			}
		}
		return false;
	}

	protected void readDescriptionPacket(ByteBuf buf, EntityPlayer player) {
	}

	protected void readGuiPacket(ByteBuf buf, EntityPlayer player) {
	}

	protected void sendDescPacket() {
		if (isServer())
		{
			PacketTile packetTile = new PacketTile("utile_desc", DESC_PACKET_ID, this);
			List<Object> list = new ArrayList();
			writeDescriptionPacket(list, null);
			packetTile.addData(list);

			PacketSystem.INSTANCE.sendToAllAround(packetTile, this);
		}
	}

	protected boolean shouldSendGuiPacket(EntityPlayerMP playerMP) {
		return playerMP.isEntityAlive() && playerMP.openContainer != null;
	}

	protected void syncClientNextTick() {
		_syncClientNextTick = true;
	}

	protected void update(int ticks) {
	}

	protected void afterFirstUpdate(int ticks) {
		syncClientNextTick();
	}

	protected void postUpdate(int ticks) {
	}

	public boolean isPoweredByRedstone() {
		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity() {
		if (_ticksRunning == 0)
		{
			firstTick();
		}
		update(_ticksRunning);
		_ticksRunning++;
		if (_ticksRunning + 1 == Integer.MAX_VALUE)
		{
			_ticksRunning = 1;
		}

		if (isServer() && _ticksRunning % 3 == 0 && this instanceof IGuiInterface)
		{
			Iterator<EntityPlayer> it = playersUsingGUI.iterator();
			while (it.hasNext())
			{
				EntityPlayer player = it.next();
				if (player instanceof EntityPlayerMP && shouldSendGuiPacket((EntityPlayerMP) player))
				{
					PacketTile packet = new PacketTile("gui", GUI_PACKET_ID, this);
					List<Object> objects = new ArrayList();
					writeGuiPacket(objects, player);
					packet.addData(objects);
					PacketSystem.INSTANCE.sendToPlayer(packet, (EntityPlayerMP) player);
				} else
				{
					it.remove();
				}
			}
		}
		postUpdate(_ticksRunning);
		if (_syncClientNextTick)
		{
			sendDescPacket();
			_syncClientNextTick = false;
		}
		afterFirstUpdate(_ticksRunning);
	}

	protected void writeDescriptionPacket(List<Object> dataList, EntityPlayer player) {
	}

	protected void writeGuiPacket(List<Object> dataList, EntityPlayer player) {
	}

	public int getTicksRunning() {
		return _ticksRunning;
	}
}