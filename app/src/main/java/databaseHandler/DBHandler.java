package databaseHandler;

/**
 * Created by Sai sreenivas on 10/30/2017.
 */


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import object.DateAndCount;

import static databaseHandler.Constants.COLUMN_CLOTHES_COUNT;
import static databaseHandler.Constants.COLUMN_CLOTHES_DATE;
import static databaseHandler.Constants.COLUMN_CLOTHES_INNERS;
import static databaseHandler.Constants.COLUMN_CLOTHES_PANTS;
import static databaseHandler.Constants.COLUMN_CLOTHES_SHIRTS;
import static databaseHandler.Constants.COLUMN_ID;
import static databaseHandler.Constants.TABLE_CLOTHES;

/**
 * Created by Sai sreenivas on 12/30/2016.
 */

public class DBHandler extends SQLiteOpenHelper {
    Context context;

    public DBHandler(Context context) {
        super(context,Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_CLOTHES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHES);
// Creating tables again
        onCreate(db);

    }

    public void AddDateAndCount(DateAndCount dateAndCount){
        /*ContentValues value = new ContentValues();
        value.put(COLUMN_MONTH_NAME, expense.get_month());
        value.put(COLUMN_NAME, expense.get_name());
        value.put(COLUMN_AMOUNT, expense.get_amount());*/
        if(dateAndCount.getDate() == "" || dateAndCount.getShirts() == 0 || dateAndCount.getPants() == 0 ||
                dateAndCount.getInners() == 0) {
            Toast.makeText(context, "count is missing", Toast.LENGTH_SHORT);
        }
        else {
            Log.v("dbHandler", dateAndCount.getDate() + " " + dateAndCount.getShirts() + " " + dateAndCount.getPants()+
                dateAndCount.getInners() );
            String addQuery = "INSERT INTO " + Constants.TABLE_CLOTHES + "(" + Constants.COLUMN_CLOTHES_DATE + "," +
                    Constants.COLUMN_CLOTHES_SHIRTS + "," + Constants.COLUMN_CLOTHES_PANTS + "," +
                    Constants.COLUMN_CLOTHES_INNERS + ")" + " VALUES ('" + dateAndCount.getDate() + "','" +
                    dateAndCount.getShirts() + "','" + dateAndCount.getPants() + "','" + dateAndCount.getInners() + "')";
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(addQuery);
            db.close();
        }
    }

    public ArrayList<DateAndCount> ShowDateAndCount(){
//        String showDateAndCountQuery = "SELECT * FROM " + Constants.CREATE_CLOTHES_TABLE +")";
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(showDateAndCountQuery, null);
        Cursor cursor = db.query(TABLE_CLOTHES, new String[]{COLUMN_ID, COLUMN_CLOTHES_DATE,
                COLUMN_CLOTHES_SHIRTS + " + " +COLUMN_CLOTHES_PANTS + " + " +COLUMN_CLOTHES_INNERS + " AS " +
                        COLUMN_CLOTHES_COUNT}, null, null, null, null, null);

        ArrayList<DateAndCount> dateAndCount = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                DateAndCount dateAndCount1 = new DateAndCount();
                dateAndCount1.setDate(cursor.getString(1));
                dateAndCount1.setCount(cursor.getInt(2));
                dateAndCount.add(dateAndCount1);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return dateAndCount;
    }


/*

    public ArrayList<DateAndCount> databaseToArry(DateAndCount expense){
        String fullQuery = "SELECT * FROM " + Constants.TABLE_MONTHLY_EXPENSE + " WHERE " + Constants.COLUMN_MONTH_NAME + " LIKE '" +
                expense.get_month() + "';";
        ArrayList<DateAndCount> totalList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(fullQuery, null);
        if(cursor.moveToFirst()){
            do {
                ExpenseDone row = new ExpenseDone();
                row.set_id(Integer.parseInt(cursor.getString(0)));
                Log.v("databaseToArray", cursor.getString(0));
                row.set_month(cursor.getString(1));
                row.set_name(cursor.getString(2));
                row.set_amount(Integer.parseInt(cursor.getString(3)));
                row.set_date(cursor.getString(4));
                // Adding contact to list
                totalList.add(row);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return  totalList;
    }
*/


