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

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class ElectrodynamicsRegistries {

	/* GAS */
	public static final ResourceLocation GAS_REGISTRY_LOC = new ResourceLocation(References.ID, "gases");
	public static final ResourceKey<Registry<Gas>> GAS_REGISTRY_KEY = ResourceKey.createRegistryKey(GAS_REGISTRY_LOC);
	public static Supplier<Registry<Gas>> GAS_REGISTRY;

	public static void init() {
	    GAS_REGISTRY = ElectrodynamicsGases.GASES.getRegistry();
	}
	
	@SubscribeEvent
	public static void registerRegistries(NewRegistryEvent event) {
	    Electrodynamics.LOGGER.info("firing");
	    
	}

}
