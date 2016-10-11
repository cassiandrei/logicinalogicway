package compclub.inf.com.logicinalogicway;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import compclub.inf.com.logicinalogicway.Model.Contexto;
import compclub.inf.com.logicinalogicway.Model.ContextoDAO;
import compclub.inf.com.logicinalogicway.Model.Regra;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegrasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegrasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/*
 * Create by Cassiano on 27/09
 */
public class RegrasFragment extends Fragment {

    private static final String CONTEXTO_ID = "contexto_id";

    private long contexto_id;

    private List<String> regras;

    private OnFragmentInteractionListener mListener;

    public RegrasFragment() {
        // Required empty public constructor
    }

    /*
     * Use esse método de fábrica para criar uma nova instância desse
     * fragment usando os parametros providos.
     *
     * @param _contexto_id ID do contexto no banco de dados.
     * @return Uma nova instância do fragment RegrasFragment.
     */
    public static RegrasFragment newInstance(long _contexto_id) {
        RegrasFragment fragment = new RegrasFragment();
        Bundle args = new Bundle();
        args.putLong(CONTEXTO_ID, _contexto_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contexto_id = getArguments().getLong(CONTEXTO_ID);
            ContextoDAO dao = new ContextoDAO(this.getContext());
            dao.open();
            Contexto c = dao.getContextoByID(contexto_id);
            dao.close();
            List<Regra> lr = c.getRegras();
            for (Regra r: lr)
                regras.add(r.toLabel());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regras, container, false);
        ListView listView = (ListView) view.findViewById(R.id.lv_regras);
        // TODO: lida com a lista de string regras, que contem as regras. poe na na list view
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
