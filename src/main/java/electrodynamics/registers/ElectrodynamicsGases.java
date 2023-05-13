package electrodynamics.registers;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.common.eventbus.RegisterFluidToGasMapEvent;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class ElectrodynamicsGases {
	
	//this will work until I can think of a more fool-proof solution
	//for now we can assume there aren't 5 types of oxygen gas
	public static final ConcurrentHashMap<Fluid, HashSet<Gas>> MAPPED_GASSES = new ConcurrentHashMap<>();
	
	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, References.ID);
	
	public static final RegistryObject<Gas> EMPTY = GASES.register("empty", () -> new Gas(() -> Items.AIR, null, TextUtils.gas("empty")));
	public static final RegistryObject<Gas> HYDROGEN = GASES.register("hydrogen", () -> new Gas(() -> Items.AIR, ElectrodynamicsTags.Gases.HYDROGEN, TextUtils.gas("hydrogen"), 33, ElectrodynamicsFluids.fluidHydrogen));
	public static final RegistryObject<Gas> OXYGEN = GASES.register("oxygen", () -> new Gas(() -> Items.AIR, ElectrodynamicsTags.Gases.OXYGEN, TextUtils.gas("oxygen"), 90, ElectrodynamicsFluids.fluidOxygen));
	
	@SubscribeEvent
	public static void registerFluidToGasPairs(RegisterFluidToGasMapEvent event) {
		
		event.add(ElectrodynamicsFluids.fluidHydrogen, HYDROGEN.get());
		event.add(ElectrodynamicsFluids.fluidOxygen, OXYGEN.get());
		
	}
	

}
