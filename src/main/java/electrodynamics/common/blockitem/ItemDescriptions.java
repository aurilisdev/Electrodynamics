package electrodynamics.common.blockitem;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeMachine;

public class ItemDescriptions {

	public static void setup() {

		// machines
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.electricfurnace), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.electricfurnacedouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.electricfurnacetriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.wiremill), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.wiremilldouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.wiremilltriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralcrusher), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralcrusherdouble), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralcrushertriple), "|translate|tooltip.machine.voltage.960");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralgrinder), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralgrinderdouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralgrindertriple), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.oxidationfurnace), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralwasher), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.chemicalmixer), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.chemicalcrystallizer), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.energizedalloyer), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.reinforcedalloyer), "|translate|tooltip.machine.voltage.960");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.lathe), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.chargerlv), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.chargermv), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.chargerhv), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.fermentationplant), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.electricpump), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.cobblestonegenerator), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.electrolyticseparator), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.electricarcfurnace), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.electricarcfurnacedouble), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.electricarcfurnacetriple), "|translate|tooltip.machine.voltage.480");

		// generators
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.solarpanel), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.advancedsolarpanel), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.thermoelectricgenerator), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.combustionchamber), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.hydroelectricgenerator), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.windmill), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.coalgenerator), "|translate|tooltip.machine.voltage.120");

		// misc
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.downgradetransformer), "|translate|tooltip.transformer.energyloss");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.upgradetransformer), "|translate|tooltip.transformer.energyloss");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.batterybox), "|translate|tooltip.machine.voltage.120");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.lithiumbatterybox), "|translate|tooltip.machine.voltage.240");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.carbynebatterybox), "|translate|tooltip.machine.voltage.480");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.creativepowersource), "|translate|tooltip.creativepowersource.joke");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.creativefluidsource), "|translate|tooltip.creativefluidsource.joke");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.fluidvoid), "|translate|tooltip.fluidvoid");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.tanksteel), "|translate|tooltip.tanksteel.capacity");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.tankreinforced), "|translate|tooltip.tankreinforced.capacity");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.tankhsla), "|translate|tooltip.tankhsla.capacity");
		BlockItemDescriptable.addDescription(DeferredRegisters.blockSeismicMarker, "|translate|tooltip.seismicmarker.redstone");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.seismicrelay), "|translate|tooltip.seismicrelay.use");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.coolantresavoir), "|translate|tooltip.coolantresavoir.place");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.motorcomplex), "|translate|tooltip.motorcomplex.use");
		BlockItemDescriptable.addDescription(DeferredRegisters.blockFrame, "|translate|tooltip.blockframe.joke");
		BlockItemDescriptable.addDescription(DeferredRegisters.blockFrameCorner, "|translate|tooltip.blockframe.joke");
		BlockItemDescriptable.addDescription(DeferredRegisters.getSafeBlock(SubtypeMachine.quarry), "|translate|tooltip.quarry.power");
		BlockItemDescriptable.addDescription(DeferredRegisters.blockLogisticalManager, "|translate|tooltip.logisticalmanager.use");
	}

}
