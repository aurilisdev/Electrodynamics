package electrodynamics.common.item.gear.tools;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.common.tile.electricitygrid.TileWire;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ItemMultimeter extends Item {

	public ItemMultimeter(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {

		if (context.getLevel().isClientSide) {
			return super.useOn(context);
		}

		TileEntity tile = context.getLevel().getBlockEntity(context.getClickedPos());
		if (tile instanceof TileWire) {
			TileWire wire = (TileWire) tile;
			ElectricNetwork net = wire.getNetwork();

			IFormattableTextComponent display = new StringTextComponent("");

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
			display.append(ChatFormatter.getChatDisplayShort(net.getResistance(), DisplayUnit.RESISTANCE).append(" ( -").append(ChatFormatter.getChatDisplayShort(Math.round(net.getLastEnergyLoss() / net.getActiveTransmitted() * 100.0), DisplayUnit.PERCENTAGE)).append(" ").append(ChatFormatter.getChatDisplayShort(net.getLastEnergyLoss() * 20, DisplayUnit.WATT).append(" )")));
			display.append(", ");

			// minimum voltage
			double minimumVoltage = net.getMinimumVoltage();
			if (minimumVoltage < 0) {
				minimumVoltage = net.getActiveVoltage();
			}
			display.append(ChatFormatter.getChatDisplayShort(minimumVoltage, DisplayUnit.VOLTAGE));

			context.getPlayer().displayClientMessage(display, true);
		}
		return super.useOn(context);
	}
}