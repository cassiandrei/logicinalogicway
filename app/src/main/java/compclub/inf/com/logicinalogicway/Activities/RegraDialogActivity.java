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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import compclub.inf.com.logicinalogicway.R;

public class RegraDialogActivity extends Activity {

    List<Spinner> campos;
    List<CheckBox> checks;
    String letras;
    int position;

    private Spinner createSpinner(String options){
        Spinner spinner = new Spinner(this);
        String[] array = (" ," + options).split(",");

        spinner.setAdapter(new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1,
                array
        ));

        spinner.setSelection(0);
        return spinner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regra_dialog);

        Button bt_criaRegra = (Button) findViewById(R.id.bt_criaRegra);
        Button bt_cancelaRegra = (Button) findViewById(R.id.bt_cancelaRegra);
        Spinner sp_qtdCasas = (Spinner) findViewById(R.id.sp_qtdCasas);
        final LinearLayout ll_regraLetra = (LinearLayout) findViewById(R.id.ll_regraLetra);
        final LinearLayout ll_regraCorte = (LinearLayout) findViewById(R.id.ll_regraCorte);

        campos = new ArrayList<>();
        checks = new ArrayList<>();

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
                    campos_str[i] = campos.get(i).getSelectedItem().toString();
                    if (letras.indexOf(campos_str[i]) == -1)
                        campos_str[i] = " ";
                    checks_str[i] = checks.get(i).isChecked();
                }

                Intent resultData = new Intent();
                resultData.putExtra("criado",true);
                resultData.putExtra("position",position);
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

        letras = getIntent().getStringExtra("variaveis");
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle == null) {
            position = -1;
            for (int i = 0; i < 4; i++) {
                Spinner sp = createSpinner(letras);
                CheckBox cb = new CheckBox(this.getApplicationContext());
                cb.setChecked(true);
                cb.setText(Integer.toString(i + 1));
                campos.add(sp);
                checks.add(cb);
                ll_regraLetra.addView(sp);
                ll_regraCorte.addView(cb);
            }
            sp_qtdCasas.setSelection(3);
        }
        else{
            position = bundle.getInt("position");
            String[] fields = bundle.getStringArray("fields");
            boolean[] _checks = bundle.getBooleanArray("checks");
            ArrayList<String> cbfields = new ArrayList<>();
            for (String s : (" ," + letras).split(","))
                cbfields.add(s);
            for (int i=0; i<fields.length; i++){
                Spinner sp = createSpinner(letras);
                CheckBox cb = new CheckBox(this.getApplicationContext());
                sp.setSelection(cbfields.indexOf(fields[i]));
                cb.setChecked(_checks[i]);
                cb.setText(Integer.toString(i + 1));
                campos.add(sp);
                checks.add(cb);
                ll_regraLetra.addView(sp);
                ll_regraCorte.addView(cb);
            }
            sp_qtdCasas.setSelection(fields.length-1);
        }

        sp_qtdCasas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int item = i+1;
                while (campos.size() > item){
                    Spinner et = campos.get(campos.size()-1);
                    CheckBox cb = checks.get(checks.size()-1);
                    ll_regraLetra.removeView(et);
                    ll_regraCorte.removeView(cb);
                    campos.remove(et);
                    checks.remove(cb);
                }

                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.setMargins(5,0,0,0);

                while (campos.size() < item){
                    Spinner et = createSpinner(letras);
                    CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setChecked(true);
                    cb.setText(Integer.toString(campos.size()+1));
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
