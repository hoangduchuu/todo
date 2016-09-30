package com.hoangduchuu.hoang.cs001;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Detail extends AppCompatActivity {
    final MyDB myDB = new MyDB(this, "list_DB.sqlite", null, 1);
    int idtoDel;
    TextView txtvTask, txtvNote, txtvDate, txtvStatus, txtvPriority;
    Toolbar toolbarDetail;
    int priorityID = 88;
    int statusID = 88;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbarDetail = (Toolbar)findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbarDetail);
        //get intent data
        Intent i = getIntent();
        int id = i.getIntExtra("id", -99);
        idtoDel = id;
        String task = i.getStringExtra("task");
        String note = i.getStringExtra("note");
        String date = i.getStringExtra("date");
        int priority = i.getIntExtra("priority", -99);
        int status = i.getIntExtra("status", -99);

        priorityID = priority;
        statusID = status;

        txtvTask = (TextView)findViewById(R.id.textViewTaskName);
        txtvNote = (TextView)findViewById(R.id.textViewNote);
        txtvDate = (TextView)findViewById(R.id.textViewDate);
        txtvStatus = (TextView)findViewById(R.id.textViewStatus);
        txtvPriority = (TextView)findViewById(R.id.textViewPriority);


        txtvTask.setText(task);
        txtvNote.setText(note);
        txtvDate.setText(date);

        String priorityValue = "vcl";
        switch (priority){
            case 0 :  priorityValue = "Hight"; break;
            case 1 :  priorityValue = "Low"; break;
            case 2 :  priorityValue = "Medium"; break;
        }

        String statusValue = "vcl";
        switch (status){
            case 0 : statusValue = "Done";break;
            case 1 : statusValue = "To-Do";
        }
        txtvStatus.setText(statusValue);
        txtvPriority.setText(priorityValue);
    }

    // Implement Menu method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.editDetail:
                Intent i = new Intent(getApplicationContext(), EditItemActivity.class);
                i.putExtra("task", txtvTask.getText().toString());
                i.putExtra("note", txtvNote.getText().toString());
                i.putExtra("date", txtvDate.getText().toString());
                i.putExtra("status", txtvStatus.getText().toString());
                i.putExtra("priority", txtvPriority.getText().toString());
                i.putExtra("statusID", statusID);
                i.putExtra("priorityID", priorityID);
                i.putExtra("id", idtoDel);
                startActivity(i);
                break;
            case R.id.deleteDetail:
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

                                myDB.queryDB("DELETE FROM aTask where id = '"+idtoDel+"' ");
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                Detail.this.finish();
                            }
                        }
                );
                myBuilder.setTitle("Confirm");
                myBuilder.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
