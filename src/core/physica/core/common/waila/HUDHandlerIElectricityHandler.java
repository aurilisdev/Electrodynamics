package physica.core.common.waila;

import java.util.List;

import mcp.mobius.waila.api.ITaggedList;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.api.core.abstraction.FaceDirection;
import physica.api.core.electricity.IElectricTile;
import physica.api.core.electricity.IElectricityProvider;
import physica.api.core.electricity.IElectricityReceiver;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;

public class HUDHandlerIElectricityHandler implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		try
		{
			if (!config.getConfig("physica.electricityhandler"))
			{
				return currenttip;
			}
		} catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		if (!accessor.getNBTData().hasKey(IElectricTile.ELECTRICITY_NBT))
		{
			return currenttip;
		}

		double energy = accessor.getNBTInteger(accessor.getNBTData(), IElectricTile.ELECTRICITY_NBT);
		double maxEnergy = accessor.getNBTInteger(accessor.getNBTData(), "Capacity");
		if (maxEnergy != 0 && ((ITaggedList) currenttip).getEntries("WattEnergyStorage").size() == 0)
		{
			((ITaggedList) currenttip).add(ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(energy, Unit.RF, Unit.WATTHOUR), Unit.WATTHOUR) + " / "
					+ ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(maxEnergy, Unit.RF, Unit.WATTHOUR), Unit.WATTHOUR), "WattEnergyStorage");
			((ITaggedList) currenttip).add(ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(energy, Unit.RF, Unit.WATTTICK), Unit.WATTTICK) + " / "
					+ ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy(maxEnergy, Unit.RF, Unit.WATTTICK), Unit.WATTTICK), "WattEnergyStorage");
		}

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		if (te instanceof IElectricTile)
		{
			Integer energy = -1;
			Integer maxsto = -1;
			if (te instanceof IElectricityProvider)
			{
				energy = ((IElectricityProvider) te).getElectricityStored(FaceDirection.UNKNOWN);
				maxsto = ((IElectricityProvider) te).getElectricCapacity(FaceDirection.UNKNOWN);
			} else if (te instanceof IElectricityReceiver)
			{
				energy = ((IElectricityReceiver) te).getElectricityStored(FaceDirection.UNKNOWN);
				maxsto = ((IElectricityReceiver) te).getElectricCapacity(FaceDirection.UNKNOWN);
			}

			tag.setInteger(IElectricTile.ELECTRICITY_NBT, energy);
			tag.setInteger("Capacity", maxsto);
		}
		return tag;
	}

}
