package br.com.gabrielanery.pokedex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class consultaPokemon extends AppCompatActivity {
    int value = -1; // or other values
    DataBase banco;
    boolean imagemTrocou=false;

    ImageView imagem2;
    Button botaoImagem2;
    EditText nome2;
    EditText tipo2;
    EditText localizacao2;
    EditText ataque12;
    EditText ataque22;
    EditText ataque32;
    EditText ataque42;
    TextView  text1  ;
    TextView  text2  ;
    TextView  text3  ;
    TextView  text4  ;
    TextView  text5  ;
    TextView  text6  ;


    Bitmap olhaeuaqui = null;
    Uri uri;
    ArrayList<Ataque> listAtaques=new ArrayList<>();;
Pokemon p =new Pokemon();
    public void  inicializaCampos()
    {
        Typeface mycustomFont = Typeface.createFromAsset(getAssets(), "metal.ttf");
        nome2 = (EditText) findViewById(R.id.editNome2);
        imagem2 = (ImageView) findViewById(R.id.imageView32);
        tipo2 = (EditText) findViewById(R.id.editTipo2);
        localizacao2 = (EditText) findViewById(R.id.editLocalizacao2);
        ataque12 = (EditText) findViewById(R.id.editAtaque12);
        ataque22 = (EditText) findViewById(R.id.editAtaque22);
        ataque32 = (EditText) findViewById(R.id.editAtaque32);
        ataque42 = (EditText) findViewById(R.id.editAtaque42);
        botaoImagem2 = (Button) findViewById(R.id.botaoImagem2);


        text1 = (TextView) findViewById(R.id.textTipo);
        text2 = (TextView) findViewById(R.id.textAtaque1);
        text3 = (TextView) findViewById(R.id.textAtaque2);
        text4 = (TextView) findViewById(R.id.textAtaque3);
        text5 = (TextView) findViewById(R.id.textAtaque4);
        text6 = (TextView) findViewById(R.id.textLocalizacao);


        nome2.setTypeface(mycustomFont);
        tipo2.setTypeface(mycustomFont);
        localizacao2.setTypeface(mycustomFont);
        ataque12.setTypeface(mycustomFont);
        ataque22.setTypeface(mycustomFont);
        ataque32.setTypeface(mycustomFont);
        ataque42.setTypeface(mycustomFont);
        botaoImagem2.setTypeface(mycustomFont);

        text1.setTypeface(mycustomFont);
        text2.setTypeface(mycustomFont);
        text3.setTypeface(mycustomFont);
        text4.setTypeface(mycustomFont);
        text5.setTypeface(mycustomFont);
        text6.setTypeface(mycustomFont);




    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_pokemon);
        inicializaCampos();
        Bundle b = getIntent().getExtras();

        value = b.getInt("id");
        Toast.makeText(consultaPokemon.this, String.valueOf(value),Toast.LENGTH_SHORT).show();
        banco = new DataBase(this, null);
        p=banco.mostraTodosPokemons(value);

        localizacao2.setText(p.getLocalizacao());
        nome2.setText(p.getNome());
        tipo2.setText(p.getTipo());
        listAtaques = banco.mostraAtaques(value);
        ataque12.setText(listAtaques.get(0).getDescricao());
        ataque22.setText(listAtaques.get(1).getDescricao());
        ataque32.setText(listAtaques.get(2).getDescricao());
        ataque42.setText(listAtaques.get(3).getDescricao());
        String path = Environment.getExternalStorageDirectory()+ "/Save Image Tutorial/myimage"+value+".png";
        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imagem2.setImageBitmap(myBitmap);
        }
    }

    public void excluiPokemon(View v)
    {
        banco.excluiPokemon(String.valueOf(value));
        finish();
    }
    public void editaPokemon(View v)
    {
        try {
            String erro = null;
            if (nome2.getText().toString().trim().length() == 0) {
                nome2.setError("Nome é obrigatório");
                throw new RuntimeException("Crash!");
            }
            if (tipo2.getText().toString().trim().length() == 0) {
                tipo2.setError("Tipo é obrigatório");
                throw new RuntimeException("Crash!");

            }
            if (localizacao2.getText().toString().trim().length() == 0) {
                localizacao2.setError("Localização é obrigatório");
                throw new RuntimeException("Crash!");

            }

            if (ataque12.getText().toString().trim().length() == 0 && (ataque22.getText().toString().trim().length()) == 0 && ataque32.getText().toString().trim().length() == 0 && ataque42.getText().toString().trim().length() == 0) {
                ataque12.setError("O pokemon precisa ter pelo menos um ataque!");
                throw new RuntimeException("O pokemon precisa ter pelo menos um ataque");

            }

            banco.editaPokemon(value, nome2.getText().toString(), localizacao2.getText().toString(), tipo2.getText().toString());
            banco.editaAtaque(listAtaques.get(0).getId(), ataque12.getText().toString());
            banco.editaAtaque(listAtaques.get(1).getId(), ataque22.getText().toString());
            banco.editaAtaque(listAtaques.get(2).getId(), ataque32.getText().toString());
            banco.editaAtaque(listAtaques.get(3).getId(), ataque42.getText().toString());
            if (imagemTrocou)
            {
                OutputStream output;
                File filepath = Environment.getExternalStorageDirectory();
                // Create a new folder in SD Card
                File dir = new File(filepath.getAbsolutePath()
                        + "/Save Image Tutorial/");
                dir.mkdirs();
                // Create a name for the saved image
                File file = new File(dir, "myimage" + value + ".png");
                // Show a toast message on successful save
                Toast.makeText(consultaPokemon.this, file.toString(),Toast.LENGTH_SHORT).show();
                Log.d("lala", file.toString());
                try
                {
                    output = new FileOutputStream(file);
                    olhaeuaqui.compress(Bitmap.CompressFormat.PNG, 100, output);
                    output.flush();
                    output.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finish();
            }
        }catch (Exception e)
        {

        }

    }
    public void adicionaImagem2 (View v){
        Intent it2= new Intent(consultaPokemon.this, cortaImagem.class);
//startActivity(it2);
        startActivityForResult(it2,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            //Intent intent = getIntent();
            uri= data.getParcelableExtra("uri");
            Toast.makeText(consultaPokemon.this,uri.toString(),Toast.LENGTH_LONG).show();

            imagemTrocou=true;
            try {
                olhaeuaqui = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagem2.setImageBitmap(olhaeuaqui);
        }
    }
}
