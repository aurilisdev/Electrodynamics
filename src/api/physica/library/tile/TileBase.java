package physica.library.tile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.api.core.tile.ITileBase;
import physica.library.location.GridLocation;

public abstract class TileBase extends TileEntity implements ITileBase {
	private int					_ticksRunning	= 0;
	private GridLocation		location;
	protected Set<EntityPlayer>	playersUsingGUI	= new HashSet<>();
	@Deprecated // Deprecate field as it is removed in newer versions
	public final int			xCoord			= 0;
	@Deprecated // Deprecate field as it is removed in newer versions
	public final int			yCoord			= 0;
	@Deprecated // Deprecate field as it is removed in newer versions
	public final int			zCoord			= 0;
	@Deprecated // Deprecate field as it is removed in newer versions
	public final World			worldObj		= null;

	@Override
	public GridLocation getLocation()
	{
		return location == null ? location = new GridLocation(this) : location.set(super.xCoord, super.yCoord, super.zCoord);
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
	}

	@Override
	public int getTicksRunning()
	{
		return _ticksRunning;
	}
}
