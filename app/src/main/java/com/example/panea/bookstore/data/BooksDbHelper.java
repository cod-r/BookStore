package com.example.panea.bookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.panea.bookstore.data.BookContract.BookEntry;


public class BooksDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "books_inventory";

    public BooksDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + " ("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.BOOK_PRODUCT_NAME + " TEXT NOT NULL, "
                + BookEntry.BOOK_PRICE + " INTEGER NOT NULL, "
                + BookEntry.BOOK_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + BookEntry.BOOK_SUPPLIER_NAME + " TEXT, "
                + BookEntry.BOOK_SUPPLIER_PHONE + " TEXT);";
        sqLiteDatabase.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
