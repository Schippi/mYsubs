package tool;

import java.net.URL;

import javax.swing.RowFilter;

@SuppressWarnings("rawtypes")
class MyRowFilter<M, I> extends RowFilter {

	private static final String TEXT_SPACE = " ";
	int[] ind = new int[3];
	String[] match;
boolean onlynew;

	@Override
	public boolean include(Entry entry) {		
		return includeA(entry);
	}

	private boolean includeA(Entry entry) {
		for (int i = 0; i < match.length; i++) {
			if (!(((String) entry.getValue(ind[2])).toLowerCase().contains(
					match[i])
					|| ((String) entry.getValue(ind[1])).toLowerCase()
							.contains(match[i])
					|| ((String) entry.getValue(ind[0])).toLowerCase()
							.contains(match[i]))) {
				return false;
				
			}
		}
		if(onlynew){
			//TODO make the 3 variable
			return ((TestModel)entry.getModel()).getDatabyURL((URL)entry.getValue(3)).isNew();
		}else{
			return true;
		}
	}


	public void init(int a, int b, int c, String match, boolean onlynew) {
		ind[0] = a;
		ind[1] = b;
		ind[2] = c;
		this.onlynew=onlynew;
		this.match = match.toLowerCase().split(TEXT_SPACE);
	}
}
