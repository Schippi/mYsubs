package tool;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImgPanel extends JPanel {

	private static final String ICON_FILE_LOAD = "data/icons/LoadIcon.png";
	Image img;

	public ImgPanel() {
		super();
		setOpaque(false);
		this.setBorder(BorderFactory.createEtchedBorder());
		img = ImagePanel.resizeToBig(new ImageIcon(getClass().getClassLoader()
				.getResource(ICON_FILE_LOAD)).getImage(), 130, 130);
		setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 10, 10, null);
	}
}
