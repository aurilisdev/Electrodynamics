package physica.core.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.core.common.CoreTabRegister;
import physica.core.common.block.BlockEnergyCable.EnumEnergyCable;
import physica.core.common.network.EnergyTransferNetwork;
import physica.core.common.network.ITransferNode;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;

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
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof ITransferNode<?>)
			{
				EnergyTransferNetwork network = ((ITransferNode<?>) tile).getTransferNetwork();
				player.addChatMessage(new ChatComponentText("Network stats"));
				player.addChatMessage(new ChatComponentText(" - Last Active Receiver Count: " + network.receiverMap.size()));
				player.addChatMessage(new ChatComponentText(" - Network Nodes: " + network.transferNodeSet.size()));
				player.addChatMessage(new ChatComponentText(" - Current Transfer: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(network.lastEnergyBuffer, Unit.RF, Unit.WATT), Unit.WATT) + " / "
						+ ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(network.getVisualTransferRate(), Unit.RF, Unit.WATT), Unit.WATT)));
				player.addChatMessage(new ChatComponentText(
						" - Current Voltage: " + (network.lastEnergyBuffer / (network.getType().getTransferRate() / 120)) + " / " + (network.getType() == EnumEnergyCable.superConductor ? "infinite" : network.getType().getVoltage())));
				return true;
			}
		}
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
}
