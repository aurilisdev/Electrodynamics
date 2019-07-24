package physica.library.client.render.obj.model;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;

public class GroupObject {
	public String			name;
	public ArrayList<ModelFace>	faces	= new ArrayList<>();
	public int				glDrawingMode;

	public GroupObject() {
		this("");
	}

	public GroupObject(String name) {
		this(name, -1);
	}

	public GroupObject(String name, int glDrawingMode) {
		this.name = name;
		this.glDrawingMode = glDrawingMode;
	}

	@SideOnly(Side.CLIENT)
	public void render()
	{
		if (faces.size() > 0)
		{
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawing(glDrawingMode);
			render(tessellator);
			tessellator.draw();
		}
	}

	@SideOnly(Side.CLIENT)
	public void render(Tessellator tessellator)
	{
		if (faces.size() > 0)
		{
			for (ModelFace face : faces)
			{
				face.addFaceForRender(tessellator);
			}
		}
	}
}