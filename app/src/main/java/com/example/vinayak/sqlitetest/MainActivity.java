package com.example.vinayak.sqlitetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etNum) EditText etNum;
    @BindView(R.id.insertOne) Button insertOne;
    @BindView(R.id.tv_sqlite_db) TextView tvSqlite;
    @BindView(R.id.etDeleteOne) EditText etDeleteOne;
    @BindView(R.id.deleteOne) Button deleteOne;
    @BindView(R.id.displayAll) Button displayAll;
    @BindView(R.id.deleteAll) Button deleteAll;
    @BindView(R.id.etSelectOne) EditText etSelectOne;
    @BindView(R.id.selectOne) Button selectOne;
    @BindView(R.id.tvSelectedCon) TextView tvSelectedCon;

    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the view using butterknife
        ButterKnife.bind(this);

        db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */

        displayContacts();


        insertOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Insert: ", "Inserting ..");
                String name=etName.getText().toString();
                String number=etNum.getText().toString();

                Log.d("Insert: ", "Inserting .."+name+ " "+number);

                db.addContact(new Contact(name,number));
                displayContacts();

            }
        });



        displayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayContacts();
            }
        });


        deleteOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteContactById(Integer.parseInt(etDeleteOne.getText().toString()));
                displayContacts();
            }
        });

        selectOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact c = db.getContact(Integer.parseInt(etSelectOne.getText().toString()));
                if(c!=null)
                {
                String contactstr = "Selected Contact:\nId: "+c.getID()+" ,Name: " + c.getName() + " ,Phone: " + c.getPhoneNumber();
                tvSelectedCon.setText(contactstr);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Oh Dear!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                displayContacts();
            }
        });

    }

    void displayContacts(){
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        String contactstr="SQLite DB\n";
        for (Contact cn : contacts) {
            contactstr = contactstr+"Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber()+"\n";
            // Writing Contacts to log
            Log.d("Name: ", contactstr);
        }
        tvSqlite.setText(contactstr);
    }

}
