package electrodynamics.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.inventory.container.ContainerElectricFurnaceTriple;
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
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenElectricFurnaceTriple extends GenericScreen<ContainerElectricFurnaceTriple> {

    public ScreenElectricFurnaceTriple(ContainerElectricFurnaceTriple container, PlayerInventory playerInventory, ITextComponent title) {
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
	ySize += 20;
	playerInventoryTitleY += 20;
	components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
    }

    @Override
    protected ScreenComponentSlot createScreenSlot(Slot slot) {
	return new ScreenComponentSlot(slot instanceof SlotRestricted ? EnumSlotType.SPEED : EnumSlotType.NORMAL, this, slot.xPos - 1, slot.yPos - 1);
    }

    private List<? extends ITextProperties> getEnergyInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	TileElectricFurnace box = container.getHostFromIntArray();
	if (box != null) {
	    ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);

	    list.add(new TranslationTextComponent("gui.o2oprocessor.usage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(
			    box.getProcessor(0).getUsage() * 20 + box.getProcessor(1).getUsage() * 20 + box.getProcessor(2).getUsage() * 20,
			    ElectricUnit.WATT)).mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.o2oprocessor.voltage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }
}