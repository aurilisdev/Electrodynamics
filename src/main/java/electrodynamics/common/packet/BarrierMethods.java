package electrodynamics.common.packet;

import java.util.HashMap;
import java.util.HashSet;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.client.Minecraft;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

/**
 * Apparently with packets, certain class calls cannot be called within the packet itself because Java
 * 
 * SoundInstance for example is an exclusively client class only
 * 
 * Place methods that need to use those here
 */
public class BarrierMethods {

	public static void handlerClientCombustionFuels(HashSet<CombustionFuelSource> fuels) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level != null && minecraft.player != null) {
			CombustionFuelRegister.INSTANCE.setClientValues(fuels);
		}
	}

	public static void handlerClientCoalGenFuels(HashSet<Item> fuels) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level != null && minecraft.player != null) {
			CoalGeneratorFuelRegister.INSTANCE.setClientValues(fuels);
		}
	}
	
	public static void handlerSetGuidebookInitFlag() {
		ScreenGuidebook.setInitNotHappened();
	}
	
	public static void handlerClientThermoGenHeatSources(HashMap<Fluid, Double> heatSources) {
		Minecraft minecraft = Minecraft.getInstance();
		if(minecraft.level != null && minecraft.player != null) {
			ThermoelectricGeneratorHeatRegister.INSTANCE.setClientValues(heatSources);
		}
	}

}