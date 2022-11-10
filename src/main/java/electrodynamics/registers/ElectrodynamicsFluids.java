package electrodynamics.registers;

import static electrodynamics.registers.ElectrodynamicsRegisters.supplier;

import java.util.HashMap;

import electrodynamics.api.References;
import electrodynamics.common.fluid.types.gas.FluidHydrogen;
import electrodynamics.common.fluid.types.gas.FluidOxygen;
import electrodynamics.common.fluid.types.liquid.FluidClay;
import electrodynamics.common.fluid.types.liquid.FluidConcrete;
import electrodynamics.common.fluid.types.liquid.FluidEthanol;
import electrodynamics.common.fluid.types.liquid.FluidHydraulic;
import electrodynamics.common.fluid.types.liquid.FluidHydrogenFluoride;
import electrodynamics.common.fluid.types.liquid.FluidPolyethylene;
import electrodynamics.common.fluid.types.liquid.FluidSulfate;
import electrodynamics.common.fluid.types.liquid.FluidSulfuricAcid;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsFluids {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);

	// Liquids
	public static FluidEthanol fluidEthanol;
	public static FluidSulfuricAcid fluidSulfuricAcid;
	public static FluidHydrogenFluoride fluidHydrogenFluoride;
	public static FluidPolyethylene fluidPolyethylene;
	public static FluidClay fluidClay;
	public static FluidConcrete fluidCement;
	public static FluidHydraulic fluidHydraulic;
	public static HashMap<SubtypeSulfateFluid, RegistryObject<Fluid>> mineralFluidMap = new HashMap<>();

	// Gasses
	public static FluidOxygen fluidOxygen;
	public static FluidHydrogen fluidHydrogen;

	static {
		// Liquids
		FLUIDS.register("fluidethanol", supplier(() -> fluidEthanol = new FluidEthanol()));
		FLUIDS.register("fluidsulfuricacid", supplier(() -> fluidSulfuricAcid = new FluidSulfuricAcid()));
		FLUIDS.register("fluidhydrogenfluoride", supplier(() -> fluidHydrogenFluoride = new FluidHydrogenFluoride()));
		FLUIDS.register("fluidpolyethylene", supplier(() -> fluidPolyethylene = new FluidPolyethylene()));
		FLUIDS.register("fluidclay", supplier(() -> fluidClay = new FluidClay()));
		FLUIDS.register("fluidconcrete", supplier(() -> fluidCement = new FluidConcrete()));
		FLUIDS.register("fluidhydraulic", supplier(() -> fluidHydraulic = new FluidHydraulic()));
		for (SubtypeSulfateFluid mineral : SubtypeSulfateFluid.values()) {
			mineralFluidMap.put(mineral, FLUIDS.register("fluidsulfate" + mineral.name(), supplier(() -> new FluidSulfate(mineral))));
		}
		// Gasses
		FLUIDS.register("fluidoxygen", supplier(() -> fluidOxygen = new FluidOxygen()));
		FLUIDS.register("fluidhydrogen", supplier(() -> fluidHydrogen = new FluidHydrogen()));

	}
}
