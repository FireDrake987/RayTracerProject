package rayTracing;

import java.awt.Color;

public class Cube extends MultiPlane {
	Color color, colX, colY, colZ;
	double xStart, yStart, zStart, xEnd, yEnd, zEnd;
	public Cube(Color col, double xPos, double yPos, double zPos, double width, double height, double depth) {
		this(col, col, col, col, xPos, yPos, zPos, width, height, depth);
	}
	public Cube(Color col, Color colx, Color coly, Color colz, double xPos, double yPos, double zPos, double width, double height, double depth) {
		color = col;
		colX = colx;
		colY  = coly;
		colZ = colz;
		xStart = xPos;
		yStart = yPos;
		zStart = zPos;
		xEnd = xPos + width;
		yEnd = xPos + height;
		zEnd = xPos + depth;
		MultiPlane[] startPlanes = new MultiPlane[] {
				new MultiPlane(col, new Point3D(xPos, yPos, zPos), new Point3D(xPos, yPos, zPos + depth), new Point3D(xPos, yPos + height, zPos + depth), new Point3D(xPos, yPos + height, zPos)), 
				new MultiPlane(col, new Point3D(xPos + width, yPos, zPos), new Point3D(xPos + width, yPos, zPos + depth), new Point3D(xPos + width, yPos + height, zPos + depth), new Point3D(xPos + width, yPos + height, zPos)), 
				new MultiPlane(col, new Point3D(xPos, yPos, zPos), new Point3D(xPos, yPos, zPos + depth), new Point3D(xPos + width, yPos, zPos + depth), new Point3D(xPos + width, yPos, zPos)), 
				new MultiPlane(col, new Point3D(xPos, yPos + height, zPos), new Point3D(xPos, yPos + height, zPos + depth), new Point3D(xPos + width, yPos + height, zPos + depth), new Point3D(xPos + width, yPos + height, zPos)), 
				new MultiPlane(col, new Point3D(xPos, yPos, zPos), new Point3D(xPos + width, yPos, zPos), new Point3D(xPos + width, yPos + height, zPos), new Point3D(xPos, yPos + height, zPos)), 
				new MultiPlane(col, new Point3D(xPos, yPos, zPos + depth), new Point3D(xPos + width, yPos, zPos + depth), new Point3D(xPos + width, yPos + height, zPos + depth), new Point3D(xPos, yPos + height, zPos + depth)), 
		};
		Plane[] plns = new Plane[startPlanes.length * 2];
		for(int i = 0; i < startPlanes.length; i ++) {
			plns[i * 2] = startPlanes[i].planes[0];
			plns[i * 2 + 1] = startPlanes[i].planes[1];
		}
		this.planes = plns;
	}
	public Color getColor(Point3D intersectionPoint) {
		double weightX = (intersectionPoint.getX() - xStart) / xEnd;
		double weightY = (intersectionPoint.getY() - yStart) / yEnd;
		double weightZ = (intersectionPoint.getZ() - zStart) / zEnd;
		int red = (int) (((1 - weightX) * color.getRed()) + (weightX * colX.getRed()) + ((1 - weightY) * color.getRed()) + (weightY * colY.getRed()) + ((1 - weightZ) * color.getRed()) + (weightZ * colZ.getRed()));
		int green = (int) (((1 - weightX) * color.getGreen()) + (weightX * colX.getGreen()) + ((1 - weightY) * color.getGreen()) + (weightY * colY.getGreen()) + ((1 - weightZ) * color.getGreen()) + (weightZ * colZ.getGreen()));
		int blue = (int) (((1 - weightX) * color.getBlue()) + (weightX * colX.getBlue()) + ((1 - weightY) * color.getBlue()) + (weightY * colY.getBlue()) + ((1 - weightZ) * color.getBlue()) + (weightZ * colZ.getBlue()));
		red /= 3;//Fix the weighting to darken instead of lighten
		green /= 3;
		blue /= 3;
		Color col = new Color(red, green, blue);
		return col;
	}
}
