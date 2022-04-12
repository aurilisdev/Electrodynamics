package electrodynamics.prefab.utilities.tile;

import java.util.HashMap;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class CombustionFuelSource {

	public static HashMap<TagKey<Fluid>, CombustionFuelSource> FUELS = new HashMap<>();

	private int fluidUsage;
	private int powerMultiplier;

	private CombustionFuelSource(int usageAmount, int powerMultiplier) {
		fluidUsage = usageAmount;
		this.powerMultiplier = powerMultiplier;
	}

	public int getFluidUsage() {
		return fluidUsage;
	}

	public int getPowerMultiplier() {
		return powerMultiplier;
	}

	public static CombustionFuelSource getSourceFromFluid(Fluid fluid) {
		for (TagKey<Fluid> fluidTag : FUELS.keySet()) {
			if (ForgeRegistries.FLUIDS.tags().getTag(fluidTag).contains(fluid)) {
				return FUELS.get(fluidTag);
			}
		}
		return new CombustionFuelSource(0, 0);
	}

	public static void addFuelSource(TagKey<Fluid> fluidTag, int usageAmount, int powerMultiplier) {
		FUELS.put(fluidTag, new CombustionFuelSource(usageAmount, powerMultiplier));
	}

}
