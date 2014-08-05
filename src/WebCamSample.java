

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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import processing.core.PApplet;
import topcodes.*;
import webcam.WebCam;
import webcam.WebCamException;

/**
 * This is a sample application that integrates the webcam library with the
 * TopCode scanner. This code will only work on windows machines--I tested with
 * XP, but it should work fine with Vista as well.
 * 
 * To run this sample, you will need a webcamera with VGA (640x480) resolution.
 * A Logitech QuickCam is a good choice. Plug in your camera, and then use this
 * command to launch the demo: <blockquote> $ java -cp lib/topcodes.jar
 * WebCamSample </blockquote>
 *
 * @author Michael Horn
 * @version $Revision: 1.1 $, $Date: 2008/02/04 15:00:59 $
 */
public class WebCamSample extends JPanel implements WindowListener, ActionListener{

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

	/** Panel */
	JPanel pnlButton = new JPanel();

	/** Button */
	JButton btnSetOrigin = new JButton("Set Origin");

	/** List for previous frames **/
	protected List<TopCode> topCodes;

	/** number of frames to use for average **/
	protected int frame_average;

	/** X Offset */
	float xoffset = 0;

	/** Y Offset */
	float yoffset = 0;
	
	int screen_x, screen_y = 0;
	
	int world_x, world_y = 0;

	/** Calibration Phase */
	boolean CalibrationPhase = true;

	public WebCamSample() {
		super();
		setLayout(new BorderLayout());
		PApplet embedded = new MySketch();
		add(embedded, BorderLayout.CENTER);
		//embedded.init();
		
		this.frame = new JFrame("TopCodes Webcam Sample");
		this.frame.addWindowListener(this);
		this.webcam = new WebCam();
		this.scanner = new Scanner();
		this.animator = new Timer(100, this); // 10 frames / second

		//this.topCodes = new ArrayList<TopCode>(3);
		//this.frame_average = 7;

		// --------------------------------------------------
		// Set up the application frame
		// --------------------------------------------------
		
		setPreferredSize(new Dimension(640, 480)); // Dimension of player: 640 x
													// 480
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setContentPane(this);
		//frame.addWindowListener(this);
		frame.pack();
		this.setVisible(true);
		frame.setVisible(true);
		//btnSetOrigin.setBounds(60, 400, 220, 30);
		//pnlButton.setBounds(800, 800, 200, 100);
		//pnlButton.add(btnSetOrigin);
		//add(pnlButton);
		//btnSetOrigin.setBorderPainted(false);
		//btnSetOrigin.setOpaque(false);
		frame.setTitle("TOPCode Viewer");

		/**
		 * This code segment will open an infinite amount of java applications
		 * running video Do not uncomment out these lines.
		 */
		/**
		 * WebCamSample WinSize = new WebCamSample(); WinSize.setSize(700, 520);
		 * frame.getContentPane().add(WinSize); frame.pack();
		 * frame.setVisible(true);
		 */

		// --------------------------------------------------
		// Connect to the webcam (this might fail if the
		// camera isn't connected yet).
		// --------------------------------------------------
		try {
			this.webcam.initialize();

			// ---------------------------------------------
			// This can be set to other resolutions like
			// (320x240) or (1600x1200) depending on what
			// your camera supports
			// ---------------------------------------------
			this.webcam.openCamera(640, 480); // Dimension of webcam
		} catch (Exception x) {
			x.printStackTrace();
		}

		requestFocusInWindow();
		animator.start();
		embedded.init();
	}

	/******************************************************************/
	public void windowClosing(WindowEvent e) {
		this.webcam.closeCamera();
		this.webcam.uninitialize();
		frame.setVisible(false);
		frame.dispose();
		System.exit(0);
	}

	public static void main(String[] args) {

		// --------------------------------------------------
		// Fix cursor flicker problem (sort of :( )
		// --------------------------------------------------
		System.setProperty("sun.java2d.noddraw", "true");

		// --------------------------------------------------
		// Use standard Windows look and feel
		// --------------------------------------------------
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception x) {
			;
		}

		// --------------------------------------------------
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		// --------------------------------------------------
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new WebCamSample();
				
			}
		});
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == animator){
			try {
				if (webcam.isCameraOpen()) {
					webcam.captureFrame();
					topCodes = scanner.scan(webcam.getFrameData(),
							webcam.getFrameWidth(), webcam.getFrameHeight());
					System.out.println(topCodes);
				}
			} catch (WebCamException wcx) {
				System.err.println(wcx);
			}
			animator.restart();
		}
		
	}
}
