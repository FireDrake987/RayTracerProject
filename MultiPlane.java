package rayTracing;

public class MultiPlane {
	private java.awt.Color color;
	private Plane[] planes;
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
	public java.awt.Color getColor() {
		return color;
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