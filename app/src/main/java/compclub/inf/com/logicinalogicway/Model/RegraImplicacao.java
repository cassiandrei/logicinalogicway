package compclub.inf.com.logicinalogicway.Model;

import android.util.Pair;

import java.util.List;

import compclub.inf.com.logicinalogicway.Model.Regra;

/**
 * Created by rafael on 13/09/16.
 */
public class RegraImplicacao extends Regra {

    public RegraImplicacao(){
        super(2);
    }

    public String toLabel(){
        List<Pair<String, Boolean>> l = getCampos();
        Pair<String, Boolean> e = l.get(0);
        Pair<String, Boolean> d = l.get(1);
        return e.first + " -> " + e.second;
    }
}
