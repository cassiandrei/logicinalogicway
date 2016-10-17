package compclub.inf.com.logicinalogicway.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import compclub.inf.com.logicinalogicway.Classes.Questao;
import compclub.inf.com.logicinalogicway.R;


public class QuestaoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Questao questao;

    public QuestaoFragment() {

    }

    public void setQuestao(Questao questao){
        this.questao = questao;
    }

    // TODO: Rename and change types and number of parameters
    public static QuestaoFragment newInstance(Questao questao) {
        QuestaoFragment fragment = new QuestaoFragment();
        fragment.setQuestao(questao);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questao, container, false);
        TextView tv_enunciado = (TextView) view.findViewById(R.id.tv_enunciado);
        tv_enunciado.setText(questao.getEnunciado());
        RadioButton rb_a = (RadioButton) view.findViewById(R.id.rb_a);
        RadioButton rb_b = (RadioButton) view.findViewById(R.id.rb_b);
        RadioButton rb_c = (RadioButton) view.findViewById(R.id.rb_c);
        RadioButton rb_d = (RadioButton) view.findViewById(R.id.rb_d);
        rb_a.setText(questao.getAlternativas()[0]);
        rb_b.setText(questao.getAlternativas()[1]);
        rb_c.setText(questao.getAlternativas()[2]);
        rb_d.setText(questao.getAlternativas()[3]);
        Log.println(Log.INFO, "DEBUG", "Criando view do fragment de questao");
        return view;
    }
}
