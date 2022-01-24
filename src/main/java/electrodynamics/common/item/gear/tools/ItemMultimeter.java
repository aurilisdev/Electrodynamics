package electrodynamics.common.item.gear.tools;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
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
				String finalString = ChatFormatter.getChatDisplay(net.getActiveVoltage() == 0 ? 0 : net.getActiveTransmitted() * 20 / net.getActiveVoltage(), DisplayUnit.AMPERE).replace(" Amps", "") + " / " + ChatFormatter.getChatDisplay(net.networkMaxTransfer, DisplayUnit.AMPERE) + ", ";
				finalString += ChatFormatter.getChatDisplay(net.getActiveVoltage(), DisplayUnit.VOLTAGE) + ", ";
				finalString += ChatFormatter.getChatDisplay(net.getActiveTransmitted() * 20, DisplayUnit.WATT) + ", ";
				finalString += ChatFormatter.getChatDisplay(net.getResistance(), DisplayUnit.RESISTANCE) + " ( -" + Math.round(net.getLastEnergyLoss() / net.getActiveTransmitted() * 100) + "% " + ChatFormatter.getChatDisplay(net.getLastEnergyLoss() * 20, DisplayUnit.WATT) + " )";
				context.getPlayer().displayClientMessage(new TextComponent(finalString), true);
			}
		}
		return super.useOn(context);
	}
}