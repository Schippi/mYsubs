package gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class StartProgressBarUI extends BasicProgressBarUI {

	private Color from = new Color(0xA83838).darker();
	private Color to = Color.red.darker();

	@Override
	protected void paintDeterminate(Graphics g, JComponent c) {
		if (!(g instanceof Graphics2D)) {
			return;
		}

		Insets b = progressBar.getInsets(); // area for border
		int barRectWidth = progressBar.getWidth() - (b.right + b.left);
		int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

		if (barRectWidth <= 0 || barRectHeight <= 0) {
			return;
		}

		// amount of progress to draw
		int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(new GradientPaint(c.getX(), c.getY(), from, c.getX()
				+ c.getWidth(), c.getY() + c.getHeight(), to));
		g2.fillRect(b.left, b.top, amountFull + b.left, barRectHeight);

		// Deal with possible text painting
		if (progressBar.isStringPainted()) {
			paintString(g, b.left, b.top, barRectWidth, barRectHeight,
					amountFull, b);
		}
	}

	public Color getFrom() {
		return from;
	}

	public void setFrom(Color from) {
		this.from = from;
	}

	public Color getTo() {
		return to;
	}

	public void setTo(Color to) {
		this.to = to;
	}
}
