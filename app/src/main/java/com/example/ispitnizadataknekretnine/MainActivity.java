package com.example.ispitnizadataknekretnine;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ispitnizadataknekretnine.Activity.DetailUnos;
import com.example.ispitnizadataknekretnine.Activity.DetaljiPrikaz;
import com.example.ispitnizadataknekretnine.Activity.SettingsActivity;
import com.example.ispitnizadataknekretnine.DataBase.DataBaseHelper;
import com.example.ispitnizadataknekretnine.DataBase.Nekretnina;
import com.example.ispitnizadataknekretnine.RvAdapter.MyAdapter;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {

    Toolbar toolbar;
    List<String> drawerItems;
    DrawerLayout drawerLayout;
    ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    RecyclerView rv_lista;
    List<Nekretnina> nekretninas;
    MyAdapter myAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        fillDrawer();
        setupDrawer();
        fillDataRV();
        setupRec();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mian_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_add):
                Intent intent = new Intent(this, DetailUnos.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }
    }

    public void fillDrawer(){
        drawerItems = new ArrayList<>();
        drawerItems.add("Sve nekretnine");
        drawerItems.add("Settings");
    }

    private void setupDrawer(){
        drawerList = findViewById(R.id.lv_lista);
        drawerLayout = findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = "Unknown";
                switch (i){
                    case 0:
                        title = "Sve nekretnine";
                        break;
                    case 1:
                        title = "Settings";
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
                setTitle(title);
                drawerLayout.closeDrawer(drawerList);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
    }

    public DataBaseHelper getDatabaseHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }


    public void setupRec (){
        rv_lista = findViewById(R.id.rv_lista_main);
        rv_lista.setLayoutManager(new LinearLayoutManager(this));
        if (myAdapter == null){
            try {
                myAdapter = new MyAdapter(getDatabaseHelper().getmNekretninaDao().queryForAll(), this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rv_lista.setAdapter(myAdapter);
        }else {
            myAdapter.notifyDataSetChanged();
        }
    }

    public void fillDataRV (){
        try {
            nekretninas = getDatabaseHelper().getmNekretninaDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(Nekretnina nekretnina) {
        Intent intent = new Intent(this, DetaljiPrikaz.class);
        intent.putExtra("nekretnina_id", nekretnina.getId());
        startActivity(intent);
    }
}
