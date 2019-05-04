package tool;


public class TimeoutThread extends Thread {
	Thread time;
	boolean i;
	int times = 1;
	MainPanel panel;
	long tosleep;
	boolean fertig=false;
	private static final long TIMEOUT=30000;
	private static final String UPDATE_FAILED = "update failed (Timeout)";
	
	public TimeoutThread(Thread updateThread, MainPanel panel) {
		super();
		setName(TimeoutThread.class.getCanonicalName());
		time = updateThread;
		this.panel = panel;
		i = false;
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		int p = 0;
		tosleep=System.currentTimeMillis()+TIMEOUT;
		while (p < times) {
			if(tosleep-System.currentTimeMillis()<=0){
				break;
			}
			try {
				Thread.sleep(tosleep-System.currentTimeMillis());
			} catch (InterruptedException e) {
				i=true;
			}
			p++;
		}
		if (i && !fertig) {
			//TODO Threadsave
			time.stop();
			panel.update.setEnabled(true);
			panel.restoreBtn(UPDATE_FAILED);
		}
	}

	public void bam() {
		tosleep=System.currentTimeMillis()+TIMEOUT;
		times++;
		i = true;
	}
	public void end(){
		fertig=true;
		times=0;
	}

}
