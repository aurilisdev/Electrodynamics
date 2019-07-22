package physica.forcefield.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import physica.forcefield.PhysicaForcefields;
import physica.library.location.Location;
import physica.library.tile.TileBase;

public class TileFortronField extends TileBase {

	private Location constructorCoord = new Location(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

	public TileFortronField() {
		sendDescPacket();
	}

	public void setConstructor(TileFortronFieldConstructor constructor)
	{
		if (isServer())
		{
			if (constructor != null)
			{
				constructorCoord = constructor.getLocation();
				constructor.activeFields.add(this);
				fieldColor = constructor.fieldColorMultiplier();
			}
		}
	}

	public Location getConstructorCoord()
	{
		return constructorCoord;
	}

	public boolean isForcefieldActive()
	{
		TileEntity tile = constructorCoord.getTile(worldObj);
		if (!(tile instanceof TileFortronFieldConstructor))
		{
			return false;
		}
		TileFortronFieldConstructor constructor = (TileFortronFieldConstructor) tile;
		if (constructor.getTicksRunning() <= 1)
		{
			return true;
		}
		if (!constructor.isActivated || constructor.isDestroying)
		{
			return false;
		}
		return true;
	}

	public int fieldColor = PhysicaForcefields.DEFAULT_COLOR;

	public boolean isValidField()
	{
		TileEntity constructor = constructorCoord.getTile(worldObj);
		return constructor instanceof TileFortronFieldConstructor && ((TileFortronFieldConstructor) constructor).isActivated();
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		Location loc = getLocation();
		return new S35PacketUpdateTileEntity(loc.xCoord, loc.yCoord, loc.zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		constructorCoord.writeToNBT(tag, "constructorCoord");
		tag.setInteger("fieldColor", fieldColor);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		constructorCoord.readFromNBT(tag, "constructorCoord");
		fieldColor = tag.getInteger("fieldColor");
	}

	@Override
	public int hashCode()
	{
		Location loc = getLocation();
		return loc.xCoord * 2 * loc.yCoord * loc.zCoord;
	}
}
