package physica.library.tile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.api.core.tile.ITileBase;
import physica.library.location.Location;

public abstract class TileBase extends TileEntity implements ITileBase {
	@Deprecated
	public int					xCoord;
	@Deprecated
	public int					yCoord;
	@Deprecated
	public int					zCoord;
	@Deprecated
	public World				worldObj;
	private int					_ticksRunning	= 0;
	private Location			location;
	protected Set<EntityPlayer>	playersUsingGUI	= new HashSet<>();

	@Override
	public Location getLocation()
	{
		worldObj = super.worldObj;
		return location == null ? location = new Location(this) : location.set(super.xCoord, super.yCoord, super.zCoord);
	}

	public World World()
	{
		return getWorldObj();
	}

	@Override
	public Collection<EntityPlayer> getPlayersUsingGui()
	{
		return playersUsingGUI;
	}

	@Override
	public void updateEntity()
	{
		_ticksRunning = handleUpdate(_ticksRunning);
		xCoord = super.xCoord;
		yCoord = super.yCoord;
		zCoord = super.zCoord;
		worldObj = super.worldObj;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		handleWriteToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		handleReadFromNBT(tag);
		xCoord = super.xCoord;
		yCoord = super.yCoord;
		zCoord = super.zCoord;
	}

	@Override
	public int getTicksRunning()
	{
		return _ticksRunning;
	}
}
