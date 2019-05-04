package tool;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class VidGridPanel extends JPanel {

	private ImagePanel imgp;

	
	public VidGridPanel(ImagePanel imgp){
		this.imgp=imgp;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(125,150));
		setPreferredSize(new Dimension(125,130));
		setSize(new Dimension(125,150));
	}
	public ImagePanel getImgp() {
		return imgp;
	}

	public void setImgp(ImagePanel imgp) {
		this.imgp = imgp;
	}
	
}
