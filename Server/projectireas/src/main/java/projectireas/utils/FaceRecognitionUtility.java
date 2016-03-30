package projectireas.utils;


import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;

import projectireas.core.FaceResult;

public class FaceRecognitionUtility {

	private static final String TRAINING_DIR = "/home/kartik/workspace/projectireas/src/main/resources/assets/training";

	private final FaceRecognizer faceRecognizer;
	
	
	public FaceRecognitionUtility(){

		File root = new File(TRAINING_DIR);

		FilenameFilter imgFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
			}
		};

		File[] imageFiles = root.listFiles(imgFilter);

		MatVector images = new MatVector(imageFiles.length);

		Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
		IntBuffer labelsBuf = labels.getIntBuffer();

		int counter = 0;

		for (File image : imageFiles) {
			Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
			
			resize(img,img, new Size(100,100));

			int label = Integer.parseInt(image.getName().split("\\-")[0]);

			images.put(counter, img);

			labelsBuf.put(counter, label);

			counter++;
		}

		//faceRecognizer = createFisherFaceRecognizer();
		// FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
		// FaceRecognizer faceRecognizer = createLBPHFaceRecognizer()
		
		faceRecognizer = createFisherFaceRecognizer();
		faceRecognizer.train(images, labels);
	}
	
	
	public FaceRecognitionUtility(final String face_id){

		File root = new File(TRAINING_DIR);

		FilenameFilter imgFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				Boolean startsWith  = name.startsWith(face_id+"-") || name.startsWith("8-") ;
				return (startsWith)&&(name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png"));
			}
		};

		File[] imageFiles = root.listFiles(imgFilter);

		MatVector images = new MatVector(imageFiles.length);

		Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
		IntBuffer labelsBuf = labels.getIntBuffer();

		int counter = 0;

		for (File image : imageFiles) {
			Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
			
			resize(img,img, new Size(100,100));

			int label = Integer.parseInt(image.getName().split("\\-")[0]);

			images.put(counter, img);

			labelsBuf.put(counter, label);

			counter++;
		}

		//faceRecognizer = createFisherFaceRecognizer();
		// FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
		// FaceRecognizer faceRecognizer = createLBPHFaceRecognizer()
		
		faceRecognizer = createFisherFaceRecognizer();
		faceRecognizer.train(images, labels);
	}
	
	
	public FaceResult findTheFace(Mat faceMat) {
		resize(faceMat, faceMat, new Size(100, 100));
		int[] label = new int[1];
		double[] confidence = new double[1];
	    faceRecognizer.predict(faceMat, label, confidence);;
		return new FaceResult(false,label[0], confidence[0]);
	}

}
