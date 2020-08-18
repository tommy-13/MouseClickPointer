/**
 * 
 */
package view.general;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.Rectangle;
import javax.swing.JWindow;

import observer.SettingsObserver;
import safeLoad.Settings;

/**
 * @author tommy
 *
 */
@SuppressWarnings("serial")
public class MouseActionWindow extends JWindow implements SettingsObserver {
    

	private Settings prefs = Settings.getInstance();
	
	private final long TIME_WHEEL = Settings.MILLIS_ARROWS;
	
	private final String STR_LEFT       = Settings.LETTER_LEFT;
	private final String STR_RIGHT      = Settings.LETTER_RIGHT;
	private final String STR_MIDDLE     = Settings.LETTER_MIDDLE;
	//private final String STR_WHEEL_DOWN = Preferences.LETTER_WHEEL_DOWN;
	//private final String STR_WHEEL_UP   = Preferences.LETTER_WHEEL_UP;

	
	//Polygon polygonDown;
	//Polygon polygonUp;
	Polygon polygonFilledDown;
	Polygon polygonFilledUp;
	
	
	
	/**
	 * @param arg0
	 */
	public MouseActionWindow() {
		
		//polygonUp         = createPolygon(true);
		//polygonDown       = createPolygon(false);
		polygonFilledUp   = createFilledPolygon(true);
		polygonFilledDown = createFilledPolygon(false);

		setWindowForVirtualScreen();
		setAlwaysOnTop(true);
		setBackground(new Color(0, true));
	}
	
	
	private void setWindowForVirtualScreen() {
		Rectangle virtualBounds = new Rectangle();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		String strDev = "Detected graphical output divices:";
		for (int j = 0; j < gs.length; j++) {
			GraphicsDevice gd = gs[j];
			strDev += "\n" + gd.toString() + " -";
			GraphicsConfiguration[] gc = gd.getConfigurations();
			for (int i=0; i < gc.length; i++) {
				Rectangle vb = gc[i].getBounds();
				virtualBounds = virtualBounds.union(vb);
				strDev += " bound: (x=" + vb.x + ",y=" + vb.y + ",width=" + vb.width + ",height=" + vb.height + ");";
			}
		}
		System.out.println(strDev);

		setBounds(virtualBounds);
	}
	
	
	public void close() {
		this.dispose();
	}
	
	
	
	private boolean leftMouseButtonPressed = false;
	public void onLeftMouseButtonPressed() {
		leftMouseButtonPressed = true;
		setVisible(true);
		this.repaint();
	}
	public void onLeftMouseButtonReleased() {
		leftMouseButtonPressed = false;
		shouldSetInvisible();
		this.repaint();
	}
	
	private boolean rightMouseButtonPressed = false;
	public void onRightMouseButtonPressed() {
		rightMouseButtonPressed = true;
		setVisible(true);
		this.repaint();
	}
	public void onRightMouseButtonReleased() {
		rightMouseButtonPressed = false;
		shouldSetInvisible();
		this.repaint();
	}
	
	private boolean middleMouseButtonPressed = false;
	public void onMiddleMouseButtonPressed() {
		middleMouseButtonPressed = true;
		setVisible(true);
		this.repaint();
	}
	public void onMiddleMouseButtonReleased() {
		middleMouseButtonPressed = false;
		shouldSetInvisible();
		this.repaint();
	}
	
