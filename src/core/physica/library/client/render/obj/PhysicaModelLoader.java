package physica.library.client.render.obj;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFormatException;
import physica.library.client.render.obj.model.WavefrontObject;

public class PhysicaModelLoader {

	public static WavefrontObject loadWavefrontModel(ResourceLocation resource) throws ModelFormatException
	{
		return new WavefrontObject(resource);
	}
}