package compclub.inf.com.logicinalogicway.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import compclub.inf.com.logicinalogicway.Activities.IntroActivity;
import compclub.inf.com.logicinalogicway.Activities.RegraDialogActivity;
import compclub.inf.com.logicinalogicway.Classes.Contexto;
import compclub.inf.com.logicinalogicway.Classes.Regra;
import compclub.inf.com.logicinalogicway.Classes.RegraOrdenacao;
import compclub.inf.com.logicinalogicway.R;


public class RegrasFragment extends Fragment {

    private Contexto contexto;
    private OnFragmentInteractionListener mListener;

    public RegrasFragment() {
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
    }

    public void updateRegras(){
        ListView listView = (ListView) this.getView().findViewById(R.id.lv_regras);
        ((RegraAdapter)listView.getAdapter()).notifyDataSetChanged();
        listView.setAdapter(listView.getAdapter());
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
                intent.putExtra("variaveis", contexto.getVariaveis());
                startActivityForResult(intent, 0);
            }
        });
        ListView lv_regras = (ListView) view.findViewById(R.id.lv_regras);
        registerForContextMenu(lv_regras);
        RegraAdapter adaptador = new RegraAdapter(getContext(), R.layout.row_regra, (ArrayList<Regra>) contexto.getRegras());
        lv_regras.setAdapter(adaptador);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if (v.getId() == R.id.lv_regras){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Escolha uma ação:");
            menu.add(menu.NONE, 0, 0, "Editar");
            menu.add(menu.NONE, 1, 1, "Excluir");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == 0) {
            Bundle b = new Bundle();
            List<Pair<String,Boolean>> campos = contexto.getRegras().get(info.position).getCampos();
            String[] fields = new String[campos.size()];
            boolean[] checks = new boolean[campos.size()];
            for (int i=0; i<campos.size(); i++){
                fields[i] = campos.get(i).first;
                checks[i] = campos.get(i).second;
            }
            b.putStringArray("fields",fields);
            b.putBooleanArray("checks", checks);
            b.putInt("position", info.position);
            Intent intent = new Intent(getActivity(), RegraDialogActivity.class);
            intent.putExtra("bundle",b);
            intent.putExtra("variaveis", contexto.getVariaveis());
            startActivityForResult(intent, 0);
        }
        else if (item.getItemId() == 1){
            contexto.excluiRegra(info.position);
            Toast.makeText(this.getContext(), "Rule Deleted", Toast.LENGTH_LONG).show();
            updateRegras();
        }
        return true;
    }

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
        void onFragmentInteraction(Uri uri);
    }

    private class RegraAdapter extends ArrayAdapter<Regra>{
        private ArrayList<Regra> items;

        public RegraAdapter(Context context, int textViewResourceId, ArrayList<Regra> items){
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if (v == null){
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_regra, null);
            }
            LinearLayout ll = (LinearLayout) v.findViewById(R.id.ll_rowRegra);
            Regra regra = items.get(position);
            Log.println(Log.INFO, "LOGIC", "Regra: " + regra.toLabel());
            for (Pair<String, Boolean> r : regra.getCampos()) {
                TextView tv = new TextView(getContext());
                SpannableString content = new SpannableString(r.first);
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
                tv.setTypeface(Typeface.MONOSPACE);
                tv.setTextSize(24);
                if (!r.second) {
                    tv.setTextColor(Color.YELLOW);
                    content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                }
                tv.setText(content);
                ll.addView(tv);
                // Adiciona espaço em branco entre letras
                if (regra.getCampos().indexOf(r) < regra.getCampos().size() - 1) {
                    TextView btv = new TextView(getContext());
                    btv.setText(" ");
                    btv.setTypeface(Typeface.MONOSPACE);
                    btv.setTextSize(24);
                    ll.addView(btv);
                }
            }
            return v;
        }
    }
}
