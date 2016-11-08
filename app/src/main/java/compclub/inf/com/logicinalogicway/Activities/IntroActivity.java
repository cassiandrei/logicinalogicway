package compclub.inf.com.logicinalogicway.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import compclub.inf.com.logicinalogicway.Model.VersionDAO;
import compclub.inf.com.logicinalogicway.R;

/*
 * Create by Cassiano
 */
public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        VersionDAO vdao = new VersionDAO(getApplicationContext());
        vdao.open();
        String version = vdao.getVersion();
        vdao.close();

        if (version.equals("0.0")) {
            while (!isNetworkAvailable()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Acesso à rede indisponível");
                builder.setMessage("Aparentemente seu dispositivo está sem conexão com a internet. " +
                        "Certifique-se que uma conexão está disponível antes de prosseguir.");
                builder.setNeutralButton("Prosseguir", null);
                builder.show();
            }
        }
        else if (!isNetworkAvailable()){
            Toast.makeText(this, "Sem conexão com a internet. Impossível detectar atualizações.", Toast.LENGTH_LONG);
            navigateToTitulos();
        }
        new BancoPopulator().execute();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class BancoPopulator extends AsyncTask {
        String version;
        String newVersion;

        private boolean needUpgradeBanco(){
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                VersionDAO vdao = new VersionDAO(getApplicationContext());
                vdao.open();
                version = vdao.getVersion();
                vdao.close();

                URL url = new URL("http://logicinalogicway.heroku.com/version?version="+version);
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

                JSONObject jo = new JSONObject(buffer.toString());
                if (jo.getBoolean("atualizacoes")) {
                    newVersion = jo.getString("newVersion");
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        private JSONArray baixaJSON(){
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://logicinalogicway.heroku.com/update?version="+version);
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
                    String variaves = problema.getString("variaveis");
                    cdao.createContexto(titulo, definicao, tipo, variaves);
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
                    e.printStackTrace();
                }
            }
            qdao.close();
            cdao.close();

            VersionDAO vdao = new VersionDAO(getApplicationContext());
            vdao.open();
            vdao.setVersion(newVersion);
            vdao.close();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (needUpgradeBanco()){
                JSONArray json = null;
                do{
                    json = baixaJSON();
                }while (json.equals(null));
                upgradeBanco(json);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            navigateToTitulos();
        }
    }

    public void navigateToTitulos(){
        Intent intent = new Intent(IntroActivity.this, MenuActivity.class);
        startActivity(intent);
    }

}