    public int getCount(){
        String countQuery = "SELECT * FROM " + Constants.TABLE_CLOTHES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

/*
        public Cursor cursorEdit(Cursor cursor){
            String editQuery = "SELECT MESSAGE_ID,MESSAGE_NUMBER,MESSAGE_FULL FROM TABLE_MESSAGES WHERE MESSAGE_DATA IN(DEBITTED, " +
                    "CREDITTED)";
            Cursor c1 = cursor.    }
    /*

    public void AddMessage(MessageEach message){
        String addMessageQuery = "INSERT INTO " + Constants.TABLE_MESSAGE + " (" + Constants.COLUMN_NUMBER + ", " + Constants.COLUMN_MESSAGE + ", " + Constants.COLUMN_DATE +
                ", " + Constants.COLUMN_MY_NAME + ", " + Constants.COLUMN_MONEY + ") " + " VALUES('"+message.getmAddress() + "','"+ message.getmBody()+ "','"+ message.getmDate()+
                "','" + message.getmName() + "','" + message.getmMoney() +"')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(addMessageQuery);
        db.close();
    }

    public MessageEach PreviousMessage(){
        String previousQuery = "SELECT * FROM " + Constants.TABLE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(previousQuery, null);

        cursor.moveToLast();
        MessageEach messageEach = new MessageEach();
        if(cursor.getCount() != 0) {
            messageEach.setmBody(cursor.getString(2));
            messageEach.setmDate(cursor.getString(3));
            Log.v("PreviousMessage", cursor.getString(2));
        }
        else {
            messageEach.setmBody("01");
            messageEach.setmDate("02");
        }
        return messageEach;
    }

    public void DeleteMessages(){
        String deleteMesssageQuery = "DELETE FROM " + Constants.TABLE_MESSAGE + " WHERE " + Constants.COLUMN_MESSAGE + " LIKE ('%Offers%')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteMesssageQuery);
        db.close();
    }


    public void DeleteMessage(MessageEach message){
        String deleteMesssageQuery = "DELETE FROM " + Constants.TABLE_MESSAGE + " WHERE " + Constants.COLUMN_MESSAGE + " = '"+ message.getmBody() + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteMesssageQuery);
        db.close();
    }

    public ArrayList<MessageEach> ViewMessagesDeposit(){
        String viewMessageQuery = "SELECT * FROM " + Constants.TABLE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(viewMessageQuery,null);
        ArrayList<MessageEach> messageEachList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                MessageEach messageEach = new MessageEach();
                messageEach.setmAddress(cursor.getString(1));
                messageEach.setmBody(cursor.getString(2));
                messageEach.setmDate(cursor.getString(3));
                //Log.v("DBHandler", cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
                messageEachList.add(messageEach);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return messageEachList;
    }

    public ArrayList<MessageEach> ViewMessagesSeparated(){
        String viewMessageQuery = "SELECT * FROM " + Constants.TABLE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(viewMessageQuery,null);
        ArrayList<MessageEach> messageEachList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                MessageEach messageEach = new MessageEach();
                messageEach.setmAddress(cursor.getString(1));
                messageEach.setmBody(cursor.getString(2));
                messageEach.setmDate(cursor.getString(3));
                messageEach.setmName(cursor.getString(4));
                messageEachList.add(messageEach);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return messageEachList;
    }

    public void deleteAllMessages(){
        String deleteQuery = "DELETE FROM " + Constants.TABLE_MESSAGE;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();
    }

   */
/* public void copyDbToExternal(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//data//" + context.getApplicationContext().getPackageName() + "//databases//"
                        + DATABASE_NAME;
                String backupDBPath = DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*//*


    public int getLastCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_LASTDATECOUNT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public List<ExpenseDone> viewLastDate(){
        String viewAllQuery= "SELECT * FROM " + Constants.TABLE_LASTDATECOUNT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(viewAllQuery, null);
        List<ExpenseDone> expenseDones = new ArrayList<>();
        if(cursor.moveToLast()){
            ExpenseDone expenseDone1 = new ExpenseDone();
            expenseDone1.set_count(cursor.getString(2));
            expenseDone1.set_date(cursor.getString(1));
            expenseDones.add(expenseDone1);
        }
        return expenseDones;
    }

    public void deleteEarlierDates(Long DateStr){
        String deleteEarlierQuery = "SELECT * FROM " + Constants.TABLE_LASTDATECOUNT + " WHERE " + Constants.COLUMN_DATE + " = " + DateStr;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c1= db.rawQuery(deleteEarlierQuery, null);
        String deleteAllQuery = "DELETE FROM " + Constants.TABLE_LASTDATECOUNT;
        db.execSQL(deleteAllQuery);
        if(c1.moveToFirst()){
            do {
                String addData = "INSERT INTO " + Constants.TABLE_LASTDATECOUNT + "('" + Constants.COLUMN_DATE + "','" + Constants.COLUMN_COUNT +"') VALUES('"
                        + c1.getString(1) + "','" + c1.getString(2) + "')";
                db.execSQL(addData);
                db.close();
                Log.v("deleteEarlierDates", c1.getString(1) + " " + c1.getString(2) );
            }
            while(c1.moveToNext());
        }
    }

    public void addThePresentDate(ExpenseDone expenseDone){
        String addData = "INSERT INTO " + Constants.TABLE_LASTDATECOUNT + "('" + Constants.COLUMN_DATE + "','" + Constants.COLUMN_COUNT +"') VALUES('"
                + expenseDone.get_date() + "','" + expenseDone.get_count() + "')";
        Log.v("addThePresentDate", expenseDone.get_date() + "','" + expenseDone.get_count());
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(addData);
        db.close();
    }

    public ExpenseDone showLast(){
        ExpenseDone expense = new ExpenseDone();
        String selectLastQuery = "SELECT * FROM " + Constants.TABLE_LASTDATECOUNT;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor c1 = db.rawQuery(selectLastQuery, null);
        c1.moveToLast();
        if(c1.isLast()){
            expense.set_date(c1.getString(1));
            expense.set_count(c1.getString(2));
        }
        c1.close();
        return expense;
    }
*/

}

