/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.panea.bookstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panea.bookstore.data.BookContract.BookEntry;
import com.example.panea.bookstore.data.BookProvider;
import com.example.panea.bookstore.data.BooksDbHelper;

/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {
    public static final String LOG_TAG = BookCursorAdapter.class.getSimpleName();

    private LayoutInflater mInflater;
    private ViewGroup mViewGroup;

    static private class Holder {
        TextView mQuantityTextView;
        Button mSaleButton;

        public Holder(View view) {
            mQuantityTextView = (TextView) view.findViewById(R.id.quantity);
            mSaleButton = (Button) view.findViewById(R.id.sale_button);
        }
    }

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.v(LOG_TAG, "merge" + cursor.getPosition());
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        Holder holder = new Holder(view);
        view.setTag(holder);
        return view;
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        final Holder holder = (Holder) view.getTag();
        final int position = cursor.getPosition();



        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);

        // Find the columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.BOOK_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.BOOK_QUANTITY);

        // Read the book attributes from the Cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        String bookPrice = cursor.getString(priceColumnIndex);
        String bookQuantity = cursor.getString(quantityColumnIndex);



        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(bookName);
        priceTextView.setText("Price: " + bookPrice);
        holder.mQuantityTextView.setText("Quantity: " + bookQuantity);

        // Find the sale button
        holder.mSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPosition(position);
                Log.v(LOG_TAG, "item pos " + cursor.getPosition());

                // Get the current quantity and subtract one
                String quantityString = holder.mQuantityTextView
                        .getText().toString().trim();
                // Subtract the "Quantity: " from the string
                quantityString = quantityString.substring(10);
                Log.v(LOG_TAG, "string value " + quantityString);
                int currentQuantity = Integer.parseInt(quantityString);
                if (currentQuantity > 0) {
                    holder.mQuantityTextView.setText(String.valueOf("Quantity: "
                            + (--currentQuantity)));
                    BooksDbHelper dbHelper = new BooksDbHelper(view.getContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.BOOK_QUANTITY, currentQuantity);
                    long id = cursor.getLong(cursor.getColumnIndex(BookEntry._ID));
                    db.update(BookEntry.TABLE_NAME, values, "_id=" + id, null);
                    db.close();
                } else {
                    Toast.makeText(view.getContext(), "The book is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
