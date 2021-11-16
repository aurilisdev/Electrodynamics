package electrodynamics.common.tags;

import electrodynamics.Electrodynamics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;

public class ElectrodynamicsTags {
	
	public static void init() {
		Fluids.init();
	}

	private static class Fluids {
		
		public static final Tags.IOptionalNamedTag<Fluid> SULFURIC_ACID = forgeTag("sulfuric_acid");
		
		private static void init() {}
		
		private static Tags.IOptionalNamedTag<Fluid> forgeTag(String name) {
			Electrodynamics.LOGGER.info(name);
            return FluidTags.createOptional(new ResourceLocation("forge", "fluids/" + name));
        }
	}
	
	
	
}
