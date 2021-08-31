package electrodynamics.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.inventory.container.ContainerFermentationPlant;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.tile.TileFermentationPlant;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentSlot;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.EnumSlotType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
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
public class ScreenFermentationPlant extends GenericScreen<ContainerFermentationPlant> {
    public ScreenFermentationPlant(ContainerFermentationPlant container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	components.add(new ScreenComponentProgress(() -> {
	    GenericTile furnace = container.getHostFromIntArray();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
		if (processor.operatingTicks > 0) {
		    return Math.min(1.0, processor.operatingTicks / (processor.requiredTicks / 2.0));
		}
	    }
	    return 0;
	}, this, 42, 30));
	components.add(new ScreenComponentProgress(() -> {
	    GenericTile furnace = container.getHostFromIntArray();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
		if (processor.operatingTicks > 17) {
		    return Math.min(1.0, (processor.operatingTicks - processor.requiredTicks / 2.0) / (processor.requiredTicks / 2.0));
		}
	    }
	    return 0;
	}, this, 98, 30));
	components.add(new ScreenComponentProgress(() -> 0, this, 42, 50).left());
	components.add(new ScreenComponentFluid(() -> {
	    TileFermentationPlant boiler = container.getHostFromIntArray();
	    if (boiler != null) {
		ComponentFluidHandler handler = boiler.getComponent(ComponentType.FluidHandler);
		for (Fluid fluid : handler.getValidInputFluids()) {
		    FluidTank tank = handler.getTankFromFluid(fluid, true);
		    if (tank.getFluidAmount() > 0) {
			return tank;
		    }
		}
	    }
	    return null;
	}, this, 21, 18));
	components.add(new ScreenComponentFluid(() -> {
	    TileFermentationPlant boiler = container.getHostFromIntArray();
	    if (boiler != null) {
		ComponentFluidHandler handler = boiler.getComponent(ComponentType.FluidHandler);
		for (Fluid fluid : handler.getValidOutputFluids()) {
		    FluidTank tank = handler.getTankFromFluid(fluid, false);
		    if (tank.getFluidAmount() > 0) {
			return tank;
		    }
		}
	    }
	    return null;
	}, this, 127, 18));

	components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
    }

    @Override
    protected ScreenComponentSlot createScreenSlot(Slot slot) {
	return new ScreenComponentSlot(slot instanceof SlotRestricted && ((SlotRestricted) slot)
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
	    list.add(new TranslationTextComponent("gui.fermentationplant.usage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.fermentationplant.voltage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }
}