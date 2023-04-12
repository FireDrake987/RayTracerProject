package rayTracing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Camera extends JPanel {
	private static final long serialVersionUID = 1L;
	private double yaw, pitch;//Yaw is rotY, pitch is rowX-Z(moves through yaw)
	public final double V_FOV, H_FOV;
	private double x, y, z, w, h;
	private double antialiasingValue;
	private JFrame frame = new JFrame();
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
		System.out.println("PAINT");
		for(double yw = yaw - H_FOV * (1/2); yw < yaw + H_FOV * (1/2); yw += H_FOV / (antialiasingValue * w)) {
			System.out.println("dml1");
			for(double ptch = pitch - V_FOV * (1/2); ptch < pitch + V_FOV * (1/2); ptch += V_FOV / (antialiasingValue * h)) {
				System.out.printf("%s is yaw\n%s is pitch", yw, ptch);
				double vecX = Math.cos(ptch) * Math.sin(yw);
				double vecY = Math.sin(ptch);
				double vecZ = Math.cos(ptch) * Math.cos(yw);
				Vector vec = new Vector(new Point3D(vecX, vecY, vecZ));
				Ray ray = new Ray(new Point3D(x, y, z), vec);
				double lowDist = Double.MAX_VALUE;
				MultiPlane lowPlane = null;
				for(MultiPlane plane : planes) {
					double dist = plane.intersects(ray);
					if(dist < lowDist) {
						lowPlane = plane;
						lowDist = dist;
					}
				}
				if(lowPlane != null && lowDist >= 0) {
					Color color = lowPlane.getColor();
					g.setColor(color);
					double xPos = yw - yaw + H_FOV * (1/2);
					xPos /= H_FOV;
					xPos *= w;
					double yPos = ptch - pitch + V_FOV * (1/2);
					yPos /= V_FOV;
					yPos *= h;
					g.fillRect((int) xPos, (int) yPos, (int) (1 / antialiasingValue), (int) (1 / antialiasingValue));
				}
			}
		}
	}
	public void start() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while(true) {
			frame.repaint();
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				System.err.println(e);
			}
		}
	}
}
