package electrodynamics.common.item;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.common.tile.network.TileWire;
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
		String finalString = ChatFormatter
			.getElectricDisplay(net.getActiveVoltage() == 0 ? 0 : net.getActiveTransmitted() / net.getActiveVoltage() * 20,
				ElectricUnit.AMPERE)
			.replace(" Amps", "") + " / " + ChatFormatter.getElectricDisplay(net.networkMaxTransfer, ElectricUnit.AMPERE) + ", ";
		finalString += ChatFormatter.getElectricDisplay(net.getActiveVoltage(), ElectricUnit.VOLTAGE) + ", ";
		finalString += ChatFormatter.getElectricDisplay(net.getActiveTransmitted() * 20, ElectricUnit.WATT) + ", ";
		finalString += ChatFormatter.getElectricDisplay(net.getResistance(), ElectricUnit.RESISTANCE) + " ( -"
			+ Math.round(net.getLastEnergyLoss() / net.getActiveTransmitted() * 100) + "% "
			+ ChatFormatter.getElectricDisplay(net.getLastEnergyLoss() * 20, ElectricUnit.WATT) + " )";
		context.getPlayer().sendStatusMessage(new StringTextComponent(finalString), true);
	    }
	}
	return super.onItemUse(context);
    }
}