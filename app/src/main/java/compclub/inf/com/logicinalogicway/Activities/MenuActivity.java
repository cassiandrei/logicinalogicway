package compclub.inf.com.logicinalogicway.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import compclub.inf.com.logicinalogicway.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton titulos = (ImageButton) this.findViewById(R.id.bt_titulos);
        titulos.setOnClickListener(criaListener(TitulosActivity.class));

        ImageButton estatisticas = (ImageButton) this.findViewById(R.id.bt_estatisticas);
        estatisticas.setOnClickListener(criaListener(TitulosActivity.class));

        ImageButton tutorial = (ImageButton) this.findViewById(R.id.bt_tutorial);
        tutorial.setOnClickListener(criaListener(TitulosActivity.class));

        ImageButton sobre = (ImageButton) this.findViewById(R.id.bt_sobre);
        sobre.setOnClickListener(criaListener(SobreActivity.class));
    }

    private ImageButton.OnClickListener criaListener(final Class destino){
        return new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, destino);
                startActivity(intent);
            }
        };
    }

}
