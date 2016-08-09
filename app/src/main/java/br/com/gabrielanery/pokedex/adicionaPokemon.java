package br.com.gabrielanery.pokedex;

import android.content.Intent;
import android.graphics.Bitmap;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class adicionaPokemon extends AppCompatActivity {
    DataBase banco;
    Pokemon p;

    ImageView imagem;
    Ataque a1= new Ataque();
    Ataque a2= new Ataque();
    Ataque a3= new Ataque();
    Ataque a4= new Ataque();


    EditText nome;
    EditText tipo;
    EditText localizacao;
    EditText ataque1;
    EditText ataque2;
    EditText ataque3;
    EditText ataque4;

    Button botaoImagem;
    Button botaoAdiciona;


    TextView  text1  ;
    TextView  text2  ;
    TextView  text3  ;
    TextView  text4  ;
    TextView  text5  ;
    TextView  text6  ;
    TextView  text7  ;


    Bitmap olhaeuaqui = null;
    Uri uri;

    private final int PHODO_CODE=100;
public void  inicializaCampos()
{
    Typeface mycustomFont = Typeface.createFromAsset(getAssets(),"metal.ttf");
    imagem          = (ImageView) findViewById(R.id.imageView32);
    nome            = (EditText) findViewById(R.id.editText);
    tipo            = (EditText) findViewById(R.id.editText2);
    localizacao     = (EditText) findViewById(R.id.editText3);
    ataque1         = (EditText) findViewById(R.id.editAtaque1);
    ataque2         = (EditText) findViewById(R.id.editAtaque2);
    ataque3         = (EditText) findViewById(R.id.editAtaque3);
    ataque4         = (EditText) findViewById(R.id.editAtaque4);
    botaoImagem     = (Button) findViewById(R.id.button4);
    botaoAdiciona   = (Button) findViewById(R.id.button2);

    text1           = (TextView) findViewById(R.id.textView2);
    text2           =(TextView) findViewById(R.id.textView3);
    text3           =(TextView) findViewById(R.id.textView4);
    text4           =(TextView) findViewById(R.id.textView5);
    text5           =(TextView) findViewById(R.id.textView6);
    text6           =(TextView) findViewById(R.id.textView9);
    text7           =(TextView) findViewById(R.id.textView10);


    ataque1.setText(null);
    ataque2.setText(null);
    ataque3.setText(null);
    ataque4.setText(null);




    nome.setTypeface(mycustomFont);
    tipo.setTypeface(mycustomFont);
    localizacao.setTypeface(mycustomFont);
    ataque1.setTypeface(mycustomFont);
    ataque2.setTypeface(mycustomFont);
    ataque3.setTypeface(mycustomFont);
    ataque4.setTypeface(mycustomFont);
    botaoImagem.setTypeface(mycustomFont);
    botaoAdiciona.setTypeface(mycustomFont);
    text1.setTypeface(mycustomFont);
    text2.setTypeface(mycustomFont);
    text3.setTypeface(mycustomFont);
    text4.setTypeface(mycustomFont);
    text5.setTypeface(mycustomFont);
    text6.setTypeface(mycustomFont);
    text7.setTypeface(mycustomFont);




}
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_pokemon);
        banco = new DataBase(this, null);
        inicializaCampos();


    }
    public void adicionaImagem (View v){
        Intent it2= new Intent(adicionaPokemon.this, cortaImagem.class);
//startActivity(it2);
        startActivityForResult(it2,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

             uri= data.getParcelableExtra("uri");
            try {
                olhaeuaqui = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagem.setImageBitmap(olhaeuaqui);
        }
    }
    public void adicionarClick (View v) throws FileNotFoundException {
        try
        {
            if (nome.getText().toString().trim().length() == 0) {
                nome.setError("Nome é obrigatório");
                throw new RuntimeException("Crash!");
            }
            if (tipo.getText().toString().trim().length() == 0) {
                tipo.setError("Tipo é obrigatório");
                throw new RuntimeException("Crash!");

            }
            if (localizacao.getText().toString().trim().length() == 0) {
                localizacao.setError("Localização é obrigatório");
                throw new RuntimeException("Crash!");

            }

            if (ataque1.getText().toString().trim().length() == 0 && (ataque2.getText().toString().trim().length()) == 0 && ataque3.getText().toString().trim().length() == 0 && ataque4.getText().toString().trim().length() == 0) {
                ataque1.setError("O pokemon precisa ter pelo menos um ataque!");
                throw new RuntimeException("O pokemon precisa ter pelo menos um ataque");

            }
            if (imagem.getDrawable() == null) {

                botaoImagem.setError("Escolha uma imagem");
                throw new RuntimeException("Escolha uma imagem para o seu Pokemon");
            }
            p = new Pokemon();
            p.setNome(nome.getText().toString());
            p.setTipo(tipo.getText().toString());
            p.setLocalizacao(localizacao.getText().toString());
            a1.setDescricao(ataque1.getText().toString());
            a2.setDescricao(ataque2.getText().toString());
            a3.setDescricao(ataque3.getText().toString());
            a4.setDescricao(ataque4.getText().toString());
            Long id = banco.adicionaPokemon(p, a1, a2, a3, a4);
            Toast.makeText(adicionaPokemon.this, id.toString(),Toast.LENGTH_SHORT).show();
            OutputStream output;
            // Find the SD Card path
            File filepath = Environment.getExternalStorageDirectory();
            // Create a new folder in SD Card
            File dir = new File(filepath.getAbsolutePath()
                    + "/Save Image Tutorial/");
            dir.mkdirs();
            // Create a name for the saved image
            File file = new File(dir, "myimage" + id + ".png");
            // Show a toast message on successful save
            Log.d("lala", file.toString());
            try
            {
                output = new FileOutputStream(file);
                olhaeuaqui.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finish();

        }
        catch (Exception e)
        {
            Toast.makeText(adicionaPokemon.this, e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
