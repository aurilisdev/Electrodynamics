package electrodynamics.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.inventory.container.ContainerChemicalMixer;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.tile.TileChemicalMixer;
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
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalMixer extends GenericScreen<ContainerChemicalMixer> {
    public ScreenChemicalMixer(ContainerChemicalMixer container, Inventory playerInventory, Component title) {
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
		if (processor.operatingTicks > processor.requiredTicks / 2.0) {
		    return Math.min(1.0, (processor.operatingTicks - processor.requiredTicks / 2.0) / (processor.requiredTicks / 2.0));
		}
	    }
	    return 0;
	}, this, 98, 30));
	components.add(new ScreenComponentProgress(() -> 0, this, 42, 50).left());
	components.add(new ScreenComponentFluid(() -> {
	    TileChemicalMixer boiler = container.getHostFromIntArray();
	    if (boiler != null) {
		AbstractFluidHandler<?> handler = boiler.getComponent(ComponentType.FluidHandler);
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
	    TileChemicalMixer boiler = container.getHostFromIntArray();
	    if (boiler != null) {
		AbstractFluidHandler<?> handler = boiler.getComponent(ComponentType.FluidHandler);
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
		.mayPlace(new ItemStack(electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed)))
			? EnumSlotType.SPEED
			: slot instanceof SlotRestricted ? EnumSlotType.LIQUID : EnumSlotType.NORMAL,
		this, slot.x - 1, slot.y - 1);
    }

    private List<? extends FormattedText> getEnergyInformation() {
	ArrayList<FormattedText> list = new ArrayList<>();
	GenericTile box = menu.getHostFromIntArray();
	if (box != null) {
	    ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = box.getComponent(ComponentType.Processor);

	    list.add(new TranslatableComponent("gui.chemicalmixer.usage",
		    new TextComponent(ChatFormatter.getElectricDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT))
			    .withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
	    list.add(new TranslatableComponent("gui.chemicalmixer.voltage",
		    new TextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
			    .withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
	}
	return list;
    }
}