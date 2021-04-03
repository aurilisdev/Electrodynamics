package electrodynamics.common.item;

import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.common.tile.network.TileWire;
import improvedapi.core.capability.CapabilityNetworkProvider;
import improvedapi.core.electricity.ElectricityNetwork;
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
		String finalString = ElectricityChatFormatter
			.getDisplay(net.getLastVoltage() == 0 ? 0 : net.getCurrentTransmission() / net.getLastVoltage() * 20, ElectricUnit.AMPERE)
			.replace(" Amps", "") + " / " + ElectricityChatFormatter.getDisplay(net.networkMaxTransfer, ElectricUnit.AMPERE) + ", ";
		finalString += ElectricityChatFormatter.getDisplay(net.getLastVoltage(), ElectricUnit.VOLTAGE) + ", ";
		finalString += ElectricityChatFormatter.getDisplay(net.getCurrentTransmission() * 20, ElectricUnit.WATT) + ", ";
		finalString += ElectricityChatFormatter.getDisplay(net.getResistance(), ElectricUnit.RESISTANCE) + " ( -"
			+ Math.round(net.getLastEnergyLoss() / net.getCurrentTransmission() * 100) + "% "
			+ ElectricityChatFormatter.getDisplay(net.getLastEnergyLoss() * 20, ElectricUnit.WATT) + " )";
		context.getPlayer().sendStatusMessage(new StringTextComponent(finalString), true);
	    } else {
		tile.getCapability(CapabilityNetworkProvider.INSTANCE).ifPresent(consumer -> {
		    if (consumer.getNetwork() instanceof ElectricityNetwork) {
			ElectricityNetwork net = (ElectricityNetwork) consumer.getNetwork();
			context.getPlayer().sendStatusMessage(new StringTextComponent("resistance:" + net.getTotalResistance()), true);
		    }
		});
	    }
	}
	return super.onItemUse(context);
    }
}