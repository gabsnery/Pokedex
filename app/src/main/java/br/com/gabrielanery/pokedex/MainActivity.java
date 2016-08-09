package br.com.gabrielanery.pokedex;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import android.widget.AdapterView.OnItemClickListener;
public class MainActivity extends AppCompatActivity
{
    //ArrayAdapter<String> adapter;
    public DataBase d;
    ListView listView1 ;
    TextView nome1;
    TextView tipo;
    TextView id;
    Button bot;
    Bitmap bitmap;
    private  static  final String TAG="socorro";
    public ArrayList<Pokemon> listaPokemon = new ArrayList<Pokemon>() {
        @Override
        public void add(int location, Pokemon object) {

        }

        @Override
        public boolean add(Pokemon object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends Pokemon> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Pokemon> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean equals(Object object) {
            return false;
        }

        @Override
        public Pokemon get(int location) {
            return null;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Pokemon> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @Override
        public ListIterator<Pokemon> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Pokemon> listIterator(int location) {
            return null;
        }

        @Override
        public Pokemon remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Pokemon set(int location, Pokemon object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<Pokemon> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView1=(ListView) findViewById(R.id.listView);
        d= new DataBase(this, null);
        listaPokemon=d.mostraPokemon();
        //Toast.makeText(MainActivity.this,listaPokemon.get(0).getId(),Toast.LENGTH_SHORT).show();
        listaAdapter adapterAgoraVai= new listaAdapter(this,listaPokemon,Typeface.createFromAsset(getAssets(),"metal.ttf"));
        for (Pokemon pok1:listaPokemon)
        {

            String path = Environment.getExternalStorageDirectory()+ "/Save Image Tutorial/myimage"+pok1.getId()+".png";
            File imgFile = new File(path);
            if(imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                pok1.setImagem(myBitmap);
            }
            else
                Toast.makeText(MainActivity.this,"no IMAGE IS PRESENT'",Toast.LENGTH_SHORT).show();

        }


        listView1.setAdapter(adapterAgoraVai);
        listView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id)
            {

                //Toast.makeText(MainActivity.this, String.valueOf(listaPokemon.get(position).getId()),Toast.LENGTH_SHORT).show();
                Intent it = new Intent(MainActivity.this, consultaPokemon.class);
                it.putExtra("id", listaPokemon.get(position).getId());
                startActivity(it);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listView1=(ListView) findViewById(R.id.listView);
        d= new DataBase(this, null);
        listaPokemon=d.mostraPokemon();
        for (Pokemon pok1:listaPokemon)
        {
            String path = Environment.getExternalStorageDirectory()+ "/Save Image Tutorial/myimage"+pok1.getId()+".png";
            File imgFile = new File(path);
            if(imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                pok1.setImagem(myBitmap);
            }
        }
        listaAdapter adapterAgoraVai= new listaAdapter(this,listaPokemon,Typeface.createFromAsset(getAssets(),"metal.ttf"));
        listView1.setAdapter(adapterAgoraVai);
    }
    public void clickAdicionarPok (View v)
    {
        Intent it= new Intent(MainActivity.this, adicionaPokemon.class);

        startActivity(it);
    }

}
