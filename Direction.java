package rayTracing;

public class Direction {
	private double ratioX, ratioY, ratioZ;
	public Direction(Direction angle, double magnitude) {
		this((angle.getX() /  angle.getMagnitude()) * magnitude, (angle.getY() / angle.getMagnitude()) * magnitude, (angle.getZ() / angle.getMagnitude()) * magnitude);
	}
	public Direction(double X, double Y, double Z) {
		ratioX = X;
		ratioY = Y;
		ratioZ = Z;
	}
	public double getX() {
		return ratioX;
	}
	public double getY() {
		return ratioY;
	}
	public double getZ() {
		return ratioZ;
	}
	public double getMagnitude() {
		return (getX() + getY() + getZ());
	}
	public Direction add(Direction dir) {
		return new Direction(getX() + dir.getX(), getY() + dir.getY(), getZ() + dir.getZ());
	}
	public String toString() {
		return getX() + ":" + getY() + ":" + getZ();
	}
}