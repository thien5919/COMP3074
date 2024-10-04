package ca.gbc.comp3074.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "my.db";
    private static final int DB_VERSION = 1;
    public DBHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE ITEMS(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ITEMS" );
        onCreate(db);
    }
    public void addItem( String item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("NAME", item);
        db.insert("ITEMS", null, values);
        db.close();
    }

    public ArrayList<String> getAllItems(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();

        Cursor c = db.rawQuery("SELECT NAME FROM ITEMS", null);
        if (c.moveToFirst()){
            do {
                list.add(c.getString(0));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public void deleteItem(String item){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("ITEMS", "name=?", new String[]{item});
        db.close();
    }
}
