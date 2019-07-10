package physica.core.common.tile.cable;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.ITileBasePowered;
import physica.core.common.block.BlockEnergyCable.EnumEnergyCable;
import physica.core.common.network.EnergyTransferNetwork;
import physica.core.common.network.ITransferNode;
import physica.library.location.BlockLocation;
import physica.library.tile.TileBase;

public class TileEnergyCable extends TileBase implements ITileBasePowered, ITransferNode<IEnergyReceiver> {

	public boolean shouldFlame = false;

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	@Override
	public int getEnergyStored()
	{
		return 0;
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (ticks % 40 == 0)
		{
			if (shouldFlame)
			{
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.fire);
			}
		}
	}

	@Override
	public void destroyNode()
	{
		shouldFlame = true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		return getTransferNetwork().receiveAndDistributeEnergy(maxReceive, this, simulate);
	}

	@Override
	public boolean canUpdate()
	{
		return true;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return getTransferNetwork().getVisualTransferRate();
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		if (!shouldFlame)
		{
			getTransferNetwork().validateNetwork();
		}
	}

	@Override
	public int getEnergyUsage()
	{
		return 0;
	}

	private EnergyTransferNetwork network;

	@Override
	public void setTransferNetwork(EnergyTransferNetwork network)
	{
		this.network = network;
	}

	@Override
	public EnergyTransferNetwork getTransferNetwork()
	{
		if (network == null)
		{
			network = new EnergyTransferNetwork(this, EnumEnergyCable.values()[Math.max(0, Math.min(EnumEnergyCable.values().length - 1, getBlockMetadata()))]);
		}
		return network;
	}

	@Override
	public boolean isValid()
	{
		return !isInvalid();
	}

	@Override
	public World getWorld()
	{
		return worldObj;
	}

	@Override
	public BlockLocation getNodeLocation()
	{
		return getBlockLocation();
	}

	@Override
	public void setEnergyStored(int energy)
	{
	}

}