	public void shouldSetInvisible() {
		if (!leftMouseButtonPressed && !rightMouseButtonPressed && !middleMouseButtonPressed && !mouseWheelUp && !mouseWheelDown) {
			setVisible(false);
		}
	}
	
	
	private class RunnableTimer implements Runnable {
		private long time = System.currentTimeMillis();
		private boolean mouseWheelUp;
		public RunnableTimer(boolean mouseWheelUp) {
			this.mouseWheelUp = mouseWheelUp;
		}
		public void resetTime() {
			time = System.currentTimeMillis();
		}
		@Override
		public void run() {
			while (System.currentTimeMillis() - time < TIME_WHEEL) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// do nothing
				}
			}
			if (mouseWheelUp) {
				onMouseWheelNotUp();
			}
			else {
				onMouseWheelNotDown();
			}
		}
	}
	
	private boolean mouseWheelUp = false;
	private Thread timerWheelUp;
	private RunnableTimer runnableTimerWheelUp;
	public void onMouseWheelUp() {
		mouseWheelUp = true;
		if (timerWheelUp != null && timerWheelUp.isAlive()) {
			runnableTimerWheelUp.resetTime();
		}
		else {
			runnableTimerWheelUp = new RunnableTimer(true);
			timerWheelUp = new Thread(runnableTimerWheelUp);
			timerWheelUp.start();
		}
		this.setVisible(true);
		this.repaint();
	}
	private void onMouseWheelNotUp() {
		mouseWheelUp = false;
		shouldSetInvisible();
		this.repaint();
	}

	private boolean mouseWheelDown = false;
	private Thread timerWheelDown;
	private RunnableTimer runnableTimerWheelDown;
	public void onMouseWheelDown() {
		mouseWheelDown = true;
		if (timerWheelDown != null && timerWheelDown.isAlive()) {
			runnableTimerWheelDown.resetTime();
		}
		else {
			runnableTimerWheelDown = new RunnableTimer(false);
			timerWheelDown = new Thread(runnableTimerWheelDown);
			timerWheelDown.start();
		}
		this.setVisible(true);
		this.repaint();
	}
	private void onMouseWheelNotDown() {
		mouseWheelDown = false;
		shouldSetInvisible();
		this.repaint();
	}
	
	
	private int mouseX, mouseY;
	public void onMouseMoved(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.repaint();
		/*
		if (this.leftMouseButtonPressed) {
			//this.update(this.getGraphics());
			this.setVisible(true);
		}*/
	}
	
	
	
	//private final Color colLeft = new Color(0.9f,0.2f,0.2f);
	//private final Color colLeftTransparent = new Color(0.9f,0.2f,0.2f,0.4f);
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		Font font = new Font("Dialog", Font.BOLD, prefs.getLetterSize());
	    //getFont().deriveFont((float) prefs.getLetterSize());
		g2.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		
		
		
	
		if (leftMouseButtonPressed) {			
			if (prefs.isDiscLeftSelected()) {
				fillOval(g2, prefs.getColorDiscLeft(), mouseX, mouseY, prefs.getRadiusDisc());
			}
			if (prefs.isCircleLeftSelected()) {
				drawOval(g2, prefs.getColorCircleLeft(), mouseX, mouseY, prefs.getRadiusCircle(), prefs.getLineSizeCircle());
			}
			if (prefs.isLetterLeftSelected()) {
				g2.setColor(prefs.getColorLetterLeft());
				g2.drawString(STR_LEFT, mouseX - prefs.getLetterDistanceX() - metrics.stringWidth(STR_LEFT), mouseY - prefs.getLetterDistanceY());
			}
		}
	
		if (rightMouseButtonPressed) {
			
			if (prefs.isDiscRightSelected()) {
				fillOval(g2, prefs.getColorDiscRight(), mouseX, mouseY, prefs.getRadiusDisc());
			}
			if (prefs.isCircleRightSelected()) {
				drawOval(g2, prefs.getColorCircleRight(), mouseX, mouseY, prefs.getRadiusCircle(), prefs.getLineSizeCircle());
			}
			if (prefs.isLetterRightSelected()) {
				g2.setColor(prefs.getColorLetterRight());
				g2.drawString(STR_RIGHT, mouseX + prefs.getLetterDistanceX(), mouseY - prefs.getLetterDistanceY());
			}
		}
	
		if (middleMouseButtonPressed) {
			
			if (prefs.isDiscMiddleSelected()) {
				fillOval(g2, prefs.getColorDiscMiddle(), mouseX, mouseY, prefs.getRadiusDisc());
			}
			if (prefs.isCircleMiddleSelected()) {
				drawOval(g2, prefs.getColorCircleMiddle(), mouseX, mouseY, prefs.getRadiusCircle(), prefs.getLineSizeCircle());
			}
			if (prefs.isLetterMiddleSelected()) {
				g2.setColor(prefs.getColorLetterMiddle());
				g2.drawString(STR_MIDDLE, mouseX - metrics.stringWidth(STR_MIDDLE)/2, mouseY - prefs.getLetterDistanceY());
			}
		}
		
		if (mouseWheelDown) {
			
			if (prefs.isFilledArrowWheelSelected()) {
				g2.setColor(prefs.getColorFilledArrowWheel());
				
				polygonFilledDown.translate(mouseX+10, mouseY+26+prefs.getDistanceFilledArrow());
				g2.fillPolygon(polygonFilledDown);
				polygonFilledDown.translate(-mouseX-10, -mouseY-26-prefs.getDistanceFilledArrow());
			}
			/*
			if (prefs.isArrowWheelSelected()) {
				g2.setColor(prefs.getColorArrowWheel());
				
				polygonDown.translate(mouseX, mouseY+60);
				g2.setStroke(new BasicStroke(prefs.getLineSizeArrow()));
				g2.drawPolygon(polygonDown);
				polygonDown.translate(-mouseX, -mouseY-60);
			}
			if (prefs.isLetterWheelSelected()) {
				g2.setColor(prefs.getColorLetterWheel());
				g2.drawString(STR_WHEEL_DOWN, mouseX - metrics.stringWidth(STR_WHEEL_DOWN)/2, mouseY + metrics.getHeight() + 32);
			}
			*/
		}

		if (mouseWheelUp) {
			
			if (prefs.isFilledArrowWheelSelected()) {
				g2.setColor(prefs.getColorFilledArrowWheel());
				
				polygonFilledUp.translate(mouseX+10, mouseY-prefs.getDistanceFilledArrow()+2);
				g2.fillPolygon(polygonFilledUp);
				polygonFilledUp.translate(-mouseX-10, -mouseY+prefs.getDistanceFilledArrow()-2);
			}
			/*
			if (prefs.isArrowWheelSelected()) {
				g2.setColor(prefs.getColorArrowWheel());
				
				polygonUp.translate(mouseX, mouseY-32);
				g2.setStroke(new BasicStroke(prefs.getLineSizeArrow()));
				g2.drawPolygon(polygonUp);
				polygonUp.translate(-mouseX, -mouseY+32);
			}
			if (prefs.isLetterWheelSelected()) {
				g2.setColor(prefs.getColorLetterWheel());
				g2.drawString(STR_WHEEL_UP, mouseX - metrics.stringWidth(STR_WHEEL_UP)/2, mouseY - metrics.getHeight() + 8);
			}
			*/
		}
	}


	
	private void drawOval(Graphics2D g, Color col, int mouseX, int mouseY, int radius, int lineSize) {
		g.setColor(col);
		g.setStroke(new BasicStroke(lineSize));
		g.drawOval(mouseX-radius, mouseY-radius, 2 * radius, 2 * radius);
	}
	private void fillOval(Graphics2D g, Color col, int mouseX, int mouseY, int radius) {
		g.setColor(col);
		g.fillOval(mouseX-radius, mouseY-radius, 2 * radius, 2 * radius);
	}
	
	
	
	
	
	@Override
	public void fireSettingChanged() {
		//polygonUp         = createPolygon(true);
		//polygonDown       = createPolygon(false);
		polygonFilledUp   = createFilledPolygon(true);
		polygonFilledDown = createFilledPolygon(false);
		this.repaint();
	}

	
	/*
	private Polygon createPolygon(boolean up) {
		int factor = up ? -1 : 1;
		int[] xPoints = {0, -prefs.getThicknessArrow(), 0, prefs.getThicknessArrow()};
		int[] yPoints = {factor*prefs.getThicknessArrow()/2, 0, factor*prefs.getThicknessArrow(), 0};
		return new Polygon(xPoints,yPoints,xPoints.length);
	}
	*/

	private Polygon createFilledPolygon(boolean up) {
		int factor = up ? -1 : 1;
		int[] xPoints = {0, -prefs.getThicknessFilledArrow(), 0, prefs.getThicknessFilledArrow()};
		int[] yPoints = {factor*prefs.getThicknessFilledArrow()/2, 0, factor*prefs.getThicknessFilledArrow(), 0};
		return new Polygon(xPoints,yPoints,xPoints.length);
	}
}
