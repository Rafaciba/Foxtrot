package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private FloatingActionButton fab;
    private MenuItem itemMessages;
    private RelativeLayout badgeLayout;
    private TextView badgeCarrinho;
    private ImageButton iconButtonCarrinho;


    ShowDialog showDialog = new ShowDialog(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("aceito", false)) {
            Intent intent = new Intent(MainActivity.this, TermosActivity.class);
            startActivity(intent);
            return;
        }

        SharedPreferences loginPrefs = getSharedPreferences("login", MODE_PRIVATE);

        if(loginPrefs.getString("usuario", null) != null){

            try {
                JSONObject json = new JSONObject(loginPrefs.getString("usuario", null));
                Cliente u = new Cliente(json.getInt("idCliente"),
                        json.getString("nomeCompletoCliente"),
                        json.getString("emailCliente"),
                        json.getString("senhaCliente"),
                        json.getString("CPFCliente"),
                        json.getString("celularCliente"),
                        json.getString("telComercialCliente"),
                        json.getString("telResidencialCliente"),
                        json.getString("dtNascCliente"),
                        json.getInt("recebeNewsLetter"));

                SingletonCliente singletonClienteLogado = SingletonCliente.getInstance();
                singletonClienteLogado.setClienteLogado(u);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        SingletonCliente singletonCliente = SingletonCliente.getInstance();

        if (singletonCliente.estaLogado()){
            MenuItem statusLogin = navigationView.getMenu().findItem(R.id.nav_status_login);
            statusLogin.setTitle(R.string.cliente_logado);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                drawer.closeDrawers();
                int id = menuItem.getItemId();

                if (id == R.id.nav_produtos) {
                    CategoriasFragment fragment = new CategoriasFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
                    return true;
                } else if (id == R.id.nav_sobre) {
                    Intent i = new Intent(MainActivity.this, SobreActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_status_login) {
                    SingletonCliente singletonCliente = SingletonCliente.getInstance();

                    if (singletonCliente.estaLogado()){

                        menuItem.setTitle(R.string.cliente_deslogado);
                        singletonCliente.setClienteLogado(null);
                        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove("usuario");
                        editor.apply();

                        showDialog.showMessage("Desconectado","Sair");

                        menuItem.setChecked(false);

                        return false;
                    }else{
                        menuItem.setTitle(R.string.cliente_logado);
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }

                return false;
            }
        });


        toggle =
                new ActionBarDrawerToggle(this, drawer,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
                startActivity(intent);
            }
        });

        if(NetworkUtil.getConnectivityStatus(MainActivity.this) == 0){
            ShowDialog sd = new ShowDialog(MainActivity.this);
            sd.showConnectionMessage();
        }

        Fragment fragment;
        Intent i = getIntent();
        if((i != null) && ((i.getStringExtra("fragment") != null) || (i.getStringExtra("busca") != null))){
            if(i.getStringExtra("fragment") != null) {
                String pagina = i.getStringExtra("fragment");
                if (pagina.equals("resumo")) {
                    fragment = new ResumoCompraFragment();
                } else {
                    fragment = new CategoriasFragment();
                }
            }else if (i.getStringExtra("busca") != null){
                Bundle bundleBusca = new Bundle();
                bundleBusca.putString("busca", i.getStringExtra("busca"));

                fragment = new BuscaFragment();
                fragment.setArguments(bundleBusca);
            }else{
                fragment = new CategoriasFragment();
            }
        }else{
            fragment = new CategoriasFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem itemMessages = menu.findItem(R.id.menuCarrinho);

        itemMessages.setActionView(R.layout.badge_layout);

        badgeLayout = (RelativeLayout) itemMessages.getActionView();
        badgeCarrinho = (TextView) badgeLayout.findViewById(R.id.badge_textView);

        SingletonCarrinho sc = SingletonCarrinho.getInstance();
        badgeCarrinho.setText(""+sc.getItensCarrinho().size());

        //badgeCarrinho.setVisibility(View.GONE); // initially hidden

        iconButtonCarrinho = (ImageButton) badgeLayout.findViewById(R.id.badge_icon_button);
        //iconButtonCarrinho.setTextColor(getResources().getColor(R.color.action_bar_icon_color_disabled));

        iconButtonCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CarrinhoActivity.class);
                startActivity(i);
            }
        });

        SearchView searchView = (SearchView) menu.findItem(R.id.search_field).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("busca",s);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_field) {
            return true;
        }else if(id == R.id.menuCarrinho){
            Intent i = new Intent(MainActivity.this, CarrinhoActivity.class);
            startActivity(i);
            return true;
        }

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
