package tool;

public class AutoUpdateThread extends Thread {

	MainPanel mp;
	public static final long SECOND = 1000;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	
	public static final int TIME=(int)HOUR;

	public AutoUpdateThread(MainPanel mp) {
		super();
		setName(AutoUpdateThread.class.getCanonicalName());
		this.mp = mp;
		this.start();
	}

	@Override
	public void run() {
		long time = TIME;
		while (true) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			if (mp.autoupdate.isSelected()) {
				mp.updateGo();
			}
		}

	}
}
