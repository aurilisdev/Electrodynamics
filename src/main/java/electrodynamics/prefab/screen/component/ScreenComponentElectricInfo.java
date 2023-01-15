package electrodynamics.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.IconType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentElectricInfo extends ScreenComponentGuiTab {
	
	private Function<ComponentElectrodynamic, Double> wattage = null;
	
	public ScreenComponentElectricInfo(final TextPropertySupplier infoHandler, final IScreenWrapper gui, final int x, final int y) {
		super(GuiInfoTabTextures.REGULAR, IconType.ENERGY_GREEN, infoHandler, gui, x, y);
	}

	public ScreenComponentElectricInfo(final IScreenWrapper gui, final int x, final int y) {
		this(ScreenComponentGuiTab.EMPTY, gui, x, y);
	}

	public ScreenComponentElectricInfo wattage(double wattage) {
		return wattage(e -> wattage);
	}

	public ScreenComponentElectricInfo wattage(Function<ComponentElectrodynamic, Double> wattage) {
		this.wattage = wattage;
		return this;
	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		if (infoHandler == EMPTY) {
			return getElectricInformation();
		}
		return super.getInfo(list);
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list2 = new ArrayList<>();
		if (gui instanceof GenericScreen<?> menu) {
			if (((GenericContainerBlockEntity<?>) menu.getMenu()).getUnsafeHost() instanceof GenericTile tile) {
				if (tile.getComponent(ComponentType.Electrodynamic) instanceof ComponentElectrodynamic electro) {
					if (tile instanceof IElectricGenerator generator) {
						TransferPack transfer = generator.getProduced();
						list2.add(TextUtils.gui("machine.current", Component.literal(ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list2.add(TextUtils.gui("machine.output", Component.literal(ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list2.add(TextUtils.gui("machine.voltage", Component.literal(ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					} else {
						if (wattage == null) {
							double usage = tile.getComponent(ComponentType.Processor) instanceof ComponentProcessor proc ? proc.getUsage() * 20 : 0;
							for (ComponentProcessor proc : tile.getProcessors()) {
								if (proc != null) {
									usage += proc.getUsage() * 20;
								}
							}
							list2.add(TextUtils.gui("machine.usage", Component.literal(ChatFormatter.getChatDisplayShort(usage, DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						} else {
							list2.add(TextUtils.gui("machine.usage", Component.literal(ChatFormatter.getChatDisplayShort(wattage.apply(electro), DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						}
						list2.add(TextUtils.gui("machine.voltage", Component.literal(ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					}
				}
			}
		}
		return list2;
	}
}