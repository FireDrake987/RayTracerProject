package rayTracing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Camera extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int intendedFramerate = 30;
	private double yaw, pitch;//Yaw is rotY, pitch is rowX-Z(moves through yaw)
	public final double V_FOV, H_FOV;
	private double x, y, z, w, h;
	private double antialiasingValue;
	private Color background;
	public JFrame frame = new JFrame();
	private MultiPlane[] planes;
	private Double framerate;
	public Camera(Color bcol, double width, double height, double VerticalFOV, double HorizontalFOV, double xPos, double yPos, double zPos, double Yaw, double Pitch, double quality, MultiPlane[] Planes) {
		background = bcol;
		w = width;
		h = height;
		x = xPos;
		y = yPos;
		z = zPos;
		yaw = Yaw;
		pitch = Pitch;
		V_FOV = VerticalFOV;
		H_FOV = HorizontalFOV;
		antialiasingValue = quality;
		planes = Planes;
		this.setSize((int) w, (int) h);
		frame.add(this);
		frame.setSize((int) w, (int) h);
	}
	public void paintComponent(Graphics g) {
		long smillis = System.currentTimeMillis();
		Graphics2D g2d = (Graphics2D) g;
		for(double yw = yaw - H_FOV * 0.5; yw < yaw + H_FOV * 0.5; yw += H_FOV / (antialiasingValue * w)) {
			for(double ptch = pitch - V_FOV * 0.5; ptch < pitch + V_FOV * 0.5; ptch += V_FOV / (antialiasingValue * h)) {
				double vecX = Math.cos(ptch) * Math.sin(yw);
				double vecY = Math.sin(ptch);
				double vecZ = Math.cos(ptch) * Math.cos(yw);
				Vector vec = new Vector(new Point3D(vecX, vecY, vecZ));
				Ray ray = new Ray(new Point3D(x, y, z), vec);
				double lowDist = Double.MAX_VALUE;
				Point3D lowInt = null;
				MultiPlane lowPlane = null;
				for(MultiPlane plane : planes) {
					double dist = plane.intersects(ray);
					if(dist < 0) {
						continue;
					}
					else if(dist < lowDist) {
						lowPlane = plane;
						lowDist = dist;
						lowInt  = plane.getIntersection(ray);
					}
				}
				double xPos = yw - yaw;
				xPos += H_FOV * 0.5;
				xPos /= H_FOV;
				xPos *= w;
				double yPos = ptch - pitch;
				yPos += V_FOV * 0.5;
				yPos /= V_FOV;
				yPos *= h;
				if(lowPlane != null && lowDist >= 0) {
					g2d.setColor(lowPlane.getColor(lowInt));
				}
				else {
					g2d.setColor(background);
				}
				g2d.fillRect((int) Math.floor(xPos), (int) Math.floor(yPos), (int) Math.ceil(1 / antialiasingValue), (int) Math.ceil(1 / antialiasingValue));
			}
		}
		long elapsedmillis = System.currentTimeMillis() - smillis;
		if(elapsedmillis < 1000.0 / intendedFramerate) {
			try {
				Thread.sleep((long) ((1000.0 / intendedFramerate) - elapsedmillis));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		elapsedmillis = System.currentTimeMillis() - smillis;
		this.framerate = 1.0 / (elapsedmillis / 1000.0);
		g2d.setColor(new Color(0, 0, 0));
		g2d.drawString("FPS:" + ((Double) (Math.round(Math.min(framerate, intendedFramerate) * 1000) / 1000.0)).toString(), 10, 10);
		String pos = String.format("(%s, %s, %s)", Math.round(x * 1000) / 1000.0, Math.round(y * 1000) / 1000.0, Math.round(z * 1000) / 1000.0);
		g2d.drawString(pos, (int) (w - g2d.getFontMetrics().stringWidth(pos) - 25), 10);
	}
	public void move(double RightMovement, double UpMovement, double ForwardsMovement) {
		x += Math.cos(pitch) * Math.sin(yaw + 0.5 * Math.PI) * RightMovement;
		y += Math.sin(pitch) * RightMovement;
		z += Math.cos(pitch) * Math.cos(yaw + 0.5 * Math.PI) * RightMovement;
		x += Math.cos(pitch + 0.5 * Math.PI) * Math.sin(yaw) * UpMovement;
		y += Math.sin(pitch + 0.5 * Math.PI) * UpMovement;
		z += Math.cos(pitch + 0.5 * Math.PI) * Math.cos(yaw) * UpMovement;
		x += Math.cos(pitch) * Math.sin(yaw) * ForwardsMovement;
		y += Math.sin(pitch) * ForwardsMovement;
		z += Math.cos(pitch) * Math.cos(yaw) * ForwardsMovement;
	}
	public void directMove(double xMv, double yMv, double zMv) {
		x += xMv;
		y += yMv;
		z += zMv;
	}
	public void rotate(double pitch, double yaw) {
		this.pitch += (Math.PI * pitch) / h;
		this.yaw += (Math.PI * yaw) / w;
	}
	public void maximize() {
		h = frame.getMaximumSize().height;
		w = frame.getMaximumSize().width;
		frame.setSize((int) w, (int) h);
		this.setSize((int) w, (int) h);
		frame.repaint();
	}
	public double getx() {
		return x;
	}
	public double gety() {
		return y;
	}
	public double getz() {
		return z;
	}
	public double getw() {
		return w;
	}
	public double geth() {
		return h;
	}
	public int getMoveRight() {return 0;}//To be overridden
	public int getMoveUp() {return 0;}//To be overridden
	public int getMoveForwards() {return 0;}//To be overridden
	public void updateState() {}//To be overridden
	public void start() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while(true) {
			updateState();
			move(getMoveRight(), getMoveUp(), getMoveForwards());
			frame.repaint();
			try {
				Thread.sleep((long) (1000.0 / intendedFramerate));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
