package compclub.inf.com.logicinalogicway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import compclub.inf.com.logicinalogicway.Model.Contexto;
import compclub.inf.com.logicinalogicway.Model.ContextoDAO;

/*
 * Create by Cassiano
 */

public class TitulosActivity extends AppCompatActivity {

    private ListView listaTitulos;

    private String[] descricao = {"descricao1","descricao2","descricao3",
            "descricao4","descricao5","descricao6","descricao7","descricao8",
            "descricao9","descricao10",};

    private List<String> questoes;
    private List<Long> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questoes);

        ContextoDAO cDAO = new ContextoDAO(this);
        cDAO.open();
        List<Contexto> contextos = cDAO.getAllContextos();
        cDAO.close();

        questoes = new ArrayList<>();
        ids = new ArrayList<>();
        for (Contexto c : contextos) {
            questoes.add(c.getTitulo());
            ids.add(c.getId());
        }

        listaTitulos = (ListView) findViewById(R.id.lv_questoes);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                questoes
        );
        listaTitulos.setAdapter(adaptador);
        listaTitulos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putLong("_id", ids.get(position));
                Intent intent = new Intent(TitulosActivity.this,ContextoActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                int codigoPosicao = position;
                Toast.makeText(getApplicationContext(), descricao[codigoPosicao] , Toast.LENGTH_SHORT).show();
            }
        });
    }
}