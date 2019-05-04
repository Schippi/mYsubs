package tool;

import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

public class GlassPaneListener extends MouseInputAdapter {
	Toolkit toolkit;
	Component toTopButton;
	Component exitButton, initComp;
	JFrame frame;

	Point initialClick = new Point(0, 0);
	private Component BScomp = null;

	public GlassPaneListener(Component toTopButton, Component exitButton,
			JFrame frame, Component initComp) {
		toolkit = Toolkit.getDefaultToolkit();
		this.toTopButton = toTopButton;
		this.exitButton = exitButton;
		this.frame = frame;
		this.initComp = initComp;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int thisX = frame.getLocation().x;
		int thisY = frame.getLocation().y;

		// Determine how much the mouse moved since the initial click
		int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
		int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

		// Move window to this position
		int X = thisX + xMoved;
		int Y = thisY + yMoved;
		frame.setLocation(X, Y);
		// frame.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

		initialClick = e.getPoint();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
