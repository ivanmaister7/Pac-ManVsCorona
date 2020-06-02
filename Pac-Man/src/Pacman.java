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
    private int check = 0;
    private int x = 0;
    private int y = 0;
    private int pacSize = 50;
    private int pixel[][] = new int [200][200];
    Virus virus[] = new Virus[10];
	JLabel pacman;
	JLabel bord[] = new JLabel[2];
	JPanel panel;
	JLabel but = new JLabel();
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

   public void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(215, 235));

        
        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.black);
        for (int i = 0; i < pixel.length; i++) {
        	for (int j = 0; j < pixel.length; j++) {
        		pixel[i][j] = -1;
        	}
		}
        add(panel);
        
        for (int i = 0; i < bord.length; i++) {
			bord[i] = new JLabel();
			bord[i].setIcon(new ImageIcon("images/bord"+(i+1)+".png"));
			panel.add(bord[i]);
		}
        bord[0].setBounds(50,50,50,100);
        addBoardsToPixelList(bord[0]);
        bord[1].setBounds(150,0,50,150);
        addBoardsToPixelList(bord[1]);
        
        but.setBounds(100,0,50,50);
        panel.add(but);
        
        pacman = new JLabel();
        pacman.setBounds(x, y, pacSize, pacSize);
        pacman.setIcon(iconPacman);
        for (int i = 0; i < 50; i++) {
        	for (int j = 0; j < 50; j++) {
        		pixel[i][j] = -3;
        	}
		}
        panel.add(pacman);
        
        for (int v = 0; v < virus.length; v++) {
			virus[v] = new Virus();
			virus[v].add(panel);
		}
        virus[0].setBounds(50,0,50,50);
        addVirusToPixelList(virus[0], 0);
        virus[1].setBounds(100,0,50,50);
        addVirusToPixelList(virus[1], 1);
        virus[2].setBounds(0,50,50,50);
        addVirusToPixelList(virus[2], 2);
        virus[3].setBounds(100,50,50,50);
        addVirusToPixelList(virus[3], 3);
        virus[4].setBounds(0,100,50,50);
        addVirusToPixelList(virus[4], 4);
        virus[5].setBounds(100,100,50,50);
        addVirusToPixelList(virus[5], 5);
        virus[6].setBounds(0,150,50,50);
        addVirusToPixelList(virus[6], 6);
        virus[7].setBounds(50,150,50,50);
        addVirusToPixelList(virus[7], 7);
        virus[8].setBounds(100,150,50,50);
        addVirusToPixelList(virus[8], 8);
        virus[9].setBounds(150,150,50,50);
        addVirusToPixelList(virus[9], 9);
        
        
        
	
   }
 private void addBoardsToPixelList(JLabel bord) {
	for (int i = (int) bord.getBounds().getMinY(); i < (int) bord.getBounds().getMaxY(); i++) {
		for (int j = (int) bord.getBounds().getMinX(); j < (int) bord.getBounds().getMaxX(); j++) {
			pixel[i][j] = -2;
		}
	}
	
}
 private void addVirusToPixelList(Virus bord,int index) {
		for (int i = (int) bord.getBounds().getMinY(); i < (int) bord.getBounds().getMinY()+50; i++) {
			for (int j = (int) bord.getBounds().getMinX(); j < (int) bord.getBounds().getMinX()+50; j++) {
				pixel[i][j] = index;
			}
		}
		
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
	   
	   for (int i = 0; i < bord.length; i++) {
		   int x1bord = (int)bord[i].getBounds().getMinX();
		   int y1bord = (int)bord[i].getBounds().getMinY();
		   int x2bord = (int)bord[i].getBounds().getMaxX();
		   int y2bord = (int)bord[i].getBounds().getMaxY();
		   if(x>x1bord&&x<x2bord&&y>y1bord&&y<y2bord) {
			   isLeft = false;
			   isUp = false;
			   if(x<150)
				   x++;
			   if(y<150)
				   y++;
		   }
		   else if(x+pacSize>x1bord&&x+pacSize<x2bord&&y>y1bord&&y<y2bord) {
			   isRight = false;
			   isUp = false;
			   if(x>0)
				   x--;
			   if(y<150)
				   y++;
		   }
		   else if(x>x1bord&&x<x2bord&&y+pacSize>y1bord&&y+pacSize<y2bord) {
			   isLeft = false;
			   isDown = false;
			   if(x<150)
				   x++;
			   if(y>0)
				   y--;
		   }
		   else if(x+pacSize>x1bord&&x+pacSize<x2bord&&y+pacSize>y1bord&&y+pacSize<y2bord) {
			   isRight = false;
			   isDown = false;
			   if(x>0)
				   x--;
			   if(y>0)
				   y--;
		   }
	}
	   
	   
       if (isLeft) x-=DIR_STEP;
       if (isRight) x+=DIR_STEP;
       if (isUp) y-=DIR_STEP;
       if (isDown) y+=DIR_STEP;
       
     for (int i = 0; i < virus.length; i++) {
		virus[i].anim();
	}
	
     //but.setText(""+check);
       pacman.setLocation(x, y);
       analizationLocation();
       checkFinal();
    	  
   }
   
   private void checkFinal() {
	   check = 0;
	   for (int i = 0; i < virus.length; i++) {
		   if(virus[i].isVis()==false) 
			   check++;
	   }
	if(check==virus.length)
		pacman.setIcon(iconBord1);
	
	
}

private void analizationLocation() {
	if(pixel[y+25][x+25]>-1) {
		virus[pixel[y+25][x+25]].setVis();
		for (int i = 0; i < pixel.length; i++) {
			for (int j = 0; j < pixel.length; j++) {
				if(pixel[i][j]==pixel[y+25][x+25])
					pixel[i][j] = -5;
			}
		}
	}
	
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
