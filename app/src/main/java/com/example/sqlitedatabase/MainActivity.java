package com.example.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDataBaseHelper db;
    EditText nomInput, auteurInput, categorieInput;
    Button buttonAddData, buttonViewAllData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLiteDataBaseHelper(this);
        nomInput=(EditText) findViewById(R.id.nom_edittext);
        auteurInput=(EditText) findViewById(R.id.auteur_edittext);
        categorieInput=(EditText) findViewById(R.id.category_edittext);
        buttonAddData = (Button) findViewById(R.id.ajouter_button);
        AddData();
        buttonViewAllData = (Button) findViewById(R.id.afficher_données_button);
        ViewAll();
    }
    public void AddData() {
        buttonAddData.setOnClickListener (
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { // insert the new raw data on click of the button
                        boolean isInserted = db.insertData(nomInput.getText().toString(), auteurInput.getText().toString(),
                                categorieInput.getText().toString());
                        if(isInserted==true)
                            Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void ViewAll() {
        buttonViewAllData.setOnClickListener (
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor data = db.getAllData();
                    if (data.getCount() == 0) {
                        // Show message
                        showMessage("Error", "No Data Found !");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (data.moveToNext()) {
                        buffer.append("Id :" + data.getString(0) + "\n");
                        buffer.append("Nom :" + data.getString(1) + "\n");
                        buffer.append("Auteur :" + data.getString(2) + "\n");
                        buffer.append("Catégorie :" + data.getString(3) + "\n");
                    }
                    showMessage("Data", buffer.toString());
                }
            }
        );
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}