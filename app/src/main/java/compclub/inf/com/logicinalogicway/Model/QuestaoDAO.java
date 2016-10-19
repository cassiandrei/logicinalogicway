package compclub.inf.com.logicinalogicway.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import compclub.inf.com.logicinalogicway.Classes.Questao;

/**
 * Created by rafael on 03/10/16.
 */

public class QuestaoDAO {

    private SQLiteDatabase database;
    private BancoHelper dbHelper;
    private String[] allColumns = {
            "_id",
            "enunciado",
            "op_a",
            "op_b",
            "op_c",
            "op_d",
            "op_e",
            "resposta",
            "respondida",
            "acertada",
            "context_id"
    };

    private static final String TABLE = "questoes";

    public QuestaoDAO(Context context){
        dbHelper = new BancoHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Questao createQuestao(String enunciado, String[] opcoes, int resposta, int contexto_id){
        ContentValues values = new ContentValues();
        values.put(allColumns[1], enunciado);
        values.put(allColumns[2], opcoes[0]);
        values.put(allColumns[3], opcoes[1]);
        values.put(allColumns[4], opcoes[2]);
        values.put(allColumns[5], opcoes[3]);
        values.put(allColumns[6], opcoes[4]);
        values.put(allColumns[7], resposta);
        values.put(allColumns[8], 0);
        values.put(allColumns[9], 0);
        values.put(allColumns[10], contexto_id);

        long insertId = database.insert(TABLE, null, values);
        return getQuestaoByID(insertId);
    }

    private Questao cursorToQuestao(Cursor cursor) {
        String[] opcoes = {
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
        };
        Questao q = new Questao(cursor.getString(1), opcoes, cursor.getInt(6));
        q.setId(cursor.getLong(0));
        q.setRespondida(cursor.getInt(8)!=0);
        q.setAcertada(cursor.getInt(9)!=0);
        return q;
    }

    public Questao getQuestaoByID(long _id){
        Cursor cursor = database.query(TABLE,
            allColumns, allColumns[0] + " = " + _id,
            null, null, null, null);

        cursor.moveToFirst();
        Questao newQuestao = cursorToQuestao(cursor);
        cursor.close();
        return newQuestao;
    }

    public void deleteQuestao(Questao questao) {
        long id = questao.getId();
        System.out.println("Question deleted with id: " + id);
        database.delete(TABLE, allColumns[0] + " = " + id, null);
    }

    public List<Questao> getAllQuestoesFromContexto(long context_id) {
        List<Questao> questoes = new ArrayList<Questao>();

        Cursor cursor = database.query(TABLE,
                allColumns, allColumns[10] + " = " + context_id,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Questao questao = cursorToQuestao(cursor);
            questoes.add(questao);
            cursor.moveToNext();
        }
        cursor.close();
        return questoes;
    }

}
