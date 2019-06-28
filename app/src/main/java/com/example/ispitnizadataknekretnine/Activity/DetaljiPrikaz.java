package com.example.ispitnizadataknekretnine.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ispitnizadataknekretnine.DataBase.DataBaseHelper;
import com.example.ispitnizadataknekretnine.DataBase.Nekretnina;
import com.example.ispitnizadataknekretnine.MainActivity;
import com.example.ispitnizadataknekretnine.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetaljiPrikaz extends AppCompatActivity {

    Toolbar toolbar;
    List<String> drawerItems;
    DrawerLayout drawerLayout;
    ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    DataBaseHelper dataBaseHelper;
    Nekretnina nekretnina;
    AlertDialog dijalog;

    TextView et_naslovO;
    TextView et_opisO;
    TextView et_adresaO;
    TextView et_brojO;
    TextView et_kvadraturaO;
    TextView et_brSobaO;
    TextView et_cenaO;
    ImageView iv_slikaO;

    private static final int SELECT_PICTURE = 0;
    public static String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji_prikaz);

        setupToolbar();
        fillDrawer();
        setupDrawer();
        getNekretninaData(getIntent());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prikaz, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_edit_prikaz):
                //metoda za edit
                break;

            case (R.id.action_delete_prikaz):
              //  delete();
                showDialog();
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
                        Intent intent = new Intent(DetaljiPrikaz.this, SettingsActivity.class);
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

    public void delete (){
        try {
            getDatabaseHelper().getmNekretninaDao().delete(nekretnina);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getNekretninaData (Intent intent){
        int nekretnina_id = intent.getIntExtra("nekretnina_id", 1);

        try {
            nekretnina = getDatabaseHelper().getmNekretninaDao().queryForId(nekretnina_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        et_naslovO = findViewById(R.id.tv_nazivO);
        et_opisO = findViewById(R.id.tv_opisO);
        et_adresaO = findViewById(R.id.tv_adresaO);
        et_brojO = findViewById(R.id.tv_brojO);
        et_kvadraturaO = findViewById(R.id.tv_kvadO);
        et_brSobaO = findViewById(R.id.tv_sobaO);
        et_cenaO = findViewById(R.id.tv_cenaO);
        iv_slikaO = findViewById(R.id.iv_slikaO);


        et_naslovO.setText(nekretnina.getNaziv());
        et_opisO.setText(nekretnina.getOpis());
        et_adresaO.setText(nekretnina.getAdresa());
        et_brojO.setText(""+nekretnina.getKontakt());
        et_kvadraturaO.setText(""+nekretnina.getKvadratura());
        et_brSobaO.setText(""+nekretnina.getBrojSoba());
        et_cenaO.setText(""+nekretnina.getCena());
        if (nekretnina.getSlika() !=null){
            iv_slikaO.setImageBitmap(BitmapFactory.decodeFile(nekretnina.getSlika()));
        }
    }


    private void showDialog(){
        if (dijalog == null){
            dijalog = new Dijalog(this).prepereDialog();
        } else {
            if (dijalog.isShowing()){
                dijalog.dismiss();
            }
        }
        dijalog.show();
    }

    public class Dijalog extends AlertDialog.Builder {
        public Dijalog(Context context) {
            super(context);
            setTitle("Moj Dialog");
            setMessage("Da li ste sigurni da zelite da obrisete ?");
            setPositiveButton("Potrvrdi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   delete();
                }
            });
            setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        public AlertDialog prepereDialog (){
            AlertDialog dialog = create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }

}
