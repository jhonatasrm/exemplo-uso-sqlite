package com.jhonatasrm.exemplo_uso_sqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContentDAO {

    private static ContentDAO instance;

    private SQLiteDatabase db;

    public static ContentDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ContentDAO(context.getApplicationContext());
        }
        return instance;
    }

    public ContentDAO(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<Content> list() {

        String[] columns = {
                ContentContract.Columns._ID,
                ContentContract.Columns.TITLE,
                ContentContract.Columns.DESCRIPTION
        };

        List<Content> contents = new ArrayList<>();

        try (Cursor c = db.query(ContentContract.TABLE_NAME, columns, null, null, null, null, ContentContract.Columns.TITLE)) {
            if (c.moveToFirst()) {
                do {
                    Content p = ContentDAO.fromCursor(c);
                    contents.add(p);
                } while (c.moveToNext());
            }

            return contents;
        }

    }

    private static Content fromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(ContentContract.Columns._ID));
        String nome = c.getString(c.getColumnIndex(ContentContract.Columns.TITLE));
        String valor = c.getString(c.getColumnIndex(ContentContract.Columns.DESCRIPTION));
        return new Content(id, nome, valor);
    }

    public void save(Content content) {
        ContentValues values = new ContentValues();
        values.put(ContentContract.Columns.TITLE, content.getNome());
        values.put(ContentContract.Columns.DESCRIPTION, content.getDescription());
        long id = db.insert(ContentContract.TABLE_NAME, null, values);
        content.setId((int) id);
    }

    public void update(Content content) {
        ContentValues values = new ContentValues();
        values.put(ContentContract.Columns.TITLE, content.getNome());
        values.put(ContentContract.Columns.DESCRIPTION, content.getDescription());
        db.update(ContentContract.TABLE_NAME, values, ContentContract.Columns._ID + " = ?", new String[]{String.valueOf(content.getId())});
    }

    public void delete(Content content) {
        db.delete(ContentContract.TABLE_NAME, ContentContract.Columns._ID + " = ?", new String[]{String.valueOf(content.getId())});
    }
}
