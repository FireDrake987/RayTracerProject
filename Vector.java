package types;

public class Vector {
	private Direction direction;
	public Vector(Point3D pnt1, Point3D pnt2) {
		this(new Point3D(pnt2.getX() - pnt1.getX(), pnt2.getY() - pnt1.getY(), pnt2.getZ() - pnt1.getZ()));
	}
	public Vector(Point3D pnt) {
		direction = new Direction(pnt.getX(), pnt.getY(), pnt.getZ());
	}
	public Vector(Direction dir ) {
		direction = dir;
	}
	public double getX() {
		return direction.getX();
	}
	public double getY() {
		return direction.getY();
	}
	public double getZ() {
		return direction.getZ();
	}
	public Point3D getPoint() {
		return new Point3D(getX(), getY(), getZ());
	}
	public Direction getDirection() {
		return direction;
	}
	public double getMagnitude() {
		return direction.getMagnitude();
	}
	public Vector add(Vector vec) {
		return new Vector(getDirection().add(vec.getDirection()));
	}
	public double dot(Vector vec) {
		return getX() * vec.getX() + getY() * vec.getY() + getZ() * vec.getZ();
	}
	public Vector cross(Vector vec) {
		return new Vector(new Point3D(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getZ()));
	}
}