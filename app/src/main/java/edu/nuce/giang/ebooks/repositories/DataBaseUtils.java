package edu.nuce.giang.ebooks.repositories;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.nuce.giang.ebooks.models.LibraryModel;

public class DataBaseUtils extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbLibrary";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "library";

    //define table filed name
    private static final String KEY_ID = "id";
    private static final String KEY_BOOK_ID = "bookId";
    private static final String KEY_BOOK_PAGE_CURRENT = "pageCurrent";
    private static final String KEY_FINISHED = "finishBook";


    public DataBaseUtils(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_BOOK_ID + " INTEGER, "
                + KEY_BOOK_PAGE_CURRENT + " INTEGER, "
                + KEY_FINISHED + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<LibraryModel> getAllBooks() throws Exception {
        List<LibraryModel> models = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                do {
                    LibraryModel model = new LibraryModel(
                            cursor.getLong(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3)
                    );
                    models.add(model);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return models;
    }

    public List<LibraryModel> getAllBookFinished() throws Exception {
        List<LibraryModel> models = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + KEY_BOOK_PAGE_CURRENT + " > 0 AND " + KEY_BOOK_PAGE_CURRENT + " = " + KEY_FINISHED;
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                do {
                    LibraryModel model = new LibraryModel(
                            cursor.getLong(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3)
                    );
                    models.add(model);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return models;
    }

    public List<LibraryModel> getAllBookReadingNow() throws Exception {
        List<LibraryModel> models = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + KEY_BOOK_PAGE_CURRENT + " > 0 AND " + KEY_BOOK_PAGE_CURRENT + " < " + KEY_FINISHED;
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                do {
                    LibraryModel model = new LibraryModel(
                            cursor.getLong(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3)
                    );
                    models.add(model);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return models;
    }

    public long countBooks() throws Exception {
        long count = 0;
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                count = cursor.getLong(0);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return count;
    }

    public long addBook(LibraryModel model) throws Exception {
        SQLiteDatabase database = null;
        long id;
        try {
            database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_BOOK_ID, model.getBookId());
            values.put(KEY_BOOK_PAGE_CURRENT, model.getPageCurrent());
            values.put(KEY_FINISHED, model.getFinishBook());

            // Inserting Row, return id
            id = database.insert(TABLE_NAME, "", values);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (database != null) {
                database.close(); // Closing database connection
            }
        }
        return id;
    }

    public LibraryModel getBook(Integer bookId) throws Exception {
        SQLiteDatabase database = null;
        LibraryModel model = null;
        try {
            database = this.getWritableDatabase();

            @SuppressLint("Recycle") Cursor cursor = database.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_BOOK_ID + " = ?",
                    new String[]{String.valueOf(bookId)}
            );
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                model = new LibraryModel(
                        cursor.getLong(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3)
                );
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (database != null) {
                database.close(); // Closing database connection
            }
        }
        return model;
    }

    public int updateNote(LibraryModel model) throws Exception {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_BOOK_ID, model.getBookId());
            values.put(KEY_BOOK_PAGE_CURRENT, model.getPageCurrent());
            values.put(KEY_FINISHED, model.getFinishBook());
            return db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(model.getId())});
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void deleteBook(long id) throws Exception {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.delete(TABLE_NAME, KEY_ID + " = ?",
                    new String[]{String.valueOf(id)});
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
}
