package rachma.tn.team.binary.rachma.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by marwen on 28/08/17.
 */

public class LocalStorage extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "Rachma";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_PLAYERS_NAMES = "playernames";

    // Players names Table Columns
    private static final String ID = "id";
    private static final String PLAYER_NAME = "playerName";


    private static LocalStorage sInstance;


    private LocalStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Singleton
    public static synchronized LocalStorage getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new LocalStorage(context.getApplicationContext());
        }
        return sInstance;
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }


    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_PLAYERS_NAMES_TABLE = "CREATE TABLE " + TABLE_PLAYERS_NAMES +
                "(" +
                ID + " INTEGER PRIMARY KEY," + // Define a primary key
                PLAYER_NAME + " VARCHAR(50)" +
                ")";

        sqLiteDatabase.execSQL(CREATE_PLAYERS_NAMES_TABLE);

    }


    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS_NAMES);
            onCreate(db);
        }
    }


    // save player name
    public Boolean savePlayerName(String playerName) {

        Boolean success;

        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();


        if (!playerNameAlreadyExists(playerName)) {
            // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
            // consistency of the database.
            db.beginTransaction();
            try {

                ContentValues values = new ContentValues();
                values.put(PLAYER_NAME, playerName);

                db.insertOrThrow(TABLE_PLAYERS_NAMES, null, values);
                db.setTransactionSuccessful();
                success = true;
            } catch (Exception e) {
                Log.d(TAG, "Error while trying to add post to database");
                success = false;
            } finally {
                db.endTransaction();
            }
        } else
            // player name already exists
            success = false;

        return success;
    }


    // find saved player names
    public String[] findPlayerName(String playerName) {

        Integer i = 0;

        String FIND_PLAYER_NAME_QUERY = new String("SELECT * from " + TABLE_PLAYERS_NAMES + " WHERE " + PLAYER_NAME + " Like \"" + playerName + "%\" ");


        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(FIND_PLAYER_NAME_QUERY, null);

        String[] playerNamesFound = new String[cursor.getCount()];

        try {
            if (cursor.moveToFirst()) {
                do {

                    playerNamesFound[i] = cursor.getString(cursor.getColumnIndex(PLAYER_NAME));
                    i++;

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return playerNamesFound;
    }


    public Boolean playerNameAlreadyExists(String playerName) {

        if (findPlayerName(playerName).length == 0)
            return false;
        else if (findPlayerName(playerName)[0].equals(playerName))
            return true;
        else
            return false;

    }
}
