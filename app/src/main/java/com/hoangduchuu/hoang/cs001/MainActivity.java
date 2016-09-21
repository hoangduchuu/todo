package com.hoangduchuu.hoang.cs001;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    ArrayAdapter<String> todoAdappter;
    ListView lvItem;
    EditText edtText;
    int selectedPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItem = (ListView)findViewById(R.id.listViewItems);
        edtText = (EditText)findViewById(R.id.editTextText);
        lvItem.setAdapter(todoAdappter);

        lvItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                todoAdappter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                String itemName = lvItem.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "clicked " + lvItem.getItemAtPosition(position).toString() , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("itemName",itemName);
                startActivityForResult(i, 0);
            }
        });

    }


    public void populateArrayItems() {
        todoItems = new ArrayList<String>();
        todoItems.add("Item 1 a ");
        readItems();
        todoAdappter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);

    }



    private void readItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try{
           todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch (IOException e ){

        }
    }
    private void writeItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);
        }catch (IOException e ){

        }
    }

    public void onAddItem(View view) {
        todoAdappter.add(edtText.getText().toString());
        edtText.setText("");
        writeItems();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                String newName = data.getStringExtra("new_name");
                todoItems.set(selectedPosition, newName);
                todoAdappter.notifyDataSetChanged();
                break;
        }
    }
}
