package electrodynamics.common.item.gear.tools;

import java.util.function.Supplier;

import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.common.tile.electricitygrid.TileWire;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.common.item.ItemVoltaic;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class ItemMultimeter extends ItemVoltaic {

	public ItemMultimeter(Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(properties, creativeTab);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {

		if (context.getLevel().isClientSide) {
			return super.useOn(context);
		}

		BlockEntity tile = context.getLevel().getBlockEntity(context.getClickedPos());
		if (tile instanceof TileWire wire) {
			ElectricNetwork net = wire.getNetwork();

			MutableComponent display = new TextComponent("");

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