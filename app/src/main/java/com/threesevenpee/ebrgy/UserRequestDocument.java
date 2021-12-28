package com.threesevenpee.ebrgy;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class UserRequestDocument extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.user_document_request);

        //spinner document type
        Spinner spinnerDocuType = findViewById(R.id.spinnerUserRequestDocumentType);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.document_type, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDocuType.setAdapter(adapter1);
        spinnerDocuType.setOnItemSelectedListener(this);


        //spinner document purpose
        Spinner spinnerDocuPurpose = findViewById(R.id.spinnerUserRequestDocumentPurpose);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.document_purpose, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDocuPurpose.setAdapter(adapter2);
        spinnerDocuPurpose.setOnItemSelectedListener(this);


        //spinner ID type
        Spinner spinnerIDType = findViewById(R.id.spinnerUserRequestIDType);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.id_type, android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIDType.setAdapter(adapter3);
        spinnerIDType.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}