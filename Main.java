package rayTracing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Main {
	public static boolean wPressed = false;
	public static boolean aPressed = false;
	public static boolean sPressed = false;
	public static boolean dPressed = false;
	public static boolean spacePressed = false;
	public static boolean shiftPressed = false;
	public static void main(String[] args) {
		MultiPlane[] scene = new MultiPlane[] {
				new MultiPlane(new java.awt.Color(255, 0, 0), new Point3D(10, 10, 0), new Point3D(0, 10, 0), new Point3D(0, 10, 10), new Point3D(10, 10, 10)), 
				new MultiPlane(new java.awt.Color(0, 255, 0), new Point3D(0, 0, 0), new Point3D(10, 0, 0), new Point3D(10, 10, 0), new Point3D(0, 10, 0)), 
				new MultiPlane(new java.awt.Color(0, 0, 255), new Point3D(0, 0, 0), new Point3D(0, 0, 10), new Point3D(0, 10, 10), new Point3D(0, 10, 0)), 
				new MultiPlane(new java.awt.Color(255, 255, 0), new Point3D(10, 0, 0), new Point3D(10, 0, 10), new Point3D(10, 10, 10), new Point3D(10, 10, 0)), 
				new MultiPlane(new java.awt.Color(255, 0, 255), new Point3D(0, 0, 0), new Point3D(10, 0, 0), new Point3D(10, 0, 10), new Point3D(0, 0, 10)), 
				new MultiPlane(new java.awt.Color(0, 255, 255), new Point3D(0, 0, 10), new Point3D(10, 0, 10), new Point3D(10, 10, 10), new Point3D(0, 10, 10))
		};
		Camera cam = new Camera(400, 200, Math.PI, (135 / 180.0) * Math.PI, 5, 5, -10, 0, 0, 2, scene) {
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
					return 1;
				}
				else if(Main.shiftPressed == true) {
					return -1;
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
				if(e.getKeyCode() == 49) {//Space bar
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
				if(e.getKeyCode() == 49) {//Space bar
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
		cam.start();
	}
}