package com.example.rishikesh.ireas;

import org.opencv.core.Mat;

import android.util.Base64;
import android.util.Log;

import com.example.rishikesh.ireas.rest.model.SMat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenCvUtils {
    public static SMat matToSMat(Mat mat) {

        int elemSize = (int) mat.elemSize();

        SMat sMat = new SMat();
        sMat.setCols(mat.cols());
        sMat.setRows(mat.rows());
        sMat.setType(mat.type());
        sMat.setElemSize(elemSize);

        byte[] data = new byte[mat.cols() * mat.cols() * elemSize];
        mat.get(0, 0, data);
        ArrayList<Byte> dataList = new ArrayList<Byte>();
        for (Byte b: data ) {
            dataList.add(b);
        }
        sMat.setData(dataList);

      return sMat;
    }

    public static Mat matFromSMat(SMat sMat) {

        int i=0;
        int cols = sMat.getCols();
        int row = sMat.getRows();
        int type = sMat.getType();

        Byte[] data = (Byte[]) sMat.getData().toArray();
        byte[] bytes = new byte[data.length];

        for (Byte b: data) {
            bytes[i++]=b;
        }

        Mat mat = new Mat(row, cols,type);
        mat.put(0, 0, bytes);
        return mat;
    }
}