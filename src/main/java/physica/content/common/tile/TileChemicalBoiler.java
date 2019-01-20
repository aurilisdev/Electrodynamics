package physica.content.common.tile;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.oredict.OreDictionary;
import physica.api.base.IGuiInterface;
import physica.api.lib.tile.TileBasePoweredContainer;
import physica.content.client.gui.GuiChemicalBoiler;
import physica.content.common.FluidRegister;
import physica.content.common.inventory.ContainerChemicalBoiler;

public class TileChemicalBoiler extends TileBasePoweredContainer implements IGuiInterface {
	public static final int		TICKS_REQUIRED			= 800;
	public static final int		SLOT_ENERGY				= 0;
	public static final int		SLOT_INPUT1				= 1;
	public static final int		SLOT_INPUT2				= 2;

	private static final int[]	ACCESSIBLE_SLOTS_UP		= new int[] { SLOT_INPUT1, SLOT_INPUT2 };
	private static final int[]	ACCESSIBLE_SLOTS_DOWN	= new int[] { SLOT_INPUT1 };

	protected FluidTank			waterTank				= new FluidTank(FluidRegistry.WATER, 0, 5000);
	protected FluidTank			hexaTank				= new FluidTank(FluidRegister.LIQUID_HE, 0, 5000);

	protected int				operatingTicks			= 0;

	@Override
	protected void update(int ticks) {
		super.update(ticks);
		if (isServer())
		{
			if (hasEnoughEnergy())
			{
				if (canProcess())
				{
					if (operatingTicks < TICKS_REQUIRED)
					{
						operatingTicks++;
					} else
					{
						process();
						operatingTicks = 0;
					}
					extractEnergy();
				}
			} else
			{
				drainBattery(SLOT_ENERGY);
			}
			if (getStackInSlot(SLOT_INPUT1) != null && getStackInSlot(SLOT_INPUT1).getItem() == Items.water_bucket && waterTank.getFluidAmount() < waterTank.getCapacity())
			{
				setInventorySlotContents(SLOT_INPUT1, new ItemStack(Items.bucket));
				waterTank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
			}
		} else
		{
		}
	}

	private boolean canProcess() {
		return waterTank.getFluidAmount() >= 1000 && OreDictionary.getOreName(OreDictionary.getOreID(getStackInSlot(SLOT_INPUT2))).equalsIgnoreCase("oreUranium");
	}

	private void process() {
		waterTank.drain(1000, true);
		decrStackSize(SLOT_INPUT2, 1);
		hexaTank.fill(new FluidStack(FluidRegister.LIQUID_HE, 1250), true);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagCompound waterCompound = new NBTTagCompound();
		waterTank.writeToNBT(waterCompound);
		NBTTagCompound hexaCompound = new NBTTagCompound();
		hexaTank.writeToNBT(hexaCompound);
		nbt.setTag("WaterTank", waterCompound);
		nbt.setTag("HexaTank", hexaCompound);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagCompound waterCompound = (NBTTagCompound) nbt.getTag("WaterTank");
		NBTTagCompound hexaCompound = (NBTTagCompound) nbt.getTag("HexaTank");
		waterTank.readFromNBT(waterCompound);
		hexaTank.readFromNBT(hexaCompound);
	}

	@Override
	protected void writeDescriptionPacket(List<Object> dataList, EntityPlayer player) {
		super.writeDescriptionPacket(dataList, player);
		writeGuiPacket(dataList, player);

	}

	@Override
	protected void readDescriptionPacket(ByteBuf buf, EntityPlayer player) {
		super.readDescriptionPacket(buf, player);
		readGuiPacket(buf, player);
	}

	@Override
	protected void writeGuiPacket(List<Object> dataList, EntityPlayer player) {
		super.writeGuiPacket(dataList, player);
		dataList.add(operatingTicks);
		dataList.add(waterTank.getFluidAmount());
		dataList.add(hexaTank.getFluidAmount());
	}

	@Override
	protected void readGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readGuiPacket(buf, player);
		operatingTicks = buf.readInt();
		waterTank.getFluid().amount = buf.readInt();
		hexaTank.getFluid().amount = buf.readInt();
	}

	public int getOperatingTicks() {
		return operatingTicks;
	}

	public FluidTank getWaterTank() {
		return waterTank;
	}

	public FluidTank getHexTank() {
		return hexaTank;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack == null ? false
				: slot == SLOT_ENERGY ? (stack.getItem() instanceof IEnergyContainerItem)
						: slot == SLOT_INPUT2 ? OreDictionary.getOreName(OreDictionary.getOreID(stack)).equalsIgnoreCase("oreUranium") : slot == SLOT_INPUT1 && stack.getItem() == Items.water_bucket;
	}

	@Override
	public GuiScreen getClientGuiElement(int id, EntityPlayer player) {
		return new GuiChemicalBoiler(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player) {
		return new ContainerChemicalBoiler(player, this);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return getEnergyUsage() * 2;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return !from.equals(getFacing().getOpposite());
	}

	@Override
	public int getEnergyUsage() {
		return 400;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == ForgeDirection.UP.ordinal() ? ACCESSIBLE_SLOTS_UP : side == ForgeDirection.DOWN.ordinal() ? ACCESSIBLE_SLOTS_DOWN : ACCESSIBLE_SLOTS_NONE;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == SLOT_INPUT1 && getStackInSlot(SLOT_INPUT1) != null && getStackInSlot(SLOT_INPUT1).getItem() == Items.bucket;
	}

	public boolean isRotating() {
		return getOperatingTicks() > 0;
	}
}
