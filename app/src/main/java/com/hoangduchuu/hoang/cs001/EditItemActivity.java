package com.hoangduchuu.hoang.cs001;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {
    EditText edtIntent;
    Button btnSave;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String ItemGeted = getIntent().getStringExtra("itemName");

        edtIntent = (EditText)findViewById(R.id.editTextInten);
        edtIntent.setText(ItemGeted);

        // btnSave put Intent
        btnSave = (Button)findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sqlitePosition = getIntent().getStringExtra("sqlitePosition");
                String resultString = edtIntent.getText().toString();

                Intent i =  new Intent(EditItemActivity.this, MainActivity.class);
                i.putExtra("sqlitePositionResult", sqlitePosition);
                i.putExtra("new_name", resultString);
                setResult(RESULT_OK, i );
                finish();
            }
        });
    }

}
