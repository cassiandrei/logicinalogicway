package compclub.inf.com.logicinalogicway.Model;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 12/09/16.
 */
public class Questao {
    private String enunciado;
    private String[] alternativas;
    private int resposta;
    private boolean respondida;
    private boolean acertada;
    private long id;
    private List<Pair<Integer, Integer>> marcacoes;

    public Questao(String enunciado, String[] alternativas, int resposta){
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.resposta = resposta;
        this.setRespondida(false);
        this.setAcertada(false);
        this.marcacoes = new ArrayList<>();
    }

    public boolean responde(int alternativa){
        this.setRespondida(true);
        if (alternativa == this.resposta){
            this.setAcertada(true);
            return true;
        }
        else
            return false;
    }

    public void criaNovaMarcacao(int inicio, int fim){
        this.getMarcacoes().add(new Pair<Integer, Integer>(inicio,fim));
    }

    public void excluiMarcacao(int indice){
        this.getMarcacoes().remove(indice);
    }

    public boolean isRespondida() {
        return respondida;
    }

    public void setRespondida(boolean respondida) {
        this.respondida = respondida;
    }

    public boolean isAcertada() {
        return acertada;
    }

    public void setAcertada(boolean acertada) {
        this.acertada = acertada;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getAlternativas() {
        return alternativas;
    }

    public List<Pair<Integer, Integer>> getMarcacoes() {
        return marcacoes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }
}
