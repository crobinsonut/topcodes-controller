/*
 * @(#) WebCamSample.java
 * 
 * Tangible Object Placement Codes (TopCodes)
 * Copyright (c) 2007 Michael S. Horn
 * 
 *           Michael S. Horn (michael.horn@tufts.edu)
 *           Tufts University Computer Science
 *           161 College Ave.
 *           Medford, MA 02155
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2) as
 * published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
import webcam.*;
import topcodes.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.*;

import java.util.Queue;

/**
 * This is a sample application that integrates the webcam library
 * with the TopCode scanner.  This code will only work on windows
 * machines--I tested with XP, but it should work fine with 
 * Vista as well.
 * 
 * To run this sample, you will need a webcamera with VGA (640x480)
 * resolution.  A Logitech QuickCam is a good choice.  Plug in your
 * camera, and then use this command to launch the demo:
 * <blockquote>
 *   $ java -cp lib/topcodes.jar WebCamSample
 * </blockquote>
 *
 * @author Michael Horn
 * @version $Revision: 1.1 $, $Date: 2008/02/04 15:00:59 $
 */
public class WebCamSample extends JPanel
   implements ActionListener, WindowListener {

   
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/** The main app window */
   protected JFrame frame;

   /** Camera Manager dialog */
   protected WebCam webcam;

   /** TopCode scanner */
   protected Scanner scanner;

   /** Animates display */
   protected Timer animator;
   
   /** List for previous frames **/
   protected ArrayList<TopCode> topCodes;
   
   /** number of frames to use for average**/
   protected int frame_average;
   
   public WebCamSample() {
      super(true);
      this.frame    = new JFrame("TopCodes Webcam Sample");
      this.webcam   = new WebCam();
      this.scanner  = new Scanner();
      this.animator = new Timer(100, this);  // 10 frames / second
      this.topCodes = new ArrayList<TopCode>(3);
      this.frame_average = 7;      
      
      //--------------------------------------------------
      // Set up the application frame
      //--------------------------------------------------
      setOpaque(true);
      setPreferredSize(new Dimension(640, 480)); //Dimension of player: 640 x 480
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.setContentPane(this);
      frame.addWindowListener(this);
      frame.pack();
      frame.setVisible(true);

      
      //--------------------------------------------------
      // Connect to the webcam (this might fail if the
      // camera isn't connected yet).
      //--------------------------------------------------
      try {
         this.webcam.initialize();

         //---------------------------------------------
         // This can be set to other resolutions like
         // (320x240) or (1600x1200) depending on what
         // your camera supports
         //---------------------------------------------
         this.webcam.openCamera(640, 480); //Dimension of webcam
      } catch (Exception x) {
         x.printStackTrace();
      }

      requestFocusInWindow();
      animator.start();
   }
   
   protected void paintComponent(Graphics graphics) {
      Graphics2D g = (Graphics2D)graphics;
      List<TopCode> codes = null;
      //ArrayList<TopCode> filter = new ArrayList<TopCode>(3);
      //filter.add(top.getCode());
      
      //----------------------------------------------------------
      // Capture a frame from the video stream and scan it for
      // TopCodes. 
      //----------------------------------------------------------
      try {
         if (webcam.isCameraOpen()) {
            webcam.captureFrame();
            codes = scanner.scan(
               webcam.getFrameData(),
               webcam.getFrameWidth(),
               webcam.getFrameHeight());
         }
      } catch (WebCamException wcx) {
         System.err.println(wcx);
      }

      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON
                         //RenderingHints.KEY_RENDERING,
                         //RenderingHints.VALUE_RENDER_QUALITY
                         );


      BufferedImage image = scanner.getImage();
      if (image != null) {
         g.drawImage(image, 0, 0, null);
      }

      if (codes != null && codes.size() > 0) {
    	  for(TopCode top: codes){
    		  int z = (int) (250.825 * 100 / top.getDiameter());
    		  top.draw(g);
    		  System.out.println(top.getCenterX() + " " + top.getCenterY() + " " + z);
    		  
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
      this.webcam.closeCamera();
      this.webcam.uninitialize();
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

   public void setCode() {}
   public static void main(String[] args) {

      //--------------------------------------------------
      // Fix cursor flicker problem (sort of :( )
      //--------------------------------------------------
      System.setProperty("sun.java2d.noddraw", "true");
      
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
               new WebCamSample();
            }
         });
   }
}

