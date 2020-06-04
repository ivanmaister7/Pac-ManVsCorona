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
    private static final int HEIGH = 600;
    private static final int WIDHT = 800;
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private int life = 3;
    private int check = 0;
    private int scoreNum = 0;
    private int count = 0;
    private int second = 0;
    private int activeVirusesCount = 0;
    private int x = 0;
    private int y = 0;
    private int pacSize = 50;
    private int pixel[][] = new int [HEIGH][WIDHT];
    Virus virus[] = new Virus[83];
    JLabel activeVirus[] = new JLabel[5];
    JLabel pacLifes[] = new JLabel[3];
	JLabel pacman;
	JLabel score;
	JLabel bord[] = new JLabel[6];
	JPanel panel;
	JLabel but = new JLabel();
	JLabel pointPanel;
	ImageIcon iconPacman = new ImageIcon("images/pacman.png");
	
	public Pacman(){
        super("ColorGame");
        createGUI();
        this.addKeyListener(this);
        thread = new MoveThread2(this);
        thread.start();
   }

   public void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDHT+15, HEIGH+50+35));

        
        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.black);
        for (int i = 0; i < HEIGH; i++) {
        	for (int j = 0; j < WIDHT; j++) {
        		pixel[i][j] = -1;
        	}
		}
        add(panel);
        
        for (int i = 0; i < pacLifes.length; i++) {
			pacLifes[i] = new JLabel();
			pacLifes[i].setIcon(new ImageIcon("images/life.png"));
			pacLifes[i].setBounds(60+60*i,600,50,50);
		    panel.add(pacLifes[i]);
		}
        
        score = new JLabel();
        score.setText(scoreNum+"/"+10*virus.length);
        score.setBounds(650,600,100,50);
        panel.add(score);
        
        pointPanel = new JLabel();
        pointPanel.setIcon(new ImageIcon("images/pointpanel.png"));
        pointPanel.setBounds(0,600,WIDHT,50);
        panel.add(pointPanel);
        
        
        for (int i = 0; i < bord.length; i++) {
			bord[i] = new JLabel();
			bord[i].setIcon(new ImageIcon("images/bord"+(i+1)+".png"));
			panel.add(bord[i]);
		}
        bord[0].setBounds(50,50,200,150);
        addBoardsToPixelList(bord[0]);
        bord[1].setBounds(300,50,200,300);
        addBoardsToPixelList(bord[1]);
        bord[2].setBounds(550,50,200,150);
        addBoardsToPixelList(bord[2]);
        bord[3].setBounds(50,250,200,300);
        addBoardsToPixelList(bord[3]);
        bord[4].setBounds(300,400,200,150);
        addBoardsToPixelList(bord[4]);
        bord[5].setBounds(550,250,200,300);
        addBoardsToPixelList(bord[5]);
        
        but.setBounds(100,0,50,50);
        panel.add(but);
        
        for (int i = 0; i < activeVirus.length; i++) {
        	activeVirus[i] = new JLabel();
			activeVirus[i].setIcon(iconPacman);
			activeVirus[i].setBounds(-100,-100,50,50);
			activeVirus[i].setVisible(false);
			panel.add(activeVirus[i]);
		}
        
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
			putOnPlace(v);
	        addVirusToPixelList(virus[v], v);
			virus[v].add(panel);
		}
   
   }
   
 private void putOnPlace(int v) {
	 int xloc = -1;
	 int yloc = -1;
	 for (int i = 0; i < HEIGH/50; i++) {
     	for (int j = 0; j < WIDHT/50; j++) {
				if(pixel[50*i][50*j]==-1&&xloc+yloc<0) {
					xloc = 50*j;
					yloc = 50*i;
				}
			}
		}
	 virus[v].setBounds(xloc,yloc,50,50);
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
	   timer();
	   if(x<1) { 
    	   isLeft = false;
	   }
	   if(x>WIDHT-50) { 
    	   isRight = false;
	   }
	   if(y<1) { 
    	   isUp = false;
	   }
	   if(y>HEIGH-50) {
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
			   if(x<WIDHT-50)
				   x++;
			   if(y<HEIGH-50)
				   y++;
		   }
		   else if(x+pacSize>x1bord&&x+pacSize<x2bord&&y>y1bord&&y<y2bord) {
			   isRight = false;
			   isUp = false;
			   if(x>0)
				   x--;
			   if(y<HEIGH-50)
				   y++;
		   }
		   else if(x>x1bord&&x<x2bord&&y+pacSize>y1bord&&y+pacSize<y2bord) {
			   isLeft = false;
			   isDown = false;
			   if(x<WIDHT-50)
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
	
     if(second==5*(activeVirusesCount+1)&&activeVirusesCount<5) {
    	 createActiveVirus();
     }
       deathCheck();
       pacman.setLocation(x, y);
       analizationLocation();
       checkFinal();
       for (int i = 0; i < activeVirus.length; i++) {
		movesForActiveViruses(activeVirus[i]);
	}
       
    	  
   }
    
   private void movesForActiveViruses(JLabel AV) {
	   if(AV.isVisible()) {
    	   boolean leftIsClear = false;
    	   boolean rightIsClear = false;
    	   boolean upIsClear = false;
    	   boolean downIsClear = false;
    	   if(pixel[(int) (AV.getBounds().getMinY()-3)][(int) AV.getBounds().getMinX()]!=-2&&pixel[(int) (AV.getBounds().getMinY()-3)][(int) AV.getBounds().getMinX()+45]!=-2) {
    		   upIsClear = true;
    	   }
    	   if(pixel[(int) (AV.getBounds().getMinY())][(int) AV.getBounds().getMinX()+51]!=-2&&pixel[(int) (AV.getBounds().getMinY()+40)][(int) AV.getBounds().getMinX()+51]!=-2) {
    		   rightIsClear = true;
    	   }
    	   if(pixel[(int) (AV.getBounds().getMinY()+3)][(int) AV.getBounds().getMinX()]!=-2&&pixel[(int) (AV.getBounds().getMinY()+3)][(int) AV.getBounds().getMinX()+45]!=-2) {
    		   downIsClear = true;
    	   }
    	   if(pixel[(int) (AV.getBounds().getMinY())][(int) AV.getBounds().getMinX()-3]!=-2&&pixel[(int) (AV.getBounds().getMinY()+45)][(int) AV.getBounds().getMinX()-3]!=-2) {
    		   leftIsClear = true;
    	   }
    	   if(rightIsClear)
    		   AV.setLocation((int)AV.getBounds().getMinX()+1,(int)AV.getBounds().getMinY());
    	   else if(rightIsClear==false) {
    		   int newy = (int)AV.getBounds().getMinY();
    		   if(newy>y)
    			   newy--;
    		   else {
    			   newy++;
    		   }
    		   AV.setLocation((int)AV.getBounds().getMinX(),newy);
    	   }
    		   
    	   //but.setText(""+rightIsClear);
       }
	
}

private void deathCheck() {
	for (int i = 0; i < activeVirus.length; i++) {
		   int x1bord = (int)activeVirus[i].getBounds().getMinX();
		   int y1bord = (int)activeVirus[i].getBounds().getMinY();
		   int x2bord = (int)activeVirus[i].getBounds().getMaxX();
		   int y2bord = (int)activeVirus[i].getBounds().getMaxY();
		   if(x1bord+25>x&&x1bord+25<x+50&&y+50==y1bord) {
			  restartPacman();
		   }
		   else if(y1bord+25>y&&y1bord+25<y+50&&x==x2bord) {
			   restartPacman();
			   }
		   else if(x1bord+25>x&&x1bord+25<x+50&&y==y2bord) {
			   restartPacman();
			   }
		   else if(y1bord+25>y&&y1bord+25<y+50&&x+50==x1bord) {
			   restartPacman();
			   }
	}
}

private void restartPacman() {
	life--;
	pacLifes[life].setVisible(false);
	x = 0;
	y = 0;
	
}

private void createActiveVirus() {
	   boolean complited = false;
	while(complited==false&&ifFinal()==false) {
		int virusx = random.nextInt(pixel.length);
		int virusy = random.nextInt(pixel.length);
		if(pixel[virusy][virusx]>-1) {
			Virus v = virus[pixel[virusy][virusx]];
			virusx = (int) v.getBounds().getMinX();
			virusy = (int) v.getBounds().getMinY();
			if(activeVirusesCount%2==0) {
				virusx+=9;
				virusy+=9;
			}
			activeVirus[activeVirusesCount].setLocation(virusx,virusy);
			activeVirus[activeVirusesCount].setVisible(true);
			activeVirusesCount++;
			complited = true;
		}
		
	}
	
   }

   private void timer() {
	   count++;
	   second = count/100;
   }
   
   private void restartTimer() {
		count = 0;
	}
   
   private void checkFinal() {
	   check = 0;
	   for (int i = 0; i < virus.length; i++) {
		   if(virus[i].isVis()==false) 
			   check++;
	   }
	if(ifFinal())
		pacman.setIcon(iconPacman);
	
	
}
   private boolean ifFinal() {
	   if(check==virus.length)
		   return true;
	   return false;
   }

private void analizationLocation() {
	if(pixel[y+25][x+25]>-1) {
		virus[pixel[y+25][x+25]].setVis(false);
		scoreNum+=10;
		score.setText(scoreNum+"/"+10*virus.length);
		int change = pixel[y+25][x+25];
		for (int i = 0; i < HEIGH; i++) {
        	for (int j = 0; j < WIDHT; j++) {
				if(pixel[i][j]==change)
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
