package rayTracing;

public class Ray {
	private Vector vector;
	private Point3D point;
	public Ray(Point3D pnt, Vector vec) {
		point = pnt;
		vector = vec;
	}
	public Point3D getPoint() {
		return point;
	}
	public double getX() {
		return point.getX();
	}
	public double getY() {
		return point.getY();
	}
	public double getZ() {
		return point.getZ();
	}
	public Vector getVector() {
		return vector;
	}
}