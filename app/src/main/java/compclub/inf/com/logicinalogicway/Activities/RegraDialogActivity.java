package compclub.inf.com.logicinalogicway.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import compclub.inf.com.logicinalogicway.Classes.RegraOrdenacao;
import compclub.inf.com.logicinalogicway.R;

public class RegraDialogActivity extends Activity {

    List<EditText> campos;
    List<CheckBox> checks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regra_dialog);

        Button bt_criaRegra = (Button) findViewById(R.id.bt_criaRegra);
        Button bt_cancelaRegra = (Button) findViewById(R.id.bt_cancelaRegra);
        Spinner sp_qtdCasas = (Spinner) findViewById(R.id.sp_qtdCasas);
        final LinearLayout ll_regraLetra = (LinearLayout) findViewById(R.id.ll_regraLetra);
        final LinearLayout ll_regraCorte = (LinearLayout) findViewById(R.id.ll_regraCorte);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(5,0,0,0);

        campos = new ArrayList<>();
        checks = new ArrayList<>();
        for (int i=0; i<4; i++){
            EditText et = new EditText(this.getApplicationContext());
            CheckBox cb = new CheckBox(this.getApplicationContext());
            cb.setChecked(true);
            //et.setLayoutParams(params);
            campos.add(et);
            checks.add(cb);
            ll_regraLetra.addView(et);
            ll_regraCorte.addView(cb);
        }

        bt_cancelaRegra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultData = new Intent();
                resultData.putExtra("criado",false);
                setResult(Activity.RESULT_CANCELED, resultData);
                finish();
            }
        });

        bt_criaRegra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] campos_str = new String[campos.size()];
                boolean[] checks_str = new boolean[checks.size()];
                for (int i=0; i<campos.size(); i++){
                    campos_str[i] = campos.get(i).getText().toString();
                    checks_str[i] = checks.get(i).isChecked();
                }

                Intent resultData = new Intent();
                resultData.putExtra("criado",true);
                resultData.putExtra("campos",campos_str);
                resultData.putExtra("checks",checks_str);
                setResult(Activity.RESULT_OK, resultData);
                finish();
            }
        });

        Integer array[] = new Integer[10];
        for(int i=0; i<10; i++) array[i] = i+1;

        sp_qtdCasas.setAdapter(new ArrayAdapter<Integer>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1,
                array
        ));

        sp_qtdCasas.setSelection(3);
        sp_qtdCasas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int item = i+1;
                while (campos.size() > item){
                    EditText et = campos.get(campos.size()-1);
                    CheckBox cb = checks.get(checks.size()-1);
                    ll_regraLetra.removeView(et);
                    ll_regraCorte.removeView(cb);
                    campos.remove(et);
                    checks.remove(cb);
                }

                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.setMargins(5,0,0,0);

                while (campos.size() < item){
                    EditText et = new EditText(getApplicationContext());
                    CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setChecked(true);
                    et.setLayoutParams(params);
                    ll_regraLetra.addView(et);
                    ll_regraCorte.addView(cb);
                    campos.add(et);
                    checks.add(cb);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
