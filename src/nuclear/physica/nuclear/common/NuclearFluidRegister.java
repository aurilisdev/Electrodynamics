package physica.nuclear.common;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent.Post;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import physica.CoreReferences;
import physica.api.core.IContent;
import physica.nuclear.NuclearReferences;

public class NuclearFluidRegister implements IContent {

	public static Fluid LIQUID_HE = new Fluid(NuclearReferences.PREFIX + "uranium_hexafluoride").setGaseous(true).setTemperature(35);

	@Override
	public void init()
	{
		FluidRegistry.registerFluid(LIQUID_HE);
		LIQUID_HE = FluidRegistry.getFluid(NuclearReferences.PREFIX + "uranium_hexafluoride");
	}

	public static void textureStitchEventPre(Pre event)
	{
		event.map.registerIcon(CoreReferences.PREFIX + "fluids/uranium_hexafluoride");
	}

	public static void textureStitchEventPost(Post event)
	{
		IIcon icon = event.map.getTextureExtry(CoreReferences.PREFIX + "fluids/uranium_hexafluoride");
		LIQUID_HE.setIcons(icon);
	}
}
