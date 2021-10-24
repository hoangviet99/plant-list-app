package org.pytorch.demo.vision.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.pytorch.demo.R;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, "plant.db", null,1);
    }
    public static Boolean Boolean_Check_Insert_Genus_Plant=false;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table genus_Plant(image INTEGER,name TEXT,description TEXT,numGenus TEXT)");
        insertGenus_Plant();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists family_Plant");

    }
    public Cursor getDataGenus_Plant(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from genus_Plant",null);
        return  cursor;
    }
    public  void insertGenus_Plant() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("image", R.drawable.plant_chi_ven_ven);
        contentValues.put("name", "CHI VÊN VÊN");
        contentValues.put("description", "Anisoptera Korth");
        contentValues.put("numGenus", "1");
        db.insert("genus_Plant", null, contentValues);

        contentValues.put("image", R.drawable.plant_chi_dau);
        contentValues.put("name", "CHI DẦU");
        contentValues.put("description", "Dipterocarpus Gaertn");
        contentValues.put("numGenus", "1");
        db.insert("genus_Plant", null, contentValues);

        contentValues.put("image", R.drawable.plant_chi_sao);
        contentValues.put("name", "CHI SAO");
        contentValues.put("description", "Hopea L.");
        contentValues.put("numGenus", "1");
        db.insert("genus_Plant", null, contentValues);

        contentValues.put("image", R.drawable.plant_chi_chai);
        contentValues.put("name", "CHI CHAI");
        contentValues.put("description", "Shorea Roxb. ex Gaertn");
        contentValues.put("numGenus", "1");
        db.insert("genus_Plant", null, contentValues);

        contentValues.put("image", R.drawable.plant_chi_tau);
        contentValues.put("name", "CHI TÁU");
        contentValues.put("description", "Vatica L.");
        contentValues.put("numGenus", "1");
        db.insert("genus_Plant", null, contentValues);
    }}