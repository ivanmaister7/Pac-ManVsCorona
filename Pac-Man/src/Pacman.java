import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Pacman extends JFrame implements KeyListener{

	private Thread thread;
    private static Random random = new Random();
    private static final int DIR_STEP = 1;
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private int x = 0;
    private int y = 0;
    int co = 0;
    int num = 0;
    Virus v;
	JLabel pacman;
	JLabel bord[] = new JLabel[2];
	JPanel panel;
	ImageIcon iconPacman = new ImageIcon("images/pacman.png");
	ImageIcon iconBord1 = new ImageIcon("images/bord1.png");
	ImageIcon iconBord2 = new ImageIcon("images/bord2.png");
	
	public Pacman(){
        super("ColorGame");
        createGUI();
        this.addKeyListener(this);
        thread = new MoveThread2(this);
        thread.start();
   }

	public Point getCorner1() {
		Point p = new Point(x,y);
		return p;
	}
	public Point getCorner2() {
		Point p = new Point(x+50,y);
		return p;
	}
	public Point getCorner3() {
		Point p = new Point(x,y+50);
		return p;
	}
	public Point getCorner4() {
		Point p = new Point(x+50,y+50);
		return p;
	}
   public void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(215, 235));

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.black);
        add(panel);
        
        for (int i = 0; i < bord.length; i++) {
			bord[i] = new JLabel();
			bord[i].setIcon(new ImageIcon("images/bord"+(i+1)+".png"));
			bord[i].setBounds(50+i*100,50,50,100);
			panel.add(bord[i] );
		}
       
        
        pacman = new JLabel();
        pacman.setBounds(x, y, 50, 50);
        pacman.setIcon(iconPacman);
        panel.add(pacman);
        
        v = new Virus();
        v.setBounds(50, 0, 45, 45);
        v.add(panel);
        
        
        
	
   }
 //Listener
   
   @Override
   public void keyPressed(KeyEvent e) {
       if (e.getKeyCode()==KeyEvent.VK_LEFT) { 
    	   isLeft = true;
    	   isRight = false;
    	   isUp = false;
    	   isDown = false;  
       }
       if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
    	   isLeft = false;
    	   isRight = true;
    	   isUp = false;
    	   isDown = false; 
       }
       if (e.getKeyCode()==KeyEvent.VK_UP) {
    	   isLeft = false;
    	   isRight = false;
    	   isUp = true;
    	   isDown = false; 
       }
       if (e.getKeyCode()==KeyEvent.VK_DOWN) {
    	   isLeft = false;
    	   isRight = false;
    	   isUp = false;
    	   isDown = true; 
       }
   }


@Override
   public void keyReleased(KeyEvent e) {
       if (e.getKeyCode()==KeyEvent.VK_LEFT) isLeft = false;
       if (e.getKeyCode()==KeyEvent.VK_RIGHT) isRight = false;
       if (e.getKeyCode()==KeyEvent.VK_UP) isUp = false;
       if (e.getKeyCode()==KeyEvent.VK_DOWN) isDown = false;
   }

   @Override
   public void keyTyped(KeyEvent arg0) {}
   

 
   public void animate() {
	   if(x<1) { 
    	   isLeft = false;
	   }
	   if(x>150) { 
    	   isRight = false;
	   }
	   if(y<1) { 
    	   isUp = false;
	   }
	   if(y>150) {
    	   isDown = false;
	   }
	   if(x>50&&x<100&&y>50&&y<150) {
		   isLeft = false;
		   isUp = false;
		   x++;
	   }
	   else if(x+50>50&&x+50<100&&y>50&&y<150) {
		   isRight = false;
		   isUp = false;
	   }
	   else if(x>50&&x<100&&y+50>50&&y+50<150) {
		   isLeft = false;
		   isDown = false;
		   x++;
	   }
	   else if(x+50>50&&x+50<100&&y+50>50&&y+50<150) {
		   
		   isRight = false;
		   isDown = false;
	   }
	   
	   
       if (isLeft) x-=DIR_STEP;
       if (isRight) x+=DIR_STEP;
       if (isUp) y-=DIR_STEP;
       if (isDown) y+=DIR_STEP;
       
       v.anim();
       pacman.setLocation(x, y);
    	  
   }
   
   public static void main(String[] args) {
		Pacman pac = new Pacman();
		pac.pack();
        pac.setLocationRelativeTo(null);
        pac.setVisible(true);
	}
}
//Engine thread

class MoveThread2 extends Thread{
    Pacman runKeyboard;
     
    public MoveThread2(Pacman runKeyboard) {
        super("MoveThread");
        this.runKeyboard = runKeyboard;
    }
     
    public void run(){
        while(true) {
            runKeyboard.animate();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
