package electrodynamics.registers;

import java.util.function.Supplier;

import electrodynamics.Electrodynamics;
import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.registries.NewRegistryEvent;

//@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class ElectrodynamicsRegistries {

	/* GAS */
	
	
	//@SubscribeEvent
	public static void registerRegistries(NewRegistryEvent event) {
	    Electrodynamics.LOGGER.info("firing");
	    
	}

}
