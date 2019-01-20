package physica.content.common.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.base.IGuiInterface;
import physica.api.lib.tile.TileBasePoweredContainer;
import physica.content.client.gui.GuiAssembler;
import physica.content.common.ItemRegister;
import physica.content.common.inventory.ContainerAssembler;

public class TileAssembler extends TileBasePoweredContainer implements IGuiInterface {
	public static final int		TICKS_REQUIRED			= 1200;
	public static final int		SLOT_OUTPUT				= 6;
	private static final int[]	ACCESSIBLE_SLOTS_UP		= new int[] { SLOT_OUTPUT };
	private static final int[]	ACCESSIBLE_SLOTS_ELSE	= new int[] { 0, 1, 2, 3, 4, 5 };
	protected int				operatingTicks			= 0;
	protected EntityItem		entityItem				= null;

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return slot == 6 ? !isRestricted(itemStack) : itemStack.getItem() == ItemRegister.itemDarkmatterContainer;
	}

	public boolean canProcess() {
		ItemStack itemStack = getStackInSlot(6);
		if (isRestricted(itemStack)) return false;
		for (int i = 0; i <= 5; i++)
		{
			ItemStack itemStackInSlot = getStackInSlot(i);
			if (itemStackInSlot == null) return false;
			if (itemStackInSlot.getItem() != ItemRegister.itemDarkmatterContainer) return false;
		}
		return itemStack.stackSize < 64;
	}

	public boolean isRestricted(ItemStack itemStack) {
		return itemStack == null || itemStack.stackSize <= 0 || !itemStack.isStackable() || itemStack.hasTagCompound();
	}

	@Override
	protected void update(int ticks) {
		super.update(ticks);
		if (isServer())
		{
			if (canProcess() && hasEnoughEnergy())
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
			} else if (getStackInSlot(SLOT_OUTPUT) == null || !canProcess())
			{
				operatingTicks = 0;
			}
		} else
		{
			ItemStack itemStack = getStackInSlot(SLOT_OUTPUT);

			if (itemStack != null)
			{
				if (entityItem == null || !itemStack.isItemEqual(entityItem.getEntityItem()))
				{
					entityItem = getEntityForItem(itemStack);
				}
			} else
			{
				entityItem = null;
			}
		}
	}

	private EntityItem getEntityForItem(ItemStack itemStack) {
		EntityItem entityItem = new EntityItem(worldObj, 0, 0, 0, itemStack.copy());
		entityItem.setAgeToCreativeDespawnTime();

		return entityItem;
	}

	private void process() {
		for (int i = 0; i <= 5; i++)
		{
			ItemStack itemStackInSlot = getStackInSlot(i);
			itemStackInSlot.setItemDamage(itemStackInSlot.getItemDamage() + 1);
			if (itemStackInSlot.getItemDamage() >= itemStackInSlot.getMaxDamage())
			{
				decrStackSize(i, 1);
			}
		}
		ItemStack itemStack = getStackInSlot(SLOT_OUTPUT);
		if (itemStack != null)
		{
			itemStack.stackSize++;
		}
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
	}

	@Override
	protected void readGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readGuiPacket(buf, player);
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
		return from.equals(ForgeDirection.DOWN);
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
		return getStackInSlot(slot) != null && getStackInSlot(slot).stackSize > 1;
	}

	@Override
	public int getSizeInventory() {
		return 7;
	}

	@Override
	public GuiScreen getClientGuiElement(int id, EntityPlayer player) {
		return new GuiAssembler(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player) {
		return new ContainerAssembler(player, this);
	}

	@Override
	public int getEnergyUsage() {
		return 48600;
	}
}
