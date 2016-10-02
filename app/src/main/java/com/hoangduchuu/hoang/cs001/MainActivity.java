package com.hoangduchuu.hoang.cs001;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Communicator, AdapterView.OnItemSelectedListener {
    final MyDB myDB = new MyDB(this, "list_DB.sqlite", null, 1);
    ListView lvItem;
    TextView textViewCalenda;
    ArrayList<TaskList> arrayItemList;
    Toolbar toolBar;
    FragmentManager fragmentManager = getFragmentManager();
    int sqliteIdSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItem = (ListView) findViewById(R.id.listViewItems);

        textViewCalenda = (TextView) findViewById(R.id.textViewCalenda);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        Intent i = getIntent();
        createDB();

        //show listview
        arrayItemList = new ArrayList<TaskList>();
        Cursor cursorOfItem = myDB.getDB("SELECT * FROM aTask");
        while (cursorOfItem.moveToNext()) {
            arrayItemList.add(new TaskList(
                    cursorOfItem.getInt(0),
                    cursorOfItem.getString(1),
                    cursorOfItem.getString(2),
                    cursorOfItem.getString(3),
                    cursorOfItem.getInt(4),
                    cursorOfItem.getInt(5)

            ));
        }

        final TaskAdapter adapter1 = new TaskAdapter(
                getApplicationContext(),
                R.layout.item_layout_list,
                arrayItemList
        );

        lvItem.setAdapter(adapter1);
        // DiaLog Date
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dueDate = dayOfMonth + "/" + (1 + monthOfYear) + "/" + year;
                textViewCalenda.setText("Due Dates: " + dueDate);
            }
        };
        // click intent new Activity 
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int selectedID = arrayItemList.get(position).getId();
                String selectedTask = arrayItemList.get(position).getTaskName();
                String selectedNote = arrayItemList.get(position).getTaskNote();
                String selectedDate = arrayItemList.get(position).getDueDate();
                int selectedPriority = arrayItemList.get(position).getPriority();
                int selectedStatus = arrayItemList.get(position).getStatus();

                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("id", selectedID);
                i.putExtra("task", selectedTask);
                i.putExtra("note", selectedNote);
                i.putExtra("date", selectedDate);
                i.putExtra("priority", selectedPriority);
                i.putExtra("status", selectedStatus);
                startActivity(i);

            }
        }); // end intent activity

        // LongClick edit
        lvItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sqliteIdSelected =  arrayItemList.get(position).getId();

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setMessage("Do you want delete this Task");

                // Set nagative button
                myBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //
                                dialog.cancel();
                            }
                        }
                );
                //see positive  button
                myBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                myDB.queryDB("DELETE FROM aTask where id = '" + sqliteIdSelected + "' ");
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                MainActivity.this.finish();
                            }
                        }
                );
                myBuilder.setTitle("Confirm");
                myBuilder.show();
                return false;
            }
        }); // end longClick
        
    } // end onCreate

    public void createDB() {
        myDB.queryDB("CREATE TABLE IF NOT EXISTS aTask(" +
                "id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "taskName   VARCHAR, " +
                "dueDate    VARCHAR, " +
                "taskNote   VARCHAR,  " +
                "priority   INTEGER, " +
                "status     INTEGER)");
    }

    public void clickedaddItem() {
        Intent i = new Intent(getApplicationContext(), AddTaskActvity.class);
        startActivity(i);
    }
    // Implement Menu method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itThem:
                Toast.makeText(MainActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.itXoa:
                Toast.makeText(MainActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.itSua:
                Toast.makeText(MainActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.itAdd:
                clickedaddItem();
                break;

        }
        return super.onOptionsItemSelected(item);
    } // end menu method

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void UpdateSql2(String itemName, String dueDate, int priority) {
    }

}
