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
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.IGuiInterface;
import physica.library.tile.TileBaseContainer;
import physica.nuclear.client.gui.GuiNeutronCaptureChamber;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.inventory.ContainerNeutronCaptureChamber;

public class TileNeutronCaptureChamber extends TileBaseContainer implements IGuiInterface {

	public static final int TICKS_REQUIRED = 2847;
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_OUTPUT = 1;
	private static final int[] ACCESSIBLE_SLOTS_UP = new int[] { SLOT_INPUT };
	private static final int[] ACCESSIBLE_SLOTS_DOWN = new int[] { SLOT_OUTPUT };

	protected float operatingTicks = 0;
	private boolean hasDeuterium;

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (canProcess()) {
			hasDeuterium = true;
			if (operatingTicks < TICKS_REQUIRED) {
				operatingTicks += getTicksAddition();
			} else {
				process();
				operatingTicks = 0;
			}
		} else {
			hasDeuterium = false;
			operatingTicks = 0;
		}
	}

	private float getTicksAddition()
	{
		ForgeDirection facing = getFacing().getOpposite();
		TileFissionReactor reactor = (TileFissionReactor) worldObj.getTileEntity(xCoord + facing.offsetX, yCoord + facing.offsetY, zCoord + facing.offsetZ);
		return reactor.temperature / TICKS_REQUIRED;
	}

	public boolean canProcess()
	{
		ForgeDirection facing = getFacing().getOpposite();
		TileEntity tile = worldObj.getTileEntity(xCoord + facing.offsetX, yCoord + facing.offsetY, zCoord + facing.offsetZ);
		if (tile instanceof TileFissionReactor) {
			if (getStackInSlot(SLOT_INPUT) == null || getStackInSlot(SLOT_OUTPUT) != null && getStackInSlot(SLOT_OUTPUT).stackSize >= getInventoryStackLimit()) {
				return false;
			}
			TileFissionReactor reactor = (TileFissionReactor) tile;
			return reactor.hasFuelRod() && reactor.temperature > 1000;
		} else {
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, new ItemStack(getBlockType())));
			getLocation().setBlockAir(worldObj);
			return false;
		}
	}

	private void process()
	{
		ItemStack output = getStackInSlot(SLOT_OUTPUT);
		getStackInSlot(SLOT_INPUT).stackSize--;
		if (getStackInSlot(SLOT_INPUT).stackSize <= 0) {
			setInventorySlotContents(SLOT_INPUT, null);
		}
		if (output != null) {
			output.stackSize++;
		} else {
			setInventorySlotContents(SLOT_OUTPUT, new ItemStack(NuclearItemRegister.itemTritiumCell));
		}
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeClientGuiPacket(dataList, player);
		dataList.add(operatingTicks);
		dataList.add(hasDeuterium);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readClientGuiPacket(buf, player);
		operatingTicks = buf.readFloat();
		hasDeuterium = buf.readBoolean();
	}

	public float getOperatingTicks()
	{
		return operatingTicks;
	}

	@Override
	public int getSizeInventory()
	{
		return 2;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return slot == SLOT_INPUT ? stack != null && stack.getItem() == NuclearItemRegister.itemDeuteriumCell : false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiNeutronCaptureChamber(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerNeutronCaptureChamber(player, this);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return side == ForgeDirection.DOWN.ordinal() ? ACCESSIBLE_SLOTS_DOWN : side == ForgeDirection.UP.ordinal() ? ACCESSIBLE_SLOTS_UP : ACCESSIBLE_SLOTS_DOWN;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return true;
	}

	public boolean hasDeuterium()
	{
		return hasDeuterium;
	}
}
