package electrodynamics.common.item.gear.tools;

import java.util.function.Supplier;

import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.common.tile.electricitygrid.TileWire;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.common.item.ItemVoltaic;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class ItemMultimeter extends ItemVoltaic {

	public ItemMultimeter(Properties properties, Supplier<ItemGroup> creativeTab) {
		super(properties, creativeTab);
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
			display.append(VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(transferAmps, DisplayUnits.AMPERE), ChatFormatter.getChatDisplayShort(net.networkMaxTransfer, DisplayUnits.AMPERE)));
			display.append(", ");

			// active voltage
			display.append(ChatFormatter.getChatDisplayShort(net.getActiveVoltage(), DisplayUnits.VOLTAGE));
			display.append(", ");

			// active power
			display.append(ChatFormatter.getChatDisplayShort(net.getActiveTransmitted() * 20, DisplayUnits.WATT));
			display.append(", ");

			// resistance and energy loss
			display.append(ChatFormatter.getChatDisplayShort(net.getResistance(), DisplayUnits.RESISTANCE).append(" ( -").append(ChatFormatter.getChatDisplayShort(Math.round(net.getLastEnergyLoss() / net.getActiveTransmitted() * 100.0), DisplayUnits.PERCENTAGE)).append(" ").append(ChatFormatter.getChatDisplayShort(net.getLastEnergyLoss() * 20, DisplayUnits.WATT).append(" )")));
			display.append(", ");

			// minimum voltage
			double minimumVoltage = net.getMinimumVoltage();
			if (minimumVoltage < 0) {
				minimumVoltage = net.getActiveVoltage();
			}
			display.append(ChatFormatter.getChatDisplayShort(minimumVoltage, DisplayUnits.VOLTAGE));

			context.getPlayer().displayClientMessage(display, true);
		}
		return super.useOn(context);
	}
}