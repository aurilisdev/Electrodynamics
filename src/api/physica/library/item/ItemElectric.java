package physica.library.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import physica.api.core.electricity.IElectricItem;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;

public class ItemElectric extends Item implements IElectricItem {

	public static final String ENERGY_NBT_DATA = "Energy";

	protected int capacity;
	protected int receiveRate;
	protected int extractRate;

	public ItemElectric() {
	}

	public ItemElectric(int capacity) {
		this(capacity, capacity, capacity);
	}

	public ItemElectric(int capacity, int maxTransfer) {

		this(capacity, maxTransfer, maxTransfer);
	}

	public ItemElectric(int capacity, int maxReceive, int maxExtract) {
		this.capacity = capacity;
		receiveRate = maxReceive;
		extractRate = maxExtract;
		setNoRepair();
		setMaxDamage(100);
		setMaxStackSize(1);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		ItemStack disCharged = new ItemStack(this);
		setEnergyStored(disCharged, 0);
		list.add(disCharged);
		ItemStack charged = new ItemStack(this);
		setEnergyStored(charged, capacity);
		list.add(charged);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack container) {
		return 1.0 - getElectricityStored(container) / (double) getElectricCapacity(container);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getElectricityStored(stack) < getElectricCapacity(stack) && getElectricityStored(stack) > 0;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean par4) {
		super.addInformation(stack, player, info, par4);
		info.add(EnumChatFormatting.AQUA + "Energy Stored: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy((double) getElectricityStored(stack), Unit.RF, Unit.WATTHOUR), Unit.WATTHOUR));
	}

	@Override
	public int extractElectricity(ItemStack container, int amount, boolean simulate) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey(ENERGY_NBT_DATA)) {
			return 0;
		}
		int energy = container.stackTagCompound.getInteger(ENERGY_NBT_DATA);
		int energyExtracted = Math.min(energy, Math.min(extractRate, amount));

		if (!simulate) {
			energy -= energyExtracted;
			setEnergyStored(container, energy);
		}
		return energyExtracted;
	}

	@Override
	public int getElectricCapacity(ItemStack container) {
		return capacity;
	}

	@Override
	public int receiveElectricity(ItemStack container, int maxReceive, boolean simulate) {
		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.stackTagCompound.getInteger(ENERGY_NBT_DATA);
		int energyReceived = Math.min(capacity - energy, Math.min(receiveRate, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			setEnergyStored(container, energy);
		}
		return energyReceived;
	}

	public ItemElectric setCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public ItemElectric setExtractRate(int extractRate) {
		this.extractRate = extractRate;
		return this;
	}

	public ItemElectric setReceiveRate(int receiveRate) {
		this.receiveRate = receiveRate;
		return this;
	}

	public ItemElectric setMaxTransfer(int maxTransfer) {
		setExtractRate(maxTransfer);
		setReceiveRate(maxTransfer);
		return this;
	}

}
