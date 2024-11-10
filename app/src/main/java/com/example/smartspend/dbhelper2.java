package com.example.smartspend;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbhelper2 extends SQLiteOpenHelper {

    public dbhelper2 (Context context) {
        super(context, "sqldj2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the main table with the correct columns
        String query = "CREATE TABLE main2 (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(20), " +
                "info VARCHAR(100), " +
                "inprice INTEGER, " +
                "outprice INTEGER, " +
                "final INTEGER" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS main2");
        onCreate(sqLiteDatabase);
    }

    // Insert data into the main table
    public void insertdata(String name, String info, int inprice, int outprice, int finalPrice) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("info", info);
        contentValues.put("inprice", inprice);
        contentValues.put("outprice", outprice);
        contentValues.put("final", finalPrice);
        sqLiteDatabase.insert("main2", null, contentValues);
    }

    // Retrieve data based on the id
    public String[] showdata(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, name, info, inprice, outprice, final FROM main2 WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            String[] result = new String[6];
            result[0] = cursor.getString(0);
            result[1] = cursor.getString(1);
            result[2] = cursor.getString(2);
            result[3] = cursor.getString(3);
            result[4] = cursor.getString(4);
            result[5] = cursor.getString(5);
            cursor.close();
            return result;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    // Display all records in the database
    public String displayAllData() {
        Cursor cursor = getAllData();
        StringBuilder data = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String info = cursor.getString(cursor.getColumnIndexOrThrow("info"));
                int inprice = cursor.getInt(cursor.getColumnIndexOrThrow("inprice"));
                int outprice = cursor.getInt(cursor.getColumnIndexOrThrow("outprice"));
                int finalPrice = cursor.getInt(cursor.getColumnIndexOrThrow("final"));

                data.append("ID: ").append(id)
                        .append(", Name: ").append(name)
                        .append(", Info: ").append(info)
                        .append(", Not received: ").append(inprice)
                        .append(", Send : ").append(outprice)
                        .append(", Final: ").append(finalPrice)
                        .append("\n \n");
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            data.append("No data found");
        }
        return data.toString();
    }

    // Get all data from the main table
    private Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM main2", null);
    }

    // Delete data by id
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("main2", "id = ?", new String[]{id});
    }

    // Update a record by id
    public void updateRecordById(int id, String name, String info, int inprice, int outprice, int finalPrice) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("info", info);
        values.put("inprice", inprice);
        values.put("outprice", outprice);
        values.put("final", finalPrice);
        sqLiteDatabase.update("main2", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // Truncate the "main" table (delete all records)
    public void truncateMainTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM main2");
        db.execSQL("VACUUM");
    }
}
