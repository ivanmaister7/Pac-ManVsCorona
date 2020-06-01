import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Virus extends JLabel{
	
	ImageIcon icon = new ImageIcon("images/coronav.png");
	JLabel virus;
	int co = 0;
	int num = 0;
	
	public Virus() {
		virus = new JLabel();
		virus.setBorder(BorderFactory.createLineBorder(Color.black));
		virus.setIcon(icon);
		
	}
	public void setBounds(int x, int y,int width, int height) {
		virus.setBounds(x,y,width,height);
	}
	public void add(JPanel panel) {
		panel.add(virus);
	}
	public void anim() {
		co++;
	       if((co/100)%2==0) {
	    	   num++;
	       }
	       else if((co/100)%2==1) {
	    	   num--;
	       }
	       virus.setBounds(50-num/10,0-num/10, 50+2*num/10, 50+2*num/10);
	}

}
