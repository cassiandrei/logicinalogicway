package compclub.inf.com.logicinalogicway.Classes;

import android.util.Pair;

/**
 * Created by rafael on 13/09/16.
 */
public class RegraOrdenacao extends Regra {

    public RegraOrdenacao(){
        super(1);
    }

    public void setNumCampos(int campos){
        while (this.getCampos().size() > campos)
            this.getCampos().remove(this.getCampos().size()-1);
        while (this.getCampos().size() < campos)
            this.getCampos().add(new Pair<String, Boolean>("",true));
    }

    public int getNumCampos(){
        return this.getCampos().size();
    }

    public String toLabel(){
        String s = "";
        for(Pair<String, Boolean> p : getCampos()){
            s+= p.first + " ";
        }
        return s;
    }
}
