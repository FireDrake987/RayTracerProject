package rayTracing;

public class MultiPlane {
	private java.awt.Color color;
	public Plane[] planes;
	public MultiPlane() {}//In case any subclasses get lazy
	public MultiPlane(java.awt.Color col, Point3D ... pnts) {
		color = col;
		if(pnts.length < 3) {
			throw new IllegalArgumentException("You can't define a plane with less than three points!");
		}
		planes = new Plane[pnts.length - 2];
		for(int i = 0; i < pnts.length - 2; i ++) {
			planes[i] = new Plane(pnts[0], pnts[i + 1], pnts[i + 2]);
		}
	}
	public MultiPlane(java.awt.Color col, Plane ... plns) {
		color = col;
		planes = plns;
	}
	public java.awt.Color getColor(Point3D intersectionPoint) {
		return color;
	}
	public Point3D getIntersection(Ray ray) {
		double highestBidder = Double.MAX_VALUE;
		int highestSpot = -1;
		for(int i = 0; i < planes.length; i ++) {
			Plane plane = planes[i];
			double intersection = plane.intersects(ray.getPoint(), ray.getPoint().add(ray.getVector().getPoint()));
			if(intersection < highestBidder && intersection >= 0) {
				highestBidder = intersection;
				highestSpot = i;
			}
		}
		if(Math.abs(highestBidder - Double.MAX_VALUE) <= 0.00000000001 * Double.MAX_VALUE) {
			return null;
		}
		return planes[highestSpot].getIntersection(ray);
	}
	public double intersects(Ray ray) {
		double highestBidder = Double.MAX_VALUE;
		for(Plane plane : planes) {
			double intersection = plane.intersects(ray.getPoint(), ray.getPoint().add(ray.getVector().getPoint()));
			if(intersection < highestBidder && intersection >= 0) {
				highestBidder = intersection;
			}
		}
		if(Math.abs(highestBidder - Double.MAX_VALUE) <= 0.00000000001 * Double.MAX_VALUE) {
			return -1;
		}
		return highestBidder;
	}
}