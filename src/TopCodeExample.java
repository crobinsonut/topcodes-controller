import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

import topcodes.Scanner;
import topcodes.TopCode;
import webcam.WebCam;
import webcam.WebCamException;


public class TopCodeExample extends JPanel
   implements ActionListener, WindowListener, Camera{

   
   /** The main app window */
   protected JFrame frame;

   /** Camera Manager dialog */
   protected WebCam webcam;

   /** TopCode scanner */
   protected Scanner scanner;

   /** Animates display */
   protected Timer animator;
   
   protected Queue<BufferedImage> frames;
   


   public TopCodeExample() {
      super(true);
      this.frame    = new JFrame("TopCodes Webcam Sample");
      this.webcam   = new WebCam();
      this.scanner  = new Scanner();
      this.animator = new Timer(100, this);  // 10 frames / second
      this.frames = new ArrayDeque();
      
      //--------------------------------------------------
      // Set up the application frame
      //--------------------------------------------------
      setOpaque(true);
      setPreferredSize(new Dimension(640, 480));
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.setContentPane(this);
      frame.addWindowListener(this);
      frame.pack();
      frame.setVisible(true);

      
      //--------------------------------------------------
      // Connect to the webcam (this might fail if the
      // camera isn't connected yet).
      //--------------------------------------------------

      requestFocusInWindow();
      animator.start();
   }


   protected void paintComponent(Graphics graphics) {
      Graphics2D g = (Graphics2D)graphics;
      List<TopCode> codes = null;

      //----------------------------------------------------------
      // Capture a frame from the video stream and scan it for
      // TopCodes. 
      //----------------------------------------------------------
     if (!frames.isEmpty()) {
        codes = scanner.scan(
           frames.remove());
     }
      
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
      g.setFont(new Font(null, 0, 12));

      BufferedImage image = scanner.getImage();
      if (image != null) {
         g.drawImage(image, 0, 0, null);
      }

      if (codes != null) {
         for (TopCode top : codes) {

            // Draw the topcode in place
            top.draw(g);

            //--------------------------------------------
            // Draw the topcode ID number below the symbol
            //--------------------------------------------
            String code = String.valueOf(top.getCode());
            int d = (int)top.getDiameter();
            int x = (int)top.getCenterX();
            int y = (int)top.getCenterY();
            int fw = g.getFontMetrics().stringWidth(code);
            
            g.setColor(Color.WHITE);
            g.fillRect((int)(x - fw/2 - 3),
                       (int)(y + d/2 + 6),
                       fw + 6, 12);
            g.setColor(Color.BLACK);
            g.drawString(code, x - fw/2, y + d/2 + 16);
         }
      }
   }


   
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == animator) repaint();
   }

      
/******************************************************************/
/*                        WINDOW EVENTS                           */
/******************************************************************/
   public void windowClosing(WindowEvent e) {
      frame.setVisible(false);
      frame.dispose();
      System.exit(0);
   }
   
   public void windowActivated(WindowEvent e) { } 
   public void windowClosed(WindowEvent e) { }
   public void windowDeactivated(WindowEvent e) { }
   public void windowDeiconified(WindowEvent e) { } 
   public void windowIconified(WindowEvent e) { } 
   public void windowOpened(WindowEvent e) { }


   public static void main(String[] args) {

      //--------------------------------------------------
      // Fix cursor flicker problem (sort of :( )
      //--------------------------------------------------
      System.setProperty("sun.java2d.noddraw", "");
      
      //--------------------------------------------------
      // Use standard Windows look and feel
      //--------------------------------------------------
      try { 
         UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
      } catch (Exception x) { ; }

      //--------------------------------------------------
      // Schedule a job for the event-dispatching thread:
      // creating and showing this application's GUI.
      //--------------------------------------------------
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               new TopCodeExample();
            }
         });
   }


@Override
public void onInitialized(WebCam camera) {
	// TODO Auto-generated method stub
	
}


@Override
public void onOpened(WebCam camera) {
	// TODO Auto-generated method stub
	
}


@Override
public void onFrame(WebCam camera) {
	// TODO Auto-generated method stub
	frames.add(camera.getFrameImage());
}


@Override
public void onClosed(WebCam camera) {
	// TODO Auto-generated method stub
	
}


@Override
public void onUninitialized(WebCam camera) {
	// TODO Auto-generated method stub
	
}

}
