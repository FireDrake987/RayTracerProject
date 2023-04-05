package rayTracing;

public class Main {

	public static void main(String[] args) {
		Plane plane = new Plane(new Point3D(1, 2, 2), new Point3D(3, 3, 3), new Point3D(4, 5, 4));//Change points
		Point3D p1 = new Point3D(0, 0, 0);//Change point
		Point3D p2 = new Point3D(2.25, 3, 2.75);//Change point
		Vector vec = new Vector(p1, p2);
		Ray ray = new Ray(new Point3D(0, 0, 0), vec);
		System.out.println(plane.intersects(ray));
	}

}