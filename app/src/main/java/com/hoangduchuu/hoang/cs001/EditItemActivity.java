package com.hoangduchuu.hoang.cs001;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {
    EditText edtIntent;
    Button btnSave;
    TextView txtViewDueDate;
    ImageButton imageButtonDueDate;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);



        imageButtonDueDate = (ImageButton)findViewById(R.id.imageButtonDueDate);
        txtViewDueDate = (TextView)findViewById(R.id.textViewDueDate);
        edtIntent = (EditText)findViewById(R.id.editTextInten);
        btnSave = (Button)findViewById(R.id.buttonSave);
        final Calendar calendar = Calendar.getInstance();

        String ItemGeted = getIntent().getStringExtra("itemName");
        final String dueDate = getIntent().getStringExtra("dueDate");
        edtIntent.setText(ItemGeted);
        txtViewDueDate.setText(dueDate);

        // btnSave put Intent
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sqlitePosition = getIntent().getStringExtra("sqlitePosition");
                String resultString = edtIntent.getText().toString();
                String dueDateResult = txtViewDueDate.getText().toString();

                Intent i =  new Intent(EditItemActivity.this, MainActivity.class);
                i.putExtra("sqlitePositionResult", sqlitePosition);
                i.putExtra("new_name", resultString);
                i.putExtra("dueDateResult", dueDateResult);
                setResult(RESULT_OK, i );
                finish();
            }
        });

        imageButtonDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditItemActivity.this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            txtViewDueDate.setText("Due dates: " + dayOfMonth +"/"+ (monthOfYear + 1) +"/"+ year);
        }
    };
}
