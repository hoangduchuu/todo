package com.hoangduchuu.hoang.cs001;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class EditItemActivity extends AppCompatActivity {
    final MyDB myDB = new MyDB(this, "list_DB.sqlite", null, 1);
    TextView txtViewDueDate;
    Spinner spinnerPriority, spinnerStatus;
    EditText edtTask, edtNote;
    int sqlId = -1;
    int selectedPriority;
    int selectedStatus;
    ImageButton imageButton ;
    String dueDate = "hehe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolBar;
        toolBar = (Toolbar)findViewById(R.id.toolbarEditItem);
        setSupportActionBar(toolBar);

        edtTask = (EditText)findViewById(R.id.editTextName);
        edtNote = (EditText)findViewById(R.id.editTextNotes);
        txtViewDueDate = (TextView)findViewById(R.id.textViewDateDue);
        imageButton = (ImageButton)findViewById(R.id.imageButtonA);
        final Calendar calendar = Calendar.getInstance();




        /// get
        Intent i = getIntent();
        int priorityID = i.getIntExtra("priorityID", 11);
        int statusID = i.getIntExtra("statusID", 11);
        sqlId = i.getIntExtra("id" , 32131);

        edtTask.setText(i.getStringExtra("task").toString());
        edtNote.setText(i.getStringExtra("note").toString());
        txtViewDueDate.setText(i.getStringExtra("date").toString());
        // end get

        spinnerPriority = (Spinner)findViewById(R.id.spinnerPriority);
        spinnerStatus = (Spinner)findViewById(R.id.spinnerStatus);
        // spinnerPriority
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.priority,android.R.layout.simple_spinner_item);
        spinnerPriority.setAdapter(adapter);
        spinnerPriority.setSelection(priorityID);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),R.array.status,android.R.layout.simple_spinner_item);
        spinnerStatus.setAdapter(adapter2);
        spinnerStatus.setSelection(statusID);

        // 02 action spinner
        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPriority = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }); // end 02 action spiinner

        // DiaLog Date
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dueDate = dayOfMonth +"/"+ (1+monthOfYear) +"/"+ year;
                txtViewDueDate.setText(dueDate);
            }
        };
        // btn calenda
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditItemActivity.this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });// end btn calendar
    }

    // Implement Menu method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itSave:
                if (edtTask.getText().toString().isEmpty()) msnToast("Task Name is empty, enter Task Name before submit");
                else save();
                break;
            case R.id.itDelete:
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
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

                                myDB.queryDB("DELETE FROM aTask where id = '"+sqlId+"' ");
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                EditItemActivity.this.finish();
                            }
                        }
                );
                myBuilder.setTitle("Confirm");
                myBuilder.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    } // end menu method


    public void msnToast(String string){
        Toast.makeText(getApplicationContext(), "" + string, Toast.LENGTH_SHORT).show();
    }
    public void save(){

        String taskName = edtTask.getText().toString();
        String taskNnote = edtNote.getText().toString();
        String dueDate = txtViewDueDate.getText().toString();
        myDB.queryDB("UPDATE aTask SET taskName ='"+taskName+"', dueDate = '"+dueDate+"', taskNote = '"+taskNnote+"', priority ='"+selectedPriority+"' , status ='"+selectedStatus+"' WHERE id = '"+sqlId+"'");
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

}
