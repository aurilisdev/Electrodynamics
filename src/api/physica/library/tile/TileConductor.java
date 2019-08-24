package physica.library.tile;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import physica.api.core.abstraction.Face;
import physica.api.core.conductor.EnumConductorType;
import physica.api.core.conductor.IConductor;
import physica.library.location.Location;
import physica.library.net.ElectricNetworkRegistry;
import physica.library.net.energy.ElectricNetwork;

public class TileConductor extends TileEntity implements IConductor {
	public ElectricNetwork energyNetwork;

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public ElectricNetwork getNetwork()
	{
		return getNetwork(true);
	}

	public TileEntity[] getConnectedCables(TileEntity tileEntity)
	{
		TileEntity[] cables = { null, null, null, null, null, null };
		for (Face orientation : Face.VALID)
		{
			TileEntity cable = new Location(tileEntity).OffsetFace(orientation).getTile(tileEntity.getWorldObj());
			if (cable instanceof IConductor)
			{
				cables[orientation.ordinal()] = cable;
			}
		}
		return cables;
	}

	@Override
	public ElectricNetwork getNetwork(boolean createIfNull)
	{
		if (energyNetwork == null && createIfNull)
		{
			TileEntity[] adjacentCables = getConnectedCables(this);
			HashSet<ElectricNetwork> connectedNets = new HashSet<>();
			for (TileEntity cable : adjacentCables)
			{
				if (cable instanceof IConductor && ((IConductor) cable).getNetwork(false) != null)
				{
					connectedNets.add(((IConductor) cable).getNetwork());
				}
			}
			if (connectedNets.size() == 0)
			{
				energyNetwork = new ElectricNetwork(new IConductor[] { this });
			} else if (connectedNets.size() == 1)
			{
				energyNetwork = (ElectricNetwork) connectedNets.toArray()[0];
				energyNetwork.conductorSet.add(this);
			} else
			{
				energyNetwork = new ElectricNetwork(connectedNets);
				energyNetwork.conductorSet.add(this);
			}
		}
		return energyNetwork;
	}

	@Override
	public void invalidate()
	{
		if (!worldObj.isRemote)
		{
			getNetwork().split(this);
		}
		super.invalidate();
	}

	@Override
	public void setNetwork(ElectricNetwork network)
	{
		if (network != energyNetwork)
		{
			removeFromNetwork();
			energyNetwork = network;
		}
	}

	@Override
	public void refreshNetwork()
	{
		if (!worldObj.isRemote)
		{
			for (Face side : Face.VALID)
			{
				TileEntity tileEntity = new Location(this).OffsetFace(side).getTile(worldObj);
				if (tileEntity instanceof IConductor)
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
		if (energyNetwork != null)
		{
			energyNetwork.removeCable(this);
		}
	}

	@Override
	public void fixNetwork()
	{
		getNetwork().fixMessedUpNetwork(this);
	}

	@Override
	public int getElectricCapacity(Face from)
	{
		return getCableType().getTransferRate();
	}

	@Override
	public EnumConductorType getCableType()
	{
		return EnumConductorType.values()[Math.max(0, Math.min(EnumConductorType.values().length - 1, getBlockMetadata()))];
	}

	@Override
	public int receiveElectricity(Face from, int maxReceive, boolean simulate)
	{
		if (simulate)
		{
			return 0;
		}
		ArrayList<TileEntity> ignored = new ArrayList<>();
		ignored.add(new Location(this).OffsetFace(from).getTile(worldObj));
		return getNetwork().emit(maxReceive, ignored);
	}

	@Override
	public boolean canConnectElectricity(Face from)
	{
		return true;
	}

	@Override
	public void onChunkUnload()
	{
		invalidate();
		ElectricNetworkRegistry.getInstance().pruneEmptyNetworks();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	@Override
	public void destroyNodeViolently()
	{
		worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.fire);
	}
}
