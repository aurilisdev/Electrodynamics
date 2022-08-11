package physica.core.common.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.Face;
import physica.api.core.electricity.IElectricityProvider;
import physica.core.common.event.FulminationEventHandler;
import physica.library.location.GridLocation;
import physica.library.tile.TileBase;

public class TileFulmination extends TileBase implements IElectricityProvider {

	public static int MAX_ENERGY_STORED = 500000;
	private int energyStored;

	public int getElectricityStored() {
		return energyStored;
	}

	@Override
	public void setElectricityStored(int energy) {
		energyStored = Math.min(energy, MAX_ENERGY_STORED);
	}

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		if (!FulminationEventHandler.INSTANCE.isRegistered(this)) {
			FulminationEventHandler.INSTANCE.register(this);
		}
		GridLocation loc = getLocation();
		if (energyStored > 0 && ticks % 20 == 0) {
			for (Face dir : Face.VALID) {
				TileEntity tile = World().getTileEntity(loc.xCoord + dir.offsetX, loc.yCoord + dir.offsetY, loc.zCoord + dir.offsetZ);
				if (tile != null) {
					if (AbstractionLayer.Electricity.isElectricReceiver(tile)) {
						if (AbstractionLayer.Electricity.canConnectElectricity(tile, dir.getOpposite())) {
							energyStored -= AbstractionLayer.Electricity.receiveElectricity(tile, dir.getOpposite(), energyStored, false);
						}
					}
				}
			}
		}
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player) {
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(energyStored);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player) {
		super.readSynchronizationPacket(buf, player);
		energyStored = buf.readInt();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energyStored", energyStored);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energyStored = tag.getInteger("energyStored");
	}

	@Override
	public int getElectricCapacity(Face from) {
		return MAX_ENERGY_STORED;
	}

	@Override
	public boolean canConnectElectricity(Face from) {
		return true;
	}

	@Override
	public int extractElectricity(Face from, int maxExtract, boolean simulate) {
		int value = Math.min(5000, energyStored);
		if (!simulate) {
			energyStored -= value;
		}
		return value;
	}

	@Override
	public int getElectricityStored(Face from) {
		return energyStored;
	}
}
