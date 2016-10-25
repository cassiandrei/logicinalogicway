package compclub.inf.com.logicinalogicway.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import compclub.inf.com.logicinalogicway.Activities.RegraDialogActivity;
import compclub.inf.com.logicinalogicway.Classes.Contexto;
import compclub.inf.com.logicinalogicway.Classes.Regra;
import compclub.inf.com.logicinalogicway.Classes.RegraOrdenacao;
import compclub.inf.com.logicinalogicway.R;


public class RegrasFragment extends Fragment {

    private List<String> regras;

    private Contexto contexto;

    private OnFragmentInteractionListener mListener;

    public RegrasFragment() {
        // Required empty public constructor
    }

    public static RegrasFragment newInstance(Contexto contexto) {
        RegrasFragment fragment = new RegrasFragment();
        fragment.setContexto(contexto);
        return fragment;
    }

    public void setContexto(Contexto contexto){
        this.contexto = contexto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regras = new ArrayList<>();
    }

    public void updateRegras(){
        regras.clear();
        for (Regra r: contexto.getRegras())
            regras.add(r.toLabel());
        ListView listView = (ListView) this.getView().findViewById(R.id.lv_regras);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                regras
        );
        listView.setAdapter(adaptador);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regras, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.bt_addRegra);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegraDialogActivity.class);
                startActivityForResult(intent, 0);
            }
        });
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
