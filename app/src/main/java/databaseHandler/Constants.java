package databaseHandler;

/**
 * Created by Sai sreenivas on 10/30/2017.
 */

public class Constants {
    public static final int DATABASE_VERSION = 3;
    static final String DATABASE_NAME = "clothsdb";
    // Contacts table name
    static final String TABLE_CLOTHES = "clothes_table";
    // Shops Table Columns names
    static final String COLUMN_ID = "id";
    static final String COLUMN_CLOTHES_DATE = "clothes_date";
    static final String COLUMN_CLOTHES_COUNT = "clothes_count";
    static final String COLUMN_CLOTHES_SHIRTS = "clothes_shirt_count";
    static final String COLUMN_CLOTHES_PANTS = "clothes_pant_count";
    static final String COLUMN_CLOTHES_INNERS = "clothes_inner_count";

    static final String COLUMN_AMOUNT = "expense_amount";
    static final String COLUMN_EXPENSE_DATE = "expense_date";
    static final String CREATE_CLOTHES_TABLE = "CREATE TABLE " + TABLE_CLOTHES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_CLOTHES_DATE + " TEXT,"
            + COLUMN_CLOTHES_SHIRTS + " INTEGER, " + COLUMN_CLOTHES_PANTS + " INTEGER, " + COLUMN_CLOTHES_INNERS + " INTEGER)" ;
    static final String COLUMN_NUMBER = "message_number";
    static final String COLUMN_MESSAGE = "message_data";
    static final String COLUMN_DATE= "message_date";
    static final String COLUMN_MY_NAME = "message_my_name";
    static final String COLUMN_MONEY = "message_my_money";
    static final String TABLE_MESSAGE = "messages_required";
    static final String CREATE_MESSAGE_TABLE = "CREATE TABLE "+ TABLE_MESSAGE + "(" + COLUMN_ID + "INTEGER PRIMARY KEY,"
            + COLUMN_NUMBER + " TEXT," + COLUMN_MESSAGE + " TEXT," + COLUMN_DATE + " DATE," + COLUMN_MY_NAME + " TEXT, " +
            COLUMN_MONEY + " TEXT)";
    static final String COLUMN_COUNT = "message_total_count";
    static final String TABLE_LASTDATECOUNT = "message_last_date_count";
    static final String CREATE_LASTDATECOUNT_TABLE = "CREATE TABLE " + TABLE_LASTDATECOUNT + " (" + COLUMN_ID +
            " INTEGER PRIMARY KEY, " + COLUMN_DATE + " TEXT, " + COLUMN_COUNT + " TEXT" + ")";
}
