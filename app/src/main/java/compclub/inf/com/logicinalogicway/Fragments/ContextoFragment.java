package compclub.inf.com.logicinalogicway.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import compclub.inf.com.logicinalogicway.Classes.Contexto;
import compclub.inf.com.logicinalogicway.Classes.Questao;
import compclub.inf.com.logicinalogicway.R;

/**
 * Created by rafael on 13/10/16.
 */

public class ContextoFragment extends Fragment implements ActionMode.Callback {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String TITULO = "titulo";
    private static final String DEFINICAO = "definicao";
    private static final String TIPO = "tipo";
    private static final String QUESTOES = "questoes";

    private TextView definicao;
    private Contexto contexto;
    private ListView listaMarcacoes;

    private ContextoFragmentListener listener;

    public ContextoFragment() {
    }

    public void setContexto(Contexto contexto){
        this.contexto = contexto;
    }

    public void setListener(ContextoFragmentListener listener){
        this.listener = listener;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ContextoFragment newInstance(Contexto contexto, ContextoFragmentListener listener) {
        ContextoFragment fragment = new ContextoFragment();
        fragment.setContexto(contexto);
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_marcacoes, container, false);
        listaMarcacoes = (ListView) rootView.findViewById(R.id.lv_marcacoes);
        Log.println(Log.INFO, "contexto", listaMarcacoes.toString());
        rootView = inflater.inflate(R.layout.fragment_contexto, container, false);
        TextView titulo    = (TextView) rootView.findViewById(R.id.tv_Titulo);
        definicao = (TextView) rootView.findViewById(R.id.tv_contexto);

        ListView listaQuestoes = (ListView) rootView.findViewById(R.id.lv_questoes);
        titulo.setText(contexto.getTitulo());
        definicao.setText(contexto.getDefinicao());

        final TextView selecionado = (TextView) rootView.findViewById(R.id.tv_problema);

        definicao.setTextIsSelectable(true);
        definicao.setCustomSelectionActionModeCallback(this);

        String [] vetor = new String[contexto.getQuestoes().size()];
        for(int i = 0; i < contexto.getQuestoes().size(); i++){
            vetor[i]="Questão " + String.valueOf(i+1);
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                vetor
        );
        listaQuestoes.setAdapter(adaptador);

        listaQuestoes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onSwitchToNextFragment(contexto.getQuestoes().get(position));

                if(position == 0){
                    selecionado.setText("Questao selecionado");
                }
            }
        });

        return rootView;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        Toast.makeText(this.getContext(),"onCreateActionMode Called ",Toast.LENGTH_SHORT).show();
        menu.clear();
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_marcacao, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        Toast.makeText(this.getContext(),"onPrepareActionMode Called ",Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        Log.println(Log.INFO, "CLicado", Integer.toString(menuItem.getItemId()) + " " + Integer.toString(R.id.marcar));
        switch (menuItem.getItemId()){
            case R.id.marcar:
                if (definicao.isFocused()){
                    contexto.criaNovaMarcacao(
                            definicao.getSelectionStart(),
                            definicao.getSelectionEnd()
                    );
                }
                actionMode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }

    public interface ContextoFragmentListener{
        public void onSwitchToNextFragment(Questao questao);
    }

}
