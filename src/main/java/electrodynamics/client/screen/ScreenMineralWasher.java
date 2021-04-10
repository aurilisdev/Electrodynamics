package electrodynamics.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.api.gui.GenericScreen;
import electrodynamics.api.gui.component.GuiComponentElectricInfo;
import electrodynamics.api.gui.component.GuiComponentFluid;
import electrodynamics.api.gui.component.GuiComponentInfo;
import electrodynamics.api.gui.component.GuiComponentProgress;
import electrodynamics.api.gui.component.GuiComponentSlot;
import electrodynamics.api.gui.component.GuiComponentSlot.EnumSlotType;
import electrodynamics.api.tile.GenericTile;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentFluidHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.common.inventory.container.ContainerMineralWasher;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.tile.TileMineralWasher;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenMineralWasher extends GenericScreen<ContainerMineralWasher> {
    public ScreenMineralWasher(ContainerMineralWasher container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	components.add(new GuiComponentProgress(() -> {
	    GenericTile furnace = container.getHostFromIntArray();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
		if (processor.operatingTicks > 0) {
		    return processor.operatingTicks / processor.requiredTicks;
		}
	    }
	    return 0;
	}, this, 46, 30));
	components.add(new GuiComponentFluid(() -> {
	    TileMineralWasher boiler = container.getHostFromIntArray();
	    if (boiler != null) {
		ComponentFluidHandler handler = boiler.getComponent(ComponentType.FluidHandler);
		return handler.getTankFromFluid(DeferredRegisters.fluidSulfuricAcid);
	    }
	    return null;
	}, this, 21, 18));
	components.add(new GuiComponentElectricInfo(this::getEnergyInformation, this, -GuiComponentInfo.SIZE + 1, 2));
    }

    @Override
    protected GuiComponentSlot createGuiSlot(Slot slot) {
	return new GuiComponentSlot(slot instanceof SlotRestricted && ((SlotRestricted) slot)
		.isItemValid(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed)))
			? EnumSlotType.SPEED
			: slot instanceof SlotRestricted ? EnumSlotType.LIQUID : EnumSlotType.NORMAL,
		this, slot.xPos - 1, slot.yPos - 1);
    }

    private List<? extends ITextProperties> getEnergyInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	GenericTile box = container.getHostFromIntArray();
	if (box != null) {
	    ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = box.getComponent(ComponentType.Processor);

	    list.add(new TranslationTextComponent("gui.chemicalmixer.usage",
		    new StringTextComponent(ElectricityChatFormatter.getDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.chemicalmixer.voltage",
		    new StringTextComponent(ElectricityChatFormatter.getDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }

}