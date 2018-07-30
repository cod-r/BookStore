package com.example.panea.bookstore.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class BookContract {
    // Prevent instantiating the contract class
    BookContract(){};

    public static final String CONTENT_AUTHORITY = "com.example.panea.bookstore";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "books";


    public static final class BookEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;
        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;



        public static final String TABLE_NAME = "books";

        public static final String _ID = BaseColumns._ID;

        public static final String BOOK_PRODUCT_NAME = "book_name";
        public static final String BOOK_PRICE = "price";
        public static final String BOOK_QUANTITY = "quantity";
        public static final String BOOK_SUPPLIER_NAME = "supplier_name";
        public static final String BOOK_SUPPLIER_PHONE = "suplier_phone_no";


    }
}
