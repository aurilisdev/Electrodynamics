package electrodynamics.registers;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class ElectrodynamicsGases {

	public static final ResourceKey<Registry<Gas>> GAS_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(References.ID, "gases"));
	public static final IForgeRegistry<Gas> GASES = RegistryManager.ACTIVE.getRegistry(GAS_REGISTRY_KEY);
	public static final DeferredRegister<Gas> GAS_REGISTER = DeferredRegister.create(GAS_REGISTRY_KEY, References.ID);
	
	

}
