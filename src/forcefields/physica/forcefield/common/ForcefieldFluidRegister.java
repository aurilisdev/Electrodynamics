package physica.forcefield.common;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent.Post;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.forcefield.ForcefieldReferences;

public class ForcefieldFluidRegister implements IContent {

	public static Fluid LIQUID_FORTRON = new Fluid(ForcefieldReferences.PREFIX + "fortron").setGaseous(true).setTemperature(20);

	@Override
	public void register(LoadPhase phase) {
		if (phase == LoadPhase.FluidRegister) {
			FluidRegistry.registerFluid(LIQUID_FORTRON);
			LIQUID_FORTRON = FluidRegistry.getFluid(ForcefieldReferences.PREFIX + "fortron");
		}
	}

	public static void textureStitchEventPre(Pre event) {
		event.map.registerIcon(ForcefieldReferences.PREFIX + "fluids/fortron");
	}

	public static void textureStitchEventPost(Post event) {
		IIcon icon = event.map.getTextureExtry(ForcefieldReferences.PREFIX + "fluids/fortron");
		LIQUID_FORTRON.setIcons(icon);
	}

}
