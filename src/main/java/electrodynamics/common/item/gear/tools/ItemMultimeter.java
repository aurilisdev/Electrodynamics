package electrodynamics.common.item.gear.tools;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.common.tile.network.electric.TileWire;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

		if (context.getLevel().isClientSide) {
			return super.useOn(context);
		}

		BlockEntity tile = context.getLevel().getBlockEntity(context.getClickedPos());
		if (tile instanceof TileWire wire) {
			ElectricNetwork net = wire.getNetwork();

			MutableComponent display = Component.empty();

			// active current to max current ratio
			double transferAmps = net.getActiveVoltage() == 0 ? 0 : net.getActiveVoltage() == 0 ? 0 : net.getActiveTransmitted() * 20 / net.getActiveVoltage();
			display.append(ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(transferAmps, DisplayUnit.AMPERE), ChatFormatter.getChatDisplayShort(net.networkMaxTransfer, DisplayUnit.AMPERE)));
			display.append(", ");

			// active voltage
			display.append(ChatFormatter.getChatDisplayShort(net.getActiveVoltage(), DisplayUnit.VOLTAGE));
			display.append(", ");

			// active power
			display.append(ChatFormatter.getChatDisplayShort(net.getActiveTransmitted() * 20, DisplayUnit.WATT));
			display.append(", ");

			// resistance and energy loss
			display.append(ChatFormatter.getChatDisplayShort(net.getResistance(), DisplayUnit.RESISTANCE).append(" ( -").append(ChatFormatter.getChatDisplayShort(Math.round(net.getLastEnergyLoss() / net.getActiveTransmitted() * 100), DisplayUnit.PERCENTAGE)).append(ChatFormatter.getChatDisplayShort(net.getLastEnergyLoss() * 20, DisplayUnit.WATT).append(" )")));
			display.append(", ");

			// minimum voltage
			display.append(ChatFormatter.getChatDisplayShort(net.getMinimumVoltage(), DisplayUnit.VOLTAGE));

			/*
			 * String finalString = ChatFormatter.getChatDisplayShort(net.getActiveVoltage() == 0 ? 0 : net.getActiveTransmitted() * 20 / net.getActiveVoltage(), DisplayUnit.AMPERE) + " / " + ChatFormatter.getChatDisplayShort(net.networkMaxTransfer, DisplayUnit.AMPERE) + ", "; finalString += ChatFormatter.getChatDisplayShort(net.getActiveVoltage(), DisplayUnit.VOLTAGE) + ", "; finalString += ChatFormatter.getChatDisplayShort(net.getActiveTransmitted() * 20, DisplayUnit.WATT) + ", "; finalString += ChatFormatter.getChatDisplayShort(net.getResistance(), DisplayUnit.RESISTANCE) + " ( -" + Math.round(net.getLastEnergyLoss() / net.getActiveTransmitted() * 100) + "% " + ChatFormatter.getChatDisplayShort(net.getLastEnergyLoss() * 20, DisplayUnit.WATT) + " ), "; finalString += ChatFormatter.getChatDisplayShort(net.getMinimumVoltage(), DisplayUnit.VOLTAGE);
			 */

			context.getPlayer().displayClientMessage(display, true);
		}
		return super.useOn(context);
	}
}