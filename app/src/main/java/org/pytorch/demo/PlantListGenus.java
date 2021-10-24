package org.pytorch.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.pytorch.demo.vision.PlantFamily;
import org.pytorch.demo.vision.PlantFamilyAdapter;
import org.pytorch.demo.vision.database.DataBaseHelper;

import java.util.ArrayList;

public class PlantListGenus extends AppCompatActivity {
    //Context context;
    SearchView searchview_genus_list;
    TextView txt_family_tab;
    TextView txt_genus_tab;
    TextView txt_species_tab;
    ListView custom_listview_plant_genus;
    DataBaseHelper db=new DataBaseHelper(this);
    ArrayList<PlantFamily> data;
    PlantFamilyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list_genus);
        createData();

        //searchview_genus_list = findViewById(R.id.searchview_genus_list);
        SearchView theFilter = (SearchView) findViewById(R.id.searchview_genus_list);
        txt_family_tab = findViewById(R.id.txt_genus_list_family_tab);
        txt_genus_tab = findViewById(R.id.txt_genus_list_genus_tab);
        txt_species_tab = findViewById(R.id.txt_genus_list_species_tab);
        custom_listview_plant_genus = findViewById(R.id.custom_listview_plant_genus);

        SpannableString content = new SpannableString("CHI");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txt_genus_tab.setText(content);

        adapter = new PlantFamilyAdapter(this, R.layout.custom_listview_plant_family, data);
        custom_listview_plant_genus.setAdapter(adapter);
        custom_listview_plant_genus.setClickable(true);

        theFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                (PlantListGenus.this).adapter.getFilter().filter(query);
            }
        });


        custom_listview_plant_genus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1) {
                    PlantFamily item = data.get(position);
                    Toast.makeText(PlantListGenus.this, item.name, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_family_tab.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(PlantListGenus.this, PlantListFamily.class));
        });

        txt_species_tab.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(PlantListGenus.this, PlantListSpecies.class));
        });
    }

    private void createData() {
        data = new ArrayList<PlantFamily>();
        Cursor cursor = db.getDataGenus_Plant();
        int count = cursor.getCount();
        if (cursor.moveToFirst()) {
            do {
                PlantFamily plantFamily = new PlantFamily(
                        cursor.getInt(cursor.getColumnIndex("image")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getString(cursor.getColumnIndex("numGenus"))
                );

                data.add(plantFamily);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}