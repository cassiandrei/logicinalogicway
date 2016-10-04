package compclub.inf.com.logicinalogicway;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import compclub.inf.com.logicinalogicway.Model.Questao;


public class QuestaoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ENUNCIADO = "enunciado";
    private static final String OP_A = "op_a";
    private static final String OP_B = "op_b";
    private static final String OP_C = "op_c";
    private static final String OP_D = "op_d";
    private static final String RESPOSTA = "resposta";
    private static final String RESPONDIDA = "respondida";
    private static final String ACERTADA = "acertada";

    public QuestaoFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static QuestaoFragment newInstabnce(Questao questao) {
        QuestaoFragment fragment = new QuestaoFragment();
        Bundle args = new Bundle();
        args.putString(ENUNCIADO, questao.getEnunciado());
        args.putString(OP_A, questao.getAlternativas()[0]);
        args.putString(OP_B, questao.getAlternativas()[1]);
        args.putString(OP_C, questao.getAlternativas()[2]);
        args.putString(OP_D, questao.getAlternativas()[3]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questao, container, false);
    }
}
