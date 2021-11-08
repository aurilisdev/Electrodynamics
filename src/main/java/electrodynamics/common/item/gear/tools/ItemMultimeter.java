package electrodynamics.common.item.gear.tools;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.common.tile.network.TileWire;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ItemMultimeter extends Item {

    public ItemMultimeter(Properties properties) {
	super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
	if (!context.getLevel().isClientSide) {
	    BlockEntity tile = context.getLevel().getBlockEntity(context.getClickedPos());
	    if (tile instanceof TileWire wire) {
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
		context.getPlayer().displayClientMessage(new TextComponent(finalString), true);
	    }
	}
	return super.useOn(context);
    }
}