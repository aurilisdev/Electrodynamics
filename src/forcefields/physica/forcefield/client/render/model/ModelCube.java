package physica.forcefield.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class ModelCube extends ModelBase {

	public static final ModelCube INSTANCE = new ModelCube();

	private ModelRenderer cube;

	public ModelCube() {
		cube = new ModelRenderer(this);
		int size = 16;
		cube.addBox(-size / 2, -size / 2, -size / 2, size, size, size);
		cube.setTextureSize(112, 70);
		cube.mirror = true;
	}

	public void render() {
		float f = 0.0625F;
		cube.render(f);
	}
}
