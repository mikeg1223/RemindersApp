package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  static int SPLASH = 3300;
    private DBHelper db;
    Animation topAnimation, bottomAnimation;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        String[] data = db.isLoggedIn();

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        imageView = findViewById(R.id.animationID);
        textView = findViewById(R.id.ReminderID);

        int rlID = db.getID("reminderListID");
        int rID = db.getID("reminderID");
        int aID = db.getID("alertID");

        Alert.setCurrentId(aID);
        Reminder.setCurrentID(rID);
        ReminderList.setCurrentID(rlID);


        if (data == null || data[2].equals("false")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent load = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(load);
                    finish();
                }
            }, SPLASH);
        }
        else {
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    Intent intent = new Intent(MainActivity.this, ListoflistActivity.class);
                    intent.putExtra("username", data[0]);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH);
        }



    }
}