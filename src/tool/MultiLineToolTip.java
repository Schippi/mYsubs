package tool;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalToolTipUI;

/**
 * Creates a MultilineToolTip in a very easy way<br/>
 * http://www.java2s.com/Code/Java/Swing-JFC/MultiLineToolTipExample.htm <br/>
 * 
 */
@SuppressWarnings("serial")
public class MultiLineToolTip extends JToolTip {
	public MultiLineToolTip() {
		setUI(new MultiLineToolTipUI());
	}
}

/**
 * /** Creates multiline Tooltips<br/>
 * http://www.java2s.com/Code/Java/Swing-JFC/MultiLineToolTipExample.htm <br/>
 * entnommen
 * 
 */
class MultiLineToolTipUI extends MetalToolTipUI {
	private String[] strs;

	private static Dimension dim = new Dimension(0,0);
	@SuppressWarnings("unused")
	private int maxWidth = 0;

	private static Font font = new JLabel().getFont();
	private static FontMetrics metrics= Toolkit.getDefaultToolkit().getFontMetrics(font);
	
	@Override
	public void paint(Graphics g, JComponent c) {
		g.setFont(font);
		Dimension size = c.getSize();
		g.setColor(c.getBackground());
		g.fillRect(0, 0, size.width, size.height);
		g.setColor(c.getForeground());
		if (strs != null) {
			for (int i = 0; i < strs.length; i++) {
				g.drawString(strs[i], 3, (metrics.getHeight()) * (i + 1));
			}
		}
	}

	@Override
	@SuppressWarnings({  "unchecked" })
	public Dimension getPreferredSize(JComponent c) {
		String tipText = ((JToolTip) c).getTipText();
		if (tipText == null) {
			return dim;
		}
		@SuppressWarnings("rawtypes")
		Vector v = new Vector();
		
		String[] tt = tipText.split(" ");
		String ln="";
		int ii=0;
		int width;
		int maxWidth= 0;
		while(tt.length>ii){
			ln="";
			while(tt.length>ii && SwingUtilities.computeStringWidth(metrics, ln+tt[ii])<=250){
				if(ii+1!=tt.length){
					ln+=tt[ii]+" ";
				}else{
					ln+=tt[ii];
				}
				ii++;
			}
			if(ii==0){
				ln=tt[0];
			}
			width = SwingUtilities.computeStringWidth(metrics, ln);
			maxWidth = width > maxWidth? width : maxWidth;
			v.addElement(ln);
		}
		
		
		
//		BufferedReader br = new BufferedReader(new StringReader(tipText));
//		String line;
//		int maxWidth = 0;
//	
//		try {
//			while ((line = br.readLine()) != null) {
//				int width = SwingUtilities.computeStringWidth(metrics, line);
//				maxWidth = (maxWidth < width) ? width : maxWidth;
//				v.addElement(line);
//			}
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
		int lines = v.size();
		if (lines < 1) {
			strs = null;
			lines = 1;
		} else {
			strs = new String[lines];
			int i = 0;
			for (@SuppressWarnings("rawtypes")
			Enumeration e = v.elements(); e.hasMoreElements(); i++) {
				strs[i] = (String) e.nextElement();
			}
		}
		int height = metrics.getHeight() * lines;
		this.maxWidth = 200;
		return new Dimension(maxWidth + 6, height + 4);
	}
}
