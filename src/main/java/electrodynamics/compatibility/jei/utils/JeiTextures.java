package electrodynamics.compatibility.jei.utils;

import electrodynamics.api.References;
import electrodynamics.api.screen.ITexture;
import net.minecraft.resources.ResourceLocation;

public enum JeiTextures implements ITexture {
	
	BACKGROUND_DEFAULT(0, 0, 512, 512, 512, 512, new ResourceLocation(References.ID, "textures/screen/jei/background.png")),
	FLUID_GAUGE_DEFAULT(0, 0, 14, 49, 14, 49, new ResourceLocation(References.ID, "textures/screen/jei/fluid_gauge_default.png"));
	
	private final int u;
	private final int v;
	private final int width;
	private final int height;
	private final int imageWidth;
	private final int imageHeight;
	private final ResourceLocation loc;
	
	private JeiTextures(int u, int v, int width, int height, int imageWidth, int imageHeight, ResourceLocation loc) {
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.loc = loc;
	}
	
	@Override
	public int textureWidth() {
		return width;
	}

	@Override
	public int textureHeight() {
		return height;
	}

	@Override
	public int textureU() {
		return u;
	}

	@Override
	public int textureV() {
		return v;
	}

	@Override
	public int imageWidth() {
		return imageHeight;
	}

	@Override
	public int imageHeight() {
		return imageWidth;
	}

	@Override
	public ResourceLocation getLocation() {
		return loc;
	}

}
