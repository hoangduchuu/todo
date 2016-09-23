package com.hoangduchuu.hoang.cs001;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    final MyDB myDB = new MyDB(this, "list_DB.sqlite", null, 1);
    ArrayList<String> todoItems;
    ListView lvItem;
    EditText edtTextItem;
    Button btnAdd;
    ImageButton imgCalenda;
    TextView textViewCalenda;
    ArrayList<ItemList> arrayItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItem = (ListView)findViewById(R.id.listViewItems);
        btnAdd = (Button)findViewById(R.id.buttonAdd);
        imgCalenda = (ImageButton)findViewById(R.id.imageButtonCalenDa);
        textViewCalenda = (TextView)findViewById(R.id.textViewCalenda);
        final Calendar calendar = Calendar.getInstance();
        edtTextItem = (EditText)findViewById(R.id.editTextItemName);


        // Use SQLITE
        myDB.queryDB("CREATE TABLE IF NOT EXISTS Item2(id INTEGER PRIMARY KEY AUTOINCREMENT, itemName  VARCHAR, dueDate VARCHAR)");
//        myDB.queryDB("INSERT INTO Item2 VALUES(null, 'hoangduchuu' '01011993')");


        //show listview
        arrayItemList = new ArrayList<ItemList>();
        get_And_ShowListView();


        // DiaLog
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dueDate = dayOfMonth +"/"+ (1+monthOfYear) +"/"+ year;
                textViewCalenda.setText("Due Dates: " + dueDate);
            }
        };

        // add Item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dueDate = textViewCalenda.getText().toString();
                String itemNameAdd = edtTextItem.getText().toString();
                myDB.queryDB("INSERT INTO Item2 VALUES(null, '"+ itemNameAdd+"','"+dueDate+"')");
                // refresh ListView
                arrayItemList = new ArrayList<ItemList>();
                get_And_ShowListView();
                edtTextItem.setText("");
            }
        });

        // delete item
        lvItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String itemString = arrayItemList.get(position).getItName();
                int sqlitePosition = arrayItemList.get(position).getId();
                myDB.queryDB("DELETE FROM Item2 where id ="+sqlitePosition+"");
                arrayItemList = new ArrayList<ItemList>();
                get_And_ShowListView();
                Toast.makeText(MainActivity.this, "Deleted: " + sqlitePosition +"---"+ itemString, Toast.LENGTH_SHORT).show();
                return false;

            }
        });
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dueDate = arrayItemList.get(position).getDueDate();
                String  sqlitePosition = String.valueOf(arrayItemList.get(position).getId());
                String itemName = arrayItemList.get(position).getItName();
                Intent i = new Intent(getApplicationContext(), EditItemActivity.class);
                i.putExtra("sqlitePosition", sqlitePosition);
                i.putExtra("itemName",itemName);
                i.putExtra("dueDate",dueDate);
                startActivityForResult(i,0);
            }
        });

        // btn calenda
        imgCalenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


        // update item
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                String newName = data.getStringExtra("new_name");
                String sqlitePosition = data.getStringExtra("sqlitePositionResult");
                String new_dueDate = data.getStringExtra("dueDateResult");
                myDB.queryDB("UPDATE Item2 SET itemName ='"+newName+"', dueDate = '"+new_dueDate+"' WHERE id = '"+sqlitePosition+"'");
                Toast.makeText(MainActivity.this, "Data Changed", Toast.LENGTH_SHORT).show();
                arrayItemList = new ArrayList<ItemList>();
                get_And_ShowListView();
                break;
        }
    }

    //get and show listview function
    public void get_And_ShowListView(){
        textViewCalenda.setText("");
        Cursor cursorOfItem = myDB.getDB("SELECT * FROM Item2");
        while (cursorOfItem.moveToNext()){
            arrayItemList.add(new ItemList(
                    cursorOfItem.getInt(0),
                    cursorOfItem.getString(1),
                    cursorOfItem.getString(2)

            ));
        }
        itemAdapter adapter = new itemAdapter(
                MainActivity.this,
                R.layout.item_layout_list,
                arrayItemList
        );
        lvItem.setAdapter(adapter);
    }
}
