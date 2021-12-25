package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceTriple;
import electrodynamics.common.tile.TileElectricFurnace;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentSlot;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.EnumSlotType;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenElectricFurnaceTriple extends GenericScreen<ContainerElectricFurnaceTriple> {

	public ScreenElectricFurnaceTriple(ContainerElectricFurnaceTriple container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentProgress(() -> {
			TileElectricFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(0);
				if (processor.operatingTicks > 0) {
					return processor.operatingTicks / processor.requiredTicks;
				}
			}
			return 0;
		}, this, 84, 24));
		components.add(new ScreenComponentProgress(() -> {
			TileElectricFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(0);
				if (processor.operatingTicks > 0) {
					return 1;
				}
			}
			return 0;
		}, this, 39, 26).flame());
		components.add(new ScreenComponentProgress(() -> {
			TileElectricFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(1);
				if (processor.operatingTicks > 0) {
					return processor.operatingTicks / processor.requiredTicks;
				}
			}
			return 0;
		}, this, 84, 44));
		components.add(new ScreenComponentProgress(() -> {
			TileElectricFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(1);
				if (processor.operatingTicks > 0) {
					return 1;
				}
			}
			return 0;
		}, this, 39, 46).flame());
		components.add(new ScreenComponentProgress(() -> {
			TileElectricFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(2);
				if (processor.operatingTicks > 0) {
					return processor.operatingTicks / processor.requiredTicks;
				}
			}
			return 0;
		}, this, 84, 64));
		components.add(new ScreenComponentProgress(() -> {
			TileElectricFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(2);
				if (processor.operatingTicks > 0) {
					return 1;
				}
			}
			return 0;
		}, this, 39, 66).flame());
		imageHeight += 20;
		inventoryLabelY += 20;
		components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	@Override
	protected ScreenComponentSlot createScreenSlot(Slot slot) {
		return new ScreenComponentSlot(slot instanceof SlotRestricted ? EnumSlotType.SPEED : EnumSlotType.NORMAL, this, slot.x - 1, slot.y - 1);
	}

	private List<? extends FormattedCharSequence> getEnergyInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileElectricFurnace box = menu.getHostFromIntArray();
		if (box != null) {
			ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);

			list.add(new TranslatableComponent("gui.o2oprocessor.usage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(
							box.getProcessor(0).getUsage() * 20 + box.getProcessor(1).getUsage() * 20 + box.getProcessor(2).getUsage() * 20,
							ElectricUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.o2oprocessor.voltage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
							.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}
}