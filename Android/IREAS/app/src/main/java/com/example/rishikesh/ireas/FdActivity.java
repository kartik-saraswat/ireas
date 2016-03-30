package com.example.rishikesh.ireas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rishikesh.ireas.rest.model.Exam;
import com.example.rishikesh.ireas.rest.model.FaceResult;
import com.example.rishikesh.ireas.rest.model.SMat;
import com.example.rishikesh.ireas.rest.model.User;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FdActivity extends Activity implements CvCameraViewListener2 {

    private static final String TAG                 = "OCVSample::Activity";
    private static final Scalar SCALAR_COLOR_RED  = new Scalar(255,0,0, 255);
    private static final Scalar SCALAR_COLOR_GREEN  = new Scalar(0,255,0, 255);
    private static final Scalar SCALAR_COLOR_BLUE  = new Scalar(0,0,255, 255);
    private static final long  DETECT_THRESHOLD_MILLISECONDS  = 1000;

    private static Scalar FACE_RECT_COLOR     = SCALAR_COLOR_RED;

    public static final int        JAVA_DETECTOR       = 0;
    public static final int        NATIVE_DETECTOR     = 1;

    private MenuItem mItemFace50;
    private MenuItem mItemFace40;
    private MenuItem mItemFace30;
    private MenuItem mItemFace20;
    private MenuItem mItemType;

    private Mat mRgba;
    private Mat mGray;
    private Mat faceMat = null;

    private Rect faceRectArea = null;

    private File mCascadeFile;
    private CascadeClassifier mJavaDetector;
    private DetectionBasedTracker mNativeDetector;

    private Timestamp lastTimeStamp       = new Timestamp(0L);
    private int                    mDetectorType       = JAVA_DETECTOR;
    private String[]               mDetectorName;

    private float                  mRelativeFaceSize   = 0.2f;
    private int                    mAbsoluteFaceSize   = 0;

    private CameraBridgeViewBase mOpenCvCameraView;
    private Rect regionOfInterest;
    private Bitmap faceBitMap = null;

    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }
    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");

                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("detection_based_tracker");

                    try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

                        mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);

                        cascadeDir.delete();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }

                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public FdActivity() {
        mDetectorName = new String[2];
        mDetectorName[JAVA_DETECTOR] = "Java";
        mDetectorName[NATIVE_DETECTOR] = "Native (tracking)";

        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.face_detect_surface_view);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.fd_activity_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mGray = new Mat();
        mRgba = new Mat();
    }

    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        int height = mGray.height();
        int width = mGray.width();

        int cX = width/2;
        int cY = height/2;

        int rHeight = Math.round(1.5f * height * mRelativeFaceSize);
        int rWidth = Math.round(width * mRelativeFaceSize);


        if(regionOfInterest ==  null) {
            Point topLeftPoint = new Point(cX - rWidth, cY - rHeight);
            Point bottomRightPoint = new Point(cX + rWidth, cY + rHeight);
            regionOfInterest = new Rect(topLeftPoint, bottomRightPoint);
        }

        if (mAbsoluteFaceSize == 0) {

            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
            mNativeDetector.setMinFaceSize(mAbsoluteFaceSize);
        }

        MatOfRect faces = new MatOfRect();

        if (mDetectorType == JAVA_DETECTOR) {
            if (mJavaDetector != null)
                mJavaDetector.detectMultiScale(mGray, faces, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                        new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
        }
        else if (mDetectorType == NATIVE_DETECTOR) {
            if (mNativeDetector != null)
                mNativeDetector.detect(mGray, faces);
        }
        else {
            Log.e(TAG, "Detection method is not selected!");
        }

        Rect[] facesArray = faces.toArray();

        boolean foundFace = false;
        if(facesArray.length == 1) {
            if(!facesArray[0].contains(regionOfInterest.tl())){
                if(!facesArray[0].contains(regionOfInterest.br())){
                    foundFace = true;

                    faceRectArea = facesArray[0];
                }
            }
        }

        FACE_RECT_COLOR = SCALAR_COLOR_RED;
        if(!foundFace){
            lastTimeStamp.setTime(0L);
        } else if( (lastTimeStamp.getTime() > 0L) ){
            long diff = (new Date().getTime() - lastTimeStamp.getTime());
            if(diff > DETECT_THRESHOLD_MILLISECONDS){
                FACE_RECT_COLOR = SCALAR_COLOR_GREEN;
                imageButtonUpdate();
                lastTimeStamp.setTime(0L);
            } else {
                FACE_RECT_COLOR = SCALAR_COLOR_BLUE;
            }
        } else {
            lastTimeStamp.setTime(new Date().getTime());
            FACE_RECT_COLOR = SCALAR_COLOR_BLUE;
        }

        Core.rectangle(mRgba, regionOfInterest.tl(), regionOfInterest.br(), FACE_RECT_COLOR, 3);
        return mRgba;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemFace50 = menu.add("Face size 50%");
        mItemFace40 = menu.add("Face size 40%");
        mItemFace30 = menu.add("Face size 30%");
        mItemFace20 = menu.add("Face size 20%");
        mItemType   = menu.add(mDetectorName[mDetectorType]);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item == mItemFace50)
            setMinFaceSize(0.5f);
        else if (item == mItemFace40)
            setMinFaceSize(0.4f);
        else if (item == mItemFace30)
            setMinFaceSize(0.3f);
        else if (item == mItemFace20)
            setMinFaceSize(0.2f);
        else if (item == mItemType) {
            int tmpDetectorType = (mDetectorType + 1) % mDetectorName.length;
            item.setTitle(mDetectorName[tmpDetectorType]);
            setDetectorType(tmpDetectorType);
        }
        return true;
    }

    private void setMinFaceSize(float faceSize) {
        mRelativeFaceSize = faceSize;
        mAbsoluteFaceSize = 0;
    }

    private void setDetectorType(int type) {
        if (mDetectorType != type) {
            mDetectorType = type;

            if (type == NATIVE_DETECTOR) {
                Log.i(TAG, "Detection Based Tracker enabled");
                mNativeDetector.start();
            } else {
                Log.i(TAG, "Cascade detector enabled");
                mNativeDetector.stop();
            }
        }
    }

    public void imageButtonUpdate() {
        try {
            Thread.sleep(1000);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Mat m = new Mat(mRgba, regionOfInterest);
                    Imgproc.resize(m,m, new Size(100, 100));
                    Bitmap faceBitMap = Bitmap.createBitmap(m.rows(), m.cols(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(m, faceBitMap);
                    ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
                    imageButton.setImageBitmap(faceBitMap);
                    faceMat = new Mat(mGray,faceRectArea);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void imageButtonClicked(View v){

        Exam exam = (Exam) getIntent().getSerializableExtra("SELECTED_EXAM");
        User student = (User) getIntent().getSerializableExtra("SELECTED_STUDENT");

        SMat sMat = OpenCvUtils.matToSMat(faceMat);
        sMat.setFaceId(student.getId()+"");
        sMat.setExamId(exam.getId());
        sMat.setStudentId(student.getId());

        Call<FaceResult>  call = ((IreasApplication)getApplication())
                .getService().matchFace(sMat);
        call.enqueue(new Callback<FaceResult>() {
            @Override
            public void onResponse(Response<FaceResult> response, Retrofit retrofit) {
                FaceResult  faceResult = response.body();
                Toast.makeText(FdActivity.this, faceResult.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(FdActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}