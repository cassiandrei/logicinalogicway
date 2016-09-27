package compclub.inf.com.logicinalogicway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

/*
 * Create by Cassiano
 */

public class QuestoesActivity extends AppCompatActivity {

    private ListView listaQuestoes;
    private String[] questoes = {"questao1","questao2","questao3","questao4","questao5",
            "questao6","questao7","questao8","questao9","questao10",};

    private String[] descricao = {"descricao1","descricao2","descricao3",
            "descricao4","descricao5","descricao6","descricao7","descricao8",
            "descricao9","descricao10",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questoes);

        listaQuestoes = (ListView) findViewById(R.id.listaquestoesid);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                questoes
        );
        listaQuestoes.setAdapter(adaptador);
        listaQuestoes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(QuestoesActivity.this,ContextoActivity.class);
                startActivity(intent);
                int codigoPosicao = position;
                Toast.makeText(getApplicationContext(), descricao[codigoPosicao] , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
