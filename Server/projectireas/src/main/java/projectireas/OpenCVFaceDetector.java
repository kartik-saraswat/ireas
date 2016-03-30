package projectireas;

import java.io.File;
import java.io.FilenameFilter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class OpenCVFaceDetector {
	
	
	public static void  main(String... args){
		
		nu.pattern.OpenCV.loadLibrary();
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    CascadeClassifier faceDetector = new CascadeClassifier("/home/kartik/Downloads/opencv-2.4.11/data/haarcascades/haarcascade_frontalface_alt.xml");
	    
	    File root = new File("/home/kartik/Pictures/akshay_pics");
	    
	    FilenameFilter imgFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
			}
		};
	    
		File[] imageFiles = root.listFiles(imgFilter);
		
		for(File image: imageFiles){
			Mat img = Highgui.imread(image.getAbsolutePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE);
			
	        MatOfRect faceDetections = new MatOfRect();
	        faceDetector.detectMultiScale(img, faceDetections);
	        
	        for (Rect rect : faceDetections.toArray()) {
	        	Mat faceCropped = new Mat(img,rect);
	        	Highgui.imwrite(image.getAbsolutePath() , faceCropped);
	        }
		}
	}
}
