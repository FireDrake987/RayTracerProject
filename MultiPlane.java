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
			if(plane.intersects(ray) < highestBidder) {
				highestBidder = plane.intersects(ray);
			}
		}
		if(highestBidder > 0) {
			System.out.println(highestBidder);
		}
		return highestBidder;
	}
}