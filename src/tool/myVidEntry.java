package tool;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.api.services.youtube.model.PlaylistItem;

public class myVidEntry implements Serializable, Comparable<myVidEntry> {
	private boolean clicked, _new;
	private String user;
	private String myName;
	private String mylink;
	private String myDate;
	private long duration = 0;

	private static final long serialVersionUID = -6053513699759863886L;
	
	public static String VID_URL_START = "https://www.youtube.com/watch?v=";

	public myVidEntry(PlaylistItem ve) {
		try {
			duration = 0;
			myDate = ve.getContentDetails().getVideoPublishedAt().toStringRfc3339();
			myDate = myDate.replaceAll("T", " ").substring(0, myDate.length()-8);
			clicked = false;
			setUser(ve.getSnippet().getChannelTitle());
			myName = ve.getSnippet().getTitle();
			setNew(true);
			mylink = VID_URL_START+ve.getContentDetails().getVideoId();
			
//			duration = ve.getMediaGroup().getDuration();
//			myDate = ve.getPublished().toUiString();
//			clicked = false;
//			setUser(ve.getAuthors().get(0).getName());
//			MediaPlayer mediaPlayer = ve.getMediaGroup().getPlayer();
//			mylink = mediaPlayer.getUrl().substring(0,
//					mediaPlayer.getUrl().lastIndexOf('&'));
//			myName = ve.getTitle().getPlainText();
//			setNew(true);
		} catch (Exception a) {
			myName = "corrupt Entry";
			mylink = "http://www.google.de                 ";
			myDate = "01-01-1980";
			setUser("corrupt Entry");
			clicked = false;
			setNew(false);
		}
	}

	public static myVidEntry faveGen(myVidEntry m) {
		myVidEntry v = new myVidEntry();
		v._new = false;
		v.myDate = dateToString(new Date(System.currentTimeMillis()));
		v.clicked = m.clicked;
		v.mylink = m.mylink;
		v.myName = m.myName;
		v.duration = m.duration;
		v.setUser(m.getUser());
		return v;

	}

	public static String dateToString(Date a) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(a);
	}

	public static myVidEntry getDummy(String link) {
		myVidEntry v = new myVidEntry();
		v.mylink = link;
		return v;
	}

	public myVidEntry() {
		super();
		myName = "test";
		mylink = "http://www.google.de";
		myDate = "jetzt";
	}

	public void setclicked(boolean a) {
		clicked = a;
	}
	
	public long getDuration() {
		return duration;
	}


	public String getTitle() {
		return myName;
	}

	public boolean isClicked() {
		return clicked;
	}

	public String getDate() {
		return myDate;
	}

	public String getLink() {
		return mylink;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof myVidEntry) {
			myVidEntry v = (myVidEntry) o;
			return this.getLink().equals(v.getLink());

		}
		return false;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	/**
	 * @param _new
	 *            the _new to set
	 */
	public void setNew(boolean _new) {
		this._new = _new;
	}

	/**
	 * @return the _new
	 */
	public boolean isNew() {
		return _new;
	}

	@Override
	public String toString() {
		return myName + "\n\t" + mylink + "\n";
	}

	@Override
	public int compareTo(myVidEntry arg0) {
		return arg0.getLink().compareTo(getLink());
	}

}
