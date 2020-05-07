package com.rahat_jnu.expensetracker.ui.income;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rahat_jnu.expensetracker.ui.note.Note;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    // declare require values
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "incomeDB";
    private static final String TABLE_NAME = "incomeTable";

    public Database(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // declare table column names
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CONTENT = "discription";
    private static final String KEY_DATE = "date";






    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE "+TABLE_NAME+" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_AMOUNT+" INTEGER,"+
                KEY_CONTENT+" TEXT,"+
                KEY_DATE+" TEXT"
                +" )";
        db.execSQL(createDb);
    }

    // upgrade db if older version exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long addIncome(Income income){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_AMOUNT,income.getAmountIncome());
        v.put(KEY_CONTENT,income.getDescriptionIncome());
        v.put(KEY_DATE,income.getDateIncome());

        // inserting data into db
        long ID = db.insert(TABLE_NAME,null,v);
        return  ID;
    }


    public Income getIncome(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_AMOUNT,KEY_CONTENT,KEY_DATE};
        Cursor cursor=  db.query(TABLE_NAME,query,KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Income(
                Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3));
    }

    public List<Income> getAllIncomes(){
        List<Income> allIncomes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Income income = new Income();
                income.setIdIncome(Long.parseLong(cursor.getString(0)));
                income.setAmountIncome(Long.parseLong(cursor.getString(1)));
                income.setDescriptionIncome(cursor.getString(2));
                income.setDateIncome(cursor.getString(3));

                allIncomes.add(income);
            }while (cursor.moveToNext());
        }

        return allIncomes;

    }

    public int editIncome(Income income){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_AMOUNT,income.getAmountIncome());
        c.put(KEY_CONTENT,income.getDescriptionIncome());
        c.put(KEY_DATE,income.getDateIncome());
        return db.update(TABLE_NAME,c,KEY_ID+"=?",new String[]{String.valueOf(income.getIdIncome())});
    }



    void deleteIncome(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public long getTotalIncome() {
        long t=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) as Total FROM incomeTable", null);
        if (cursor.moveToFirst()) {
            t = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return t;
    }


}
