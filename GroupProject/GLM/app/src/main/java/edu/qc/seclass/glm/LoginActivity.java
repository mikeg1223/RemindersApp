package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button signIn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shared = getSharedPreferences("shared", MODE_PRIVATE);
        setContentView(R.layout.activity_login);

        // These are not good id names. Try "tv_email" or "tv_password" etc
        email = findViewById(R.id.editTextTextPassword2);
        password = findViewById(R.id.editTextTextPassword);
        signIn = findViewById(R.id.signInID);


        // check if user credentials are saved
        // if yes, bypass login activity; else save credentials
        /**if(shared.contains("username") && shared.contains("password")){
            Intent intent = new Intent(LoginActivity.this, ReminderlistActivity.class);
            intent.putExtra("username", shared.getString("username", null));
            startActivity(intent);
        } else {
            saveInformation(email.getText().toString().trim(), password.getText().toString().trim());
        }
         */

        DB = new DBHelper(this);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                User user = new User(usernameText, passwordText, true);
                if (usernameText.equals("") || passwordText.equals("")){
                    Toast.makeText(LoginActivity.this, "Empty email or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(DB.userLoginAttempt(usernameText, passwordText)){

                    Toast.makeText(LoginActivity.this, "Login was successful", Toast.LENGTH_SHORT).show();
                    DB.updateLastLogIn(user);

                }else if (DB.queryUserName(usernameText)){
                    Toast.makeText(LoginActivity.this, "Wrong Credentials. Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    DB.insertUser(user);
                    DB.updateLastLogIn(user);
                    Toast.makeText(LoginActivity.this, "User was created", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(LoginActivity.this, ListoflistActivity.class);
                intent.putExtra("username", usernameText); // pass the username to the next activity
                startActivity(intent);
                finish();
            }
        });
    }

    public void saveInformation(String username,String password) {
        SharedPreferences shared = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }
}