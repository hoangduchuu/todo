package com.hoangduchuu.hoang.cs001;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by hoang on 9/24/16.
 */
public class EditItemDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{
    Communicator communicator;
    TextView txtViewDueDate_DL;
    Button btnAdd, btnCanel;
    EditText editTextInten_DL;
    ImageButton imageButtonDueDate_DL;
    Spinner spinner;
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_item_dialog_layout, container, false);
        spinner = (Spinner)view.findViewById(R.id.spinner);

        final Calendar calendar = Calendar.getInstance();
        btnAdd = (Button) view.findViewById(R.id.buttonSave_DL);
        btnCanel = (Button) view.findViewById(R.id.buttonCancel);
        imageButtonDueDate_DL = (ImageButton)view.findViewById(R.id.imageButtonDueDate_DL);
        txtViewDueDate_DL = (TextView)view.findViewById(R.id.textViewDueDate_DL);
        editTextInten_DL = (EditText)view.findViewById(R.id.editTextInten_DL);
        communicator = (Communicator) getActivity();

        // Get data from activity
        Bundle bundle = getArguments();
        txtViewDueDate_DL.setText(bundle.getString("dueDate").toString());
        editTextInten_DL.setText(bundle.get("itemName").toString());

        //  spinner
       ArrayAdapter adapter =  ArrayAdapter.createFromResource(getActivity(),R.array.priority,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Btn add click action
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = txtViewDueDate_DL.getText().toString();
                String itName = editTextInten_DL.getText().toString();
                int priority =  spinner.getSelectedItemPosition();
                communicator.UpdateSql2(itName ,date, priority );
            }
        });

        btnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // btn CALENDA
        imageButtonDueDate_DL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            txtViewDueDate_DL.setText("Due dates: " + dayOfMonth +"/"+ (monthOfYear + 1) +"/"+ year);
        }
    };

    // just Implement method
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        myView = view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
