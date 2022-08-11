package physica.library.client.render.obj;

import net.minecraft.util.ResourceLocation;
import physica.library.client.render.obj.model.WavefrontObject;

public class PhysicaModelLoader {

	public static WavefrontObject loadWavefrontModel(ResourceLocation resource) {
		try {
			return new WavefrontObject(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}