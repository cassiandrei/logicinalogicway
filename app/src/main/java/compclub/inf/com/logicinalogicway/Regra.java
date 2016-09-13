package compclub.inf.com.logicinalogicway;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 12/09/16.
 */
public class Regra {
    private List<Pair<String, Boolean>> campos;
    private int tipo;

    public Regra(int tipo){
        this.tipo = tipo;
        this.campos = new ArrayList<>();
        for(int i=0;i<2;i++)
            this.getCampos().add(new Pair<String, Boolean>("",true));
    }

    public void setValorCampo(int pos, String Alternativa){
        if (this.getCampos().size() > pos) {
            boolean ativo = this.getCampos().get(pos).second;
            this.getCampos().remove(pos);
            this.getCampos().add(pos, new Pair<String, Boolean>(Alternativa, ativo));
        }
    }

    public void setCampoAtivo(int pos, boolean ativo){
        if (this.getCampos().size() > pos) {
            String alternativa = this.getCampos().get(pos).first;
            this.getCampos().remove(pos);
            this.getCampos().add(pos, new Pair<String, Boolean>(alternativa, ativo));
        }
    }

    public List<Pair<String, Boolean>> getCampos() {
        return campos;
    }

    public int getTipo() {
        return tipo;
    }
}
