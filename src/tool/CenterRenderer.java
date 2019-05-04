package tool;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class CenterRenderer extends DefaultTableCellRenderer {
	private static final String TEXT_NOTHING = "";
	private static final String HTML_RED = "<html><font color='red'>";

	public CenterRenderer() {
		super();
		super.setHorizontalAlignment(CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		String str = value != null ? value.toString() : TEXT_NOTHING;

		setText(HTML_RED + str);

		return this;
	}
	
//	public void paint(Graphics g){
//		g.setColor(Color.RED);
//		g.drawLine(0, 0, getWidth(), getHeight());
//		g.drawLine(0, getHeight(), getWidth(), 0);
//	}
}