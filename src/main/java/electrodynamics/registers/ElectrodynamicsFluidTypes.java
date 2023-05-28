package electrodynamics.registers;

import electrodynamics.api.ISubtype;
import electrodynamics.api.References;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map.Entry;

import static electrodynamics.registers.ElectrodynamicsFluids.*;

public class ElectrodynamicsFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, References.ID);

    // liquids
    public static FluidType fluidTypeEthanol;
    public static FluidType fluidTypeSulfuricAcid;
    public static FluidType fluidTypeHydrogenFluoride;
    public static FluidType fluidTypePolyethylene;
    public static FluidType fluidTypeClay;
    public static FluidType fluidTypeHydraulic;
    //condensed gases
    public static FluidType fluiTypedOxygen;
    public static FluidType fluidTypeHydrogen;

    static {
        // Liquids
        FLUID_TYPES.register("fluidethanol", () -> fluidEthanol.getFluidType());
        FLUID_TYPES.register("fluidsulfuricacid", () -> fluidSulfuricAcid.getFluidType());
        FLUID_TYPES.register("fluidhydrogenfluoride", () -> fluidHydrogenFluoride.getFluidType());
        FLUID_TYPES.register("fluidpolyethylene", () -> fluidPolyethylene.getFluidType());
        FLUID_TYPES.register("fluidclay", () -> fluidClay.getFluidType());
        FLUID_TYPES.register("fluidhydraulic", () -> fluidHydraulic.getFluidType());
        for (Entry<ISubtype, RegistryObject<Fluid>> entry : SUBTYPEFLUID_REGISTRY_MAP.entrySet()) {
            FLUID_TYPES.register("fluidsulfate" + entry.getKey().tag(), () -> entry.getValue().get().getFluidType());
        }
        //condensed gases
        FLUID_TYPES.register("fluidoxygen", () -> fluidOxygen.getFluidType());
        FLUID_TYPES.register("fluidhydrogen", () -> fluidHydrogen.getFluidType());
    }
}
