package gui;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import org.bridj.Pointer;
import org.bridj.cpp.com.COMRuntime;
import org.bridj.cpp.com.shell.ITaskbarList3;

public class StartProgressBar extends JProgressBar {

	private JFrame frame;
	private boolean onWin7;
	Pointer<Integer> pnt;
	ITaskbarList3 taskbar;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public StartProgressBar(JFrame j) {
		super();
		frame = j;
		onWin7 = System.getProperty("os.name").toString().equals("Windows 7")
				|| System.getProperty("os.name").toString().equals("Windows 8");

		if (onWin7) {
			try {
				taskbar = COMRuntime.newInstance(ITaskbarList3.class);
				boolean isvisible = frame.isVisible();
				frame.setVisible(true);
				pnt = (Pointer<Integer>) Pointer
						.pointerToAddress(com.sun.jna.Native
								.getComponentID(frame));
				frame.setVisible(isvisible);
				taskbar.SetProgressState(pnt,
						ITaskbarList3.TbpFlag.TBPF_NOPROGRESS);
			} catch (ClassNotFoundException e) {
				onWin7 = false;
			}
		}
	}

	@Override
	public void setIndeterminate(boolean a) {
		super.setIndeterminate(a);
		if (onWin7) {
			if (a) {
				taskbar.SetProgressState(pnt,
						ITaskbarList3.TbpFlag.TBPF_INDETERMINATE);
			} else {
				taskbar.SetProgressState(pnt, ITaskbarList3.TbpFlag.TBPF_NORMAL);
			}
		}
	}

	@Override
	public void setValue(int a) {
		super.setValue(a);
		if (onWin7) {
			taskbar.SetProgressState(pnt, ITaskbarList3.TbpFlag.TBPF_NORMAL);
			taskbar.SetProgressValue(pnt, (long) (getPercentComplete() * 100),
					100);
		}
	}
	
	public void killFrame(){
		frame.setVisible(false);
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
		taskbar.Release();
	}

}
