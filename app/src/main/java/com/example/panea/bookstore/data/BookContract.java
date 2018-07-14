package com.example.panea.bookstore.data;

import android.provider.BaseColumns;


public final class BookContract {
    // Prevent instantiating the contract class
    BookContract(){};

    public static final class BookEntry implements BaseColumns{
        public static final String TABLE_NAME = "books";

        public static final String _ID = BaseColumns._ID;

        public static final String BOOK_PRODUCT_NAME = "book_name";
        public static final String BOOK_PRICE = "price";
        public static final String BOOK_QUANTITY = "quantity";
        public static final String BOOK_SUPPLIER_NAME = "supplier_name";
        public static final String BOOK_SUPPLIER_PHONE = "suplier_phone_no";

    }
}
