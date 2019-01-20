package physica.nuclear.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import physica.library.tile.TileBasePoweredContainer;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearItemRegister;

public class TileFusionReactor extends TileBasePoweredContainer {

	public static final float PLASMA_SPAWN_STRENGTH = 13;
	public static final int ENERGY_USAGE = 750;
	public static final int MAX_DEUTERIUM = 1024;
	public static final int SLOT_DEUTERIUM = 0;
	public static final int SLOT_TRITIUM = 1;

	private static final int[] ACCESSIBLE_SLOTS_UP = new int[] { SLOT_DEUTERIUM, SLOT_TRITIUM };
	private int energyStored;
	private int ticksRunning;
	private boolean isRunning = false;

	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		ItemStack deuterium = getStackInSlot(SLOT_DEUTERIUM);
		ItemStack tritium = getStackInSlot(SLOT_TRITIUM);
		if (hasEnoughEnergy() && deuterium != null && deuterium.stackSize > 0 && tritium != null
				&& tritium.stackSize > 0) {
			ticksRunning++;
			if (ticksRunning % (20 * 15) == 0) {
				deuterium.stackSize--;
				tritium.stackSize--;
			}
			isRunning = true;
			extractEnergy();
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				if (direction == ForgeDirection.DOWN || direction == ForgeDirection.UP) {
					continue;
				}
				Block block = worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
				if (block.isAir(worldObj, xCoord, yCoord, zCoord) || block.getMaterial() == Material.fire || block == NuclearBlockRegister.blockPlasma) {
					NuclearBlockRegister.blockPlasma.spawn(worldObj, xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ, 10);
				}
			}
		} else {
			isRunning = false;
			ticksRunning = 0;
		}
	}

	@Override
	public int getSyncRate() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateClient(int ticks) {
		super.updateClient(ticks);
		if (isRunning) {
			float radius = 1f;
			for (float i = 0.175f; i < 0.7; i += 0.1) {
				for (int j = 0; j < 5; j++) {
					worldObj.spawnParticle("portal", xCoord + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2, yCoord + i + worldObj.rand.nextDouble() * radius - radius / 2 - 1,
							zCoord + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2, 0, 0, 0);
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Energy", energyStored);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energyStored = nbt.getInteger("Energy");
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player) {
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(energyStored);
		dataList.add(isRunning);
		dataList.add(getStackInSlot(SLOT_DEUTERIUM) != null ? getStackInSlot(SLOT_DEUTERIUM).stackSize : 0);
		dataList.add(getStackInSlot(SLOT_TRITIUM) != null ? getStackInSlot(SLOT_TRITIUM).stackSize : 0);

	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player) {
		super.readSynchronizationPacket(buf, player);
		energyStored = buf.readInt();
		isRunning = buf.readBoolean();
		if (getStackInSlot(SLOT_DEUTERIUM) != null) {
			getStackInSlot(SLOT_DEUTERIUM).stackSize = buf.readInt();
		} else {
			int size = buf.readInt();
			if (size > 0) {
				setInventorySlotContents(SLOT_DEUTERIUM, new ItemStack(NuclearItemRegister.itemDeuteriumCell, size));
			}
		}
		if (getStackInSlot(SLOT_TRITIUM) != null) {
			getStackInSlot(SLOT_TRITIUM).stackSize = buf.readInt();
		} else {
			int size = buf.readInt();
			if (size > 0) {
				setInventorySlotContents(SLOT_TRITIUM, new ItemStack(NuclearItemRegister.itemTritiumCell, size));
			}
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP;
	}

	@Override
	public int getEnergyUsage() {
		return ENERGY_USAGE;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return ENERGY_USAGE * 2;
	}

	@Override
	public int getInventoryStackLimit() {
		return MAX_DEUTERIUM;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		ItemStack slotStack = getStackInSlot(slot);
		if (slot == SLOT_DEUTERIUM && stack != null && stack.getItem() == NuclearItemRegister.itemDeuteriumCell) {
			if (slotStack != null) {
				if (slotStack.stackSize + stack.stackSize <= getInventoryStackLimit()) {
					return true;
				}
			} else {
				return true;
			}
		}
		if (slot == SLOT_TRITIUM && stack != null && stack.getItem() == NuclearItemRegister.itemTritiumCell) {
			if (slotStack != null) {
				if (slotStack.stackSize + stack.stackSize <= getInventoryStackLimit()) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == ForgeDirection.UP.ordinal() ? ACCESSIBLE_SLOTS_UP : ACCESSIBLE_SLOTS_NONE;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

}
