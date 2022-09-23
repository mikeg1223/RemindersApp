package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditListofReminderActivity extends AppCompatActivity{

    private EditText editTextListName;
    private Button backButton;
    private Button updateButton;
    private Button deleteButton;
    private User user;
    private DBHelper dbHelper;
    private ReminderList reminderList;
    private TextView dateDisplayView;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listof_reminder);

        editTextListName = findViewById(R.id.reminderNameID);
        backButton = findViewById(R.id.backID);
        deleteButton = findViewById(R.id.btn_delete_upd);
        updateButton = findViewById(R.id.btn_SAVE_upd);
        dbHelper = new DBHelper(this);
        Button dateButton =  findViewById(R.id.erMonthID);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int reminderListID = intent.getIntExtra("reminderListID", -1);
        user = dbHelper.getUser(username);
        reminderList = dbHelper.getReminderList(reminderListID, user);
        editTextListName.setHint(reminderList.getName()); // set hint to current name of list

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditListofReminderActivity.this, "Back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditListofReminderActivity.this, ReminderListActivity.class);
                intent.putExtra("username", user.getUsername());
                intent.putExtra("reminderListID", reminderList.getReminderListID());
                startActivity(intent);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteReminderList(reminderList);
                reminderList = null;
                Toast.makeText(EditListofReminderActivity.this, "List was deleted", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditListofReminderActivity.this, ListoflistActivity.class);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
                finish();
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editTextListName.getText().toString().trim();

                if (newName.equals("")) {
                    Toast.makeText(EditListofReminderActivity.this, "Failed. List name cannot be empty", Toast.LENGTH_SHORT).show();

                }
                dbHelper.updateReminderList(reminderList, user, newName);
                Intent intent = new Intent(EditListofReminderActivity.this, ReminderListActivity.class);
                intent.putExtra("username", user.getUsername());
                intent.putExtra("reminderListID", reminderList.getReminderListID());
                startActivity(intent);
                finish();
            }
        });
    }
}
