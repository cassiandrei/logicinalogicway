package compclub.inf.com.logicinalogicway.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import compclub.inf.com.logicinalogicway.Classes.Contexto;
import compclub.inf.com.logicinalogicway.Classes.Questao;

/**
 * Created by rafael on 27/09/16.
 */

public class ContextoDAO {

    private SQLiteDatabase database;
    private BancoHelper dbHelper;
    private String[] allColumns = {
            "_id",
            "titulo",
            "definicao",
            "tipo"
    };

    private static final String TABLE = "contextos";
    private Context context;

    public ContextoDAO(Context context){
        dbHelper = new BancoHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Contexto createContexto(String titulo, String descricao, String tipo){
        ContentValues values = new ContentValues();
        values.put(allColumns[1], titulo);
        values.put(allColumns[2], descricao);
        values.put(allColumns[3], tipo);

        long insertId = database.insert(TABLE, null, values);
        return getContextoByID(insertId);
    }

    private Contexto cursorToContexto(Cursor cursor) {
        Contexto c = new Contexto(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        c.setId(cursor.getLong(0));
        QuestaoDAO qdao = new QuestaoDAO(this.context);
        qdao.open();
        for (Questao q : qdao.getAllQuestoesFromContexto(c.getId())){
            c.addQuestao(q);
        }
        return c;
    }

    public Contexto getContextoByID(long _id){
        Cursor cursor = database.query(TABLE,
            allColumns, allColumns[0] + " = " + _id,
            null, null, null, null);

        cursor.moveToFirst();
        Contexto newContexto = cursorToContexto(cursor);
        cursor.close();
        return newContexto;
    }

    public void deleteContexto(Contexto contexto) {
        long id = contexto.getId();
        System.out.println("Context deleted with id: " + id);
        database.delete(TABLE, allColumns[0] + " = " + id, null);
    }

    public List<Contexto> getAllContextos() {
        List<Contexto> contextos = new ArrayList<Contexto>();

        Cursor cursor = database.query(TABLE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contexto contexto = cursorToContexto(cursor);
            contextos.add(contexto);
            cursor.moveToNext();
        }
        cursor.close();
        return contextos;
    }

}
