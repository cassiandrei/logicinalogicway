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
                + " tipo TEXT NOT NULL"
                + ");";
        String QUESTOES_CREATE = " CREATE TABLE questoes ( "
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " enunciado TEXT NOT NULL, "
                + " op_a TEXT NOT NULL,"
                + " op_b TEXT NOT NULL,"
                + " op_c TEXT NOT NULL,"
                + " op_d TEXT NOT NULL,"
                + " resposta INTEGER NOT NULL,"
                + " respondida INTEGER NOT NULL,"
                + " acertada INTEGER NOT NULL,"
                + " context_id INTEGER NOT NULL,"
                + " FOREIGN KEY (context_id) REFERENCES contextos(_id)"
                + ");";

        database.execSQL(CONTEXTO_CREATE);
        database.execSQL(QUESTOES_CREATE);

        database.execSQL("insert into contextos (titulo, definicao, tipo) values ('Vagas de Estacionamento',"+
                "'Em um prédio de uma companhia existem seis vagas\n" +
                "de estacionamento, separadas das demais vagas, para\n" +
                "os diretores da empresa. Elas est˜ao dispostas uma\n" +
                "ao lado da outra e s˜ao numeradas da esquerda para\n" +
                "a direita de um a seis. Estas vagas s˜ao ocupadas por\n" +
                "exatamente seis carros: C, D, F, H, O e V. As seguintes\n" +
                "regras também s˜ao aplicadas:', 'Calculo');");
        database.execSQL("insert into questoes (enunciado, op_a, op_b, op_c, op_d, op_e, resposta, respondida, "+
                "acertada, context_id) values ('enunciado','opcaoa','opcaob','opcaoc','opcaod','2','0','0','1');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(BancoHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS contextos");
        db.execSQL("DROP TABLE IF EXISTS questoes");
        onCreate(db);
    }
}
