package physica.nuclear.common.tile;

import java.util.List;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import physica.CoreReferences;
import physica.library.tile.TileBase;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;

public class TileTurbine extends TileBase implements IEnergyProvider {

	public static final int MAX_STEAM = 3000000;

	protected int energyStored;
	protected int lastEnergyStored;
	protected boolean isGenerating = false;
	protected int delayGeneration = 10;
	protected int steam;
	protected int mainX, mainY, mainZ;
	protected boolean hasMain = false;
	protected boolean isMain = false;

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return isMain ? super.getRenderBoundingBox().expand(1, 1, 1)
				: hasMain ? AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0) : super.getRenderBoundingBox();
	}

	public void attemptConstruct()
	{
		boolean canConstruct = true;
		int radius = 1;
		for (int i = -radius; i <= radius; i++) {
			if (!canConstruct) {
				break;
			}
			for (int j = -radius; j <= radius; j++) {
				if (i != 0 || j != 0) {
					TileEntity tile = worldObj.getTileEntity(xCoord + i, yCoord, zCoord + j);
					if (!(tile instanceof TileTurbine)) {
						canConstruct = false;
						break;
					} else {
						TileTurbine turbine = (TileTurbine) tile;
						if (turbine.hasMain()) {
							canConstruct = false;
							break;
						}
					}
				}
			}
		}
		if (canConstruct) {
			isMain = true;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					((TileTurbine) worldObj.getTileEntity(xCoord + i, yCoord, zCoord + j)).addToConstruction(this);
				}
			}
		}
	}

	public void tryDeconstruct()
	{
		if (isMain) {
			int radius = 1;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					if (i != 0 || j != 0) {
						TileEntity tile = worldObj.getTileEntity(xCoord + i, yCoord, zCoord + j);
						if (tile instanceof TileTurbine) {
							TileTurbine turbine = (TileTurbine) tile;
							if (turbine != null) {
								turbine.hasMain = false;
								turbine.mainX = turbine.mainY = turbine.mainZ = 0;
							}
						}
					}
				}
			}
			isMain = false;
			hasMain = false;
			mainX = mainY = mainZ = 0;
		} else if (hasMain) {
			TileTurbine main = (TileTurbine) worldObj.getTileEntity(mainX, mainY, mainZ);
			if (main != null) {
				main.tryDeconstruct();
			}
			hasMain = false;
			mainX = mainY = mainZ = 0;
		}

	}

	protected void addToConstruction(TileTurbine main)
	{
		mainX = main.xCoord;
		mainY = main.yCoord;
		mainZ = main.zCoord;
		hasMain = true;
	}

	public boolean hasMain()
	{
		return hasMain;
	}

	public boolean isMain()
	{
		return isMain;
	}

	public TileEntity receiver = null;
	public boolean clientSpin = false;

	@Override
	public void updateServer(int ticks)
	{
		if (hasMain && !isMain) {
			return;
		}
		if (clientSpin) {
			if (delayGeneration > 0) {
				delayGeneration--;
			} else {
				clientSpin = false;
				delayGeneration = 60;
			}
		}
		isGenerating = energyStored > lastEnergyStored || steam > 0;
		lastEnergyStored = energyStored;
		if (steam > 0) {
			float steamToRf = ConfigNuclearPhysics.TURBINE_STEAM_TO_RF_RATIO;
			energyStored = (int) Math.min(getMaxEnergyStored(ForgeDirection.UNKNOWN),
					energyStored + steam * (isMain ? steamToRf * 1.111 : steamToRf));
			steam = Math.max(steam - Math.max(75, steam), 0);
			clientSpin = true;
			delayGeneration = 60;
		}
		if (receiver == null || receiver.isInvalid()) {
			if (receiver != null && receiver.isInvalid()) {
				receiver = null;
			}
			TileEntity above = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if (above instanceof IEnergyReceiver) {
				receiver = above;
			}
		} else {
			energyStored -= ((IEnergyReceiver) receiver).receiveEnergy(ForgeDirection.DOWN, energyStored, false);
		}
		if (worldObj.getWorldTime() % 20 == 0 && isGenerating && (!hasMain || isMain)) {
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, CoreReferences.PREFIX + "block.turbine", (isMain ? 0.75f : 0.1f) * (delayGeneration / 60.0f), 1F);
		}
	}

	@Override
	public int getSyncRate()
	{
		return 1;
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(steam);
		dataList.add(isGenerating);
		dataList.add(clientSpin);
		dataList.add(hasMain);
		dataList.add(isMain);
		dataList.add(mainX);
		dataList.add(mainY);
		dataList.add(mainZ);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readSynchronizationPacket(buf, player);
		steam = buf.readInt();
		isGenerating = buf.readBoolean();
		clientSpin = buf.readBoolean();
		hasMain = buf.readBoolean();
		isMain = buf.readBoolean();
		mainX = buf.readInt();
		mainY = buf.readInt();
		mainZ = buf.readInt();
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return from.equals(ForgeDirection.UP) && !hasMain || isMain;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		int energyExtracted = Math.min(energyStored, Math.min(getMaxEnergyStored(from), maxExtract));
		energyStored -= simulate ? 0 : energyExtracted;
		int radius = 1;
		if (isMain) {
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					if (i != 0 || j != 0) {
						TileEntity tile = worldObj.getTileEntity(xCoord + i, yCoord, zCoord + j);
						if (tile instanceof TileTurbine) {
							TileTurbine turbine = (TileTurbine) tile;
							energyExtracted += turbine.extractEnergy(from, maxExtract, simulate);
						}
					}
				}
			}
		}
		return (int) (energyExtracted * (isMain ? 1.15f : 1));
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		if (!isMain && hasMain) {
			TileEntity tile = worldObj.getTileEntity(mainX, mainY, mainZ);
			if (tile instanceof TileTurbine) {
				return ((TileTurbine) tile).energyStored;
			}
		}
		return energyStored;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return !isMain && !hasMain ? 320000000 : Integer.MAX_VALUE - 1;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		energyStored = nbt.getInteger("Energy");
		steam = nbt.getInteger("Steam");
		hasMain = nbt.getBoolean("hasMain");
		isMain = nbt.getBoolean("isMain");
		mainX = nbt.getInteger("mainX");
		mainY = nbt.getInteger("mainY");
		mainZ = nbt.getInteger("mainZ");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("Energy", energyStored);
		nbt.setInteger("Steam", steam);
		nbt.setBoolean("hasMain", hasMain);
		nbt.setBoolean("isMain", isMain);
		nbt.setInteger("mainX", mainX);
		nbt.setInteger("mainY", mainY);
		nbt.setInteger("mainZ", mainZ);
	}

	public void addSteam(int steam)
	{
		this.steam = Math.min(MAX_STEAM, this.steam + steam);
		if (!isMain && hasMain) {
			TileEntity tile = worldObj.getTileEntity(mainX, mainY, mainZ);
			if (tile instanceof TileTurbine) {
				((TileTurbine) tile).steam = Math.min(MAX_STEAM, ((TileTurbine) tile).steam + this.steam);
				((TileTurbine) tile).energyStored = Math.min(((TileTurbine) tile).getMaxEnergyStored(ForgeDirection.UNKNOWN), ((TileTurbine) tile).energyStored + this.energyStored);
				this.steam = 0;
				this.energyStored = 0;
			}
		}
	}

	public boolean isGenerating()
	{
		return isGenerating;
	}

	public boolean hasClientSpin()
	{
		return clientSpin;
	}

	public int getSteam()
	{
		return steam;
	}
}
