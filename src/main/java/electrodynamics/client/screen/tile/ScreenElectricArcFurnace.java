package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.ContainerElectricArcFurnace;
import electrodynamics.common.tile.TileElectricArcFurnace;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentSlot;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.EnumSlotType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenElectricArcFurnace extends GenericScreen<ContainerElectricArcFurnace> {

	public ScreenElectricArcFurnace(ContainerElectricArcFurnace container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentProgress(() -> {
			TileElectricArcFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(0);
				if (processor.operatingTicks > 0) {
					return processor.operatingTicks / processor.requiredTicks;
				}
			}
			return 0;
		}, this, 84, 34));
		components.add(new ScreenComponentProgress(() -> {
			TileElectricArcFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(0);
				if (processor.operatingTicks > 0) {
					return 1;
				}
			}
			return 0;
		}, this, 39, 36).flame());
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2).tag("o2oprocessor"));
	}

	@Override
	protected ScreenComponentSlot createScreenSlot(Slot slot) {
		return new ScreenComponentSlot(slot instanceof SlotRestricted ? EnumSlotType.SPEED : EnumSlotType.NORMAL, this, slot.x - 1, slot.y - 1);
	}

}