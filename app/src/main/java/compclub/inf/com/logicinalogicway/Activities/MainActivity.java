package compclub.inf.com.logicinalogicway.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import compclub.inf.com.logicinalogicway.Classes.Contexto;
import compclub.inf.com.logicinalogicway.Classes.Questao;
import compclub.inf.com.logicinalogicway.Fragments.ContextoFragment;
import compclub.inf.com.logicinalogicway.Fragments.MarcacoesFragment;
import compclub.inf.com.logicinalogicway.Fragments.QuestaoFragment;
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (mSectionsPagerAdapter.isContextoFragmentActive())
            super.onBackPressed();
        else
            mSectionsPagerAdapter.switchToContexto();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Contexto contexto;
        private FragmentManager fm;
        private Fragment fragment_centro;
        private Fragment fragment_contexto;

        public SectionsPagerAdapter(FragmentManager fm, Contexto contexto) {
            super(fm);
            this.contexto = contexto;
            this.fm = fm;
           // contextoFragment = ContextoFragment.newInstance(contexto);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ContextoFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return RegrasFragment.newInstance(contexto);
                case 1:
                    if (fragment_centro == null) {
                        fragment_centro = ContextoFragment.newInstance(contexto, new ContextoFragment.ContextoFragmentListener() {
                            @Override
                            public void onSwitchToNextFragment(Questao questao) {
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.remove(fragment_centro).commit();
                                fragment_centro = QuestaoFragment.newInstance(questao);
                                notifyDataSetChanged();
                            }
                        });
                        fragment_contexto = fragment_centro;
                    }
                    return fragment_centro;
                case 2:
                    return MarcacoesFragment.newInstance(contexto);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public int getItemPosition(Object object)
        {
            if (object instanceof ContextoFragment && fragment_centro instanceof QuestaoFragment)
                return POSITION_NONE;
            if (object instanceof QuestaoFragment && fragment_centro instanceof ContextoFragment)
                return POSITION_NONE;
            return POSITION_UNCHANGED;
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

        public boolean isContextoFragmentActive(){
            return fragment_centro instanceof ContextoFragment;
        }

        public void switchToContexto(){
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment_centro).commit();
            fragment_centro = fragment_contexto;
            notifyDataSetChanged();
        }
    }
}