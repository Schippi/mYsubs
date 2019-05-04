package tool;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ImageObserver{

	static int[] x = {0,15,0};
	static int[] y = {0,0,15};
	public static final Polygon triangle= new Polygon(x,y,3);
	Image img=MainPanel.loadImg;
	MediaTracker m;
	JPanel paintPanel;
	int row=-1;
	int col=-1;
	private boolean clicked;
	private boolean isnew;
	private boolean loaded= false;
	
	
	public ImagePanel(final URL url, int r, int c, final JComponent compo){
		super();
		 setOpaque(false);
		 row=r;
		 col=c;
		 

//		m = new MediaTracker(this);
//		m.addImage(img, 0);
		paintPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				if (!loaded) {
					loaded = true;
					loadImage(url, compo);
				}
				if (clicked) {
					g.setColor(MainPanel.redColor);
				} else {
					if (isnew) {
						g.setColor(row % 2 == 0 ? MainPanel.evennewColor
								: MainPanel.oddnewColor);
					} else {
						g.setColor(row % 2 == 0 ? MainPanel.evenColor
								: Color.WHITE);
					}
				}
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
				if (img != null) {
					// try {
					// m.waitForID(0, 300);
					// } catch (InterruptedException e) {
					// e.printStackTrace();
					// }
					g.drawImage(img, 1, 1, null);
				} else {
					g.drawImage(MainPanel.thisMainPanel.defaultImg, 1, 1, null);
				}
				g.fillPolygon(triangle);
			}
		};
		this.setLayout(new BorderLayout());
		add(paintPanel, BorderLayout.CENTER);
	}
	
	private void loadImage(final URL url, final JComponent compo){
		 new Thread(){
			 @Override
			public void run(){
				 try {
					img= ImageIO.read(url);
//					img= resizeToBig(img,240,180);
				} catch (IOException e) {
					img=null;
				}
				compo.repaint();
			 }
		 }.start();
	}

	public void setpos(int r, int c){
		row=r;
		col=c;
	}
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
		
	}
	
	@Override
	public boolean isLightweight(){
		return true;
	}
	
	public static Image resizeToBig(Image originalImage, int biggerWidth, int biggerHeight) {
	    int type = BufferedImage.TYPE_INT_ARGB;


	    BufferedImage resizedImage = new BufferedImage(biggerWidth, biggerHeight, type);
	    Graphics2D g = resizedImage.createGraphics();

	    g.setComposite(AlphaComposite.Src);
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    g.drawImage(originalImage, 0, 0, biggerWidth, biggerHeight, null);
	    g.dispose();


	    return resizedImage;
	}
	
	@Override
	public boolean imageUpdate(Image image, int flags, int x, int y,
	          int width, int height) {
	        // If the image has finished loading, repaint the cell.
	        if ((flags & ALLBITS) != 0) {
	        	repaint();
	            return false;  // Return false to say we don't need further notification.
	        }
	        
	        return true;       // Image has not finished loading, need further notification.
	    }

	public void setBooleans(boolean isclicked, boolean is_new) {
		clicked=isclicked;
		isnew=is_new;
	} 
	

	
}
