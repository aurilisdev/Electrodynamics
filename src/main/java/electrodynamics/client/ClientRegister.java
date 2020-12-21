package electrodynamics.client;

import electrodynamics.DeferredRegisters;
import electrodynamics.client.screen.ScreenBatteryBox;
import electrodynamics.client.screen.ScreenCoalGenerator;
import electrodynamics.client.screen.ScreenDO2OProcessor;
import electrodynamics.client.screen.ScreenElectricFurnace;
import electrodynamics.client.screen.ScreenO2OProcessor;
import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientRegister {

	public static void setup() {
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_O2OPROCESSOR.get(), ScreenO2OProcessor::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_DO2OPROCESSOR.get(), ScreenDO2OProcessor::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);

		RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgeneratorrunning), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill), RenderType.getCutout());

	}
}
