package compclub.inf.com.logicinalogicway.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import compclub.inf.com.logicinalogicway.Classes.Contexto;
import compclub.inf.com.logicinalogicway.Classes.Regra;
import compclub.inf.com.logicinalogicway.Classes.RegraOrdenacao;
import compclub.inf.com.logicinalogicway.Fragments.ContextoFragment;
import compclub.inf.com.logicinalogicway.Fragments.MarcacoesFragment;
import compclub.inf.com.logicinalogicway.Fragments.RegrasFragment;
import compclub.inf.com.logicinalogicway.Model.ContextoDAO;
import compclub.inf.com.logicinalogicway.R;

public class MainActivity extends AppCompatActivity implements
        RegrasFragment.OnFragmentInteractionListener,
        MarcacoesFragment.OnFragmentInteractionListener {

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
        toolbar.setVisibility(View.GONE);

        // Pega ID no banco do contexto
        Bundle b = this.getIntent().getExtras();
        long id = b.getLong("_id");

        ContextoDAO cDAO = new ContextoDAO(this);
        cDAO.open();
        contexto = cDAO.getContextoByID(id);
        cDAO.close();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), contexto);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(1);
        this.getDelegate().setHandleNativeActionModesEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK){
                boolean criado = data.getBooleanExtra("criado", false);
                if (criado){
                    int position = data.getIntExtra("position", -1);
                    RegraOrdenacao regra;
                    if (position == -1)
                        regra = new RegraOrdenacao();
                    else
                        regra = (RegraOrdenacao) contexto.getRegras().get(position);
                    String[] campos = data.getStringArrayExtra("campos");
                    boolean[] checks = data.getBooleanArrayExtra("checks");
                    regra.setNumCampos(campos.length);
                    for (int i = 0; i < campos.length; i++) {
                        regra.setValorCampo(i, campos[i]);
                        regra.setCampoAtivo(i, checks[i]);
                    }
                    if (position == -1)
                        contexto.getRegras().add(regra);
                    mSectionsPagerAdapter.updateRegras();
                }
            }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onDestroy(){
        Log.println(Log.INFO, "Activity destruida!", "hehe");
        ContextoDAO cDAO = new ContextoDAO(this);
        cDAO.open();
        cDAO.updateContexto(contexto);
        cDAO.close();
        super.onDestroy();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Contexto contexto;
        private RegrasFragment regrasFragment;
        private FragmentManager fm;

        public SectionsPagerAdapter(FragmentManager fm, Contexto contexto) {
            super(fm);
            this.contexto = contexto;
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ContextoFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    regrasFragment = RegrasFragment.newInstance(contexto);
                    return regrasFragment;
                case 1:
                    return ContextoFragment.newInstance(contexto);
                case 2:
                    return MarcacoesFragment.newInstance(contexto);
                default:
                    return null;
            }
        }

        public void updateRegras(){
            regrasFragment.updateRegras();
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