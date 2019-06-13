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

	protected int energyStored;
	protected int lastEnergyStored;
	protected boolean isGenerating = false;
	protected int delayGeneration = 10;
	protected int steam;
	protected int maxSteam = 3000000;
	protected int mainX, mainY, mainZ;
	protected boolean hasMain = false;
	protected boolean isMain = false;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return isMain ? INFINITE_EXTENT_AABB
				: hasMain ? AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0) : super.getRenderBoundingBox();
	}

	public void attemptConstruct() {
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

	public void tryDeconstruct() {
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

	protected void addToConstruction(TileTurbine main) {
		mainX = main.xCoord;
		mainY = main.yCoord;
		mainZ = main.zCoord;
		hasMain = true;
	}

	public boolean hasMain() {
		return hasMain;
	}

	public boolean isMain() {
		return isMain;
	}

	@Override
	public void updateServer(int ticks) {
		lastEnergyStored = energyStored;
		if (isGenerating && !(energyStored > lastEnergyStored || steam > 0)) {
			if (delayGeneration > 0) {
				delayGeneration--;
			} else {
				isGenerating = false;
				delayGeneration = 10;
			}
		}
		isGenerating = energyStored > lastEnergyStored || steam > 0;
		if (steam > 0) {
			float steamToRf = ConfigNuclearPhysics.TURBINE_STEAM_TO_RF_RATIO;
			energyStored = (int) Math.min(getMaxEnergyStored(ForgeDirection.UNKNOWN),
					energyStored + steam * (hasMain ? steamToRf * 1.1f : steamToRf));
			steam = Math.max(steam - Math.max(75, steam), 0);
		}

		TileEntity above = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		if (above != null) {
			if (above instanceof IEnergyReceiver) {
				if (isMain) {
					int radius = 1;
					for (int i = -radius; i <= radius; i++) {
						for (int j = -radius; j <= radius; j++) {
							TileEntity tile = worldObj.getTileEntity(xCoord + i, yCoord, zCoord + j);
							if (tile instanceof TileTurbine) {
								TileTurbine turbine = (TileTurbine) tile;
								turbine.energyStored -= ((IEnergyReceiver) above).receiveEnergy(ForgeDirection.DOWN,
										turbine.energyStored, false);
							}
						}
					}
				} else if (!hasMain) {
					energyStored -= ((IEnergyReceiver) above).receiveEnergy(ForgeDirection.DOWN, energyStored, false);
				}
			}
		}
		if (!isMain && hasMain) {
			if (isGenerating) {
				TileEntity tile = worldObj.getTileEntity(mainX, mainY, mainZ);
				if (tile instanceof TileTurbine) {
					TileTurbine turbine = (TileTurbine) tile;
					turbine.isGenerating = true;
				}
			}
		}
		if (worldObj.getWorldTime() % 20 == 0 && isGenerating && (hasMain ? isMain : true)) {
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, CoreReferences.PREFIX + "block.turbine", 3f, 1F);
		}
	}

	@Override
	public int getSyncRate() {
		return 1;
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player) {
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(steam);
		dataList.add(isGenerating);
		dataList.add(hasMain);
		dataList.add(isMain);
		dataList.add(mainX);
		dataList.add(mainY);
		dataList.add(mainZ);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player) {
		super.readSynchronizationPacket(buf, player);
		steam = buf.readInt();
		isGenerating = buf.readBoolean();
		hasMain = buf.readBoolean();
		isMain = buf.readBoolean();
		mainX = buf.readInt();
		mainY = buf.readInt();
		mainZ = buf.readInt();
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from.equals(ForgeDirection.UP) && !hasMain || isMain;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
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
	public int getEnergyStored(ForgeDirection from) {
		return energyStored;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 320000000;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
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
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Energy", energyStored);
		nbt.setInteger("Steam", steam);
		nbt.setBoolean("hasMain", hasMain);
		nbt.setBoolean("isMain", isMain);
		nbt.setInteger("mainX", mainX);
		nbt.setInteger("mainY", mainY);
		nbt.setInteger("mainZ", mainZ);
	}

	public void addSteam(int steam) {
		this.steam = Math.min(maxSteam, this.steam + steam);
	}

	public boolean isGenerating() {
		return isGenerating;
	}

	public int getSteam() {
		return steam;
	}
}
