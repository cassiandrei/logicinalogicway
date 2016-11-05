package compclub.inf.com.logicinalogicway.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rafael on 05/11/16.
 */

public class VersionDAO {
    private SQLiteDatabase database;
    private BancoHelper dbHelper;
    private static final String TABLE = "version";

    public VersionDAO(Context context){
        dbHelper = new BancoHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public String getVersion(){
        String[] columns = {"version"};
        Cursor cursor = database.query(TABLE,
                columns, null,
                null, null, null, null);
        cursor.moveToFirst();
        String version = cursor.getString(0);
        cursor.close();
        return version;
    }

    public void setVersion(String version){
        String oldVersion = getVersion();
        ContentValues values = new ContentValues();
        values.put("version", version);
        database.update(TABLE, values, "version="+oldVersion,null);
    }
}
