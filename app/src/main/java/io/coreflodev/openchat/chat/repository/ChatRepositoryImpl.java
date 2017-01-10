package io.coreflodev.openchat.chat.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.coreflodev.openchat.api.ChatMessage;

public class ChatRepositoryImpl extends SQLiteOpenHelper implements ChatRepository {

    private static final String DATABASE_NAME = "chat";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MESSAGES = "messages";
    private static final String KEY_MESSAGES_ID = "id";
    private static final String KEY_MESSAGES_PSEUDO = "pseudo";
    private static final String KEY_MESSAGES_MESSAGE = "message";
    private static final String KEY_MESSAGES_DATE = "date";

    public ChatRepositoryImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public List<ChatMessage> read() {
        List<ChatMessage> messages = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGES, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    messages.add(ChatMessage.create(
                            cursor.getString(cursor.getColumnIndex(KEY_MESSAGES_PSEUDO)),
                            cursor.getString(cursor.getColumnIndex(KEY_MESSAGES_MESSAGE)),
                            new Date(cursor.getLong(cursor.getColumnIndex(KEY_MESSAGES_DATE)))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("Error on db" + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return messages;
    }

    @Override
    public void save(List<ChatMessage> chatMessages) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            db.delete(TABLE_MESSAGES, null, null);

            for (ChatMessage chatMessage : chatMessages) {
                ContentValues values = new ContentValues();
                values.put(KEY_MESSAGES_PSEUDO, chatMessage.pseudo());
                values.put(KEY_MESSAGES_MESSAGE, chatMessage.message());
                values.put(KEY_MESSAGES_DATE, chatMessage.date().getTime());

                db.insertOrThrow(TABLE_MESSAGES, null, values);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println("Error on db" + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMessageTable = "CREATE TABLE " + TABLE_MESSAGES +
                "(" +
                KEY_MESSAGES_ID + " INTEGER PRIMARY KEY," +
                KEY_MESSAGES_PSEUDO + " TEXT," +
                KEY_MESSAGES_MESSAGE + " TEXT, " +
                KEY_MESSAGES_DATE + " INTEGER" +
                ")";
        db.execSQL(createMessageTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
            onCreate(db);
        }
    }
}
