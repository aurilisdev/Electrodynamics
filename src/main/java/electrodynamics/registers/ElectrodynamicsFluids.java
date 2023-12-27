package electrodynamics.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;

import java.util.HashMap;

import electrodynamics.api.ISubtype;
import electrodynamics.api.References;
import electrodynamics.common.fluid.types.FluidEthanol;
import electrodynamics.common.fluid.types.FluidHydrogenFluoride;
import electrodynamics.common.fluid.types.FluidPolyethylene;
import electrodynamics.common.fluid.types.FluidSulfate;
import electrodynamics.common.fluid.types.FluidSulfuricAcid;
import electrodynamics.common.fluid.types.subtype.SubtypeSulfateFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElectrodynamicsFluids {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);

	public static final HashMap<ISubtype, RegistryObject<Fluid>> SUBTYPEFLUID_REGISTRY_MAP = new HashMap<>();

	// Liquids
	public static FluidEthanol fluidEthanol;
	public static FluidSulfuricAcid fluidSulfuricAcid;
	public static FluidHydrogenFluoride fluidHydrogenFluoride;
	public static FluidPolyethylene fluidPolyethylene;

	static {
		// Liquids
		FLUIDS.register("fluidethanol", supplier(() -> fluidEthanol = new FluidEthanol()));
		FLUIDS.register("fluidsulfuricacid", supplier(() -> fluidSulfuricAcid = new FluidSulfuricAcid()));
		FLUIDS.register("fluidhydrogenfluoride", supplier(() -> fluidHydrogenFluoride = new FluidHydrogenFluoride()));
		FLUIDS.register("fluidpolyethylene", supplier(() -> fluidPolyethylene = new FluidPolyethylene()));
		for (SubtypeSulfateFluid mineral : SubtypeSulfateFluid.values()) {
			SUBTYPEFLUID_REGISTRY_MAP.put(mineral, FLUIDS.register("fluidsulfate" + mineral.name(), supplier(() -> new FluidSulfate(mineral))));
		}

	}

	public static Fluid getFluid(ISubtype value) {
		return SUBTYPEFLUID_REGISTRY_MAP.get(value).get();
	}
}
