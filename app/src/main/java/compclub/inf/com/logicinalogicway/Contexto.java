package compclub.inf.com.logicinalogicway;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 12/09/16.
 */
public class Contexto {
    private String definicao;
    private String tipo;
    private List<Pair<Integer, Integer>> marcacoes;
    private List<Questao> questoes;
    private List<Regra> regras;

    public Contexto(String definicao, String tipo){
        this.definicao = definicao;
        this.tipo = tipo;
        this.marcacoes = new ArrayList<>();
        this.questoes = new ArrayList<>();
        this.regras = new ArrayList<>();
    }

    public void addQuestao(Questao q){
        this.getQuestoes().add(q);
    }

    public void criaNovaRegra(int tipo){
        if (tipo == 1)
            this.getRegras().add(new RegraOrdenacao());
        else
            this.getRegras().add(new RegraImplicacao());
    }

    public void excluiRegra(int indice){
        this.getRegras().remove(indice);
    }

    public void criaNovaMarcacao(int inicio, int fim){
        this.getMarcacoes().add(new Pair<Integer, Integer>(inicio,fim));
    }

    public void excluiMarcacao(int indice){
        this.getMarcacoes().remove(indice);
    }

    public int getNumRespondidas(){
        int t=0;
        for (Questao q : this.getQuestoes())
            t += q.isRespondida()?1:0;
        return t;
    }

    public int getNumAcertadas(){
        int t=0;
        for (Questao q : this.getQuestoes())
            t += q.isAcertada()?1:0;
        return t;
    }

    public String getDefinicao() {
        return definicao;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Pair<Integer, Integer>> getMarcacoes() {
        return marcacoes;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public List<Regra> getRegras() {
        return regras;
    }
}
