package com.example.tasklist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class AddTaskActivity extends Activity implements View.OnClickListener {
    private Button addTodoBtn;
    private EditText subjectEditText;
    private EditText descEditText;
    private RadioGroup doneRadio;
    private RadioGroup urgencyRadio;
    private DatePickerDialog picker;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Task");

        setContentView(R.layout.activity_add_task);

        subjectEditText = (EditText) findViewById(R.id.add_your_task);
        descEditText = (EditText) findViewById(R.id.date_to_perform);
        descEditText.setInputType(InputType.TYPE_NULL);
        descEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                descEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        doneRadio = (RadioGroup) findViewById(R.id.doneRadio);

        urgencyRadio = (RadioGroup) findViewById(R.id.urgencyRadio);

        addTodoBtn = (Button) findViewById(R.id.add_task);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_task:
                final String name = subjectEditText.getText().toString();
                final String desc = descEditText.getText().toString();
                //final String checked = check.getText().toString();
                //final String urgencyTask = urgencyOfTask.getText().toString();
                int status = doneRadio.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)findViewById(status);
                String checked = rb.getText().toString();
                setDone(checked);

                status = urgencyRadio.getCheckedRadioButtonId();
                rb = (RadioButton)findViewById(status);
                String urgencyTask = rb.getText().toString();
                setUrgency(urgencyTask);
                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter task title", Toast.LENGTH_LONG).show();
                }else if(desc.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter date to preform", Toast.LENGTH_LONG).show();
                }
                else{
                    dbManager.insert(name, desc, checked, urgencyTask);
                    Intent main = new Intent(AddTaskActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(main);
                }
                break;
        }
    }
    private void setDone(String s){
        RadioButton rbStart = (RadioButton)findViewById(R.id.task_to_perform);
        RadioButton rbDone = (RadioButton)findViewById(R.id.tasks_done);

        if(s.equals("Task done")){
            rbStart.setChecked(false);
            rbDone.setChecked(true);
        }
        else{
            rbStart.setChecked(true);
            rbDone.setChecked(false);
        }
    }

    private void setUrgency(String s){
        RadioButton normal = (RadioButton)findViewById(R.id.normal);
        RadioButton urgent = (RadioButton)findViewById(R.id.urgent);
        RadioButton very_urgent = (RadioButton)findViewById(R.id.very_urgent);

        if(s.equals("Urgent")){
            normal.setChecked(false);
            urgent.setChecked(true);
            very_urgent.setChecked(false);
        }
        else if(s.equals("Very Urgent")){
            normal.setChecked(false);
            urgent.setChecked(false);
            very_urgent.setChecked(true);
        }
        else{
            normal.setChecked(true);
            urgent.setChecked(false);
            very_urgent.setChecked(false);
        }
    }
}