package electrodynamics.common.item;

import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.common.electricity.network.ElectricNetwork;
import electrodynamics.common.tile.TileWire;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;

public class ItemMultimeter extends Item {

	public ItemMultimeter(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (!context.getWorld().isRemote) {
			TileEntity tile = context.getWorld().getTileEntity(context.getPos());
			if (tile instanceof TileWire) {
				TileWire wire = (TileWire) tile;
				ElectricNetwork net = wire.getNetwork();
				String finalString = ElectricityChatFormatter.getDisplay(net.getSavedAmpsTransmissionBuffer() / net.getLockedSavedVoltage() * 20, ElectricUnit.AMPERE).replace(" Amps", " Amp").replace(" Amp", "") + " / "
						+ ElectricityChatFormatter.getDisplay(net.getNetworkMaxTransfer(), ElectricUnit.AMPERE) + ", ";
				finalString += ElectricityChatFormatter.getDisplay(net.getLockedSavedVoltage(), ElectricUnit.VOLTAGE) + ", ";
				finalString += ElectricityChatFormatter.getDisplay(net.getSavedAmpsTransmissionBuffer() * 20.0, ElectricUnit.WATT) + ", ";
				finalString += ElectricityChatFormatter.getDisplay(net.getNetworkResistance() - 1, ElectricUnit.RESISTANCE) + " ( -" + (int) Math.round(100.0 - 100.0 / net.getNetworkResistance()) + "% )";
				context.getPlayer().sendStatusMessage(new StringTextComponent(finalString), true);
			}
		}
		return super.onItemUse(context);
	}
}
