package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileConfigurationActivity extends AppCompatActivity {

    private EditText et_firstName;
    private EditText et_lastName;
    private EditText et_email;
    private EditText et_password;
    private CheckBox cb_alerts;
    private Button b_save;
    private Button b_logOut;
    private DBHelper db;
    private String username;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileconfiguration);

        db = new DBHelper(this);

        et_firstName = findViewById(R.id.et_firstName);
        et_lastName = findViewById(R.id.et_lastName);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        b_save = findViewById(R.id.b_save);
        b_logOut = findViewById(R.id.b_logOut);

        cb_alerts = findViewById(R.id.cb_alerts);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        user = db.getUser(username);

        et_firstName.setText(user.getFirstName());
        et_lastName.setText(user.getLastName());
        et_email.setText(user.getEmail());
        cb_alerts.setChecked(user.getUsingAlerts() > 0);


        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = et_firstName.getText().toString().trim();
                String lname = et_lastName.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (password.equals("")){
                    password = user.getPassword();
                }

                User user = new User(username, password, cb_alerts.isChecked());
                user.setPersonalInfo(fname, lname, email);
                db.updateUser(user);
                Toast.makeText(ProfileConfigurationActivity.this, "Information Saved", Toast.LENGTH_SHORT).show();

                if(!user.getUsingAlertsBoolean()){
                    db.truncateAlertTable();
                }

                finish();
            }
        });

        b_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.logOut();
                Intent i = new Intent("logged_out_finish");
                sendBroadcast(i);
                Intent intent = new Intent(ProfileConfigurationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}