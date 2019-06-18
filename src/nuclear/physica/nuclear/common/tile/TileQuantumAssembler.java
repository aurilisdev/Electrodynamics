package physica.nuclear.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.IGuiInterface;
import physica.library.tile.TileBasePoweredContainer;
import physica.nuclear.client.gui.GuiQuantumAssembler;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.inventory.ContainerQuantumAssembler;

public class TileQuantumAssembler extends TileBasePoweredContainer implements IGuiInterface {

	public static final int TICKS_REQUIRED = 1200;
	public static final int SLOT_OUTPUT = 6;
	private static final int[] ACCESSIBLE_SLOTS_UP = new int[] { SLOT_OUTPUT };
	private static final int[] ACCESSIBLE_SLOTS_ELSE = new int[] { 0, 1, 2, 3, 4, 5 };
	protected int operatingTicks = 0;
	protected EntityItem entityItem = null;

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return slot == 6 ? !isRestricted(itemStack) : itemStack.getItem() == NuclearItemRegister.itemDarkmatterCell;
	}

	public boolean canProcess() {
		ItemStack itemStack = getStackInSlot(6);
		if (isRestricted(itemStack)) {
			return false;
		}
		for (int i = 0; i <= 5; i++) {
			ItemStack itemStackInSlot = getStackInSlot(i);
			if (itemStackInSlot == null) {
				return false;
			}
			if (itemStackInSlot.getItem() != NuclearItemRegister.itemDarkmatterCell) {
				return false;
			}
		}
		return itemStack.stackSize < 64;
	}

	public boolean isRestricted(ItemStack itemStack) {
		return itemStack == null || itemStack.stackSize <= 0 || !itemStack.isStackable() || itemStack.hasTagCompound()
			       || itemStack.getItem() == NuclearItemRegister.itemDarkmatterCell
			       || ConfigNuclearPhysics.QUANTUM_ASSEMBLER_BLACKLIST.contains(itemStack.getUnlocalizedName());
	}

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		if (canProcess() && hasEnoughEnergy()) {
			if (operatingTicks < TICKS_REQUIRED) {
				operatingTicks++;
			} else {
				process();
				operatingTicks = 0;
			}
			extractEnergy();
		} else if (getStackInSlot(SLOT_OUTPUT) == null || !canProcess()) {
			operatingTicks = 0;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateClient(int ticks) {
		super.updateClient(ticks);
		ItemStack itemStack = getStackInSlot(SLOT_OUTPUT);

		if (itemStack != null) {
			if (entityItem == null || !itemStack.isItemEqual(entityItem.getEntityItem())) {
				entityItem = getEntityForItem(itemStack);
			}
		} else {
			entityItem = null;
		}
	}

	private EntityItem getEntityForItem(ItemStack itemStack) {
		EntityItem entityItem = new EntityItem(worldObj, 0, 0, 0, itemStack.copy());
		entityItem.setAgeToCreativeDespawnTime();

		return entityItem;
	}

	private void process() {
		for (int i = 0; i <= 5; i++) {
			ItemStack itemStackInSlot = getStackInSlot(i);
			itemStackInSlot.setItemDamage(itemStackInSlot.getItemDamage() + 1);
			if (itemStackInSlot.getItemDamage() >= itemStackInSlot.getMaxDamage()) {
				decrStackSize(i, 1);
			}
		}
		ItemStack itemStack = getStackInSlot(SLOT_OUTPUT);
		if (itemStack != null) {
			itemStack.stackSize++;
		}
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player) {
		super.writeClientGuiPacket(dataList, player);
		dataList.add(operatingTicks);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readClientGuiPacket(buf, player);
		operatingTicks = buf.readInt();
	}

	public EntityItem getEntityItem() {
		return entityItem;
	}

	public int getOperatingTicks() {
		return operatingTicks;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return getEnergyUsage() * 2;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from.equals(ForgeDirection.DOWN) || from.equals(ForgeDirection.UP);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == ForgeDirection.UP.ordinal() ? ACCESSIBLE_SLOTS_UP : side != ForgeDirection.DOWN.ordinal() ? ACCESSIBLE_SLOTS_ELSE : ACCESSIBLE_SLOTS_UP;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public int getSizeInventory() {
		return 7;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player) {
		return new GuiQuantumAssembler(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player) {
		return new ContainerQuantumAssembler(player, this);
	}

	@Override
	public int getEnergyUsage() {
		return 13250;
	}
}
