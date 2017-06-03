package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private FloatingActionButton fab;

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


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

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

        // Começa com o fragment de categorias inflado
        CategoriasFragment fragment = new CategoriasFragment();
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

        SearchView searchView = (SearchView) menu.findItem(R.id.search_field).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                /*Intent i = new Intent(MainActivity.this, BuscaActivity.class);
                i.putExtra("busca",s);
                startActivity(i);*/
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
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
