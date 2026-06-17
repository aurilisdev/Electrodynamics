package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.fluid.types.FluidSulfate;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import voltaic.api.registration.BulkRegistryObject;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.common.fluid.SimpleWaterBasedFluidType;
import voltaic.prefab.utilities.math.Color;

public class ElectrodynamicsFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS,
	    Electrodynamics.ID);

    public static final RegistryObject<FluidNonPlaceable> FLUID_AMMONIA = FLUIDS.register("fluidammonia",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidammonia", "ammonia", Color.WHITE)));// new
														// Color(255,
														// 242,
														// 252,
														// 255)
    public static final RegistryObject<FluidNonPlaceable> FLUID_CLAY = FLUIDS.register("fluidclay",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidclay", "clay", Color.WHITE))); // new
													   // Color(105,
													   // 110, 121,
													   // 255)
    public static final RegistryObject<FluidNonPlaceable> FLUID_ETHANOL = FLUIDS.register("fluidethanol",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidethanol", "ethanol",
			    new Color(116, 121, 45, 230))));
    public static final RegistryObject<FluidNonPlaceable> FLUID_HYDRAULIC = FLUIDS.register("fluidhydraulic",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidhydraulic", "hydraulic", Color.WHITE))); // new
														     // Color(202,
														     // 191,
														     // 11,
														     // 255)
    public static final RegistryObject<FluidNonPlaceable> FLUID_HYDROFLUORICACID = FLUIDS.register(
	    "fluidhydrofluoricacid",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidhydrofluoricacid", "hydrofluoricacid",
			    new Color(152, 135, 0, 233))));
    public static final RegistryObject<FluidNonPlaceable> FLUID_HYDROGEN = FLUIDS.register("fluidhydrogen",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidhydrogen", "hydrogen", Color.WHITE)));
    public static final RegistryObject<FluidNonPlaceable> FLUID_OXYGEN = FLUIDS.register("fluidoxygen",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidoxygen", "oxygen",
			    new Color(139, 203, 239, 255))));
    public static final RegistryObject<FluidNonPlaceable> FLUID_POLYETHYLENE = FLUIDS.register("fluidpolyethylene",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidpolyethylene", "polyethylene",
			    new Color(140, 140, 140, 233))));
    public static final RegistryObject<FluidNonPlaceable> FLUID_SULFURICACID = FLUIDS.register("fluidsulfuricacid",
	    () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(),
		    new SimpleWaterBasedFluidType(Electrodynamics.ID, "fluidsulfuricacid", "sulfuricacid",
			    new Color(152, 135, 0, 233))));

    public static final BulkRegistryObject<FluidSulfate, SubtypeSulfateFluid> FLUIDS_SULFATE = new BulkRegistryObject<>(
	    SubtypeSulfateFluid.values(),
	    subtype -> FLUIDS.register("fluidsulfate" + subtype.name(),
		    () -> new FluidSulfate(subtype, new SimpleWaterBasedFluidType(Electrodynamics.ID,
			    "fluidsulfate" + subtype.name(), "sulfate/" + subtype.name(), Color.WHITE))));
}
