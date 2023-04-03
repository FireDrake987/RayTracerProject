package types;

public class Point3D {
	private double x, y, z;
	public Point3D(double X, double Y, double Z) {
		x = X;
		y = Y;
		z = Z;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	public Point3D average(Point3D pnt) {
		return new Point3D((pnt.getX() + getX()) / 2.0, (pnt.getY() + getY()) / 2.0, (pnt.getZ() + getZ()) / 2.0);
	}
}