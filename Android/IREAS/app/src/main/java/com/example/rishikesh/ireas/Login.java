    package com.example.rishikesh.ireas;

    import android.app.Activity;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;

    import com.example.rishikesh.ireas.rest.model.User;

    import retrofit.Call;
    import retrofit.Callback;
    import retrofit.Response;
    import retrofit.Retrofit;


    public class Login extends Activity  implements  Callback<User> {

        EditText un, pw;
        TextView error;
        Button ok;
        private String resp;
        private String errorMsg;

        TextView infoView;

        /**
         * Called when the activity is first created.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            un = (EditText) findViewById(R.id.txtusername);
            un.setText("k@h.com");
            pw = (EditText) findViewById(R.id.txtpassword);
            pw.setText("pass");
            ok = (Button) findViewById(R.id.btnlogin);
            error = (TextView) findViewById(R.id.txterror);
            infoView = (TextView) findViewById(R.id.txtinfo);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = new User();
                    user.setEmail(un.getText().toString());
                    user.setPassword(pw.getText().toString());
                    Call<User> call = ((IreasApplication) getApplication()).getService().loginUser(user);
                    call.enqueue(Login.this);
                }
            });
        }


        @Override
        public void onResponse(Response<User> response, Retrofit retrofit) {
            try {
                User user = response.body();
                infoView.setText(user.toString());

                ((IreasApplication) getApplication()).setUserLoggedIn(user);
                Intent myIntent = new Intent(Login.this, Notification.class);
                myIntent.putExtra("LOGGED_IN_USER",user);
                Login.this.startActivity(myIntent);
            } catch (Exception e) {
                infoView.setText("User Not Found");
            }
        }

        @Override
        public void onFailure (Throwable t){
            infoView.setText("Exception Occured In Login : "+ t.getLocalizedMessage());
        }
    }