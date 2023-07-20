package electrodynamics.registers;

import java.util.function.Supplier;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ElectrodynamicsRegistries {

	/* GAS */
	public static final ResourceLocation GAS_REGISTRY_LOC = new ResourceLocation(References.ID, "gases");
	public static final ResourceKey<Registry<Gas>> GAS_REGISTRY_KEY = ResourceKey.createRegistryKey(GAS_REGISTRY_LOC);
	private static Supplier<IForgeRegistry<Gas>> GAS_REGISTRY_SUPPLIER;

	public static void init() {
		GAS_REGISTRY_SUPPLIER = ElectrodynamicsGases.GASES.makeRegistry(() -> new RegistryBuilder<Gas>().setName(GAS_REGISTRY_LOC).hasTags());
	}

	public static IForgeRegistry<Gas> gasRegistry() {
		return GAS_REGISTRY_SUPPLIER.get();
	}

}
