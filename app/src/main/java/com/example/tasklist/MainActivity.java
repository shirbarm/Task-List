package com.example.tasklist;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tasklist.R;

public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC , DatabaseHelper.CHECK, DatabaseHelper.URGENT };

    final int[] to = new int[] { R.id.id, R.id.title, R.id.desc, R.id.todoCheckBox, R.id.emergency };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_emp_list);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_task, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView titleTextView = (TextView) view.findViewById(R.id.title);
                TextView descTextView = (TextView) view.findViewById(R.id.desc);
                TextView checkTextView = (TextView) view.findViewById(R.id.todoCheckBox);
                TextView emergencyTextView = (TextView) view.findViewById(R.id.emergency);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();
                String checkbox = checkTextView.getText().toString();
                String emergency = emergencyTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyTaskActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("checkbox", checkbox);
                modify_intent.putExtra("urgent", emergency);

                startActivity(modify_intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_task) {
            Intent add_mem = new Intent(this, AddTaskActivity.class);
            startActivity(add_mem);
        }else if(item.getItemId() == R.id.search_by_date){
            Intent add_mem = new Intent(this, SearchTasksByDate.class);
            startActivity(add_mem);
        }else if(item.getItemId() == R.id.task_to_perform){
            Intent modify_intent = new Intent(getApplicationContext(), TaskNotDone.class);
            startActivity(modify_intent);
        }else if(item.getItemId() == R.id.tasks_done){
            Intent modify_intent = new Intent(getApplicationContext(), TaskDone.class);
            startActivity(modify_intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void onCheckboxClicked(View view) {
        String id = view.findViewById(R.id.checkbox).toString();
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked\
        if (checked==true){

        }
        else{

        }
    }*/
}