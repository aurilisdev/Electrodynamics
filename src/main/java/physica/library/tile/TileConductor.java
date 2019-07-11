package physica.library.tile;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.cable.EnumConductorType;
import physica.api.core.cable.IConductor;
import physica.library.location.BlockLocation;
import physica.library.net.EnergyNetworkRegistry;
import physica.library.net.energy.EnergyNetwork;

public class TileConductor extends TileEntity implements IConductor {
	public EnergyNetwork energyNetwork;

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public EnergyNetwork getNetwork()
	{
		return getNetwork(true);
	}

	public TileEntity[] getConnectedCables(TileEntity tileEntity)
	{
		TileEntity[] cables = { null, null, null, null, null, null };
		for (ForgeDirection orientation : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity cable = new BlockLocation(tileEntity).TranslateTo(orientation).getTile(tileEntity.getWorldObj());
			if (cable instanceof IConductor)
			{
				cables[orientation.ordinal()] = cable;
			}
		}
		return cables;
	}

	@Override
	public EnergyNetwork getNetwork(boolean createIfNull)
	{
		if (energyNetwork == null && createIfNull)
		{
			TileEntity[] adjacentCables = getConnectedCables(this);
			HashSet<EnergyNetwork> connectedNets = new HashSet<>();
			for (TileEntity cable : adjacentCables)
			{
				if (cable instanceof IConductor && ((IConductor) cable).getNetwork(false) != null)
				{
					connectedNets.add(((IConductor) cable).getNetwork());
				}
			}
			if (connectedNets.size() == 0)
			{
				energyNetwork = new EnergyNetwork(new IConductor[] { this });
			} else if (connectedNets.size() == 1)
			{
				energyNetwork = (EnergyNetwork) connectedNets.toArray()[0];
				energyNetwork.conductorSet.add(this);
			} else
			{
				energyNetwork = new EnergyNetwork(connectedNets);
				energyNetwork.conductorSet.add(this);
			}
		}
		return energyNetwork;
	}

	@Override
	public void invalidate()
	{
		if (!this.worldObj.isRemote)
		{
			getNetwork().split(this);
		}
		super.invalidate();
	}

	@Override
	public void setNetwork(EnergyNetwork network)
	{
		if (network != this.energyNetwork)
		{
			removeFromNetwork();
			this.energyNetwork = network;
		}
	}

	@Override
	public void refreshNetwork()
	{
		if (!this.worldObj.isRemote)
		{
			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
			{
				TileEntity tileEntity = new BlockLocation(this).TranslateTo(side).getTile(worldObj);
				if ((tileEntity instanceof IConductor))
				{
					getNetwork().merge(((IConductor) tileEntity).getNetwork());
				}
			}
			getNetwork().refresh();
		}
	}

	@Override
	public void removeFromNetwork()
	{
		if (this.energyNetwork != null)
		{
			this.energyNetwork.removeCable(this);
		}
	}

	@Override
	public void fixNetwork()
	{
		getNetwork().fixMessedUpNetwork(this);
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return 1;
	}

	@Override
	public EnumConductorType getCableType()
	{
		return EnumConductorType.values()[Math.max(0, Math.min(EnumConductorType.values().length - 1, getBlockMetadata()))];
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		if (simulate)
		{
			return 0;
		}
		ArrayList<TileEntity> ignored = new ArrayList<>();
		ignored.add(new BlockLocation(this).TranslateTo(from).getTile(worldObj));
		return (int) getNetwork().emit(maxReceive, ignored);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public void onChunkUnload()
	{
		invalidate();
		EnergyNetworkRegistry.getInstance().pruneEmptyNetworks();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
}
