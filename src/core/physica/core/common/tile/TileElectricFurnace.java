package physica.core.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.oredict.OreDictionary;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.Face;
import physica.api.core.inventory.IGuiInterface;
import physica.api.core.tile.IMachineTile;
import physica.core.client.gui.GuiElectricFurnace;
import physica.core.common.block.BlockElectricFurnace.EnumElectricFurnace;
import physica.core.common.inventory.ContainerElectricFurnace;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.location.Location;
import physica.library.tile.TileBasePoweredContainer;

public class TileElectricFurnace extends TileBasePoweredContainer implements IGuiInterface, IMachineTile {

	public static final int		TICKS_REQUIRED			= 100;
	public static final int		POWER_USAGE				= ElectricityUtilities.convertEnergy(1000, Unit.WATT, Unit.RF);
	public static final int		SLOT_ENERGY				= 0;
	public static final int		SLOT_INPUT				= 1;
	public static final int		SLOT_OUTPUT				= 2;
	private static final int[]	ACCESSIBLE_SLOTS_DOWN	= new int[] { SLOT_OUTPUT };
	private static final int[]	ACCESSIBLE_SLOTS_UP		= new int[] { SLOT_INPUT };

	protected int				operatingTicks			= 0;

	@Override
	public boolean isRunning()
	{
		return operatingTicks > 0;
	}

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (hasEnoughEnergy())
		{
			ItemStack output = getStackInSlot(SLOT_OUTPUT);
			ItemStack input = getStackInSlot(SLOT_INPUT);
			if (canProcess(output, input))
			{
				if (operatingTicks < TICKS_REQUIRED)
				{
					operatingTicks++;
				} else
				{
					process(input, output);
					operatingTicks = 0;
				}
				extractEnergy();
			} else
			{
				operatingTicks = 0;
			}
			drainBattery(SLOT_ENERGY);
		} else
		{
			drainBattery(SLOT_ENERGY);
		}
	}

	public boolean canProcess(ItemStack output, ItemStack input)
	{
		if (input == null)
		{
			return false;
		}
		if (FurnaceRecipes.smelting().getSmeltingResult(input) == null)
		{
			return false;
		}
		if (output != null)
		{
			if (!output.isItemEqual(FurnaceRecipes.smelting().getSmeltingResult(input)))
			{
				return false;
			}
			if (output.stackSize + 2 > 64)
			{
				return false;
			}
		}
		return true;
	}

	private void process(ItemStack input, ItemStack output)
	{
		ItemStack resultItemStack = FurnaceRecipes.smelting().getSmeltingResult(input);
		int stackSize = 1;
		if (getBlockMetadata() == EnumElectricFurnace.INDUSTRIAL.ordinal())
		{
			boolean isOre = false;
			for (int id : OreDictionary.getOreIDs(input))
			{
				if (OreDictionary.getOreName(id).startsWith("ore"))
				{
					isOre = true;
					break;
				}
			}
			if (isOre)
			{
				for (int id : OreDictionary.getOreIDs(resultItemStack))
				{
					if (OreDictionary.getOreName(id).startsWith("ingot"))
					{
						stackSize = 2;
						break;
					}
				}
			}
		}
		if (output == null)
		{
			ItemStack finalStack = resultItemStack.copy();
			finalStack.stackSize = stackSize;
			setInventorySlotContents(SLOT_OUTPUT, finalStack);
		} else if (output.isItemEqual(resultItemStack))
		{
			output.stackSize += stackSize;
		}
		input.stackSize--;
		if (input.stackSize <= 0)
		{
			setInventorySlotContents(SLOT_INPUT, null);
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
	public int getSizeInventory()
	{
		return 3;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return slot == 1 ? FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null : slot == 0 ? AbstractionLayer.Electricity.isItemElectric(itemStack) : false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiElectricFurnace(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerElectricFurnace(player, this);
	}

	@Override
	public int getElectricityUsage()
	{
		return (int) (POWER_USAGE * (Math.pow((getBlockMetadata() + 1), 2)));
	}

	@Override
	public int[] getAccessibleSlotsFromFace(Face face)
	{
		return face == Face.DOWN ? ACCESSIBLE_SLOTS_DOWN : face == Face.UP ? ACCESSIBLE_SLOTS_UP : ACCESSIBLE_SLOTS_NONE;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, Face face)
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, Face face)
	{
		return slot == SLOT_OUTPUT;
	}

}
