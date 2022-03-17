package electrodynamics.client.guidebook.utils;

public class ImageWrapperObject {

	public int xOffset;
	public int yOffet;
	public int uStart;
	public int vStart;
	public int width;
	public int height;
	public int imgwidth;
	public int imgheight;
	public String location;
	
	public ImageWrapperObject(int xOffset, int yOffset, int uStart, int vStart, int width, int height, int imgheight, int imgwidth, String location) {
		this.xOffset = xOffset;
		this.yOffet = yOffset;
		this.uStart = uStart;
		this.vStart = vStart;
		this.width = width;
		this.height = height;
		this.imgwidth = imgwidth;
		this.imgheight = imgheight;
		this.location = location;
	}
	
}
