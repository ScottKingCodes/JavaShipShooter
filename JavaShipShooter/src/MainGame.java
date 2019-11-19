/**
 * 
 */

/**
 * @author Scott King
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;

public class MainGame extends JFrame implements KeyListener, ActionListener
{
	private Ship ship;
	private JButton startButton;
	private JButton quitButton;
	private JButton pauseButton;
	private boolean isRunning = false;
	private boolean paused = false;
	private boolean upkey, leftkey, rightkey, downkey = false;
	private int fps = 60;
	private int frameCount = 0;
	
    public MainGame()
    {
    	this.ship=new Ship();
    	this.startButton = new JButton("Start");
    	this.startButton.setFocusable(false);
    	this.quitButton = new JButton("Quit");
    	this.quitButton.setFocusable(false);
    	this.pauseButton = new JButton("Pause");
    	this.pauseButton.setFocusable(false);
    	this.setTitle("Space Shooter by Scott King");
		this.setResizable(false);
		this.setSize(1200, 800);
		this.setMinimumSize(new Dimension(1200, 800));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = this.getContentPane();
		JPanel p = new JPanel();
	    p.setLayout(new GridLayout(1,2));
	    p.add(startButton);
	    p.add(pauseButton);
	    p.add(quitButton);
	    cp.setLayout(new BorderLayout());
	    cp.setBackground(Color.BLACK);
	    cp.add(ship);
		cp.add(p, BorderLayout.SOUTH);	
		startButton.addActionListener(this);
	    quitButton.addActionListener(this);
	    pauseButton.addActionListener(this);
        addKeyListener(this);
        setFocusable(true);
        this.setVisible(true);
        setFocusTraversalKeysEnabled(false);
    }
    

	public static void main(String[] args) 
	{
		MainGame game = new MainGame();	
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		 if(e.getKeyCode()== KeyEvent.VK_RIGHT)
			rightkey = true;
	     else if(e.getKeyCode()== KeyEvent.VK_LEFT)
	    	leftkey = true;
	     else if(e.getKeyCode()== KeyEvent.VK_DOWN)
	        downkey = true;
	     else if(e.getKeyCode()== KeyEvent.VK_UP)
	        upkey = true;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getKeyCode()== KeyEvent.VK_RIGHT)
			rightkey = false;
	    else if(e.getKeyCode()== KeyEvent.VK_LEFT)
	    	leftkey = false;
	    else if(e.getKeyCode()== KeyEvent.VK_DOWN)
	        downkey = false;
	    else if(e.getKeyCode()== KeyEvent.VK_UP)
	        upkey = false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void keyCheck()
	{
		if (upkey)
		{
			ship.moveUp();
		}
		
		if (leftkey)
		{
			ship.moveLeft();
		}
		
		if (rightkey)
		{
			ship.moveRight();
		}
		
		if (downkey)
		{
			ship.moveDown();
		}
	}

	//Only run this in another Thread!
	public void gameLoop() {
	      final double GAME_HERTZ = 30.0;
	      //Calculate how many ns each frame should take for our target game hertz.
	      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	      //At the very most we will update the game this many times before a new render.
	      final int MAX_UPDATES_BEFORE_RENDER = 5;
	      //We will need the last update time.
	      double lastUpdateTime = System.nanoTime();
	      //Store the last time we rendered.
	      double lastRenderTime = System.nanoTime();
	      
	      //If we are able to get as high as this FPS, don't render again.
	      final double TARGET_FPS = 60;
	      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
	      
	      //FPS.
	      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
	      
	      while (isRunning)
	      {
	         double now = System.nanoTime();
	         int updateCount = 0;
	         
	         if (!paused)
	         {
	             //Do as many game updates as we need to, potentially playing catchup.
	            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
	            {
	               lastUpdateTime += TIME_BETWEEN_UPDATES;
	               updateCount++;
	            }
	   
	            //If for some reason an update takes forever, we don't want to do an insane number of catchups.
	            //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
	            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
	            {
	               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
	            }
	         
	    
	            ship.updatePosition();
	            keyCheck();
	            lastRenderTime = now;
	         
	            //Update the frames we got.
	            int thisSecond = (int) (lastUpdateTime / 1000000000);
	            if (thisSecond > lastSecondTime)
	            {
	               fps = frameCount;
	               frameCount = 0;
	               lastSecondTime = thisSecond;
	            }
	         
	            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
	            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
	            {
	               Thread.yield();
	            
	               //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
	               //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
	               //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
	               try {Thread.sleep(1);} catch(Exception e) {} 
	            
	               now = System.nanoTime();
	            }
	         }
	      }
	}
	
	//Starts a new thread and runs the game loop in it.
	public void runGameLoop()
	{
	   Thread loop = new Thread()
	   {
	      public void run()
	      {
	         gameLoop();
	      }
	   };
	   loop.start();
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
	      if (s == startButton)
	      {
	         isRunning = !isRunning;
	         if (isRunning)
	         {
	            startButton.setText("Stop");
	            runGameLoop();
	         }
	         else
	         {
	            startButton.setText("Start");
	         }
	      }
	      else if (s == pauseButton)
	      {
	        paused = !paused;
	         if (paused)
	         {
	            pauseButton.setText("Unpause");
	         }
	         else
	         {
	            pauseButton.setText("Pause");
	         }
	      }
	      else if (s == quitButton)
	      {
	         System.exit(0);
	      }
	     
	}

}
