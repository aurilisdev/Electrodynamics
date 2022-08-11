package physica.nuclear.common.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.Face;
import physica.api.core.electricity.IElectricityProvider;
import physica.library.location.GridLocation;
import physica.library.tile.TileBase;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;

public class TileTurbine extends TileBase implements IElectricityProvider {

	public static final int MAX_STEAM = 3000000;

	protected int energyStored;
	protected int lastEnergyStored;
	protected boolean isGenerating = false;
	public int delayGeneration = 10;
	protected int steam;
	protected GridLocation mainLocation = new GridLocation();
	protected boolean hasMain = false;
	protected boolean isMain = false;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return isMain ? super.getRenderBoundingBox().expand(1, 1, 1) : hasMain ? AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0) : super.getRenderBoundingBox();
	}

	public void attemptConstruct() {
		boolean canConstruct = true;
		int radius = 1;
		GridLocation loc = getLocation();
		for (int i = -radius; i <= radius; i++) {
			if (!canConstruct) {
				break;
			}
			for (int j = -radius; j <= radius; j++) {
				if (i != 0 || j != 0) {
					TileEntity tile = World().getTileEntity(loc.xCoord + i, loc.yCoord, loc.zCoord + j);
					if (!(tile instanceof TileTurbine)) {
						canConstruct = false;
						break;
					}
					TileTurbine turbine = (TileTurbine) tile;
					if (turbine.hasMain()) {
						canConstruct = false;
						break;
					}
				}
			}
		}
		if (canConstruct) {
			isMain = true;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					((TileTurbine) World().getTileEntity(loc.xCoord + i, loc.yCoord, loc.zCoord + j)).addToConstruction(this);
				}
			}
		}
	}

	public void tryDeconstruct() {
		if (isMain) {
			GridLocation loc = getLocation();
			int radius = 1;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					if (i != 0 || j != 0) {
						TileEntity tile = World().getTileEntity(loc.xCoord + i, loc.yCoord, loc.zCoord + j);
						if (tile instanceof TileTurbine) {
							TileTurbine turbine = (TileTurbine) tile;
							turbine.hasMain = false;
							mainLocation.set(0, 0, 0);
						}
					}
				}
			}
			isMain = false;
			hasMain = false;
			mainLocation.set(0, 0, 0);
		} else if (hasMain) {
			TileTurbine main = (TileTurbine) mainLocation.getTile(World());
			if (main != null) {
				main.tryDeconstruct();
			}
			hasMain = false;
			mainLocation.set(0, 0, 0);
		}

	}

	protected void addToConstruction(TileTurbine main) {
		mainLocation.set(main.getLocation());
		hasMain = true;
	}

	public boolean hasMain() {
		return hasMain;
	}

	public boolean isMain() {
		return isMain;
	}

	public TileEntity receiver = null;
	public boolean clientSpin = false;

	@Override
	public void updateServer(int ticks) {
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
			energyStored = (int) Math.min(getElectricCapacity(Face.UNKNOWN), energyStored + steam * (isMain ? steamToRf * 1.111 : steamToRf));
			steam = Math.max(steam - Math.max(75, steam), 0);
			clientSpin = true;
			delayGeneration = 60;
		}
		GridLocation loc = getLocation();
		if (receiver == null || receiver.isInvalid()) {
			if (receiver != null && receiver.isInvalid()) {
				receiver = null;
			}
			TileEntity above = World().getTileEntity(loc.xCoord, loc.yCoord + 1, loc.zCoord);
			if (AbstractionLayer.Electricity.isElectricReceiver(above)) {
				receiver = above;
			}
		} else {
			energyStored -= AbstractionLayer.Electricity.receiveElectricity(receiver, Face.DOWN, energyStored, false);
		}
		if (World().getWorldTime() % 20 == 0 && isGenerating && (!hasMain || isMain)) {
			World().playSoundEffect(loc.xCoord, loc.yCoord, loc.zCoord, NuclearReferences.PREFIX + "block.turbine", isMain ? 0.75f : 0.1f, 1F);
		}
	}

	@Override
	public int getSyncRate() {
		return 10;
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player) {
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(steam);
		dataList.add(isGenerating);
		dataList.add(clientSpin);
		dataList.add(hasMain);
		dataList.add(isMain);
		dataList.add(mainLocation.xCoord);
		dataList.add(mainLocation.yCoord);
		dataList.add(mainLocation.zCoord);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player) {
		super.readSynchronizationPacket(buf, player);
		steam = buf.readInt();
		isGenerating = buf.readBoolean();
		clientSpin = buf.readBoolean();
		hasMain = buf.readBoolean();
		isMain = buf.readBoolean();
		mainLocation.set(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public boolean canConnectElectricity(Face from) {
		return from == Face.UP && !hasMain || isMain;
	}

	@Override
	public int extractElectricity(Face from, int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energyStored, Math.min(getElectricCapacity(from), maxExtract));
		energyStored -= simulate ? 0 : energyExtracted;
		int radius = 1;
		if (isMain) {
			GridLocation loc = getLocation();
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					if (i != 0 || j != 0) {
						TileEntity tile = World().getTileEntity(loc.xCoord + i, loc.yCoord, loc.zCoord + j);
						if (tile instanceof TileTurbine) {
							TileTurbine turbine = (TileTurbine) tile;
							energyExtracted += turbine.extractElectricity(from, maxExtract, simulate);
						}
					}
				}
			}
		}
		return (int) (energyExtracted * (isMain ? 1.15f : 1));
	}

	@Override
	public int getElectricityStored(Face from) {
		if (!isMain && hasMain) {
			TileEntity tile = mainLocation.getTile(World());
			if (tile instanceof TileTurbine) {
				return ((TileTurbine) tile).energyStored;
			}
		}
		return energyStored;
	}

	@Override
	public int getElectricCapacity(Face from) {
		return (int) (!isMain && !hasMain ? MAX_STEAM * ConfigNuclearPhysics.TURBINE_STEAM_TO_RF_RATIO : 9 * MAX_STEAM * ConfigNuclearPhysics.TURBINE_STEAM_TO_RF_RATIO * 1.111);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energyStored = nbt.getInteger("Energy");
		steam = nbt.getInteger("Steam");
		hasMain = nbt.getBoolean("hasMain");
		isMain = nbt.getBoolean("isMain");
		mainLocation.readFromNBT(nbt, "main");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Energy", energyStored);
		nbt.setInteger("Steam", steam);
		nbt.setBoolean("hasMain", hasMain);
		nbt.setBoolean("isMain", isMain);
		mainLocation.writeToNBT(nbt, "main");
	}

	public void addSteam(int steam) {
		this.steam = Math.min(MAX_STEAM, this.steam + steam);
		if (!isMain && hasMain) {
			TileEntity tile = mainLocation.getTile(World());
			if (tile instanceof TileTurbine) {
				((TileTurbine) tile).steam = Math.min(MAX_STEAM, ((TileTurbine) tile).steam + this.steam);
				((TileTurbine) tile).energyStored = Math.min(((TileTurbine) tile).getElectricCapacity(Face.UNKNOWN), ((TileTurbine) tile).energyStored + energyStored);
				this.steam = 0;
				energyStored = 0;
			}
		}
	}

	public boolean isGenerating() {
		return isGenerating;
	}

	public boolean hasClientSpin() {
		return clientSpin;
	}

	public int getSteam() {
		return steam;
	}
}
