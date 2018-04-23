package br.com.gabrielanery.pokedex;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class cortaImagem extends AppCompatActivity {

    private CropImageView mCropImageView;
    private final int SELECT_PIC = 200;
    Bitmap auxBitmap;
    Uri savedURL;
    Uri image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corta_imagem);
       // new File("@drawable/la9.png").delete();

        //mCropImageView.setImageResource(4);
        mCropImageView = (CropImageView) findViewById(R.id.mCropImageView);



    }


    // LISTENERS
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void rotate(View view){
        if(view.getId() == R.id.bt0){
            mCropImageView.setRotation(0f);
        }
        else if(view.getId() == R.id.bt90){
            mCropImageView.setRotation(90f);
        }
        else if(view.getId() == R.id.bt180){
            mCropImageView.setRotation(180f);
        }
        else if(view.getId() == R.id.bt270){
            mCropImageView.setRotation(270f);
        }
    }




    public void escolherImagem (View v){
        final AlertDialog.Builder alerta= new AlertDialog.Builder(cortaImagem.this);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent.createChooser(intent, "Seleciona a imagem"), SELECT_PIC);
    }

    //Salva imagem e envia para activity adicionaPokemon
    public void salvar(View v) throws IOException {
        Toast.makeText(cortaImagem.this,savedURL.toString(), Toast.LENGTH_LONG).show();
        Matrix matrix = new Matrix();
        matrix.setRotate(mCropImageView.getRotation());
        auxBitmap = Bitmap.createScaledBitmap(mCropImageView.getCroppedImage(),
                mCropImageView.getCroppedImage().getWidth(),
                mCropImageView.getCroppedImage().getHeight(),
                true);

        auxBitmap = Bitmap.createBitmap(auxBitmap, 0, 0, auxBitmap.getWidth(), auxBitmap.getHeight(), matrix, true);

        ContentResolver cr = getContentResolver();
        String title = "/lala/myBitmap";
        String description = "My bitmap created by Android-er";
        savedURL =Uri.parse( MediaStore.Images.Media
                .insertImage(cr, auxBitmap, title, description));

        Toast.makeText(cortaImagem.this,savedURL.toString(), Toast.LENGTH_LONG).show();



        Intent intent = new Intent();
        intent.setClass(cortaImagem.this,adicionaPokemon.class);
        intent.putExtra("uri",savedURL);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        {
            Uri imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }


            mCropImageView.setImageBitmap(bitmap);



        }

    }


}
