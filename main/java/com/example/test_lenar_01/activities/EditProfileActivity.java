package com.example.test_lenar_01.activities;

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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test_lenar_01.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button cameraButton;
    private Button galleryButton;
    private EditText nameAndSurnameEditText;
    private EditText usernameOrEmailEditText;
    private Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageView);
        cameraButton = findViewById(R.id.cameraButton);
        galleryButton = findViewById(R.id.galleryButton);
        nameAndSurnameEditText = findViewById(R.id.nameAndSurnameEditText);
        usernameOrEmailEditText = findViewById(R.id.usernameOrEmailEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
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

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100); // 100 - stała wartość, która później posłuży do identyfikacji tej akcji
            }
        });

    }

    public void askForPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{permission}, requestCode);
    }

    public boolean checkPermission(String permission, int requestCode) {
        // jeśli nie jest przyznane to zażądaj
        if (ContextCompat.checkSelfPermission(EditProfileActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            return false;
        } else {
            Toast.makeText(EditProfileActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            return true;
        }
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
                    Toast.makeText(EditProfileActivity.this, R.string.permission_not_granted, Toast.LENGTH_LONG).show();
                    Log.d("TAG", "permissions: " + permissions);
                    if (permissions[0].equals(Manifest.permission.CAMERA)) {
                        Toast.makeText(EditProfileActivity.this, R.string.account_activity_consequence_of_lack_of_camera_permission, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case 101:
                Log.d("TAG", "Ten drugi komunikat");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                Log.d("TAG", "onActivityResult: dostałem dane obrazka");

                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                Log.d("xxx", b.getWidth() + " - " + b.getHeight());
                imageView.setImageBitmap(b);
            }
        } else if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Uri imgData = data.getData();
                InputStream stream = null;
                try {
                    stream = getContentResolver().openInputStream(imgData);
                    Bitmap b2 = BitmapFactory.decodeStream(stream);
                    imageView.setImageBitmap(b2);
                    Log.d("xxx", b2.getWidth() + " - " + b2.getHeight());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //  menu
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
}