package electrodynamics.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentElectricInfo extends ScreenComponentInfo {
	private String tag = null;
	private Function<ComponentElectrodynamic, Double> wattage = null;

	public ScreenComponentElectricInfo(final TextPropertySupplier infoHandler, final IScreenWrapper gui, final int x, final int y) {
		super(infoHandler, new ResourceLocation(References.ID + ":textures/screen/component/electric.png"), gui, x, y);
	}

	public ScreenComponentElectricInfo(final IScreenWrapper gui, final int x, final int y) {
		super(null, new ResourceLocation(References.ID + ":textures/screen/component/electric.png"), gui, x, y);
		infoHandler = this::getElectricInformation;
	}

	public ScreenComponentElectricInfo tag(String val) {
		tag = val;
		return this;
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
		if (tag != null) {
			return getElectricInformation();
		}
		return list;
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list2 = new ArrayList<>();
		if (gui instanceof GenericScreen<?> menu) {
			if (((GenericContainerBlockEntity<?>)menu.getMenu()).getUnsafeHost() instanceof GenericTile tile) {
				if (tile.getComponent(ComponentType.Electrodynamic) instanceof ComponentElectrodynamic electro) {
					if (tile instanceof IElectricGenerator generator) {
						TransferPack transfer = generator.getProduced();
						list2.add(new TranslatableComponent("gui." + tag + ".current",
								new TextComponent(ChatFormatter.getElectricDisplayShort(transfer.getAmps(), ElectricUnit.AMPERE))
										.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list2.add(new TranslatableComponent("gui." + tag + ".output",
								new TextComponent(ChatFormatter.getElectricDisplayShort(transfer.getWatts(), ElectricUnit.WATT))
										.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						list2.add(new TranslatableComponent("gui." + tag + ".voltage",
								new TextComponent(ChatFormatter.getElectricDisplayShort(transfer.getVoltage(), ElectricUnit.VOLTAGE))
										.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					} else {
						if (wattage == null) {
							double usage = tile.getComponent(ComponentType.Processor) instanceof ComponentProcessor proc ? proc.getUsage() * 20 : 0;
							for (ComponentProcessor proc : tile.getProcessors()) {
								if (proc != null) {
									usage += proc.getUsage() * 20;
								}
							}
							list2.add(new TranslatableComponent("gui." + tag + ".usage",
									new TextComponent(ChatFormatter.getElectricDisplayShort(usage, ElectricUnit.WATT)).withStyle(ChatFormatting.GRAY))
											.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						} else {
							list2.add(new TranslatableComponent("gui." + tag + ".usage",
									new TextComponent(ChatFormatter.getElectricDisplayShort(wattage.apply(electro), ElectricUnit.WATT))
											.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
						}
						list2.add(new TranslatableComponent("gui." + tag + ".voltage",
								new TextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
										.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
					}
				}
			}
		}
		return list2;
	}
}