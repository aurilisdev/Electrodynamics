package physica.library.client.render.obj.model;

public class Vertex {
	public double x, y, z;

	public Vertex(double x, double y) {
		this(x, y, 0.0);
	}

	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}