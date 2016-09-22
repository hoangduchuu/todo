package com.hoangduchuu.hoang.cs001;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final MyDB myDB = new MyDB(this, "list_DB.sqlite", null, 1);
    ArrayList<String> todoItems;
    ListView lvItem;
    EditText edtTextItem;
    Button btnAdd;
    ArrayList<ItemList> arrayItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItem = (ListView)findViewById(R.id.listViewItems);
        btnAdd = (Button)findViewById(R.id.buttonAdd);
        edtTextItem = (EditText)findViewById(R.id.editTextItemName);

        // Use SQLITE
        myDB.queryDB("CREATE TABLE IF NOT EXISTS Item(id INTEGER PRIMARY KEY AUTOINCREMENT, itemName  VARCHAR)");
//        myDB.queryDB("INSERT INTO Item VALUES(null, 'hoangduchuu')");


        //show listview
        arrayItemList = new ArrayList<ItemList>();
        get_And_ShowListView();

        // add Item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNameAdd = edtTextItem.getText().toString();
                myDB.queryDB("INSERT INTO Item VALUES(null, '"+ itemNameAdd+"')");
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
                myDB.queryDB("DELETE FROM Item where id ="+sqlitePosition+"");
                arrayItemList = new ArrayList<ItemList>();
                get_And_ShowListView();
                Toast.makeText(MainActivity.this, "Deleted: " + sqlitePosition +"---"+ itemString, Toast.LENGTH_SHORT).show();
                return false;

            }
        });
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  sqlitePosition = String.valueOf(arrayItemList.get(position).getId());
                String itemName = arrayItemList.get(position).getItName();
                Intent i = new Intent(getApplicationContext(), EditItemActivity.class);
                i.putExtra("sqlitePosition", sqlitePosition);
                i.putExtra("itemName",itemName);
                startActivityForResult(i,0);
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
                myDB.queryDB("UPDATE Item SET itemName ='"+newName+"' WHERE id = '"+sqlitePosition+"'");
                arrayItemList = new ArrayList<ItemList>();
                get_And_ShowListView();
                break;
        }
    }

    //get and show listview function
    public void get_And_ShowListView(){
        Cursor cursorOfItem = myDB.getDB("SELECT * FROM Item");
        while (cursorOfItem.moveToNext()){
            arrayItemList.add(new ItemList(
                    cursorOfItem.getInt(0),
                    cursorOfItem.getString(1)
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
