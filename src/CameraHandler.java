import java.util.ArrayList;
import java.util.List;

import webcam.WebCam;
import webcam.WebCamException;
import topcodes.Scanner;
import topcodes.TopCode;

public class CameraHandler implements Runnable{
	WebCam camera;
	Scanner scanner;
	List<Camera> listeners;
	int width;
	int height;
	
	public CameraHandler(int width, int height){
		camera = new WebCam();
		listeners = new ArrayList<Camera>();
		this.width = width;
		this.height = height;
	}
	
	public void addListener(Camera listener){
		this.listeners.add(listener);
	}
	
	public void initialize(){
		try {
			this.camera.initialize();
			for(Camera listener: this.listeners){
				listener.onInitialized(camera);
			}
		} catch (WebCamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void open(int width, int height){
		try {
			this.camera.openCamera(width, height);
			for(Camera listener: this.listeners){
				listener.onOpened(camera);
			}
		} catch (WebCamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void captureFrame(){
		try {
			this.camera.captureFrame();
			for(Camera listener: this.listeners){
				listener.onFrame(camera);
			}
		} catch (WebCamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		this.camera.closeCamera();
		for(Camera listener: this.listeners){
			listener.onClosed(camera);
		}
	}
	
	public void uninitialize(){
		this.camera.uninitialize();
		for(Camera listener: this.listeners){
			listener.onUninitialized(camera);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Initialize and open the camera
		initialize();
		open(width, height);
		
		//Start capturing frames
		//If the thread is interrupted, we stop capturing frames
		while(!Thread.currentThread().isInterrupted()){
			captureFrame();
		}
		
		//Close the camera
		close();
		uninitialize();
	}
	
}
