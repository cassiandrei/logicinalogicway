package compclub.inf.com.logicinalogicway;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import compclub.inf.com.logicinalogicway.Model.Contexto;

/**
 * Created by rafael on 13/10/16.
 */

public class MarcacoesFragment extends Fragment {

    private Contexto contexto;

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
        ListView listView = (ListView) view.findViewById(R.id.lv_marcacoes);
        list = new ArrayList<>();
        for (Pair<Integer,Integer> m: contexto.getMarcacoes())
            list.add(contexto.getDefinicao().subSequence(m.first,m.second).toString());
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                list
        );
        listView.setAdapter(adaptador);
        Log.println(Log.INFO, "marcacoes", listView.toString());
        return view;
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
    public void onResume(){
        View view = this.getView();
        ListView listView = (ListView) view.findViewById(R.id.lv_marcacoes);
        list.clear();
        for (Pair<Integer,Integer> m: contexto.getMarcacoes())
            list.add(contexto.getDefinicao().subSequence(m.first,m.second).toString());
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                list
        );
        listView.setAdapter(adaptador);
        super.onResume();
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
