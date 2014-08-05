import java.util.List;

import webcam.WebCam;
import webcam.WebCamException;
import topcodes.Scanner;
import topcodes.TopCode;

public class CameraHandler {
	WebCam camera;
	Scanner scanner;
	List<TopCodeListener> listeners;
	
	public CameraHandler(int width, int height){
		camera = new WebCam();
		try {
			this.camera.initialize();

			// ---------------------------------------------
			// This can be set to other resolutions like
			// (320x240) or (1600x1200) depending on what
			// your camera supports
			// ---------------------------------------------
			this.camera.openCamera(width, height); // Dimension of webcam
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public void addListener(TopCodeListener listener){
		this.listeners.add(listener);
	}
	
	public BufferedImage 
	
	public void update(){
		while(true){
			try{
				if(camera.isCameraOpen()){
					camera.captureFrame();
					List<TopCode> topCodes = scanner.scan(camera.getFrameData(), camera.getFrameWidth(), camera.getFrameHeight());
					
					if(!topCodes.isEmpty()){
						for(TopCodeListener listener: this.listeners){
							listener.topCodesDetected(topCodes);
						}
					}
				}
			}catch (WebCamException wcx) {
				System.err.println(wcx);
			}
		}
	}
}
