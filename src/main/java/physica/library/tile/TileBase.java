package physica.library.tile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import physica.api.core.ITileBase;

public abstract class TileBase extends TileEntity implements ITileBase {

	private int					_ticksRunning	= 0;
	protected Set<EntityPlayer>	playersUsingGUI	= new HashSet<>();

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
