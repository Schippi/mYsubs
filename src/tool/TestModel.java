package tool;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.table.DefaultTableModel;

class TestModel extends DefaultTableModel {

	private static final long serialVersionUID = 8495372982551436280L;

	char char_x = 'X';
	private volatile ArrayList<myVidEntry> data;
	private volatile ArrayList<myVidEntry> blackList;

	public TestModel() {
		super();
		data = new ArrayList<myVidEntry>();
		blackList = new ArrayList<myVidEntry>();
	}

	public Collection<? extends myVidEntry> getBlack() {
		return blackList;
	}

	private static final ColumnContext[] columnArray = { new ColumnContext("Date", String.class, false),
			new ColumnContext("author", String.class, false), new ColumnContext("Name", String.class, false),
			new ColumnContext("ID", URL.class, false) };

	public ArrayList<myVidEntry> getDa() {
		return data;
	}

	public synchronized myVidEntry getDatabyURL(URL url) {

		return data.get(Collections.binarySearch(data, myVidEntry.getDummy(url.toExternalForm())));
		// for (int i = 0; i < data.size(); i++) {
		// if (url.toExternalForm().equals(data.get(i).get_link())) {
		// return data.get(i);
		// }
		// }
		// return null;
		// return data.get(Collections.binarySearch(data,
		// myVidEntry.getDummy(url.toExternalForm())));
	}

	@Override
	public Object getValueAt(int row, int col) {
		return super.getValueAt(row, col);
	}

	public synchronized void purgeData() {
		data = new ArrayList<myVidEntry>();
		blackList = new ArrayList<myVidEntry>();
	}

	public synchronized void setBlackList(ArrayList<myVidEntry> e) {
		blackList = new ArrayList<myVidEntry>(e);
	}

	public void clearNew() {
		for (int i = 0; i < data.size(); i++) {
			data.get(i).setNew(false);
		}
	}

	public synchronized void addTest(myVidEntry v) throws MalformedURLException {
		addTest(v, true);

	}

	public synchronized void addTest(myVidEntry v, boolean sort) throws MalformedURLException {
		addTest(v, sort, true);
	}

	public synchronized void addTest(myVidEntry v, boolean sort, boolean check) throws MalformedURLException {
		URL Peter = null;

		Peter = new URL(v.getLink());

		Object[] obj = { v.getDate(), v.getUser(), v.getTitle(), Peter, char_x };
		synchronized (data) {

			if (!check || Collections.binarySearch(data, v) < 0 && !blackList.contains(v)) {

				data.add(v);
				if (sort)
					Collections.sort(data);

				super.insertRow(0, obj);
			}
		}
	}

	public synchronized void doSort() {
		Collections.sort(data);
	}

	public synchronized void delTest(myVidEntry v) {
		int i = 0;
		int urlcol = 0;
		for (int j = 0; j < getColumnCount(); j++) {
			if (getColumnClass(j).equals(URL.class)) {
				urlcol = j;
				break;
			}
		}
		for (i = 0; i < super.getRowCount(); i++) {
			if (((URL) getValueAt(i, urlcol)).toString().equals(v.getLink())) {
				// System.out.println("found: "+v.get_link());
				synchronized (data) {
					try {
						super.removeRow(i);
					} catch (IndexOutOfBoundsException e) {
						// ignore
					}
					data.remove(v);
					Collections.sort(data);
				}
				blackList.add(v);
				return;
			}
		}

	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return columnArray[col].isEditable;
	}

	@Override
	public Class<?> getColumnClass(int modelIndex) {
		return columnArray[modelIndex].columnClass;
	}

	@Override
	public int getColumnCount() {
		return columnArray.length;
	}

	@Override
	public String getColumnName(int modelIndex) {
		return columnArray[modelIndex].columnName;
	}

	@SuppressWarnings("rawtypes")
	public static class ColumnContext {
		public final String columnName;
		public final Class columnClass;
		public final boolean isEditable;

		public ColumnContext(String columnName, Class columnClass, boolean isEditable) {
			this.columnName = columnName;
			this.columnClass = columnClass;
			this.isEditable = isEditable;
		}
	}

}