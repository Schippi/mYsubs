package tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AllAdapter implements ActionListener,MouseListener,MouseMotionListener, WindowListener, ChangeListener {
	
	public void addAll(JComponent c){
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" actionperformed");

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" changeEvent");
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" winowActivated");
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" windowClosed");
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" windowClosing");
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" windowDeactivated");
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" windowDeiconified");
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" windowIconified");
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" windowOpened");
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println(getClass().getCanonicalName()+" mouseDragged");
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println(getClass().getCanonicalName()+" mouseMoved");
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" mouseClicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" mouseEntered");
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" mouseExited");
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" mousePressed");
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		System.out.println(getClass().getCanonicalName()+" mouseReleased");
		
	}

}
