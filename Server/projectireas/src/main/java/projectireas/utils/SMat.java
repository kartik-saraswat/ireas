package projectireas.utils;

import java.util.List;

/**
 * Created by kartik on 8/11/15.
 */
public class SMat {

    private int cols;
    private int rows;
    private int elemSize;
    private int type;
    private String faceId;
    private Long studentId;
    private Long examId;

    private List<Byte> data;

    public SMat() {
    }
    
    public Long getStudentId() {
		return studentId;
	}




	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}




	public Long getExamId() {
		return examId;
	}




	public void setExamId(Long examId) {
		this.examId = examId;
	}




	public int getElemSize() {
		return elemSize;
	}



	public void setElemSize(int elemSize) {
		this.elemSize = elemSize;
	}



	public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Byte> getData() {
        return data;
    }

    public void setData(List<Byte> data) {
        this.data = data;
    }
    
	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	@Override
	public String toString() {
		return "SMat [cols=" + cols + ", rows=" + rows + ", type=" + type + ", data=" + data + "]";
	}
}

