package electrodynamics.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.ISubtype;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.gui.GenericScreen;
import electrodynamics.api.gui.component.GuiComponentElectricInfo;
import electrodynamics.api.gui.component.GuiComponentFluid;
import electrodynamics.api.gui.component.GuiComponentInfo;
import electrodynamics.api.gui.component.GuiComponentProgress;
import electrodynamics.api.gui.component.GuiComponentSlot;
import electrodynamics.api.gui.component.GuiComponentSlot.EnumSlotType;
import electrodynamics.common.fluid.FluidMineral;
import electrodynamics.common.inventory.container.ContainerChemicalCrystallizer;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalCrystallizer extends GenericScreen<ContainerChemicalCrystallizer> {

    public ScreenChemicalCrystallizer(ContainerChemicalCrystallizer container, PlayerInventory playerInventory, ITextComponent title) {
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
	}, this, 41, 34));
	components.add(new GuiComponentFluid(() -> {
	    TileChemicalCrystallizer boiler = container.getHostFromIntArray();
	    if (boiler != null) {
		ComponentFluidHandler handler = boiler.getComponent(ComponentType.FluidHandler);
		for (Entry<FluidMineral, ISubtype> mineral : DeferredRegisters.MINERALFLUIDSUBTYPE_MAPPINGS.entrySet()) {
		    FluidTank tank = handler.getTankFromFluid(mineral.getKey());
		    if (tank != null && !tank.getFluid().isEmpty()) {
			return tank;
		    }
		}
		return handler.getTankFromFluid(Fluids.WATER);
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
			: EnumSlotType.NORMAL,
		this, slot.xPos - 1, slot.yPos - 1);
    }

    private List<? extends ITextProperties> getEnergyInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	GenericTile box = container.getHostFromIntArray();
	if (box != null) {
	    ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = box.getComponent(ComponentType.Processor);

	    list.add(new TranslationTextComponent("gui.chemicalcrystallizer.usage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.chemicalcrystallizer.voltage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }
}