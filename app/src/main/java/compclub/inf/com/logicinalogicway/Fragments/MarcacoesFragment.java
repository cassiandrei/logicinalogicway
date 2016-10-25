package compclub.inf.com.logicinalogicway.Fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import compclub.inf.com.logicinalogicway.Classes.Contexto;
import compclub.inf.com.logicinalogicway.R;

/**
 * Created by rafael on 13/10/16.
 */

public class MarcacoesFragment extends Fragment {

    private Contexto contexto;
    private ListView lv_marcacoes;
    private List<String> list;

    private OnFragmentInteractionListener mListener;

    public MarcacoesFragment() {
        // Required empty public constructor
    }

    public void setContexto(Contexto contexto){
        this.contexto = contexto;
    }

    public static MarcacoesFragment newInstance(Contexto contexto) {
        MarcacoesFragment fragment = new MarcacoesFragment();
        fragment.setContexto(contexto);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marcacoes, container, false);
        lv_marcacoes = (ListView) view.findViewById(R.id.lv_marcacoes);
        list = new ArrayList<>();
        populate_list();
        return view;
    }

    public void populate_list(){
        list.clear();
        for (Pair<Integer,Integer> m: contexto.getMarcacoes())
            list.add(contexto.getDefinicao().subSequence(m.first,m.second).toString());
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                list
        );
        lv_marcacoes.setAdapter(adaptador);
        registerForContextMenu(lv_marcacoes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if (v.getId() == R.id.lv_marcacoes){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Excluir a marcação?");
            menu.add(menu.NONE, 0, 0, "Sim");
            menu.add(menu.NONE, 1, 1, "Não");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == 0){
            contexto.excluiMarcacao(item.getItemId());
            populate_list();
            Toast.makeText(this.getContext(), "Item Deleted", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
