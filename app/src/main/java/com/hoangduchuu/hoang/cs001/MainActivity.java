package com.hoangduchuu.hoang.cs001;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Communicator, AdapterView.OnItemSelectedListener{
    final MyDB myDB = new MyDB(this, "list_DB.sqlite", null, 1);
    ArrayList<String> todoItems;
    ListView lvItem;
    EditText edtTextItem;
    Button btnAdd ;
    Integer clickItemDataPosition = -9;
    EditItemDialog myEditItemDialog;
    Spinner spinner;
    int prioritiPosition = -11;

    ImageButton imgCalenda;
    TextView textViewCalenda;
    ArrayList<ItemList> arrayItemList;
    FragmentManager fragmentManager = getFragmentManager();
    String myLevel = "zzz";

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
         spinner = (Spinner)findViewById(R.id.spinner2);

        // spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.priority,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String priority = spinner.getSelectedItem().toString();
                prioritiPosition = position;
                myLevel = priority;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Use SQLITE
        myDB.queryDB("CREATE TABLE IF NOT EXISTS Item3(id INTEGER PRIMARY KEY AUTOINCREMENT, itemName  VARCHAR, dueDate VARCHAR, priority VARCHAR)");
//        myDB.queryDB("INSERT INTO Item2 VALUES(null, 'hoangduchuu' '01011993', '1')");


        //show listview
        arrayItemList = new ArrayList<ItemList>();
        get_And_ShowListView();


        // DiaLog Date
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
                myDB.queryDB("INSERT INTO Item3 VALUES(null, '"+ itemNameAdd+"','"+dueDate+"', '"+ prioritiPosition +"')");
                // refresh ListView
                Toast.makeText(MainActivity.this, "" + prioritiPosition, Toast.LENGTH_SHORT).show();
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
                myDB.queryDB("DELETE FROM Item3 where id ="+sqlitePosition+"");
                arrayItemList = new ArrayList<ItemList>();
                get_And_ShowListView();
                    Toast.makeText(MainActivity.this, "Deleted: " + sqlitePosition +"---"+ itemString, Toast.LENGTH_SHORT).show();
                return false;

            }
        });
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String dueDate = arrayItemList.get(position).getDueDate();
//                String  sqlitePosition = String.valueOf(arrayItemList.get(position).getId());
//                String itemName = arrayItemList.get(position).getItName();
//                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
//                i.putExtra("sqlitePosition", sqlitePosition);
//                i.putExtra("itemName",itemName);
//                i.putExtra("dueDate",dueDate);
//                startActivityForResult(i,0);
                clickItemDataPosition = arrayItemList.get(position).getId();



                myEditItemDialog = new EditItemDialog();

                // put data activity to fragment
                Bundle bundle = new Bundle();

                String dueDate = arrayItemList.get(position).getDueDate();
                String itName = arrayItemList.get(position).getItName();


                bundle.putString("dueDate",dueDate);
                bundle.putString("itemName", itName);
                bundle.putString("prioritiPosition",String.valueOf(prioritiPosition));
                myEditItemDialog.setArguments(bundle);

                myEditItemDialog.show(fragmentManager,"stringtagne");

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


//        // update item by Activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (resultCode){
//            case RESULT_OK:
//                String newName = data.getStringExtra("new_name");
//                String sqlitePosition = data.getStringExtra("sqlitePositionResult");
//                String new_dueDate = data.getStringExtra("dueDateResult");
//                myDB.queryDB("UPDATE Item2 SET itemName ='"+newName+"', dueDate = '"+new_dueDate+"' WHERE id = '"+sqlitePosition+"'");
//                Toast.makeText(MainActivity.this, "Data Changed", Toast.LENGTH_SHORT).show();
//                arrayItemList = new ArrayList<ItemList>();
//                get_And_ShowListView();
//                break;
//        }
//    }

    //get and show listview function
    public void get_And_ShowListView(){
        textViewCalenda.setText("");
        Cursor cursorOfItem = myDB.getDB("SELECT * FROM Item3");
        while (cursorOfItem.moveToNext()){
            arrayItemList.add(new ItemList(
                    cursorOfItem.getInt(0),
                    cursorOfItem.getString(1),
                    cursorOfItem.getString(2),
                    cursorOfItem.getInt(3)

            ));
        }
        itemAdapter adapter = new itemAdapter(
                MainActivity.this,
                R.layout.item_layout_list,
                arrayItemList
        );
        lvItem.setAdapter(adapter);
    }


    @Override
    public void UpdateSql2(String itemName, String dueDate, int priority) {
        myDB.queryDB("UPDATE Item3 SET itemName ='"+itemName+"', dueDate = '"+dueDate+"', priority='"+priority+"'  WHERE id = '"+clickItemDataPosition+"'");
        Toast.makeText(getApplicationContext(), "updated change", Toast.LENGTH_SHORT).show();
        arrayItemList = new ArrayList<ItemList>();
        get_And_ShowListView();
        myEditItemDialog.dismiss();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
