package rayTracing;

public class Plane {
	private double A, B, C, D;//A plane in the form f(x)=Ax+By+Cz+D
	private Point3D[] boundingPoints;
	public Plane(Point3D point1, Point3D point2, Point3D point3) {
		boundingPoints = new Point3D[] {point1, point2, point3};
		Vector AB = new Vector(point1, point2);
		Vector AC = new Vector(point1, point3);
		Vector normal = AB.cross(AC);
		A = normal.getX();
		B = normal.getY();
		C = normal.getZ();
		D = (-1 * A * point1.getX()) + (-1 * B * point1.getY()) + (-1 * C * point1.getZ());
	}
	public Plane(double A, double B, double C, double D) {
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
	}
	public Point3D getIntersection(Ray ray) {
		return getIntersection(ray.getPoint(), ray.getPoint().add(ray.getVector().getPoint()));
	}
	public Point3D getIntersection(Point3D C, Point3D P) {
		double Cx, Cy, Cz, Px, Py, Pz, t, Ix, Iy, Iz;
		Cx = C.getX();
		Cy = C.getY();
		Cz = C.getZ();
		Px = P.getX();
		Py = P.getY();
		Pz = P.getZ();
		if((A * (Px - Cx)) + (B * (Py - Cy)) + (this.C * (Pz - Cz)) == 0) {
			return null;//No intersection
		}
		t = ((-A * Cx) - (B * Cy) - (this.C * Cz) - D)  / ((A * (Px - Cx)) + (B * (Py - Cy)) + (this.C * (Pz - Cz)));
		if(t < 0) {
			return null;//Intersection behind ray
		}
		Ix = (1-t)*Cx + t*Px;
		Iy = (1-t)*Cy + t*Py;
		Iz = (1-t)*Cz + t*Pz;
		return new Point3D(Ix, Iy, Iz);
	}
	public boolean intersects(Ray ray) {
		return intersects(ray.getPoint(), ray.getPoint().add(ray.getVector().getPoint()));
	}
	public boolean intersects(Point3D C, Point3D P) {
		double aWeight, bWeight, cWeight;
		Point3D I = getIntersection(C, P);
		Vector N = new Vector(boundingPoints[0], boundingPoints[1]).cross(new Vector(boundingPoints[0], boundingPoints[2]));
		aWeight = N.dot(new Vector(boundingPoints[1], I).cross(new Vector(boundingPoints[2], I)));
		bWeight = N.dot(new Vector(boundingPoints[2], I).cross(new Vector(boundingPoints[0], I)));
		cWeight = N.dot(new Vector(boundingPoints[0], I).cross(new Vector(boundingPoints[1], I)));
		if(aWeight >= 0 && bWeight >= 0 && cWeight >= 0) {
			return true;//Point found, and in triangle
		}
		return false;//Point found, but not in triangle
	}
}