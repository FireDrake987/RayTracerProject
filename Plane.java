package types;

public class Plane {
	private double A, B, C, D;
	public Plane(Point3D point1, Point3D point2, Point3D point3) {
		Vector AB = new Vector(point1, point2);
		Vector AC = new Vector(point1, point3);
		Vector normal = AB.cross(AC);
	}
}
