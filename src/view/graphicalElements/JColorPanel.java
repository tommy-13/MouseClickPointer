package view.graphicalElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JColorPanel extends JPanel {
	
	private Color color;
	
	public JColorPanel(Color color, Dimension dim) {
		super();
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
		setColor(color);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	
	boolean mouseEntered = false;
	public void onMouseEntered() {
		mouseEntered = true;
	}
	public void onMouseExited() {
		mouseEntered = false;
	}
	

	/**
	 *  Paint the background using the background Color of the
	 *  contained component
	 */
	@Override
	public void paintComponent(Graphics g) {		
		g.setColor(color);
		g.fillRoundRect(0, 0, 24, getHeight(), 6, 6);
		
		if (mouseEntered) {
			g.setColor(Color.BLACK);
			g.fillOval(26, getHeight()/2-4, 8, 8);
		}
	}
}
