package tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Stats implements java.io.Serializable {

	private Calendar begin;
	private double avgNew;
	private int nNew;
	private double avgUpdates;
	private int nUpdates;
	private int nClick;
	private double avgClick;
	private int nvids;

	public Stats() {
		begin = Calendar.getInstance();
		begin.setTime(new Date());
		avgClick=0;
		avgNew = 0;
		nNew = 0;
		avgUpdates = 0;
		nUpdates = 0;
		nClick = 0;
	}

	public Calendar getBegin() {
		return begin;
	}

	public void setBegin(Calendar begin) {
		this.begin = begin;
	}

	public double getAvgNew() {
		return avgNew;
	}

	public double getnNew() {
		return nNew;
	}

	public void incnNew(int nNew, int all) {
		this.nNew += nNew;
		nvids=all;
		calcAvg();
	}

	public int getNvids() {
		return nvids;
	}


	public double getAvgUpdates() {
		return avgUpdates;
	}

	public double getnUpdates() {
		return nUpdates;
	}

	public void incnUpdates() {
		this.nUpdates++;
		calcAvg();
	}

	public double getnClick() {
		return nClick;
	}

	public void incnClick() {
		this.nClick++;
		calcAvg();
	}

	public void calcAvg() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		long day = daysBetween(begin, c);
		avgNew = nNew / day;
		avgUpdates = nUpdates / day;
		avgClick = nClick / day;
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}
	
	@Override
	public String toString(){
		String out;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		out="stats since: "+dateFormat.format(begin.getTime());
		out+="  ";
		out+="numvids: "+nvids;
		out+="  ";
		out+="numupdates: "+nUpdates;
		out+="  ";
		out+="avgupdates: "+avgUpdates;
		out+="  ";
		out+="numnew: "+nNew;
		out+="  ";
		out+="avgnew: "+avgNew;
		out+="  ";
		out+="numclicks: "+nClick;
		out+="  ";
		out+="avgclick: "+avgClick;
		out+="  ";
		return out;
	}

}
