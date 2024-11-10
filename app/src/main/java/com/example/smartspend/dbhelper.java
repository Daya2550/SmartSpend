package com.example.smartspend;

import android.content.Context;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

public class dbhelper extends SQLiteOpenHelper {

    public dbhelper(Context context) {
        super(context, "sqldj", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Corrected create table query
        String query = "CREATE TABLE main (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(20), " +
                "info VARCHAR(100), " +
                "date VARCHAR(20), " +
                "other VARCHAR(50), " +
                "price INTEGER NOT NULL, " +
                "total INTEGER" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS main");
        onCreate(sqLiteDatabase);
    }

    public void insertdata(String name, String info, String date, String other, int price, int total) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("info", info);
        contentValues.put("date", date);
        contentValues.put("other", other);
        contentValues.put("price", price);
        contentValues.put("total", total);
        sqLiteDatabase.insert("main", null, contentValues);
    }

    public String[] showdata(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, name, info, date, other, price, total FROM main WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            String[] result = new String[7];
            result[0] = cursor.getString(0);
            result[1] = cursor.getString(1);
            result[2] = cursor.getString(2);
            result[3] = cursor.getString(3);
            result[4] = cursor.getString(4);
            result[5] = cursor.getString(5);
            result[6] = cursor.getString(6);
            cursor.close();
            return result;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    public String displayAllData() {
        Cursor cursor = getAllData();
        StringBuilder data = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String info = cursor.getString(cursor.getColumnIndexOrThrow("info"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String other = cursor.getString(cursor.getColumnIndexOrThrow("other"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));

                data.append("ID: ").append(id)
                        .append(", Name: ").append(name)
                        .append(", Info: ").append(info)
                        .append(", Date: ").append(date)
                        .append(", Other: ").append(other)
                        .append(", Price: ").append(price)
                        .append(", Total: ").append(total)
                        .append("\n \n");
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            data.append("No data found");
        }
        return data.toString();
    }
    private Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM main", null);
    }

    public int getPreviousTotal() {
        SQLiteDatabase db = this.getReadableDatabase();
        int previousTotal = 0;  // Default value if no previous record is found
        Cursor cursor = db.rawQuery("SELECT total FROM main ORDER BY id DESC LIMIT 1 ", null);
        if (cursor != null && cursor.moveToFirst()) {
            previousTotal = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
            cursor.close();
        }
        return previousTotal;
    }


    public String getLastSixRecords() {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        // Define the query to select the last six records
        String query = "SELECT * FROM main ORDER BY id DESC LIMIT 6";

        // Execute the query and obtain the cursor
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        // Check if the cursor has records
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column indexes, and ensure they are valid (≥ 0)
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int infoIndex = cursor.getColumnIndex("info");
                int dateIndex = cursor.getColumnIndex("date");
                int otherIndex = cursor.getColumnIndex("other");
                int priceIndex = cursor.getColumnIndex("price");
                int totalIndex = cursor.getColumnIndex("total");

                // Retrieve values only if column indexes are valid
                if (idIndex >= 0) result.append("ID: ").append(cursor.getInt(idIndex));
                if (nameIndex >= 0) result.append(", Name: ").append(cursor.getString(nameIndex));
                if (infoIndex >= 0) result.append(", Info: ").append(cursor.getString(infoIndex));
                if (dateIndex >= 0) result.append(", Date: ").append(cursor.getString(dateIndex));
                if (otherIndex >= 0) result.append(", Other: ").append(cursor.getString(otherIndex));
                if (priceIndex >= 0) result.append(", Price: ").append(cursor.getInt(priceIndex));
                if (totalIndex >= 0) result.append(", Total: ").append(cursor.getInt(totalIndex));

                result.append("\n\n"); // Separate each record with a newline
            } while (cursor.moveToNext());
        }

        // Close the cursor after use
        cursor.close();

        return result.toString();
    }

    // Delete data
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("main", "id"+ " = ?", new String[]{id});
    }


    public void updateLastRecordTotal( int newTotal) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT id FROM main ORDER BY id DESC LIMIT 1";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            if (idIndex != -1) {
                int lastId = cursor.getInt(idIndex);
                ContentValues values = new ContentValues();
                values.put("total", newTotal);
                int rowsAffected = sqLiteDatabase.update("main", values, "id = ?", new String[]{String.valueOf(lastId)});
            }
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public void updateRecordById(int id, String name, String info, String date, int price) {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("info", info);
        values.put("date", date);
        values.put("price", price);
        int rowsAffected = sqLiteDatabase.update("main", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void truncateMainTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete all rows from the "main" table
        String s = "DELETE FROM main";
        db.execSQL(s);

        // Optionally, you can reclaim space using VACUUM if needed
        db.execSQL("VACUUM");
    }





}
