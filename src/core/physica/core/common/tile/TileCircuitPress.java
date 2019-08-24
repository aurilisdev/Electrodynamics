package physica.core.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumSkyBlock;
import physica.api.core.abstraction.Face;
import physica.api.core.inventory.IGuiInterface;
import physica.api.core.tile.IMachineTile;
import physica.core.client.gui.GuiCircuitPress;
import physica.core.common.CoreItemRegister;
import physica.core.common.inventory.ContainerCircuitPress;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.location.Location;
import physica.library.tile.TileBasePoweredContainer;
import physica.library.util.OreDictionaryUtilities;

public class TileCircuitPress extends TileBasePoweredContainer implements IGuiInterface, IMachineTile {

	public static final int		SLOT_INPUT				= 0;
	public static final int		SLOT_INPUT2				= 1;
	public static final int		SLOT_OUTPUT				= 2;

	private static final int[]	ACCESSIBLE_SLOTS_TOP	= new int[] { SLOT_INPUT };
	private static final int[]	ACCESSIBLE_SLOTS_DOWN	= new int[] { SLOT_OUTPUT };
	private static final int[]	ACCESSIBLE_SLOTS_SIDES	= new int[] { SLOT_INPUT2 };

	public static final int		REQUIRED_TICKS			= 80;
	public static final int		POWER_USAGE				= ElectricityUtilities.convertEnergy(1000, Unit.WATT, Unit.RF);

	protected int				operatingTicks			= 0;

	@Override
	public boolean isRunning()
	{
		return operatingTicks > 0;
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
		int prevOperatingTicks = operatingTicks;
		operatingTicks = buf.readInt();
		if (prevOperatingTicks == 0 && operatingTicks > 0 || prevOperatingTicks > 0 && operatingTicks == 0)
		{
			Location loc = getLocation();
			World().updateLightByType(EnumSkyBlock.Block, loc.xCoord, loc.yCoord, loc.zCoord);
		}
	}

	public int getOperatingTicks()
	{
		return operatingTicks;
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (hasEnoughEnergy())
		{
			ItemStack output = getStackInSlot(SLOT_OUTPUT);
			ItemStack input = getStackInSlot(SLOT_INPUT);
			ItemStack input2 = getStackInSlot(SLOT_INPUT2);
			if (canProcess(output, input, input2))
			{
				if (operatingTicks < REQUIRED_TICKS)
				{
					operatingTicks++;
				} else
				{
					process(input, input2, output);
					operatingTicks = 0;
				}
				extractEnergy();
			} else
			{
				operatingTicks = 0;
			}
		}
	}

	public boolean canProcess(ItemStack output, ItemStack input, ItemStack input2)
	{
		if (input == null || input2 == null || output != null && output.stackSize >= output.getMaxStackSize())
		{
			return false;
		}
		if (output != null)
		{
			if (OreDictionaryUtilities.isSameOre(output, "circuitBasic"))
			{
				return OreDictionaryUtilities.isSameOre(input, "plateSteel") && input2.getItem() == Items.redstone;
			} else if (OreDictionaryUtilities.isSameOre(output, "circuitAdvanced"))
			{
				return OreDictionaryUtilities.isSameOre(input, "circuitBasic") && input2.getItem() == Items.gold_ingot;
			} else if (OreDictionaryUtilities.isSameOre(output, "circuitElite"))
			{
				return OreDictionaryUtilities.isSameOre(input, "circuitAdvanced") && input2.getItem() == Items.diamond;
			}
		} else
		{
			if (OreDictionaryUtilities.isSameOre(input, "plateSteel"))
			{
				return input2.getItem() == Items.redstone;
			} else if (OreDictionaryUtilities.isSameOre(input, "circuitBasic"))
			{
				return input2.getItem() == Items.gold_ingot;
			} else if (OreDictionaryUtilities.isSameOre(input, "circuitAdvanced"))
			{
				return input2.getItem() == Items.diamond;
			}
		}
		return true;
	}

	private void process(ItemStack input, ItemStack input2, ItemStack output)
	{
		if (output == null)
		{
			if (OreDictionaryUtilities.isSameOre(input, "plateSteel"))
			{
				if (input2.getItem() == Items.redstone)
				{
					output = new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0);
				}
			} else if (OreDictionaryUtilities.isSameOre(input, "circuitBasic"))
			{
				if (input2.getItem() == Items.gold_ingot)
				{
					output = new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1);
				}
			} else if (OreDictionaryUtilities.isSameOre(input, "circuitAdvanced"))
			{
				if (input2.getItem() == Items.diamond)
				{
					output = new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 2);
				}
			}
		} else
		{
			output.stackSize++;
		}
		setInventorySlotContents(SLOT_OUTPUT, output);
		input.stackSize--;
		if (input.stackSize <= 0)
		{
			setInventorySlotContents(SLOT_INPUT, null);
		}
		input2.stackSize--;
		if (input2.stackSize <= 0)
		{
			setInventorySlotContents(SLOT_INPUT2, null);
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (slot == SLOT_OUTPUT || stack == null)
		{
			return false;
		} else if (slot == SLOT_INPUT)
		{
			return stack.getItem() == CoreItemRegister.itemMetaCircuit || OreDictionaryUtilities.isSameOre(stack, "plateSteel");
		} else if (slot == SLOT_INPUT2)
		{
			Item item = stack.getItem();
			return item == Items.redstone || item == Items.gold_ingot || item == Items.diamond;
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromFace(Face face)
	{
		return face == Face.DOWN ? ACCESSIBLE_SLOTS_DOWN : face == Face.UP ? ACCESSIBLE_SLOTS_TOP : ACCESSIBLE_SLOTS_SIDES;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, Face face)
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, Face face)
	{
		return true;
	}

	@Override
	public int getPowerUsage()
	{
		return POWER_USAGE;
	}

	@Override
	public int getSizeInventory()
	{
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiCircuitPress(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerCircuitPress(player, this);
	}
}
