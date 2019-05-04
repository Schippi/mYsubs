package tool;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.table.DefaultTableModel;

public class SubsModel extends DefaultTableModel {

	ArrayList<String> list = new ArrayList<String>();

	public void store(String a) {
		list.add(a);
		Collections.sort(list);
		Object[][] obj =new Object[list.size()][1];
		for(int i = 0; i< list.size();i++){
			obj[i][0] = list.get(i);
		}
		super.setDataVector(obj, new Object[]{"author"});
	}
	
	
	@Override
	public Object getValueAt(int row, int col) {
		return super.getValueAt(row, col);
	}
	
	

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public Class<?> getColumnClass(int modelIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int modelIndex) {
		return "author";
	}
}
