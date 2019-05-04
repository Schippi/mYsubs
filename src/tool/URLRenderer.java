package tool;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
class URLRenderer extends DefaultTableCellRenderer implements MouseListener,
		MouseMotionListener {

	protected static final String HTML_COLOR_RED = "<html><u><font color='#C7101A'>";
	protected static final String HTML_COLOR_BLUE = "<html><u><font color='blue'>";
	protected static final String TEXT_NOTHING = "";
	protected static final String HTML_BG_COLOR = "<span style='background-color:Peru'>";

	protected int row = -1;
	protected int col = -1;
	protected boolean isRollover = false;
	protected PrintStream logger;
	protected JFrame frame;
	int del_clickcount = 0;
	protected JCheckBox backToTop;
	JTextField search;
	MainPanel main;
	private WindowFocusListener jmpListener;

	public URLRenderer(PrintStream logger, JFrame MainFrame,
			JCheckBox backToTop, JTextField search, MainPanel main) {
		this.main = main;
		this.logger = logger;
		this.search = search;
		frame = MainFrame;
		this.backToTop = backToTop;
		jmpListener = new JumpBackListener();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		String str = value != null ? value.toString() : TEXT_NOTHING;
		myVidEntry mve = null;
		try {
			mve = ((TestModel) table.getModel()).getDatabyURL(new URL(str));
		} catch (MalformedURLException e) {
			log(e);
		}
		super.setHorizontalAlignment(CENTER);
		String text;
		if (mve == null)
			return this;
		if (mve.isClicked()) {

			text = HTML_COLOR_RED;
		} else {
			text = HTML_COLOR_BLUE;
		}
		if (mve.isNew()) {
			text += HTML_BG_COLOR;

		}

		text += str.substring(MainPanel.TUBE_URL_PRE_ID_LENGTH);
		setText(text);
		return this;
	}

	public void log(Exception e) {
		logger.println("\n"+new Time(new Date().getTime()).toString()
				+ MainPanel.TEXT_LINEFEED);
		e.printStackTrace(logger);
		logger.println(MainPanel.TEXT_TRIPLELINEWRAP);
		System.err.println(MainPanel.ERROR_LOGGED);
	}

	public static boolean isURLColumn(JTable table, int column) {
		return column >= 0 && table.getColumnClass(column).equals(URL.class);
	}

	public static boolean isDelColumn(JTable table, int column) {
		return column >= 0 && table.getColumnClass(column).equals(char.class);
	}

	// FIXME date and title are also string...
	public static boolean isAuthorColumn(JTable table, int column) {
		return column >= 0 && table.getColumnClass(column).equals(String.class);
	}

	public static int getURLColumn(JTable table) {
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (isURLColumn(table, i)) {
				return i;
			}
		}
		return -1;
	}

	public static int getAuthorColumn(JTable table) {
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (isAuthorColumn(table, i)) {
				return i;
			}
		}
		return -1;
	}

	public static int getDelColumn(JTable table) {
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (isDelColumn(table, i)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		JTable table = (JTable) e.getSource();

		Point pt = e.getPoint();
		int prev_row = row;
		int prev_col = col;
		boolean prev_ro = isRollover;
		row = table.rowAtPoint(pt);
		col = table.columnAtPoint(pt);
		isRollover = isURLColumn(table, col);
		if ((row == prev_row && col == prev_col && isRollover == prev_ro)
				|| (!isRollover && !prev_ro)) {
			return;
		}

		Rectangle repaintRect;
		if (isRollover) {
			Rectangle r = table.getCellRect(row, col, false);
			repaintRect = prev_ro ? r.union(table.getCellRect(prev_row,
					prev_col, false)) : r;
		} else {
			repaintRect = table.getCellRect(prev_row, prev_col, false);
		}
		table.repaint(repaintRect);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		if (isURLColumn(table, col)) {
			table.repaint(table.getCellRect(row, col, false));
			row = -1;
			col = -1;
			isRollover = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		Point pt = e.getPoint();
		int ccol = table.columnAtPoint(pt);
		
		
		if (isURLColumn(table, ccol) && MouseEvent.BUTTON1 == e.getButton()) { // == 3
			int crow = table.rowAtPoint(pt);

			URL url = (URL) table.getValueAt(crow, ccol);
			myVidEntry mve = ((TestModel) table.getModel()).getDatabyURL(url);
			mve.setclicked(true);
			// System.out.println(url);
			try {
				if (backToTop.isSelected()){
					frame.addWindowFocusListener(jmpListener);
				}
				Desktop.getDesktop().browse(url.toURI());
				Rectangle repaintRect = table.getCellRect(crow, ccol, false);
				table.repaint(repaintRect);
				
				MainPanel.stats.incnClick();
				
					
			} catch (Exception ex) {
				log(ex);
			}
		} else if (ccol == 1 && e.getClickCount() >= 2) {
			String val = table.getValueAt(table.rowAtPoint(pt), ccol)
					.toString();
			search.setText(val);
			main.newFilter(val);

		}
	}
	
	
	public void mouse(JTable table, int co, int ro) {
		int ccol = co;
		// if(ccol>0 && ccol <4) ccol = 3;

		if (isURLColumn(table, ccol)) { // == 3
			int crow = ro;

			URL url = (URL) table.getValueAt(crow, ccol);
			myVidEntry mve = ((TestModel) table.getModel()).getDatabyURL(url);
			mve.setclicked(true);
			try {
				Desktop.getDesktop().browse(url.toURI());
				Rectangle repaintRect = table.getCellRect(crow, ccol, false);
				table.repaint(repaintRect);
				if (backToTop.isSelected()){
					frame.addWindowFocusListener(jmpListener);
				}
					
			} catch (Exception ex) {
				ex.printStackTrace();
				log(ex);
			}
		} 
	}
	
	public class JumpBackListener implements WindowFocusListener{
		@Override
		public void windowGainedFocus(WindowEvent arg0) {
			// do nothing
		}

		@Override
		public void windowLostFocus(WindowEvent arg0) {
			frame.toFront();
			frame.removeWindowFocusListener(this);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void validate() {}
    @Override
	public void revalidate() {}
}