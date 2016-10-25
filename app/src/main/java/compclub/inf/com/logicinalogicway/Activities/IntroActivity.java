package compclub.inf.com.logicinalogicway.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import compclub.inf.com.logicinalogicway.Model.BancoHelper;
import compclub.inf.com.logicinalogicway.Model.ContextoDAO;
import compclub.inf.com.logicinalogicway.Model.QuestaoDAO;
import compclub.inf.com.logicinalogicway.R;

/*
 * Create by Cassiano
 */
public class IntroActivity extends AppCompatActivity {

    private ImageView botaoIntro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        botaoIntro = (ImageView) findViewById(R.id.logoclubeid);

        botaoIntro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(IntroActivity.this,TitulosActivity.class);
                startActivity(intent);
            }
        });

        new BancoPopulator().execute();
    }

    public class BancoPopulator extends AsyncTask {

        private boolean needUpgradeBanco(){
            return false;
        }

        private JSONArray baixaJSON(){
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://www");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                    buffer.append(line + "\n");

                connection.disconnect();
                reader.close();

                return new JSONArray(buffer.toString());

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void upgradeBanco(JSONArray json){
            BancoHelper dbHelper = new BancoHelper(getApplicationContext());
            dbHelper.recreate(dbHelper.getWritableDatabase());
            ContextoDAO cdao = new ContextoDAO(getApplicationContext());
            QuestaoDAO qdao = new QuestaoDAO(getApplicationContext());
            cdao.open();
            qdao.open();
            for(int i=0; i<json.length(); i++){
                try {
                    JSONObject problema = json.getJSONObject(i);
                    String titulo = problema.getString("titulo");
                    String definicao = problema.getString("definicao");
                    String tipo = problema.getString("tipo");
                    cdao.createContexto(titulo, definicao, tipo);
                    JSONArray questoes = problema.getJSONArray("questoes");
                    for (int j=0; j<questoes.length(); j++){
                        JSONObject questao = questoes.getJSONObject(j);
                        String enunciado = questao.getString("enunciado");
                        JSONArray jopcoes = questao.getJSONArray("opcoes");
                        String[] opcoes = new String[5];
                        for (int o=0; o<5; o++)
                            opcoes[o] = jopcoes.getString(o);
                        int resposta = questao.getInt("resposta");
                        qdao.createQuestao(enunciado,opcoes,resposta,i+1);
                    }
                } catch (JSONException e) {
                    cdao.close();
                    qdao.close();
                    e.printStackTrace();
                }
            }
            qdao.close();
            cdao.close();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (needUpgradeBanco()){
                JSONArray json = null;
                while (json.equals(null))
                    json = baixaJSON();
                upgradeBanco(json);
                return 1;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (result != null) {
                Intent intent = new Intent(IntroActivity.this, TitulosActivity.class);
                startActivity(intent);
            }
        }
    }

}
