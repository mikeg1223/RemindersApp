package edu.qc.seclass.glm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private ArrayList<Reminder> reminders;
    private DBHelper db;
    private Context parent;
    private Spinner type;
    private EditText name;
    private EditText description;
    private Spinner priority;
    private TextView alert;
    private Button back;
    private Button delete;
    private Button update;
    private User user;
    private ReminderList reminderlist;
    private Button dateButton, timeButton;
    private TextView dateTextView, timeTextView;
    private int year = -1, month, day;
    private int hour = Integer.valueOf(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()));
    private int minute = Integer.valueOf(new SimpleDateFormat("mm").format(Calendar.getInstance().getTime()));

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox cb;
        private final Button b;

        public ViewHolder(View view) {
            super(view);
            cb = (CheckBox) view.findViewById(R.id.recycler_checkbox);
            b = (Button) view.findViewById(R.id.recycler_button);
        }

        public Button getButton() {
            return b;
        }

        public CheckBox getCheckBox() {
            return cb;
        }
    }

    //constructor, sets data
    public ReminderAdapter(ArrayList<Reminder> storedReminders, DBHelper d, Context context, User u, ReminderList rl) {
        reminders = storedReminders;
        db = d;
        parent = context;
        user = u;
        reminderlist = rl;
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminders.get(position);
        holder.getButton().setText(reminder.getName());
        holder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editReminder(reminder);
            }
        });
        holder.getCheckBox().setChecked(reminders.get(position).isCompleted());
        holder.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminder.setCompleted(holder.getCheckBox().isChecked());
                db.updateReminder(reminder, reminderlist, user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public Filter getFilter() {
        return reminderFilter;
    }

    private Filter reminderFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Reminder> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(reminders);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Reminder reminder : reminders) {
                    if (reminder.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(reminder);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            reminders.clear();
            reminders.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public void editReminder(Reminder reminder) {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(parent);
        LayoutInflater inflater = LayoutInflater.from(parent);
        View v = inflater.inflate(R.layout.activity_edit_reminder, null);
        myDialog.setView(v);

        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();


        type = (Spinner) v.findViewById(R.id.erTypeSpinner);
        name = (EditText) v.findViewById(R.id.erReminderName);
        name.setText(reminder.getName());
        description = (EditText) v.findViewById(R.id.erRemindDescription);
        description.setText(reminder.getDescription());
        priority = (Spinner) v.findViewById(R.id.erPrioritySpinner);
        alert = (TextView) v.findViewById(R.id.erReminderAlertID);
        back = (Button) v.findViewById(R.id.btnBack);
        update = (Button) v.findViewById(R.id.btnUpdateReminder);
        delete = (Button) v.findViewById(R.id.btnDeleteReminder);
        dateButton = (Button) v.findViewById(R.id.erMonthID);
        dateTextView = (TextView) v.findViewById(R.id.erMonthTextID);
        timeButton = v.findViewById(R.id.erTimeID);
        timeTextView = v.findViewById(R.id.erTimeTextID);

        DateTimeAlert myAlert = (DateTimeAlert) db.getAlert(reminder);
        if (myAlert != null) {
            dateTextView.setText(myAlert.getFormattedDate());
            timeTextView.setText(myAlert.getFormattedTime());
        }

        String[] priorityLevels = {"Critical", "Important", "Normal", "Low"};
        ArrayList<String> arrayListPriorityLevels = new ArrayList<>(Arrays.asList(priorityLevels));
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(parent, R.layout.spinnerlayer, arrayListPriorityLevels);
        priority.setAdapter(adapter_2);

        String[] types = {"Doctor", "School", "Appointments", "Exams"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(types));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(parent, R.layout.spinnerlayer, arrayList);
        type.setAdapter(adapter);

        dateButton.setOnClickListener(new View.OnClickListener() {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(parent,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int dayOfMonth) {
                                year = y;
                                month = m + 1;
                                day = dayOfMonth;
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(y, m, dayOfMonth);
                                dateTextView.setText(dateFormatter.format(newDate.getTime()));
                                System.out.println("year->" + year + "; month-> " + month + "; day-> " + day);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }

        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int myHour = calendar.get(Calendar.HOUR_OF_DAY);
                int myMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(parent,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                                timeTextView.setText(hourOfDay + (min > 9 ? ":" : ":0") + min);
                                hour = hourOfDay;
                                minute = min;
                            }
                        }, myHour, myMinute, true);
                timePickerDialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parent, "Back", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderType reminderType = new ReminderType(type.getSelectedItem().toString().trim());
                reminder.setName(name.getText().toString().trim());
                reminder.setType(reminderType);
                reminder.setPriority(getNumericPriority(priority.getSelectedItem().toString()));
                reminder.setDescription(description.getText().toString());

                db.insertReminderType(reminder);

                if (db.updateReminder(reminder, reminderlist, user)) {
                    if (user.getUsingAlertsBoolean() && year != -1) {
                        LocalDateTime inputDate = LocalDateTime.of(year, month, day, hour, minute);
                        boolean dateIsValid = inputDate.compareTo(LocalDateTime.now()) > 0;

                        if (dateIsValid) {
                            DateTimeAlert dateTimeAlert = (DateTimeAlert) db.getAlert(reminder);
                            if (dateTimeAlert == null) {
                                dateTimeAlert = new DateTimeAlert(year, month, day, hour, minute, reminder);
                                db.insertAlert(dateTimeAlert);
                            } else {
                                dateTimeAlert.setDateTime(year, month, day, hour, minute);
                                db.updateAlert(dateTimeAlert);
                            }
                        } else {
                            Toast.makeText(parent, "Alert date is invalid, wasn't added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(parent, "Reminder updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(parent, "Couldn't update reminder", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteReminder(reminder);
                Toast.makeText(parent, "Deleted", Toast.LENGTH_SHORT).show();
                reminders.remove(reminder);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

    }

    public void updateReminder(Reminder reminder) {
        ReminderType reminderType = new ReminderType(type.getSelectedItem().toString().trim());
        reminder.setName(name.getText().toString().trim());
        reminder.setType(reminderType);
        reminder.setPriority(getNumericPriority(priority.getSelectedItem().toString()));
        reminder.setDescription(description.getText().toString());
        db.insertReminderType(reminder);

        if (db.updateReminder(reminder, reminderlist, user)) {
            Toast.makeText(parent, "Reminder updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(parent, "Couldn't update reminder", Toast.LENGTH_SHORT).show();
        }
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
}
