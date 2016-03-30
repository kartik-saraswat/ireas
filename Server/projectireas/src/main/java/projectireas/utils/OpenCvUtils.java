package projectireas.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bytedeco.javacpp.opencv_core.Mat;

public class OpenCvUtils {
    public static SMat matToSMat(Mat mat) {

        SMat sMat = new SMat();
        sMat.setCols(mat.cols());
        sMat.setRows(mat.rows());
        sMat.setType(mat.type());

        int elemSize = (int) mat.elemSize();
        byte[] data = new byte[mat.cols() * mat.cols() * elemSize];
        mat.data().put(data);
        ArrayList<Byte> dataList = new ArrayList<Byte>((ArrayList)Arrays.asList(data));
        sMat.setData(dataList);

      return sMat;
    }

    public static Mat matFromSMat(SMat sMat) {

        int i=0;
        int cols = sMat.getCols();
        int row = sMat.getRows();
        int elemSize = sMat.getElemSize();
        int type = sMat.getType();

        Byte[] data = new Byte[row*cols];
        data = sMat.getData().toArray(data);
        byte[] bytes = new byte[data.length];

        for (Byte b: data) {
            bytes[i++]=b;
        }

        Mat mat = new Mat(row, cols,type);
        mat.data().get(bytes);
        return mat;
    }
}