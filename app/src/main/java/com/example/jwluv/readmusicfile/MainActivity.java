package com.example.jwluv.readmusicfile;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 0;
    int writeExternalStoragePermission;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check whether this app has write external storage permission or not.
        writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // If do not grant write external storage permission.
        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
        }

        textView = findViewById(R.id.textView);

        readMusicFilesFromExternalPublicStorage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getApplicationContext(), "You grant write external storage permission. Please click original button again to continue.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Read music files from public external storage
    //https://www.dev2qa.com/android-read-write-external-storage-file-example/
    public void readMusicFilesFromExternalPublicStorage() {
            // Use Environment class to get public external storage directory.
            File publicDir = Environment.getExternalStorageDirectory();

            // Because at the beginning of this method, we had grant write external storage permission
            // So we can create this directory here.
            File customPublicDir = new File(publicDir, "Custom");
            customPublicDir.mkdirs();

            File musicPublicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

            File files[] = musicPublicDir.listFiles();

            String str = "";
            for(int i=0; i<files.length; i++)
                str += files[i].getName() + "\n";

            textView.setText(str);

            File dcimPublicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

            // Use Context class to get app private external storage directory

            Context context = getApplicationContext();

            File musicPrivateDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
//            Log.d(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(Environment.DIRECTORY_MUSIC) : " + musicPrivateDir.getAbsolutePath());

            File dcimPrivateDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
//            Log.d(LOG_TAG_EXTERNAL_STORAGE, "context.getExternalFilesDir(Environment.DIRECTORY_DCIM) : " + dcimPrivateDir.getAbsolutePath());

            Toast.makeText(context, "Music dir: " + musicPrivateDir.toString(), Toast.LENGTH_LONG).show();
        }

}
