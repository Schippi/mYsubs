package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResizableFrame extends JFrame{
	 
	
	public ResizableFrame(String title){
		super(title);
		addResizer();
	}
    int mouseX = 0;
    int mouseY = 0;
   
    public void addResizer(){
        JPanel resizer = new JPanel();
        resizer.setPreferredSize(new Dimension(100,30));
        resizer.setBackground(Color.red);
        resizer.addMouseListener(new MouseListener() {
           
            @Override
			public void mouseReleased(MouseEvent e) {}
           
            @Override
			public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
               
            }
           
            @Override
			public void mouseExited(MouseEvent e) {}
           
            @Override
			public void mouseEntered(MouseEvent e) {}
           
            @Override
			public void mouseClicked(MouseEvent e) {}
        });
        resizer.addMouseMotionListener(new MouseMotionListener() {
           
            @Override
			public void mouseMoved(MouseEvent e) {}
           
            @Override
			public void mouseDragged(MouseEvent e) {
                setSize(getWidth()-(mouseX-e.getX()), getHeight()-(mouseY-e.getY()));
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
       
        JLabel label = new JLabel("Drag here");
        resizer.add(label);
       
        JPanel background = new JPanel();
        getContentPane().add(background);
        background.add(resizer);
    }


}