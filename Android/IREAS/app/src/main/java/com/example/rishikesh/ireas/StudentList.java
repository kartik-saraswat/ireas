package com.example.rishikesh.ireas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rishikesh.ireas.rest.model.ExamAttendance;
import com.example.rishikesh.ireas.rest.model.NotificationList;
import com.example.rishikesh.ireas.rest.model.StudentAttendance;
import com.example.rishikesh.ireas.rest.model.User;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by rishikesh on 10/17/2015.
 */
public class StudentList  extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentlist);

        Call<ExamAttendance> call = ((IreasApplication)getApplication())
                .getService().getStudentsList(((IreasApplication) getApplication()).getCurrentExam().getId());
        call.enqueue(new Callback<ExamAttendance>() {
            @Override
            public void onResponse(Response<ExamAttendance> response, Retrofit retrofit) {

                ExamAttendance examAttendance = response.body();
                List<StudentAttendance> studentAttendanceList = examAttendance.getStudentAttendanceList();

                StudentListAdapter adapter = new StudentListAdapter(StudentList.this, R.layout.studentlistlayout, studentAttendanceList);
                ListView examsListView = (ListView) findViewById(R.id.lststudentview);
                examsListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private class StudentListAdapter extends ArrayAdapter<StudentAttendance> {
        List<StudentAttendance> cdlist;
        public StudentListAdapter(Context context, int resource, List<StudentAttendance> cdlist) {
            super(context, resource, cdlist);
            this.cdlist= cdlist;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                convertView=getLayoutInflater().inflate(R.layout.studentlistlayout,parent,false);
            }

            StudentAttendance studentAttendance = cdlist.get(position);
            final User user = studentAttendance.getStudent();
            TextView studentName = (TextView) convertView.findViewById(R.id.lblstunamw);
            TextView studentRoll = (TextView) convertView.findViewById(R.id.lblrollno);
            Button takeAttendance = (Button) convertView.findViewById(R.id.btntkatten);
            CheckBox attendanceCheckBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            attendanceCheckBox.setChecked(studentAttendance.getAttendance());
            
            studentName.setText(user.getName());
            studentRoll.setText("14CA29");
            takeAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(StudentList.this, FdActivity.class);
                    myIntent.putExtra("SELECTED_STUDENT",user);
                    myIntent.putExtra("SELECTED_EXAM",((IreasApplication) getApplication()).getCurrentExam());
                    StudentList.this.startActivity(myIntent);
                }
            });
            return  convertView;
        }
    }
}
