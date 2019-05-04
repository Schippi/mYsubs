package tool;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.table.TableCellRenderer;


public class LineWrapRenderer extends JTextArea implements TableCellRenderer {

	static final Font fo = new JLabel().getFont();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		this.setFont(fo);
		this.setText((String) value);
		this.setWrapStyleWord(false);
		this.setLineWrap(true);
		this.setToolTipText((String) value);
		return this;
	}
	
	@Override
	public JToolTip createToolTip() {
  	  MultiLineToolTip tip = new MultiLineToolTip();
      tip.setComponent(this);
      return tip;
    }
}
