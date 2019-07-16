package physica.api.core.abstraction;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("deprecation")
public class AbstractionLayer {
	public static class Registering {
		public static Block registerBlock(Block block, String name)
		{
			return GameRegistry.registerBlock(block, name);
		}

		public static Block registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name)
		{
			return GameRegistry.registerBlock(block, itemclass, name);
		}

		public static void registerItem(Item item, String name)
		{
			GameRegistry.registerItem(item, name);
		}

		public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
		{
			GameRegistry.registerTileEntity(tileEntityClass, id);
		}

		public static void registerOre(String name, Block block)
		{
			OreDictionary.registerOre(name, block);
		}

		public static void registerOre(String name, Item item)
		{
			OreDictionary.registerOre(name, item);
		}

		public static void registerOre(String name, ItemStack itemStack)
		{
			OreDictionary.registerOre(name, itemStack);
		}
	}

	public static class Electricity {
		public static boolean isElectric(TileEntity tile)
		{
			return tile instanceof IEnergyConnection;
		}

		public static boolean isElectricReceiver(TileEntity tile)
		{
			return tile instanceof IEnergyReceiver;
		}

		public static boolean canConnectElectricity(TileEntity tile, ForgeDirection from)
		{
			if (isElectric(tile))
			{
				return ((IEnergyConnection) tile).canConnectEnergy(from);
			}
			return false;
		}

		public static int receiveElectricity(TileEntity tile, ForgeDirection from, int maxReceive, boolean simulate)
		{
			if (isElectricReceiver(tile))
			{
				if (canConnectElectricity(tile, from))
				{
					return ((IEnergyReceiver) tile).receiveEnergy(from, maxReceive, simulate);
				}
			}
			return 0;
		}

		public static boolean canInputElectricityNow(TileEntity tile, ForgeDirection from)
		{
			if (receiveElectricity(tile, from, Integer.MAX_VALUE, true) > 0)
			{
				return true;
			}
			if (isElectricReceiver(tile))
			{
				return ((IEnergyReceiver) tile).getEnergyStored(from) < ((IEnergyReceiver) tile).getMaxEnergyStored(from);
			}
			return false;
		}

		public static int getElectricityStored(TileEntity tile, ForgeDirection from)
		{
			if (isElectricReceiver(tile))
			{
				return ((IEnergyReceiver) tile).getEnergyStored(from);
			}
			return -1;
		}

		public static int getElectricCapacity(TileEntity tile, ForgeDirection from)
		{
			if (isElectricReceiver(tile))
			{
				return ((IEnergyReceiver) tile).getMaxEnergyStored(from);
			}
			return -1;
		}

		public static boolean isItemElectric(ItemStack stack)
		{
			return stack != null && stack.getItem() instanceof IEnergyContainerItem;
		}

		public static int getElectricityStored(ItemStack stack)
		{
			if (isItemElectric(stack))
			{
				return ((IEnergyContainerItem) stack.getItem()).getEnergyStored(stack);
			}
			return -1;
		}

		public static int getElectricCapacity(ItemStack stack)
		{
			if (isItemElectric(stack))
			{
				return ((IEnergyContainerItem) stack.getItem()).getMaxEnergyStored(stack);
			}
			return -1;
		}

		public static int extractElectricity(ItemStack stack, int maxExtract, boolean simulate)
		{
			if (isItemElectric(stack))
			{
				return ((IEnergyContainerItem) stack.getItem()).extractEnergy(stack, maxExtract, simulate);
			}
			return 0;
		}
	}
}
