package gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TableHeaderRenderer extends DefaultTableCellRenderer {

	//class com.sun.java.swing.plaf.windows.WindowsTableHeaderUI$XPDefaultRenderer
	static TableCellRenderer a = new JTable().getTableHeader().getDefaultRenderer();

	    @Override
	    public Component getTableCellRendererComponent(
	            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        return a.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    }
	
}
