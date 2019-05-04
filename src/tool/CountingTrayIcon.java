package tool;

import java.awt.Frame;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.Date;

import javax.swing.JFrame;

class CountingTrayIcon extends TrayIcon {
	private static final String TEXT_LINEFEED = "\n";
	int cnt = 0;
	String last_clicked;
	JFrame mother;
	long lastClick=0;
	
	public CountingTrayIcon(JFrame frame, Image image, String tooltip,
			PopupMenu popup) {
		super(image, tooltip);
		mother = frame;
		setPopupMenu(popup);
		
		addMouseListener(new MouseAdapter() {
			
			
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON2) {
					getPopupMenu().show(arg0.getComponent(), 0, 0);
				} else if (arg0.getButton() == MouseEvent.BUTTON1 &&  arg0.getClickCount()%2==0) {
					lastClick=arg0.getWhen();
					mother.setVisible(!mother.isVisible());
					mother.setState(mother.isVisible() ? Frame.NORMAL
							: Frame.ICONIFIED);
					resetcnt();
				}

			}

		});
		last_clicked = genlast();
		updateTooltip();

	}

	public void dispInfo() {
		if (cnt == 1) {
			displayMessage(MainPanel.AUTH_PROGRAM_NAME, cnt
					+ MainPanel.TEXT_TRAY_NEWVID_SINGLE, MessageType.INFO);
		} else {
			displayMessage(MainPanel.AUTH_PROGRAM_NAME, cnt
					+ MainPanel.TEXT_TRAY_NEWVID_MULTI, MessageType.INFO);
		}

	}

	public CountingTrayIcon(JFrame frame, Image image, String tooltip) {
		super(image, tooltip);
		mother = frame;
		last_clicked = genlast();
	}

	private String genlast() {
		Time time = new Time(new Date().getTime());
		return time.toString().substring(0, time.toString().lastIndexOf(':'));
	}

	public void updateTooltip() {
		if (cnt == 1) {
			setToolTip(MainPanel.AUTH_PROGRAM_NAME + TEXT_LINEFEED + cnt
					+ MainPanel.TEXT_TRAY_NEWVID_SINGLE
					+ MainPanel.TEXT_TRAY_SINCE + last_clicked);
		} else {
			setToolTip(MainPanel.AUTH_PROGRAM_NAME + TEXT_LINEFEED + cnt
					+ MainPanel.TEXT_TRAY_NEWVID_MULTI
					+ MainPanel.TEXT_TRAY_SINCE + last_clicked);
		}
	}

	public void updateupdateTooltip(int val) {
		if (cnt == 1) {
			setToolTip(MainPanel.AUTH_PROGRAM_NAME + TEXT_LINEFEED
					+ MainPanel.TEXT_TRAY_UPDATE_RUNNING + val
					+ MainPanel.TEXT_PERCENT + TEXT_LINEFEED + cnt
					+ MainPanel.TEXT_TRAY_NEWVID_SINGLE
					+ MainPanel.TEXT_TRAY_SINCE + last_clicked);
		} else {
			setToolTip(MainPanel.AUTH_PROGRAM_NAME + TEXT_LINEFEED
					+ MainPanel.TEXT_TRAY_UPDATE_RUNNING + val
					+ MainPanel.TEXT_PERCENT + TEXT_LINEFEED + cnt
					+ MainPanel.TEXT_TRAY_NEWVID_MULTI
					+ MainPanel.TEXT_TRAY_SINCE + last_clicked);
		}

	}

	public void updateall(int c, boolean info) {
		int asd = cnt;
		this.addcnt(c);
		if (info) {
			this.dispInfo();
		}
		if (asd != cnt) {
			this.updateTooltip();
		}
		if (mother.isVisible()) {
			resetcnt();
		}
	}

	public void addcnt(int c) {
		cnt += c;
	}

	public void resetcnt() {
		last_clicked = genlast();
		updateTooltip();
		cnt = 0;
	}

}