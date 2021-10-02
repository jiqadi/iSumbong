package com.example.isumbong;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class admin_create_report extends AppCompatActivity {

    MenuItem menuItem;
    SearchView searchView;
    SearchView.SearchAutoComplete searchAutoComplete;

    static ArrayList<String> serials = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;

    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_report);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //set search bar
        getMenuInflater().inflate(R.menu.activity_admin_search_bar,menu);
        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();

        searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.holo_blue_bright);

        //set suggestions
        db = new database(this);
        serials = db.getVerifiedSerial();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,serials);
        searchAutoComplete.setAdapter(arrayAdapter);
        searchView.setQueryHint("Search Serial Code");
        searchView.setSubmitButtonEnabled(true);

        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);
                Toast.makeText(admin_create_report.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(admin_create_report.this, admin_view_serial.class);
                intent.putExtra("clicked_serial", queryString);
                startActivity(intent);
            }
        });
        // Below event is triggered when submit search query.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AlertDialog alertDialog = new AlertDialog.Builder(admin_create_report.this).create();
                alertDialog.setMessage("Search keyword is " + query);
                alertDialog.show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}