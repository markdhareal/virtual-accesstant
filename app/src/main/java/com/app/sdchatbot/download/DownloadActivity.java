package com.app.sdchatbot.download;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sdchatbot.R;
import com.app.sdchatbot.util.SharedPref;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class DownloadActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;

    private TextView fileNameTextView;
    private Button downloadFile;
    private String nameOfFile;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        fileNameTextView = findViewById(R.id.pdfName);
        downloadFile = findViewById(R.id.downloadFileButtonId);

        //storage runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        }

        //get the value of shared preferences
        nameOfFile = SharedPref.loadPdfFile(this);
        fileNameTextView.setText(nameOfFile);

        //button to download the file
        downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });
    }

    //method to download the file
    private void download()
    {
        storageReference = firebaseStorage.getInstance().getReference();
        reference = storageReference.child("files/"+nameOfFile+".pdf");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(nameOfFile);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        File rootPath = new File(Environment.getExternalStorageDirectory(), "ACCESS_DOWNLOADS");

        if(!rootPath.exists())
        {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, nameOfFile+".pdf");

        reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ","created"+localFile.toString());

                if(localFile.canRead())
                {
                    progressDialog.dismiss();
                }

                Toast.makeText(DownloadActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("firebase ","not created"+e.toString());

                progressDialog.dismiss();
                Toast.makeText(DownloadActivity.this, "Failed to download", Toast.LENGTH_SHORT).show();
            }
        });

    }
}