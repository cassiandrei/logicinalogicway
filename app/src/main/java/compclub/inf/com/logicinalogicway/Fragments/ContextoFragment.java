package compclub.inf.com.logicinalogicway.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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

//    private ContextoFragmentListener listener;

    public ContextoFragment() {
    }

    public void setContexto(Contexto contexto){
        this.contexto = contexto;
    }

//    public void setListener(ContextoFragmentListener listener){
//        this.listener = listener;
//    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ContextoFragment newInstance(Contexto contexto) {
        ContextoFragment fragment = new ContextoFragment();
        fragment.setContexto(contexto);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_marcacoes, container, false);
        listaMarcacoes = (ListView) rootView.findViewById(R.id.lv_marcacoes);
        rootView = inflater.inflate(R.layout.fragment_contexto, container, false);
        TextView titulo    = (TextView) rootView.findViewById(R.id.tv_Titulo);
        definicao = (TextView) rootView.findViewById(R.id.tv_contexto);
        final TextView selecionado = (TextView) rootView.findViewById(R.id.tv_problema);
        final ListView listaQuestoes = (ListView) rootView.findViewById(R.id.lv_questoes);
        final Button botaoVoltar = (Button) rootView.findViewById(R.id.bt_voltar);
        final RadioGroup alternativas = (RadioGroup) rootView.findViewById((R.id.rg_alternativas));

        selecionado.setVisibility(View.GONE);
        botaoVoltar.setVisibility(View.GONE);
        alternativas.setVisibility(View.GONE);

        titulo.setText(contexto.getTitulo());
        definicao.setText(contexto.getDefinicao());

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
                List<Questao> questoes = contexto.getQuestoes();
                selecionado.setText(questoes.get(position).getEnunciado());
                for (int i = 0; i < alternativas.getChildCount(); i++) {
                    ((RadioButton) alternativas.getChildAt(i)).setText(questoes.get(position).getAlternativas()[i]);
                }
                selecionado.setVisibility(View.VISIBLE);
                listaQuestoes.setVisibility(View.GONE);
                botaoVoltar.setVisibility(View.VISIBLE);
                alternativas.setVisibility(View.VISIBLE);
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listaQuestoes.setVisibility(View.VISIBLE);
                botaoVoltar.setVisibility(View.GONE);
                selecionado.setVisibility(View.GONE);
                alternativas.setVisibility(View.GONE);
            }
        });
        return rootView;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        menu.clear();
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_marcacao, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.marcar:
                if (definicao.isFocused()){
                    contexto.criaNovaMarcacao(
                            definicao.getSelectionStart(),
                            definicao.getSelectionEnd()
                    );
                    for (Fragment f : this.getFragmentManager().getFragments())
                        if (f instanceof MarcacoesFragment) {
                            ((MarcacoesFragment) f).populate_list();
                            break;
                        }
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
//
//    public interface ContextoFragmentListener{
//        public void onSwitchToNextFragment(Questao questao);
//    }

}
