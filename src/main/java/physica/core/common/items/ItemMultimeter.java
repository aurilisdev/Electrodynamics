package physica.core.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.core.common.CoreTabRegister;

public class ItemMultimeter extends Item {

	public ItemMultimeter() {
		setUnlocalizedName("multimeter");
		setTextureName(CoreReferences.PREFIX + "multimeter");
		setMaxStackSize(1);
		setCreativeTab(CoreTabRegister.coreTab);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		// if (world.isRemote)
		// {
		// TileEntity tile = world.getTileEntity(x, y, z);
		// if (tile instanceof ITransferNode<?>)
		// {
		// ITransferNode<?> node = (ITransferNode<?>) tile;
		// ChatUtils.addSpamlessMessages(Integer.MAX_VALUE - 50, "Network stats", " -
		// Network Nodes: " + node.getClientNetworkSize(),
		// " - Power Transfer: " +
		// ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(node.getClientNetworkBuffer(),
		// Unit.RF, Unit.WATT), Unit.WATT) + " / "
		// +
		// ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(node.getClientMaxTransferRate(),
		// Unit.RF, Unit.WATT), Unit.WATT),
		// " - Current Voltage: " + (node.getClientNetworkBuffer() /
		// (node.getClientNetworkType().getTransferRate() / 120)) + " / "
		// + (node.getClientNetworkType() == EnumEnergyCable.superConductor ? "infinite"
		// : node.getClientNetworkType().getVoltage()));
		// return true;
		// }
		// }
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
}
