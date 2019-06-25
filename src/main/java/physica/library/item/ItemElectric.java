package physica.library.item;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;

public class ItemElectric extends Item implements IEnergyContainerItem {

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
		this.receiveRate = maxReceive;
		this.extractRate = maxExtract;
		setNoRepair();
		setMaxDamage(100);
		setMaxStackSize(1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, @SuppressWarnings("rawtypes") List list)
	{
		ItemStack disCharged = new ItemStack(this);
		setEnergyStored(disCharged, 0);
		list.add(disCharged);
		ItemStack charged = new ItemStack(this);
		setEnergyStored(charged, capacity);
		list.add(charged);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack container)
	{
		return 1.0 - getEnergyStored(container) / (double) getMaxEnergyStored(container);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List info, boolean par4)
	{
		super.addInformation(stack, player, info, par4);
		info.add(EnumChatFormatting.AQUA + "Energy Stored: " + EnumChatFormatting.GRAY
				+ ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(getEnergyStored(stack), Unit.RF, Unit.WATT), Unit.WATT));
	}

	@Override
	public int extractEnergy(ItemStack container, int amount, boolean simulate)
	{
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey(ENERGY_NBT_DATA))
		{
			return 0;
		}
		int energy = container.stackTagCompound.getInteger(ENERGY_NBT_DATA);
		int energyExtracted = Math.min(energy, Math.min(this.extractRate, amount));

		if (!simulate)
		{
			energy -= energyExtracted;
			setEnergyStored(container, energy);
		}
		return energyExtracted;
	}

	public void setEnergyStored(ItemStack container, int amount)
	{
		if (container.stackTagCompound == null)
		{
			container.stackTagCompound = new NBTTagCompound();
		}

		int energyStored = (int) Math.max(Math.min(amount, getMaxEnergyStored(container)), 0.0D);
		container.stackTagCompound.setInteger(ENERGY_NBT_DATA, energyStored);
		container.setItemDamage((int) Math.max(1, Math.abs(energyStored / (float) getMaxEnergyStored(container) * 100 - 100)));
	}

	@Override
	public int getEnergyStored(ItemStack container)
	{
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey(ENERGY_NBT_DATA))
		{
			return 0;
		}
		return container.stackTagCompound.getInteger(ENERGY_NBT_DATA);
	}

	@Override
	public int getMaxEnergyStored(ItemStack container)
	{
		return capacity;
	}

	@Override
	public int receiveEnergy(ItemStack container, int amount, boolean simulate)
	{
		if (container.stackTagCompound == null)
		{
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.stackTagCompound.getInteger(ENERGY_NBT_DATA);
		int energyReceived = Math.min(capacity - energy, Math.min(this.receiveRate, amount));

		if (!simulate)
		{
			energy += energyReceived;
			setEnergyStored(container, energy);
		}
		return energyReceived;
	}

	public ItemElectric setCapacity(int capacity)
	{
		this.capacity = capacity;
		return this;
	}

	public ItemElectric setExtractRate(int extractRate)
	{
		this.extractRate = extractRate;
		return this;
	}

	public ItemElectric setReceiveRate(int receiveRate)
	{
		this.receiveRate = receiveRate;
		return this;
	}

	public ItemElectric setMaxTransfer(int maxTransfer)
	{
		setExtractRate(maxTransfer);
		setReceiveRate(maxTransfer);
		return this;
	}

}
