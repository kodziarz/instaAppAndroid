package com.example.test_lenar_01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {

    private GridView gridView;
    private Button editProfileButton;
    //private boolean isPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String[] strings = new String[]{"a", "b", "c"};

        gridView = findViewById(R.id.gridView);
        editProfileButton = findViewById(R.id.button);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AccountActivity.this,       // tzw Context
                R.layout.account_activity_grid_element,     // nazwa pliku xml naszej komórki na liście
                R.id.textView,                // id pola txt w komórce
                strings);                 // tablica przechowująca testowe dane

        gridView.setAdapter(adapter);

        // ogarnięcie uprawnień
        if (checkPermission(Manifest.permission.CAMERA, 100)) {
            askForPermission(Manifest.permission.CAMERA, 100);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "numer klikanego wiersza w GridView = " + i);
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission(Manifest.permission.CAMERA, 100)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //jeśli jest dostępny aparat
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 200); // 200 - stała wartość, która później posłuży do identyfikacji tej akcji
                    }
                } else {
                    askForPermission(Manifest.permission.CAMERA, 100);
                }
            }
        });
    }

    public boolean checkPermission(String permission, int requestCode) {
        // jeśli nie jest przyznane to zażądaj
        if (ContextCompat.checkSelfPermission(AccountActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            return false;
        } else {
            Toast.makeText(AccountActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public void askForPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(AccountActivity.this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "Otrzymano uprawnienie");
                } else {
                    Toast.makeText(AccountActivity.this, R.string.permission_not_granted, Toast.LENGTH_LONG).show();
                    Log.d("TAG", "permissions: " + permissions);
                    if(permissions[0].equals(Manifest.permission.CAMERA)){
                        Toast.makeText(AccountActivity.this, R.string.account_activity_consequence_of_lack_of_camera_permission, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case 101:
                Log.d("TAG", "Ten drugi komunikat");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.firstOption:
                Log.d("GLEN", "kliknięto pierwszą opcję.");
                return true;
            case R.id.secondOption:
                Log.d("GLEN", "kliknięto drugą opcję.");
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                Log.d("TAG", "onActivityResult: dostałem dane obrazka");

                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                Log.d("xxx", b.getWidth() +" - "+b.getHeight());
                //iv.setImageBitmap(b);
            }
        }
    }
}