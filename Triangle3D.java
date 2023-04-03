package types;

public class Triangle3D {
	public Point3D[] points;
	public Triangle3D(Point3D pointA, Point3D pointB, Point3D pointC) {
		points = new Point3D[] {pointA, pointB, pointC};
	}
}