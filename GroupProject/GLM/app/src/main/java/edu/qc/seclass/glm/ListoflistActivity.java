package edu.qc.seclass.glm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.xmlpull.v1.XmlPullParser;

public class ListoflistActivity extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonAdd;
    private EditText editReminderListName;
    private DBHelper dbHelper;
    FloatingActionButton addReminderListButton;
    FloatingActionButton settings;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listoflist);
        Toolbar Listoflist = (Toolbar) findViewById(R.id.ListoflistTB);
        settings = findViewById(R.id.userSettings);

        addReminderListButton = findViewById(R.id.add);
        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        user = dbHelper.getUser(username);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListoflistActivity.this, ProfileConfigurationActivity.class);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
            }
        });

        addReminderListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminderList();
            }
        });

        //TODO dynamically create buttons with data extracted from DB
        Cursor cursor = dbHelper.getAllReminderLists(user);
        createLayoutDynamically(cursor, cursor.getCount());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserPerspective();
    }

    // Logic for alertDialog
    private void addReminderList() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_create_list_reminder, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();

        buttonBack = view.findViewById(R.id.backID);
        buttonAdd = view.findViewById(R.id.addID);
        editReminderListName = view.findViewById(R.id.reminderListNameID);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListoflistActivity.this, "Back", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderList reminderList = new ReminderList(editReminderListName.getText().toString().trim());

                if(dbHelper.insertReminderList(reminderList, user)) {
                    Toast.makeText(ListoflistActivity.this, "Reminder List added", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ListoflistActivity.this, "List already exists", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

                //update the user's perspective
                updateUserPerspective();
            }
        });
    }

    private void createLayoutDynamically(Cursor cursor, int n){
        LinearLayout layout = (LinearLayout) findViewById(R.id.display_lists_of_lists_layout);
        layout.removeAllViews();

        for (int i = 0; i < n; i++) {
            cursor.moveToNext();
            @SuppressLint("Range")
            int reminderListID = cursor.getInt(cursor.getColumnIndex("reminderListID"));

            Button myButton = new Button(this);
            myButton.setText(cursor.getString(1));
            myButton.setId(reminderListID);
            final int id_ = myButton.getId();

            layout.addView(myButton);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(ListoflistActivity.this, ReminderListActivity.class);
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("reminderListID", reminderListID);
                    startActivity(intent);
                    updateUserPerspective();
                }
            });

        }
    }

    private void updateUserPerspective() {
        Cursor myCursor = dbHelper.getAllReminderLists(user);
        createLayoutDynamically(myCursor, myCursor.getCount());
    }
}