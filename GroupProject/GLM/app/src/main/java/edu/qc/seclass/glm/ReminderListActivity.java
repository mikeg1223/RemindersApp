package edu.qc.seclass.glm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class ReminderListActivity extends AppCompatActivity {

    private FloatingActionButton editListButton;
    private FloatingActionButton floatingButton;
    private TextView reminderListLabel;
    private User user;
    private ReminderList reminderList;
    private DBHelper dbHelper;
    private ArrayList<Reminder> reminders;
    private Button checkOffAllButton;
    private boolean checkOffSelector = false;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    // variables on pop up
    private EditText editTextName;
    private EditText editTextDescription;
    private Button buttonAdd;
    private Button buttonBack;
    private Spinner prioritySpinner;
    private Spinner typeSpinner;
    private FloatingActionButton clear;
    RecyclerView recyclerView;
    ReminderAdapter adapter;
    BroadcastReceiver br;
    private Button dateButton;
    private Button timeButton;
    private TextView dateTextView;
    private TextView timeTextView;
    private int year = -1, month = -1, day = -1;
    private int hour = Integer.valueOf(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()));
    private int minute = Integer.valueOf(new SimpleDateFormat("mm").format(Calendar.getInstance().getTime()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderlist);
        Button dateButton = findViewById(R.id.erMonthID);
        reminders = new ArrayList<Reminder>();
        dbHelper = new DBHelper(this);
        editListButton = findViewById(R.id.editListButton);
        reminderListLabel = findViewById(R.id.reminder_list_label);
        checkOffAllButton = findViewById(R.id.check_off_all_btn);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int reminderListID = intent.getIntExtra("reminderListID", -1);
        user = dbHelper.getUser(username);
        reminderList = dbHelper.getReminderList(reminderListID, user);
        reminderListLabel.setText(reminderList.getName());

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String a = intent.getAction();
                if (a.equals("logged_out_finish")) {
                    finish();
                }
            }
        };

        registerReceiver(br, new IntentFilter("logged_out_finish"));

        editListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReminderListActivity.this, EditListofReminderActivity.class);
                intent.putExtra("username", user.getUsername());
                intent.putExtra("reminderListID", reminderList.getReminderListID());
                startActivity(intent);
                finish();
            }
        });

        floatingButton = findViewById(R.id.add);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminder();
            }
        });

        getData();

        clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ArrayList<Reminder> removal = new ArrayList<Reminder>();
                for (Reminder r : reminders) {
                    if (r.isCompleted()) {
                        removal.add(r);
                    }
                }
                for (Reminder r : removal) {
                    reminders.remove(r);
                    dbHelper.deleteReminder(r);
                }
                adapter.notifyDataSetChanged();
            }
        });

        checkOffAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Reminder r : reminders) {
                    if (!checkOffSelector) {
                        r.checkOff();
                        checkOffAllButton.setText("Uncheck All");
                    } else {
                        r.setCompleted(false);
                        checkOffAllButton.setText("Check Off All");
                    }
                    dbHelper.updateReminder(r, reminderList, user);
                    adapter.notifyDataSetChanged();
                }
                checkOffSelector = !checkOffSelector;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ReminderAdapter(reminders, dbHelper, this, user, reminderList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void addReminder() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_createreminder, null);
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        myDialog.setView(view);
        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();
        editTextName = view.findViewById(R.id.reminderNameID);
        editTextDescription = view.findViewById(R.id.remindDescriptionID);
        buttonAdd = view.findViewById(R.id.addButtonID);
        buttonBack = view.findViewById(R.id.addBackID);
        dateButton = view.findViewById(R.id.monthID);
        timeButton = view.findViewById(R.id.timeID);
        dateTextView = view.findViewById(R.id.monthTextID);
        timeTextView = view.findViewById(R.id.timeTextID);

        prioritySpinner = view.findViewById(R.id.prioritySpinnerID);
        String[] priorityLevels = {"Critical", "Important", "Normal", "Low"};
        ArrayList<String> arrayListPriorityLevels = new ArrayList<>(Arrays.asList(priorityLevels));
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this, R.layout.spinnerlayer, arrayListPriorityLevels);
        prioritySpinner.setAdapter(adapter_2);


        typeSpinner = view.findViewById(R.id.typeSpinnerID);
        String[] types = {"Doctor", "School", "Appointments", "Exams"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(types));
        ArrayAdapter<String> adpt = new ArrayAdapter<>(this, R.layout.spinnerlayer, arrayList);
        typeSpinner.setAdapter(adpt);

        dateButton.setOnClickListener(new View.OnClickListener() {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReminderListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int dayOfMonth) {
                                year = y;
                                month = m + 1; // DatePicker starts month at index 0
                                day = dayOfMonth;
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(y, m, dayOfMonth);
                                dateTextView.setText(dateFormatter.format(newDate.getTime()));
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }

        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hourLocalVar = calendar.get(Calendar.HOUR_OF_DAY);
                int minutesLocalVar = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReminderListActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                                timeTextView.setText(hourOfDay + (min > 9 ? ":" : ":0") + min);
                                hour = hourOfDay;
                                minute = min;
                            }
                        }, hourLocalVar, minutesLocalVar, true);
                timePickerDialog.show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReminderListActivity.this, "Back", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (reminderList.findReminderByName(editTextName.getText().toString().trim()) != null) {

                    Toast.makeText(ReminderListActivity.this, "Reminder already exists", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                ReminderType reminderType = new ReminderType(typeSpinner.getSelectedItem().toString().trim());
                Reminder reminder = new Reminder(editTextName.getText().toString().trim(), reminderType,
                        false, getNumericPriority(prioritySpinner.getSelectedItem().toString()),
                        user.getUsingAlertsBoolean());
                reminder.setDescription(editTextDescription.getText().toString().trim());

                dbHelper.insertReminderType(reminder);

                if (dbHelper.insertReminder(reminder, reminderList, user)) {

                    if (reminder.usesAlerts() && year != -1) {
                        LocalDateTime inputDate = LocalDateTime.of(year, month, day, hour, minute);
                        boolean dateIsValid = inputDate.compareTo(LocalDateTime.now()) > 0;

                        if (dateIsValid) {
                            DateTimeAlert dateTimeAlert = new DateTimeAlert(year, month, day, hour, minute, reminder);
                            dbHelper.insertAlert(dateTimeAlert);
                            startAlarm(reminder, dateTimeAlert);
                        } else {
                            Toast.makeText(ReminderListActivity.this, "Alert date is invalid, wasn't added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(ReminderListActivity.this, "Reminder added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReminderListActivity.this, "Couldn't add reminder", Toast.LENGTH_SHORT).show();
                }
                adapter.addReminder(reminder);
                dialog.dismiss();
            }
        });
    }

    private void startAlarm(Reminder reminder, DateTimeAlert alert) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("title", reminder.getName());
        intent.putExtra("message", reminder.getDescription());

        pendingIntent = PendingIntent.getBroadcast(this, alert.getReminderID(), intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, alert.getDateTimeAlert().getYear());
        calendar.set(Calendar.MONTH, alert.getDateTimeAlert().getMonthValue()-1);
        calendar.set(Calendar.DAY_OF_MONTH, alert.getDateTimeAlert().getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, alert.getDateTimeAlert().getHour());
        calendar.set(Calendar.MINUTE, alert.getDateTimeAlert().getMinute());
        calendar.set(Calendar.SECOND, 0);

        LocalDateTime date =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(calendar.getTimeInMillis()), ZoneId.systemDefault());
        System.out.println(date.toString());

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private int getNumericPriority(String priority) {
        switch (priority) {
            case "Critical":
                return 1;
            case "Important":
                return 2;
            case "Normal":
                return 3;
            default: //"Low"
                return 4;
        }
    }

    @SuppressLint("Range")
    private void getData() {
        Cursor cursor = dbHelper.getAllReminders(reminderList, user);
        cursor.moveToFirst();
        int length = cursor.getCount();
        for (int i = 0; i < length; ++i) {
            reminders.add(new Reminder(cursor.getString(cursor.getColumnIndex("reminderName")),
                    new ReminderType(cursor.getString(cursor.getColumnIndex("reminderTypeName"))),
                    cursor.getInt(cursor.getColumnIndex("completed")) == 1,
                    cursor.getInt(cursor.getColumnIndex("priority")),
                    user.getUsingAlerts() == 1, cursor.getInt(cursor.getColumnIndex("reminderID")),
                    cursor.getString(cursor.getColumnIndex("description"))));
            cursor.moveToNext();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SearchView searchView = findViewById(R.id.search_bar);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}