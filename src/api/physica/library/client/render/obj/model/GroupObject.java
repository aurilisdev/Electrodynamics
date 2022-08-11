package physica.library.client.render.obj.model;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import physica.library.client.render.TessellatorWrapper;

@SideOnly(Side.CLIENT)
public class GroupObject {
	public String name;
	public ArrayList<ModelFace> faces = new ArrayList<>();
	public int glDrawingMode;

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

	public void render() {
		if (faces.size() > 0) {
			TessellatorWrapper tessellator = TessellatorWrapper.instance;
			tessellator.startDrawing(glDrawingMode);
			render(tessellator);
			tessellator.draw();
		}
	}

	public void render(TessellatorWrapper tessellator) {
		if (faces.size() > 0) {
			for (ModelFace face : faces) {
				face.addFaceForRender(tessellator);
			}
		}
	}
}