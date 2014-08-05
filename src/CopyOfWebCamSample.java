

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

import webcam.WebCam;

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
public class CopyOfWebCamSample{

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	/** Camera Manager dialog */
	protected WebCam webcam;

	public CopyOfWebCamSample() {
		this.webcam = new WebCam();

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
			System.out.println("Opened camera");
			this.webcam.closeCamera();
			System.out.println("Closed camera");
			this.webcam.uninitialize();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// --------------------------------------------------
		// Fix cursor flicker problem (sort of :( )
		// --------------------------------------------------
		//System.setProperty("sun.java2d.noddraw", "true");

		// --------------------------------------------------
		// Use standard Windows look and feel
		// --------------------------------------------------
		//try {
		//	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//} catch (Exception x) {
		//	;
		//}

		// --------------------------------------------------
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		// --------------------------------------------------
		//javax.swing.SwingUtilities.invokeLater(new Runnable() {
		//	@Override
		//	public void run() {
		//		new CopyOfWebCamSample();
		//	}
		//});
		//new CopyOfWebCamSample();
		WebCam webcam = new WebCam();
		try {
			webcam.initialize();

			// ---------------------------------------------
			// This can be set to other resolutions like
			// (320x240) or (1600x1200) depending on what
			// your camera supports
			// ---------------------------------------------
			webcam.openCamera(640, 480); // Dimension of webcam
			System.out.println("Opened camera");
			webcam.closeCamera();
			System.out.println("Closed camera");
			webcam.uninitialize();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
