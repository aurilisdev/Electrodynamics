package electrodynamics.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidCement;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidClay;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidEthanol;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidHydraulic;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidHydrogen;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidHydrogenFluoride;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidOxygen;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidPolyethylene;
import static electrodynamics.registers.ElectrodynamicsFluids.fluidSulfuricAcid;
import static electrodynamics.registers.ElectrodynamicsFluids.mineralFluidMap;

import java.util.Map.Entry;

import electrodynamics.api.References;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsFluidTypes {
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, References.ID);

	// liquids
	public static FluidType fluidTypeEthanol;
	public static FluidType fluidTypeSulfuricAcid;
	public static FluidType fluidTypeHydrogenFluoride;
	public static FluidType fluidTypePolyethylene;
	public static FluidType fluidTypeClay;
	public static FluidType fluidTypeCement;
	public static FluidType fluidTypeHydraulic;
	// gasses

	public static FluidType fluiTypedOxygen;
	public static FluidType fluidTypeHydrogen;

	static {
		// Liquids
		FLUID_TYPES.register("fluidethanol", supplier(() -> fluidEthanol.getFluidType()));
		FLUID_TYPES.register("fluidsulfuricacid", supplier(() -> fluidSulfuricAcid.getFluidType()));
		FLUID_TYPES.register("fluidhydrogenfluoride", supplier(() -> fluidHydrogenFluoride.getFluidType()));
		FLUID_TYPES.register("fluidpolyethylene", supplier(() -> fluidPolyethylene.getFluidType()));
		FLUID_TYPES.register("fluidclay", supplier(() -> fluidClay.getFluidType()));
		FLUID_TYPES.register("fluidconcrete", supplier(() -> fluidCement.getFluidType()));
		FLUID_TYPES.register("fluidhydraulic", supplier(() -> fluidHydraulic.getFluidType()));
		for (Entry<SubtypeSulfateFluid, RegistryObject<Fluid>> entry : mineralFluidMap.entrySet()) {
			FLUID_TYPES.register("fluidsulfate" + entry.getKey().name(), supplier(() -> entry.getValue().get().getFluidType()));
		}
		// Gasses
		FLUID_TYPES.register("fluidoxygen", supplier(() -> fluidOxygen.getFluidType()));
		FLUID_TYPES.register("fluidhydrogen", supplier(() -> fluidHydrogen.getFluidType()));
	}
}
