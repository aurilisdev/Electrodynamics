package electrodynamics.prefab.screen.component.types.guitab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentElectricInfo extends ScreenComponentGuiTab {

	private Function<ComponentElectrodynamic, Double> wattage = null;

	public ScreenComponentElectricInfo(TextPropertySupplier infoHandler, int x, int y) {
		super(GuiInfoTabTextures.REGULAR, IconType.ENERGY_GREEN, infoHandler, x, y);
	}

	public ScreenComponentElectricInfo(int x, int y) {
		this(AbstractScreenComponentInfo.EMPTY, x, y);
	}

	public ScreenComponentElectricInfo wattage(double wattage) {
		return wattage(e -> wattage);
	}

	public ScreenComponentElectricInfo wattage(Function<ComponentElectrodynamic, Double> wattage) {
		this.wattage = wattage;
		return this;
	}

	@Override
	protected List<? extends IReorderingProcessor> getInfo(List<? extends IReorderingProcessor> list) {
		if (infoHandler == EMPTY) {
			return getElectricInformation();
		}
		return super.getInfo(list);
	}

	private List<? extends IReorderingProcessor> getElectricInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		if (gui instanceof GenericScreen<?>) {
			GenericScreen<?> menu = (GenericScreen<?>) gui;
			if (((GenericContainerBlockEntity<?>) menu.getMenu()).getUnsafeHost() instanceof GenericTile) {
				GenericTile tile = (GenericTile) ((GenericContainerBlockEntity<?>) menu.getMenu()).getUnsafeHost();
				if (tile.getComponent(IComponentType.Electrodynamic) instanceof ComponentElectrodynamic) {
					ComponentElectrodynamic electro = tile.getComponent(IComponentType.Electrodynamic);
					if (tile instanceof IElectricGenerator) {
						IElectricGenerator generator = (IElectricGenerator) tile;
						TransferPack transfer = generator.getProduced();
						list.add(ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnit.AMPERE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
						list.add(ElectroTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnit.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
						list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnit.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
					} else {
						if (wattage == null) {
							
							double usage = 0;
							
							if(tile.hasComponent(IComponentType.Processor)) {
								usage = tile.<ComponentProcessor>getComponent(IComponentType.Processor).getUsage();
							}
							
							for (ComponentProcessor proc : tile.getProcessors()) {
								if (proc != null) {
									usage += proc.getUsage() * 20;
								}
							}
							list.add(ElectroTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(usage, DisplayUnit.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
						} else {
							list.add(ElectroTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(wattage.apply(electro), DisplayUnit.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
						}
						list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
					}
				}
			}
		}
		return list;
	}
}