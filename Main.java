package rayTracing.rayTracing;

import java.awt.event.*;

public class Main {
	public static boolean wPressed = false;
	public static boolean aPressed = false;
	public static boolean sPressed = false;
	public static boolean dPressed = false;
	public static boolean spacePressed = false;
	public static boolean shiftPressed = false;
	public static void main(String[] args) {
		MultiPlane[] scene = new MultiPlane[] {
				new MultiPlane(new java.awt.Color(128, 128, 255), new Point3D(0, 0, -1), new Point3D(1, 1, -1), new Point3D(1, 0, -1)),
				new Cube(new java.awt.Color(0, 0, 0), new java.awt.Color(255, 0, 0), new java.awt.Color(0, 255, 0), new java.awt.Color(0, 0, 255), 0, 0, 0, 10, 10, 10),
				new Cube(new java.awt.Color(0, 0, 0), new java.awt.Color(255, 0, 0), new java.awt.Color(0, 255, 0), new java.awt.Color(0, 0, 255), -10, -10, -10, -50, -50, -50),
				new Cube(new java.awt.Color(0, 255, 0), -1000, 5000, -10, 2000, 10, 2000),
				new MultiPlane(new java.awt.Color(0, 0, 0), new Plane(new Point3D(0, 0.000001, 0), new Point3D(100, 0.000001, 0), new Point3D(0, 0.000001, 100)))
		};
		Camera cam = new Camera(new java.awt.Color(0, 128, 255), 1200, 600, (80 / 180.0) * Math.PI, (80 / 180.0) * Math.PI, 5, 5, -10, 0, 0, 0.20002, scene) {
			private static final long serialVersionUID = 1L;
			public int getMoveRight() {
				if(Main.dPressed == true) {
					return 1;
				}
				else if(Main.aPressed == true) {
					return -1;
				}
				else {
					return 0;
				}
			}
			public int getMoveUp() {
				if(Main.spacePressed == true) {
					this.directMove(0, -1, 0);
					return 0;//-1
				}
				else if(Main.shiftPressed == true) {
					this.directMove(0, 1, 0);
					return 0;//1
				}
				else {
					return 0;
				}
			}
			public int getMoveForwards() {
				if(Main.wPressed == true) {
					return 1;
				}
				else if(Main.sPressed == true) {
					return -1;
				}
				else {
					return 0;
				}
			}
		};
		cam.frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == 'w') {
					wPressed = true;
				}
				if(e.getKeyChar() == 'a') {
					aPressed = true;
				}
				if(e.getKeyChar() == 's') {
					sPressed = true;
				}
				if(e.getKeyChar() == 'd') {
					dPressed = true;
				}
				if(e.getKeyChar() == ' ') {
					spacePressed = true;
				}
				if(e.getKeyCode() == 16) {//Shift key
					shiftPressed = true;
				}
			}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == 'w') {
					wPressed = false;
				}
				if(e.getKeyChar() == 'a') {
					aPressed = false;
				}
				if(e.getKeyChar() == 's') {
					sPressed = false;
				}
				if(e.getKeyChar() == 'd') {
					dPressed = false;
				}
				if(e.getKeyChar() == ' ') {
					spacePressed = false;
				}
				if(e.getKeyCode() == 16) {//Shift key
					shiftPressed = false;
				}
			}
		});
		cam.frame.addMouseMotionListener(new MouseMotionAdapter() {
			public static double preX, preY;
			public void mouseMoved(MouseEvent e) {
				double xPos = e.getPoint().getX();
				double yPos = e.getPoint().getY();
				if(Math.max(Math.abs(xPos - preX), Math.abs(yPos - preY)) <= 50) {
					cam.rotate(yPos - preY, xPos - preX);
				}
				if(cam.getPitch() < -0.25 * Math.PI) {
					cam.setPitch(-0.25 * Math.PI);
				}
				else if(cam.getPitch() > 0.25 * Math.PI) {
					cam.setPitch(0.25 * Math.PI);
				}
				cam.setYaw(cam.getYaw() % (Math.PI * 2));
				if(cam.getYaw() < 0) {
					cam.setYaw(Math.PI * 2 + cam.getYaw());
				}
				preX = xPos;
				preY = yPos;
			} 
			public void mouseDragged(MouseEvent e) {
				double xPos = e.getPoint().getX();
				double yPos = e.getPoint().getY();
				if(Math.max(Math.abs(xPos - preX), Math.abs(yPos - preY)) <= 50) {
					cam.rotate(yPos - preY, xPos - preX);
				}
				preX = xPos;
				preY = yPos;
			}
		});
		cam.frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				double xPos = e.getPoint().getX();
				double yPos = e.getPoint().getY();
				xPos /= cam.getw();
				yPos /= cam.geth();
				xPos *= cam.H_FOV;
				yPos *= cam.V_FOV;
				xPos -= 0.5 * cam.H_FOV;
				yPos -= 0.5 * cam.V_FOV;
				xPos += cam.getYaw();
				yPos += cam.getPitch();
				System.out.printf("xPos: %s\nyPos: %s\n", xPos, yPos);
			}
		});
		cam.start();
	}
}