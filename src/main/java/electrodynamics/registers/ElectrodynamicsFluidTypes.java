package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import voltaic.api.registration.BulkRegistryObject;

public class ElectrodynamicsFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister
	    .create(ForgeRegistries.Keys.FLUID_TYPES, Electrodynamics.ID);

    public static final RegistryObject<FluidType> FLUID_TYPE_AMMONIA = FLUID_TYPES.register("fluidammonia",
	    () -> ElectrodynamicsFluids.FLUID_AMMONIA.get().getFluidType());
    public static final RegistryObject<FluidType> FLUID_TYPE_CLAY = FLUID_TYPES.register("fluidclay",
	    () -> ElectrodynamicsFluids.FLUID_CLAY.get().getFluidType());
    public static final RegistryObject<FluidType> FLUID_TYPE_ETHANOL = FLUID_TYPES.register("fluidethanol",
	    () -> ElectrodynamicsFluids.FLUID_ETHANOL.get().getFluidType());
    public static final RegistryObject<FluidType> FLUID_TYPE_HYDRAULIC = FLUID_TYPES.register("fluidhydraulic",
	    () -> ElectrodynamicsFluids.FLUID_HYDRAULIC.get().getFluidType());
    public static final RegistryObject<FluidType> FLUID_TYPE_HYDROFLUORICACID = FLUID_TYPES
	    .register("fluidhydrofluoricacid", () -> ElectrodynamicsFluids.FLUID_HYDROFLUORICACID.get().getFluidType());
    public static final RegistryObject<FluidType> FLUID_TYPE_HYDROGEN = FLUID_TYPES.register("fluidhydrogen",
	    () -> ElectrodynamicsFluids.FLUID_HYDROGEN.get().getFluidType());
    public static final RegistryObject<FluidType> FLUID_TYPE_OXYGEN = FLUID_TYPES.register("fluidoxygen",
	    () -> ElectrodynamicsFluids.FLUID_OXYGEN.get().getFluidType());
    public static final RegistryObject<FluidType> FLUID_TYPE_POLYETHYLENE = FLUID_TYPES.register("fluidpolyethylene",
	    () -> ElectrodynamicsFluids.FLUID_POLYETHYLENE.get().getFluidType());
    public static final RegistryObject<FluidType> FLUID_TYPE_SULFURICACID = FLUID_TYPES.register("fluidsulfuricacid",
	    () -> ElectrodynamicsFluids.FLUID_SULFURICACID.get().getFluidType());

    public static final BulkRegistryObject<FluidType, SubtypeSulfateFluid> FLUID_TYPES_SULFATE = new BulkRegistryObject<>(
	    SubtypeSulfateFluid.values(), subtype -> FLUID_TYPES.register("fluidsulfate" + subtype.tag(),
		    () -> ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(subtype).getFluidType()));
}
