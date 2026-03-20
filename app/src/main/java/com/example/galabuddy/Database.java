package com.example.galabuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "GalaBuddy.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE accounts(" +
                "account_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT)");

        db.execSQL("CREATE TABLE locations(" +
                "location_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_id INTEGER," +
                "location_title TEXT," +
                "latitude TEXT," +
                "longitude TEXT," +
                "short_description TEXT," +
                "long_description TEXT," +
                "rating INTEGER," +
                "CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account_id(accounts) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS locations");
        onCreate(db);
    }

    public Account login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM accounts WHERE username = ? AND password = ?", new String[]{username, password});

        if (cursor.moveToFirst()) {
            Account account = new Account();
            account.setID(cursor.getInt(cursor.getColumnIndexOrThrow("account_id")));
            account.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            account.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            return account;
        }

        return null;
    }

    public void register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        db.insert("accounts", null, values);
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM accounts", null);

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setID(cursor.getInt(cursor.getColumnIndexOrThrow("account_id")));
                account.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                account.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                accounts.add(account);
            } while (cursor.moveToNext());
        }

        return accounts;
    }

}
