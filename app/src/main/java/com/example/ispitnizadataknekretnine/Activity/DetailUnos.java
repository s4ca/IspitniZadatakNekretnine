package com.example.ispitnizadataknekretnine.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Button;
import android.widget.EditText;
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

public class DetailUnos extends AppCompatActivity {


   EditText et_naslovU;
   EditText et_opisU;
   EditText et_adresaU;
   EditText et_brojU;
   EditText et_kvadraturaU;
   EditText et_brSobaU;
   EditText et_cenaU;
   ImageView iv_slikaU;
    Button btnSlikaU;


    Toolbar toolbar;
    List<String> drawerItems;
    DrawerLayout drawerLayout;

    ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    DataBaseHelper dataBaseHelper;

    public static String picturePath;
    private static final int SELECT_PICTURE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_unos);

        et_naslovU = findViewById(R.id.et_naziv_unos);
        et_opisU = findViewById(R.id.et_opis_unos);
        et_adresaU = findViewById(R.id.et_adresa_unos);
       et_brojU = findViewById(R.id.et_broj_unos);
        et_kvadraturaU = findViewById(R.id.et_kvadratura_unos);
        et_brSobaU = findViewById(R.id.et_broj_soba_unos);
        et_cenaU = findViewById(R.id.et_cena_unos);
        iv_slikaU = findViewById(R.id.slika_unos);
        btnSlikaU = findViewById(R.id.btn_dodajSliku_unos);


        btnSlikaU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSlika();
            }
        });

        setupToolbar();
        fillDrawer();
        setupDrawer();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_unos, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_add_unos):
                addNekretnina();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
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
                        Intent intent2 = new Intent(DetailUnos.this, MainActivity.class);
                        startActivity(intent2);
                        break;
                    case 1:
                        title = "Settings";
                        Intent intent = new Intent(DetailUnos.this, SettingsActivity.class);
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


    public void addNekretnina (){

        Nekretnina nekretnina = new Nekretnina();
        nekretnina.setNaziv(et_naslovU.getText().toString());
        nekretnina.setOpis(et_opisU.getText().toString());
        nekretnina.setAdresa(et_adresaU.getText().toString());
      nekretnina.setKontakt(Integer.parseInt(et_brojU.getText().toString()));
      nekretnina.setKvadratura(Integer.parseInt(et_kvadraturaU.getText().toString()));
      nekretnina.setBrojSoba(Integer.parseInt(et_brSobaU.getText().toString()));
      nekretnina.setCena(Integer.parseInt(et_cenaU.getText().toString()));
      nekretnina.setSlika(picturePath);


        try {
            getDatabaseHelper().getmNekretninaDao().create(nekretnina);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null){
            OpenHelperManager.releaseHelper();
            dataBaseHelper=null;
        }
    }

    private void showSlika() {
        if (isStoragePermissionGranted()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_PICTURE);
        }
    }
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String [] filePathColumn = {MediaStore.Images.Media.DATA};

            if (selectedImage != null) {
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    iv_slikaU.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            showSlika();
        }
    }
}
