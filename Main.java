package rayTracing;

public class Main {
	public static void main(String[] args) {
		MultiPlane[] scene = new MultiPlane[] {
				new MultiPlane(new java.awt.Color(255, 0, 0), new Point3D(10, 10, 0), new Point3D(0, 10, 0), new Point3D(0, 10, 10), new Point3D(10, 10, 10)), 
				new MultiPlane(new java.awt.Color(255, 0, 0), new Point3D(0, 0, 0), new Point3D(10, 0, 0), new Point3D(10, 10, 0), new Point3D(0, 10, 0)), 
				new MultiPlane(new java.awt.Color(255, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 10), new Point3D(0, 10, 10), new Point3D(0, 10, 0)), 
				new MultiPlane(new java.awt.Color(255, 0, 0), new Point3D(10, 0, 0), new Point3D(10, 0, 10), new Point3D(10, 10, 10), new Point3D(10, 10, 0)), 
				new MultiPlane(new java.awt.Color(255, 0, 0), new Point3D(0, 0, 0), new Point3D(10, 0, 0), new Point3D(10, 0, 10), new Point3D(0, 0, 10)), 
				new MultiPlane(new java.awt.Color(255, 0, 0), new Point3D(0, 0, 10), new Point3D(10, 0, 10), new Point3D(10, 10, 10), new Point3D(0, 10, 10))
		};
		Camera cam = new Camera(400, 400, Math.PI, (135 / 180.0) * Math.PI, 5, 5, -10, 0, 0, 2, scene);
		cam.start();
	}
}