package projectireas.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.opencv.core.Core;

import io.dropwizard.hibernate.UnitOfWork;
import projectireas.core.FaceResult;
import projectireas.dao.AttendanceDao;
import projectireas.model.AttendanceModel;
import projectireas.utils.FaceRecognitionUtility;
import projectireas.utils.OpenCvUtils;
import projectireas.utils.SMat;

@Path("check/")
public class ImageResource {

	FaceRecognitionUtility recognitionUtility;
	private final AttendanceDao attendanceDao;

	static{ 
		nu.pattern.OpenCV.loadLibrary();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public ImageResource(AttendanceDao attendanceDao) {
		super();
		this.recognitionUtility = new FaceRecognitionUtility();
		this.attendanceDao = attendanceDao;
	}


	@POST
	@Path("faceImage")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public FaceResult checkFaceImage(SMat sMat){

		Mat faceMat = OpenCvUtils.matFromSMat(sMat);

		System.out.println("\nFACE MAT RECIEVED\n");

		FaceResult faceResult =  recognitionUtility.findTheFace(faceMat);
		boolean result = (faceResult.getConfidence() < 500);
		faceResult.setResult(result);
		System.out.println("\n\n\n\n Response : "+ faceResult);
		return faceResult;
	}


	@POST
	@Path("matchFaceImage")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public FaceResult matchFaceImage(SMat sMat){

		Mat faceMat = OpenCvUtils.matFromSMat(sMat);
		String faceId = sMat.getFaceId();
		System.out.println("\nFACE MAT RECIEVED\n");
		FaceRecognitionUtility recognitionUtility = new FaceRecognitionUtility(faceId);
		FaceResult faceResult =  recognitionUtility.findTheFace(faceMat);
		boolean result = faceId.equals(faceResult.getPredictedLabel()+"");
		result = result && (faceResult.getConfidence() < 2000);
		faceResult.setResult(result);

		if(result){
			updateAttendance(sMat.getExamId(), sMat.getStudentId());
		}

		System.out.println("\n\n\n\n Response : "+ faceResult);
		return faceResult;
	}

	private void updateAttendance(Long examId, Long studentId){
		AttendanceModel attendanceModel =  attendanceDao.findAttendancesByExamIdAndStudent(examId, studentId);
		attendanceModel.setAttendance(true);
		attendanceDao.updateAttendance(attendanceModel);
	}

}