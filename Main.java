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
		Camera cam = new Camera(200, 200, 181, 135, 5, 5, -10, 0, Math.PI, 1, scene);
		cam.start();
	}
}