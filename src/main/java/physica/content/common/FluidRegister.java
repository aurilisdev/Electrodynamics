package physica.content.common;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent.Post;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import physica.References;
import physica.api.base.IProxyContent;

public class FluidRegister implements IProxyContent {

	public static Fluid LIQUID_HE = new Fluid(References.PREFIX + "uranium_hexafluoride").setGaseous(true).setTemperature(35);

	@Override
	public void init() {
		FluidRegistry.registerFluid(LIQUID_HE);
		LIQUID_HE = FluidRegistry.getFluid(References.PREFIX + "uranium_hexafluoride");
	}

	public static void textureStitchEventPre(Pre event) {
		event.map.registerIcon(References.PREFIX + "fluids/uranium_hexafluoride");
	}

	public static void textureStitchEventPost(Post event) {
		IIcon icon = event.map.getTextureExtry(References.PREFIX + "fluids/uranium_hexafluoride");
		LIQUID_HE.setIcons(icon);
	}
}
