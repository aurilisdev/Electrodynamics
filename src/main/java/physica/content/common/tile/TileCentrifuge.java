package physica.content.common.tile;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import physica.api.base.IGuiInterface;
import physica.api.lib.tile.TileBasePoweredContainer;
import physica.content.client.gui.GuiCentrifuge;
import physica.content.common.FluidRegister;
import physica.content.common.ItemRegister;
import physica.content.common.inventory.ContainerCentrifuge;

public class TileCentrifuge extends TileBasePoweredContainer implements IGuiInterface {
	public static final int		TICKS_REQUIRED			= 800;
	public static final int		SLOT_ENERGY				= 0;
	public static final int		SLOT_OUTPUT1			= 1;
	public static final int		SLOT_OUTPUT2			= 2;
	private static final int[]	ACCESSIBLE_SLOTS_DOWN	= new int[] { SLOT_OUTPUT1, SLOT_OUTPUT2 };

	protected FluidTank			tank					= new FluidTank(new FluidStack(FluidRegister.LIQUID_HE, 0), 5000);

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
				} else
				{
				}
				for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
				{
					TileEntity tile = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
					if (tile instanceof TileChemicalBoiler)
					{
						TileChemicalBoiler boiler = (TileChemicalBoiler) tile;
						if (boiler.hexaTank.getFluidAmount() > 0)
						{
							tank.fill(boiler.hexaTank.drain(Math.min(10, boiler.hexaTank.getFluidAmount()), true), true);
						}
					}
				}
			} else
			{
				drainBattery(SLOT_ENERGY);
			}
		} else
		{
		}
	}

	private boolean canProcess() {
		if (getStackInSlot(SLOT_OUTPUT1) != null && getStackInSlot(SLOT_OUTPUT1).stackSize == getStackInSlot(SLOT_OUTPUT1).getMaxStackSize())
		{
			return false;
		} else if (getStackInSlot(SLOT_OUTPUT2) != null && getStackInSlot(SLOT_OUTPUT2).stackSize == getStackInSlot(SLOT_OUTPUT2).getMaxStackSize())
		{
			return false;
		}
		return tank.getFluidAmount() >= 2500;
	}

	private void process() {
		tank.drain(2500, true);
		boolean isEnriched = worldObj.rand.nextFloat() > 0.75f;
		int slot = isEnriched ? SLOT_OUTPUT1 : SLOT_OUTPUT2;
		ItemStack itemStack = getStackInSlot(slot);
		if (itemStack != null)
		{
			itemStack.stackSize++;
			setInventorySlotContents(slot, itemStack);
		} else
		{
			setInventorySlotContents(slot, new ItemStack(isEnriched ? ItemRegister.itemUranium235 : ItemRegister.itemUranium238));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagCompound compound = new NBTTagCompound();
		tank.writeToNBT(compound);
		nbt.setTag("Tank", compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagCompound compound = (NBTTagCompound) nbt.getTag("Tank");
		tank.readFromNBT(compound);
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
		dataList.add(tank.getFluidAmount());
	}

	@Override
	protected void readGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readGuiPacket(buf, player);
		operatingTicks = buf.readInt();
		tank.getFluid().amount = buf.readInt();
	}

	public int getOperatingTicks() {
		return operatingTicks;
	}

	public FluidTank getTank() {
		return tank;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack == null ? false : slot == SLOT_ENERGY ? (stack.getItem() instanceof IEnergyContainerItem) : slot == SLOT_OUTPUT1 || slot == SLOT_OUTPUT2 ? false : false;
	}

	@Override
	public GuiScreen getClientGuiElement(int id, EntityPlayer player) {
		return new GuiCentrifuge(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player) {
		return new ContainerCentrifuge(player, this);
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
		return 300;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == ForgeDirection.DOWN.ordinal() ? ACCESSIBLE_SLOTS_DOWN : ACCESSIBLE_SLOTS_NONE;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}

	public boolean isRotating() {
		return getOperatingTicks() > 0;
	}
}
