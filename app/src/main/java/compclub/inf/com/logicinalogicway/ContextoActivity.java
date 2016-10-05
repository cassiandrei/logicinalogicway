package compclub.inf.com.logicinalogicway;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import compclub.inf.com.logicinalogicway.Model.Contexto;
import compclub.inf.com.logicinalogicway.Model.ContextoDAO;

public class ContextoActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Contexto contexto;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //barra superior
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.println(Log.INFO,"<ContAct>","Vou pegar o id");
        // Pega ID no banco do contexto
        Bundle b = this.getIntent().getExtras();
        long id = b.getLong("_id");

        Log.println(Log.INFO,"<ContAct>","Vou pegar o contexto");
        ContextoDAO cDAO = new ContextoDAO(this);
        cDAO.open();
        contexto = cDAO.getContextoByID(id);
        cDAO.close();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        Log.println(Log.INFO,"<ContAct>","Vou criar o adaptador");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), contexto);


        Log.println(Log.INFO,"<ContAct>","Vou setar o adaptador");
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        //botao mensagem
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ContextoFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String TITULO = "titulo";
        private static final String DEFINICAO = "definicao";
        private static final String TIPO = "tipo";
        private static final String QUESTOES = "questoes";

        public ContextoFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ContextoFragment newInstance(Contexto contexto) {
            Log.println(Log.INFO,"<ContFrag>","Criando instancia...");
            ContextoFragment fragment = new ContextoFragment();
            Bundle args = new Bundle();
            args.putString(TITULO, contexto.getTitulo());
            args.putString(DEFINICAO, contexto.getDefinicao());
            args.putString(TIPO, contexto.getTipo());
            args.putInt(QUESTOES,contexto.getQuestoes().size());
            fragment.setArguments(args);
            Log.println(Log.INFO,"<ContFrag>","Retornando...");
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.println(Log.INFO,"<ContFrag>","Criando a vir do fragment...");
            View rootView = inflater.inflate(R.layout.fragment_contexto, container, false);
            TextView titulo    = (TextView) rootView.findViewById(R.id.tv_Titulo);
            TextView definicao = (TextView) rootView.findViewById(R.id.tv_contexto);
            ListView listaQuestoes = (ListView) rootView.findViewById(R.id.lv_questoes);
            titulo.setText(this.getArguments().getString(TITULO));
            definicao.setText(this.getArguments().getString(DEFINICAO));

            String [] vetor = new String[this.getArguments().getInt(QUESTOES)];
            for(int i=0;i<this.getArguments().getInt(QUESTOES);i++){
                vetor[i]=String.valueOf(i+1);
            }

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                    this.getContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    vetor
            );
            listaQuestoes.setAdapter(adaptador);
            listaQuestoes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Bundle b = new Bundle();
                    //b.putLong("_id", ids.get(position));
                    //Intent intent = new Intent(TitulosActivity.this,ContextoActivity.class);
                    //intent.putExtras(b);
                    //startActivity(intent);
                    //int codigoPosicao = position;
                }
            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Contexto contexto;
        private Fragment contextoFragment;

        public SectionsPagerAdapter(FragmentManager fm, Contexto contexto) {
            super(fm);
            this.contexto = contexto;
            Log.println(Log.INFO,"<SectPagAdap>","Vou criar uma nova instancia");
           // contextoFragment = ContextoFragment.newInstance(contexto);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ContextoFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return ContextoFragment.newInstance(contexto);
                case 1:
                    return ContextoFragment.newInstance(contexto);
                case 2:
                    return ContextoFragment.newInstance(contexto);
            }
            return ContextoFragment.newInstance(contexto);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Regras";
                case 1:
                    return "Contexto";
                case 2:
                    return "Marcações";
            }
            return null;
        }
    }
}
