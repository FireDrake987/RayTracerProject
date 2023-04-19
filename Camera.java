package rayTracing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Camera extends JPanel {
	private static final long serialVersionUID = 1L;
	private double yaw, pitch;//Yaw is rotY, pitch is rowX-Z(moves through yaw)
	public final double V_FOV, H_FOV;
	private double x, y, z, w, h;
	private double antialiasingValue;
	public JFrame frame = new JFrame();
	private MultiPlane[] planes;
	public Camera(double width, double height, double VerticalFOV, double HorizontalFOV, double xPos, double yPos, double zPos, double Yaw, double Pitch, double quality, MultiPlane[] Planes) {
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
		super.paintComponent(g);
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
				if(lowPlane != null && lowDist >= 0) {
					double xPos = yw - yaw;
					xPos += H_FOV * 0.5;
					xPos /= H_FOV;
					xPos *= w;
					double yPos = ptch - pitch;
					yPos += V_FOV * 0.5;
					yPos /= V_FOV;
					yPos *= h;
					Color color = lowPlane.getColor(lowInt);
					g2d.setColor(color);
					g2d.fillRect((int) Math.floor(xPos), (int) Math.floor(yPos), (int) Math.ceil(1 / antialiasingValue), (int) Math.ceil(1 / antialiasingValue));
					//System.out.printf("%s x %s y %s w %s h\n", xPos, yPos, (1 / antialiasingValue), (1 / antialiasingValue));
				}
			}
		}
	}
	public void move(double RightMovement, double UpMovement, double ForwardsMovement) {
		x += Math.cos(pitch) * Math.sin(yaw)* RightMovement;
		y += Math.sin(pitch) * UpMovement;
		z += Math.cos(pitch) * Math.cos(yaw) * ForwardsMovement;
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
	public int getMoveRight() {return 0;}//To be overridden
	public int getMoveUp() {return 0;}//To be overridden
	public int getMoveForwards() {return 0;}//To be overridden
	public void start() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while(true) {
			move(getMoveRight(), getMoveUp(), getMoveForwards());
			frame.repaint();
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				System.err.println(e);
			}
		}
	}
}
