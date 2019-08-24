package physica.nuclear.common;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent.Post;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.nuclear.NuclearReferences;

public class NuclearFluidRegister implements IContent {

	public static Fluid LIQUID_HE = new Fluid(NuclearReferences.PREFIX + "uranium_hexafluoride").setGaseous(true).setTemperature(35);

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.FluidRegister)
		{
			FluidRegistry.registerFluid(LIQUID_HE);
			LIQUID_HE = FluidRegistry.getFluid(NuclearReferences.PREFIX + "uranium_hexafluoride");
		}
	}

	public static void textureStitchEventPre(Pre event)
	{
		event.map.registerIcon(NuclearReferences.PREFIX + "fluids/uranium_hexafluoride");
	}

	public static void textureStitchEventPost(Post event)
	{
		IIcon icon = event.map.getTextureExtry(NuclearReferences.PREFIX + "fluids/uranium_hexafluoride");
		LIQUID_HE.setIcons(icon);
	}
}
