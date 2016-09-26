package compclub.inf.com.logicinalogicway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    private ImageView botaoIntro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        botaoIntro = (ImageView) findViewById(R.id.logoclubeid);

        botaoIntro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
