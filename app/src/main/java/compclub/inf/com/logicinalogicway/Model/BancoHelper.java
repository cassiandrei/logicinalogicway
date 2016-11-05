package compclub.inf.com.logicinalogicway.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rafael on 27/09/16.
 */

public class BancoHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "questions.db";
    private static final int DATABASE_VERSION = 1;

    public BancoHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CONTEXTO_CREATE = "CREATE TABLE contextos ( "
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " titulo TEXT NOT NULL,"
                + " definicao TEXT NOT NULL,"
                + " tipo TEXT NOT NULL,"
                + " variaveis TEXT NOT NULL"
                + ");";
        String QUESTOES_CREATE = " CREATE TABLE questoes ( "
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " enunciado TEXT NOT NULL, "
                + " op_a TEXT NOT NULL,"
                + " op_b TEXT NOT NULL,"
                + " op_c TEXT NOT NULL,"
                + " op_d TEXT NOT NULL,"
                + " op_e TEXT NOT NULL,"
                + " resposta INTEGER NOT NULL,"
                + " respondida INTEGER NOT NULL,"
                + " acertada INTEGER NOT NULL,"
                + " context_id INTEGER NOT NULL,"
                + " FOREIGN KEY (context_id) REFERENCES contextos(_id)"
                + ");";
        String VERSION_CREATE = " CREATE TABLE version (version TEXT NOT NULL);";

        database.execSQL(CONTEXTO_CREATE);
        database.execSQL(QUESTOES_CREATE);
        database.execSQL(VERSION_CREATE);

        database.execSQL("insert into version (version) values ('1.0');");

        database.execSQL("insert into contextos (titulo, definicao, tipo, variaveis) values ('Vagas de Estacionamento',"+
                "'Em um prédio de uma companhia existem seis vagas " +
                "de estacionamento, separadas das demais vagas, para " +
                "os diretores da empresa. Elas estão dispostas uma " +
                "ao lado da outra e são numeradas da esquerda para " +
                "a direita de um a seis. Estas vagas são ocupadas por " +
                "exatamente seis carros: C, D, F, H, O e V. As seguintes " +
                "regras também são aplicadas:', 'Posicionamento', 'CDFHOV');");

        database.execSQL("insert into questoes (enunciado, op_a, op_b, op_c, op_d, op_e, resposta, respondida, "+
                "acertada, context_id) values ('Qual  das  seguintes  opções  é  uma  lista " +
                "completa e correta de carros ocupando as vagas da esquerda para a direita?','V, O, C, F, D, H'," +
                "'C, D, H, O, V, F'," +
                "'C, V, O, F, H, D'," +
                "'D, O, H, F, V, C'," +
                "'C, F, V, O, H, D'," +
                "'3','0','0','1');");

        database.execSQL("insert into questoes (enunciado, op_a, op_b, op_c, op_d, op_e, resposta, respondida, "+
                "acertada, context_id) values ('Qual  das  seguintes  armações  pode  ser " +
                "verdadeira?','D está na terceira vaga a partir da esquerda'," +
                "'C está imediatamente ao lado de O'," +
                "'O está na terceira vaga a partir da esquerda'," +
                "'V está na quarta vaga a partir da esquerda', " +
                "'D está imediatamente ao lado de H'," +
                "'2','0','0','1');");

        database.execSQL("insert into questoes (enunciado, op_a, op_b, op_c, op_d, op_e, resposta, respondida, "+
                "acertada, context_id) values ('Qual das seguintes opções é uma vaga que" +
                " H pode ocupar?'," +
                "'1'," +
                "'2'," +
                "'3'," +
                "'4'," +
                "'5'," +
                "'2','0','0','1');");

        database.execSQL("insert into questoes (enunciado, op_a, op_b, op_c, op_d, op_e, resposta, respondida, "+
                "acertada, context_id) values ('Qual das seguintes opções deve obrigatoriamente " +
                "ser falsa?'," +
                "'C está adjacente ao D'," +
                "'V está adjacente ao F'," +
                "'D está adjacente ao O'," +
                "'H está adjacente ao V'," +
                "'O está adjacente ao H'," +
                "'3','0','0','1');");

        database.execSQL("insert into questoes (enunciado, op_a, op_b, op_c, op_d, op_e, resposta, respondida, "+
                "acertada, context_id) values ('Qual dos seguintes pares conteém carros " +
                "que podem ocupar a terceira ou a quarta vaga a partir " +
                "da esquerda?'," +
                "'H e O'" +
                ",'D e F'" +
                ",'F e V'," +
                "'H e D'," +
                "'O e D'," +
                "'0','0','0','1');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(BancoHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        recreate(db);
    }

    public void recreate(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS contextos");
        db.execSQL("DROP TABLE IF EXISTS questoes");
        onCreate(db);
    }
}
