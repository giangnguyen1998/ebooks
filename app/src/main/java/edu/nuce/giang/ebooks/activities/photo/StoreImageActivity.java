package edu.nuce.giang.ebooks.activities.photo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.adapters.Adapter_PhotosFolder;
import edu.nuce.giang.ebooks.models.ImageModel;

public class StoreImageActivity extends AppCompatActivity {

    public static ArrayList<ImageModel> al_images = new ArrayList<>();
    boolean boolean_folder;
    Adapter_PhotosFolder obj_adapter;
    GridView gv_folder;
    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_store);
        gv_folder = findViewById(R.id.gv_folder);

        gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PhotosActivity.class);
                intent.putExtra("value",i);
                startActivity(intent);
            }
        });

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(StoreImageActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) &&
                    (ActivityCompat.shouldShowRequestPermissionRationale(StoreImageActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(StoreImageActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fn_imagespath();
        }

    }

    @SuppressLint("Recycle")
    public ArrayList<ImageModel> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        assert cursor != null;
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStrFolder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }

            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>(al_images.get(int_position).getAllImagePath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAllImagePath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                ImageModel obj_model = new ImageModel();
                obj_model.setStrFolder(cursor.getString(column_index_folder_name));
                obj_model.setAllImagePath(al_path);

                al_images.add(obj_model);
            }
        }

        for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStrFolder());
            for (int j = 0; j < al_images.get(i).getAllImagePath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAllImagePath().get(j));
            }
        }
        obj_adapter = new Adapter_PhotosFolder(getApplicationContext(),al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    fn_imagespath();
                } else {
                    Toast.makeText(StoreImageActivity.this,
                            "The app was not allowed to read or write to your storage. Hence," +
                                    "it cannot function properly. " +
                                    "Please consider granting it this permission",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
