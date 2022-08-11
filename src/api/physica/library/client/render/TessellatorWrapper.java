package physica.library.client.render;

import net.minecraft.client.renderer.Tessellator;

public class TessellatorWrapper {

	public static TessellatorWrapper instance = new TessellatorWrapper();

	public Tessellator getTessellator() {
		return Tessellator.instance;
	}

	public void startDrawingQuads() {
		getTessellator().startDrawing(7);
	}

	public void startDrawing(int mode) {
		getTessellator().startDrawing(mode);
	}

	public void draw() {
		getTessellator().draw();
	}

	public void addVertexWithUV(double x, double y, double z, double u, double v) {
		getTessellator().setTextureUV(u, v);
		addVertex(x, y, z);
	}

	public void addVertex(double x, double y, double z) {
		getTessellator().addVertex(x, y, z);
	}

	public void setColorRGBA_I(int rgb, int a) {
		getTessellator().setColorRGBA_I(rgb, a);
	}

	public void setColorRGBA_F(float r, float g, float b, float a) {
		getTessellator().setColorRGBA_F(r, g, b, a);
	}

	public void setColorOpaque_I(int i) {
		getTessellator().setColorOpaque_I(i);
	}

	public void setColorOpaque(int i, int j, int k) {
		getTessellator().setColorOpaque(i, j, k);
	}

	public void setNormal(float x, float y, float z) {
		getTessellator().setNormal(x, y, z);
	}

	public void setTranslation(double x, double y, double z) {
		getTessellator().setTranslation(x, y, z);
	}

}
