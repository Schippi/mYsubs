package tool;

import java.awt.Color;
import java.awt.Component;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
class ImageRenderer extends JPanel implements TableCellRenderer {

	protected static final String TEXT_NOTHING = "";

	protected boolean isRollover = false;
	protected PrintStream logger;
	HashMap<Object, Component> img;

	public ImageRenderer(PrintStream logger) {
		this.logger = logger;
		img = new HashMap<Object, Component>();
		setBackground(Color.WHITE);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		String str = value != null ? value.toString() : TEXT_NOTHING;

		if (img.get(value) == null) {
			ImagePanel imgpanel = new ImagePanel(getImage(value), row, column,
					(table));
			img.put(value, imgpanel);
		} else {
			((ImagePanel) img.get(value)).setpos(row, column);
		}

		try {
			myVidEntry mve = null;
			mve = ((TestModel) table.getModel()).getDatabyURL(new URL(str));
			((ImagePanel) img.get(value)).setBooleans(mve.isClicked(),
					mve.isNew());
		} catch (MalformedURLException e) {
			log(e);
		}

		return img.get(value);
	}

	public static URL getImage(Object value) {
		String str = value != null ? value.toString() : TEXT_NOTHING;
		str = str.substring(MainPanel.TUBE_URL_PRE_ID_LENGTH);
		str = "http://i.ytimg.com/vi/" + str + "/default.jpg";
		try {
			return new URL(str);
		} catch (MalformedURLException e) {
			return null;
		}
		// try {
		// return ImageIO.read(url);
		// } catch (IOException e) {
		// return null;
		// }
		// return Toolkit.getDefaultToolkit().createImage(url);

	}

	public void log(Exception e) {
		logger.print(new Time(new Date().getTime()).toString()
				+ MainPanel.TEXT_LINEFEED);
		e.printStackTrace(logger);
		logger.print(MainPanel.TEXT_TRIPLELINEWRAP);
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

}