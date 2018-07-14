package com.example.panea.bookstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.panea.bookstore.data.BookContract.BookEntry;
import com.example.panea.bookstore.data.BooksDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private BooksDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Instantiate the database
        mDbHelper = new BooksDbHelper(this);
        insertBook();
        displayData();

    }

    private void insertBook() {
        // Get the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values
        ContentValues values = new ContentValues();
        values.put(BookEntry.BOOK_PRODUCT_NAME, "The Book");
        values.put(BookEntry.BOOK_PRICE, 4);
        values.put(BookEntry.BOOK_QUANTITY, 13);
        values.put(BookEntry.BOOK_SUPPLIER_NAME, "The Book Supplier");
        values.put(BookEntry.BOOK_SUPPLIER_PHONE, "+40765432101");

        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);
        Log.v("CatalogActivity", "New row id " + newRowId);
    }

    private Cursor queryData() {
        // Get the database in read mode
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    private void displayData() {
        Cursor cursor = queryData();
        TextView displayView = (TextView) findViewById(R.id.text_view_book);

        try {
            displayView.setText("The books table contains " + cursor.getCount() + " books. \n\n");
            displayView.append(BookEntry._ID + " - "
                    + BookEntry.BOOK_PRODUCT_NAME + " - "
                    + BookEntry.BOOK_PRICE + " - "
                    + BookEntry.BOOK_QUANTITY + " - "
                    + BookEntry.BOOK_SUPPLIER_NAME + " - "
                    + BookEntry.BOOK_SUPPLIER_PHONE + "\n"
            );

            // Show database entries
            while (cursor.moveToNext()) {
                displayView.append("\n"
                        + cursor.getInt(cursor.getColumnIndex(BookEntry._ID)) + " - "
                        + cursor.getString(cursor.getColumnIndex(BookEntry.BOOK_PRODUCT_NAME)) + " - "
                        + cursor.getInt(cursor.getColumnIndex(BookEntry.BOOK_PRICE)) + " - "
                        + cursor.getInt(cursor.getColumnIndex(BookEntry.BOOK_QUANTITY)) + " - "
                        + cursor.getString(cursor.getColumnIndex(BookEntry.BOOK_SUPPLIER_NAME)) + " - "
                        + cursor.getString(cursor.getColumnIndex(BookEntry.BOOK_SUPPLIER_PHONE))
                );
            }

        } finally {
            cursor.close();
        }
    }
}
