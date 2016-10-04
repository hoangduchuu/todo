package com.hoangduchuu.hoang.cs001;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.hoangduchuu.hoang.cs001.R.id.textViewDateDue;

public class AddTaskActvity extends AppCompatActivity {
    Toolbar editToolbar;
    Spinner spinnerPriority, spinnerStatus;
    ImageButton imageButton ;
    TextView txtvDueDate;
    EditText edtTaskName, edtNote;
    final MyDB myDB = new MyDB(this, "list_DB.sqlite", null, 1);

    String dueDate = "hehe";

    int spinnerStatusId = -99;
    int spinnerPriorityId = -99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editToolbar  = (Toolbar)findViewById(R.id.toolbarEditItem   );
        imageButton = (ImageButton)findViewById(R.id.imageButtonA);
        final Calendar calendar = Calendar.getInstance();
        txtvDueDate = (TextView)findViewById(textViewDateDue);
        edtTaskName = (EditText) findViewById(R.id.editTextName);
        edtNote = (EditText) findViewById(R.id.editTextNotes);


        spinnerPriority = (Spinner)findViewById(R.id.spinnerPriority);
        spinnerStatus = (Spinner)findViewById(R.id.spinnerStatus);


        // actionma menuu
        setSupportActionBar(editToolbar);

        // spinnerPriority
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.priority,android.R.layout.simple_spinner_item);
        spinnerPriority.setAdapter(adapter);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),R.array.status,android.R.layout.simple_spinner_item);
        spinnerStatus.setAdapter(adapter2);

        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String priorityString = spinnerPriority.getSelectedItem().toString();
                spinnerPriorityId = (int) spinnerPriority.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerStatusId = (int) spinnerStatus.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // DiaLog Date

        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueDate = dayOfMonth +"/"+ (1+monthOfYear) +"/"+ year;
                txtvDueDate.setText(dueDate);
            }
        };

       // btn calenda
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTaskActvity.this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });// end btn calendar
    }

    // Implement Menu method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAdd:
                if (edtTaskName.getText().toString().isEmpty()) msnToast("Task Name is empty, enter Task Name before submit");
                else itAdd();
                ;

                break;
        }
        return super.onOptionsItemSelected(item);
    } // End spinner method

    public void msnToast(String string){
        Toast.makeText(getApplicationContext(), "" + string, Toast.LENGTH_SHORT).show();
    }


    public void itAdd(){
        myDB.queryDB("CREATE TABLE IF NOT EXISTS aTask(" +
                "id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "taskName   VARCHAR, " +
                "dueDate    VARCHAR, " +
                "taskNote   VARCHAR,  " +
                "priority   INTEGER, " +
                "status     INTEGER)");

        myDB.queryDB("INSERT INTO aTask VALUES(" +
                "null,  " +
                "'"+edtTaskName.getText().toString()+"', " +
                "'"+txtvDueDate.getText().toString()+"' , " +
                "'"+edtNote.getText().toString()+"' , " +
                "'"+spinnerPriorityId+"', " +
                "'"+spinnerStatusId+"')");
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        i.putExtra("callGetFresh","huuhoang");
        startActivity(i)    ;
    }
}
