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
import physica.api.core.abstraction.Face;
import physica.api.core.inventory.IGuiInterface;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.tile.TileBasePoweredContainer;
import physica.nuclear.client.gui.GuiQuantumAssembler;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.inventory.ContainerQuantumAssembler;

public class TileQuantumAssembler extends TileBasePoweredContainer implements IGuiInterface {

	public static final int		TICKS_REQUIRED			= ConfigNuclearPhysics.QUANTUM_ASSEMBLER_TICKS_REQUIRED;
	public static final int		SLOT_INPUT				= 6;
	public static final int		SLOT_OUTPUT				= 7;
	public static final int		POWER_USAGE				= ElectricityUtilities.convertEnergy(71000, Unit.WATT, Unit.RF);

	private static final int[]	ACCESSIBLE_SLOTS_UP		= new int[] { SLOT_INPUT };
	private static final int[]	ACCESSIBLE_SLOTS_DOWN	= new int[] { SLOT_OUTPUT };
	private static final int[]	ACCESSIBLE_SLOTS_ELSE	= new int[] { 0, 1, 2, 3, 4, 5 };
	protected int				operatingTicks			= 0;
	protected EntityItem		entityItem				= null;

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return slot == SLOT_INPUT ? !isRestricted(itemStack) : slot == SLOT_OUTPUT ? false : itemStack.getItem() == NuclearItemRegister.itemDarkmatterCell;
	}

	public boolean canProcess()
	{
		ItemStack itemStack = getStackInSlot(SLOT_INPUT);
		if (isRestricted(itemStack))
		{
			return false;
		}
		for (int i = 0; i <= 5; i++)
		{
			ItemStack itemStackInSlot = getStackInSlot(i);
			if (itemStackInSlot == null)
			{
				return false;
			}
			if (itemStackInSlot.getItem() != NuclearItemRegister.itemDarkmatterCell)
			{
				return false;
			}
		}
		ItemStack output = getStackInSlot(SLOT_OUTPUT);
		return itemStack != null && output != null ? output.stackSize < output.getMaxStackSize() : true;
	}

	public boolean isRestricted(ItemStack itemStack)
	{
		return itemStack == null || itemStack.stackSize <= 0 || itemStack.getItem() == NuclearItemRegister.itemDarkmatterCell
				|| (ConfigNuclearPhysics.FLIP_BLACKLIST_TO_WHITELIST ? !ConfigNuclearPhysics.QUANTUM_ASSEMBLER_BLACKLIST.contains(itemStack.getUnlocalizedName())
						: ConfigNuclearPhysics.QUANTUM_ASSEMBLER_BLACKLIST.contains(itemStack.getUnlocalizedName()));
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
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
		} else if (getStackInSlot(SLOT_INPUT) == null || !canProcess())
		{
			operatingTicks = 0;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateClient(int ticks)
	{
		super.updateClient(ticks);
		ItemStack itemStack = getStackInSlot(SLOT_INPUT);

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

	private EntityItem getEntityForItem(ItemStack itemStack)
	{
		EntityItem entityItem = new EntityItem(World(), 0, 0, 0, itemStack.copy());
		entityItem.setAgeToCreativeDespawnTime();

		return entityItem;
	}

	private void process()
	{
		for (int i = 0; i <= 5; i++)
		{
			ItemStack itemStackInSlot = getStackInSlot(i);
			itemStackInSlot.setItemDamage(itemStackInSlot.getItemDamage() + 1);
			if (itemStackInSlot.getItemDamage() >= itemStackInSlot.getMaxDamage())
			{
				decrStackSize(i, 1);
			}
		}
		ItemStack output = getStackInSlot(SLOT_OUTPUT);
		ItemStack input = getStackInSlot(SLOT_INPUT);
		if (output != null)
		{
			if (input != null && input.hasTagCompound())
			{
				ItemStack clone = input.copy();
				clone.stackTagCompound = null;
				if (ItemStack.areItemStacksEqual(clone, output))
				{
					output.stackSize++;
				}
			}
		} else if (input != null)
		{
			ItemStack clone = input.copy();
			clone.stackTagCompound = null;
			setInventorySlotContents(SLOT_OUTPUT, clone);
		}
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeClientGuiPacket(dataList, player);
		dataList.add(operatingTicks);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readClientGuiPacket(buf, player);
		operatingTicks = buf.readInt();
	}

	public EntityItem getEntityItem()
	{
		return entityItem;
	}

	public int getOperatingTicks()
	{
		return operatingTicks;
	}

	@Override
	public boolean canConnectElectricity(Face from)
	{
		return from.equals(Face.DOWN) || from.equals(Face.UP);
	}

	@Override
	public int[] getAccessibleSlotsFromFace(Face face)
	{
		return face == Face.UP ? ACCESSIBLE_SLOTS_UP : face != Face.DOWN ? ACCESSIBLE_SLOTS_ELSE : ACCESSIBLE_SLOTS_DOWN;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, Face face)
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, Face face)
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public int getSizeInventory()
	{
		return 8;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiQuantumAssembler(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerQuantumAssembler(player, this);
	}

	@Override
	public int getPowerUsage()
	{
		return POWER_USAGE;
	}
}
