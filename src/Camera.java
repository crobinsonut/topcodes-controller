import webcam.*;

public interface Camera {
	public abstract void onInitialized(WebCam camera);
	public abstract void onOpened(WebCam camera);
	public abstract void onFrame(WebCam camera);
	public abstract void onClosed(WebCam camera);
	public abstract void onUninitialized(WebCam camera);
}

