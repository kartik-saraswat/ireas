package com.example.rishikesh.ireas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rishikesh.ireas.rest.model.Exam;
import com.example.rishikesh.ireas.rest.model.NotificationList;
import com.example.rishikesh.ireas.rest.model.User;
import com.example.rishikesh.ireas.rest.model.api.LoginUser;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by rishikesh on 10/17/2015.
 */
public class Notification extends Activity {


    TextView assignedExamText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        assignedExamText = (TextView) findViewById(R.id.assignedExam);

        Call<NotificationList> call = ((IreasApplication)getApplication())
                .getService().getNotification(((IreasApplication) getApplication()).getUserLoggedIn().getId());
        call.enqueue(new Callback<NotificationList>() {
            @Override
            public void onResponse(Response<NotificationList> response, Retrofit retrofit) {


                List<com.example.rishikesh.ireas.rest.model.Notification> notificationList = response.body().getNotificationList();
                NotificationListdadapter adapter = new NotificationListdadapter(Notification.this,R.layout.notification, notificationList);
                ListView  examsListView = (ListView) findViewById(R.id.examslistView);
                examsListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private class NotificationListdadapter extends ArrayAdapter<com.example.rishikesh.ireas.rest.model.Notification>{
        List<com.example.rishikesh.ireas.rest.model.Notification> cdlist;
        public NotificationListdadapter(Context context, int resource, List<com.example.rishikesh.ireas.rest.model.Notification> cdlist) {
            super(context, resource, cdlist);
            this.cdlist= cdlist;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if(convertView==null)
            {
                convertView=getLayoutInflater().inflate(R.layout.layoutnotification,parent,false);
            }

            final com.example.rishikesh.ireas.rest.model.Notification notification = cdlist.get(position);
            TextView examName = (TextView) convertView.findViewById(R.id.lblexamname);
            TextView examCode = (TextView) convertView.findViewById(R.id.lblexamcode);
            TextView examRoom = (TextView) convertView.findViewById(R.id.lblroomno);
            TextView examTime = (TextView) convertView.findViewById(R.id.lbltime);

            Button startExamButton = (Button) convertView.findViewById(R.id.btnexamstart);


            examName.setText(notification.getCourse().getName());
            examCode.setText(notification.getCourse().getCode().toUpperCase());
            examRoom.setText(notification.getExam().getRoom());
           ;
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(notification.getExam().getExamStartTime());   // assigns calendar to given date
            int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
            int minute = calendar.get(Calendar.MINUTE);
            examTime.setText(" "+hour+":"+minute+" ");

            startExamButton.setTag(position);
            startExamButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Exam exam = new Exam();
                    exam.setId(notification.getExam().getId());
                    ((IreasApplication) getApplication()).setCurrentExam(exam);
                    Intent myIntent = new Intent(Notification.this, StudentList.class);
                    myIntent.putExtra("CURRENT_EXAM",notification.getExam());
                    Notification.this.startActivity(myIntent);
                }
            });

            return  convertView;
        }
    }
}
