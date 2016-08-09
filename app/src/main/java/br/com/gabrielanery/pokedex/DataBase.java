package br.com.gabrielanery.pokedex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.lang.Integer.parseInt;

/**
 * Created by Gabi on 11/05/2016.
 */
public class DataBase extends SQLiteOpenHelper {
    private  static final int DATABASE_VERSION = 9;
    private  static final String DATABASE_NAME = "pokedex.db";
    public  static final String TABLE_POKEMON = "pokemons";
    public  static final String TABLE_ATAQUES = "ataques";
    public  static final String COLUMN_ID_POK = "id_pok";
    public  static final String COLUMN_NOME_POK="nome_pok";
    public  static final String COLUMN_TIPO_POK="tipo_pok";
    public  static final String COLUMN_LOCALIZACAO_POK="localizacao_pok";
    public  static final String COLUMN_ID_ATAK="id_atak";
    public  static final String COLUMN_NOME_ATAK="nome_atak";
    String query;

    public DataBase(Context context,  SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
         query="CREATE TABLE " + TABLE_POKEMON + "("+
                COLUMN_ID_POK + " integer primary key autoincrement, " +
                COLUMN_NOME_POK + " text, " +
                COLUMN_TIPO_POK + " text, " +
                COLUMN_LOCALIZACAO_POK + " text );";
        db.execSQL(query);
        query="CREATE TABLE " + TABLE_ATAQUES + "("+
                COLUMN_ID_ATAK + " integer primary key autoincrement, " +
                COLUMN_NOME_ATAK + " text, " +
                COLUMN_ID_POK + " integer, "+
                 " FOREIGN KEY("+COLUMN_ID_POK + ") REFERENCES " + TABLE_POKEMON + "(" + COLUMN_ID_POK + ") );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POKEMON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATAQUES);
        onCreate(db);
    }

    //------ADICIONA POKEMON E ATAQUE
    public long adicionaPokemon (Pokemon p, Ataque a1,Ataque a2,Ataque a3,Ataque a4)
    {
        ContentValues valores = new ContentValues();
        long resultado;
        SQLiteDatabase db = getWritableDatabase();
        valores.put(COLUMN_NOME_POK, p.getNome());
        valores.put(COLUMN_TIPO_POK, p.getTipo());
        valores.put(COLUMN_LOCALIZACAO_POK, p.getLocalizacao());

         resultado=db.insert(TABLE_POKEMON, null, valores);
        valores.clear();


        valores.put(COLUMN_NOME_ATAK, a1.getDescricao());
        valores.put(COLUMN_ID_POK, resultado);
        db.insert(TABLE_ATAQUES, null, valores);
        valores.clear();

        Log.d("SOCORRO", TABLE_ATAQUES);

        valores.put(COLUMN_NOME_ATAK, a2.getDescricao());
        valores.put(COLUMN_ID_POK, resultado);
        db.insert(TABLE_ATAQUES, null, valores);
        valores.clear();


        valores.put(COLUMN_NOME_ATAK, a3.getDescricao());
        valores.put(COLUMN_ID_POK, resultado);
        db.insert(TABLE_ATAQUES, null, valores);
        valores.clear();

        valores.put(COLUMN_NOME_ATAK, a4.getDescricao());
        valores.put(COLUMN_ID_POK, resultado);
        db.insert(TABLE_ATAQUES, null, valores);
        valores.clear();
        return resultado;

    }
    //------EDITA POKEMON E ATAQUE
    public void editaAtaque (int id, String nomeNovo)
    {
        ContentValues valores = new ContentValues();
        valores.put(COLUMN_NOME_ATAK, nomeNovo);
        SQLiteDatabase db = getReadableDatabase();
        db.update(TABLE_ATAQUES, valores, "id_atak="+id, null);
    }
    public void editaPokemon (int id, String nomeNovoPok,String localizacaoNova, String tipoNovo)
    {
        ContentValues valores = new ContentValues();
        valores.put(COLUMN_NOME_POK, nomeNovoPok);
        valores.put(COLUMN_TIPO_POK, tipoNovo);
        valores.put(COLUMN_LOCALIZACAO_POK, localizacaoNova);
        SQLiteDatabase db = getReadableDatabase();
        db.update(TABLE_POKEMON, valores, "id_Pok="+id, null);
    }
    //------MOSTRA TODOS POKEMONS
    public Pokemon mostraTodosPokemons (long id)
    {
        String query = "SELECT * FROM pokemons where "+COLUMN_ID_POK+" == "+id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        Pokemon p= new Pokemon();;
        if(c.moveToFirst()) {
            do {

                p.setNome(c.getString(c.getColumnIndex("nome_pok")));
                p.setId(parseInt(c.getString(c.getColumnIndex("id_pok"))));
                p.setTipo(c.getString(c.getColumnIndex("tipo_pok")));
                p.setLocalizacao(c.getString(c.getColumnIndex("localizacao_pok")));
            }while(c.moveToNext());
        }
        return p;
    }
    public ArrayList<Ataque> mostraAtaques (long id)
    {
        String query = "SELECT * FROM ataques where "+COLUMN_ID_POK+" == "+id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Ataque> listaAtaques = new ArrayList<>();
        Ataque a= new Ataque();;
        if(c.moveToFirst()) {
            do {
                a= new Ataque();
                a.setId(parseInt(c.getString(c.getColumnIndex("id_atak"))));
                a.setDescricao(c.getString(c.getColumnIndex("nome_atak")));
                listaAtaques.add(a);
            }while(c.moveToNext());
        }
        return listaAtaques;
    }

    public ArrayList<Pokemon> mostraPokemon ()
    {
        ArrayList<Pokemon> listaPok= new ArrayList<Pokemon>();
        String query = "SELECT * FROM " + TABLE_POKEMON;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        Pokemon p;
        if(c.moveToFirst()) {
            do {
                p= new Pokemon();
                p.setNome(c.getString(c.getColumnIndex("nome_pok")));
                p.setId(parseInt(c.getString(c.getColumnIndex("id_pok"))));
                p.setTipo(c.getString(c.getColumnIndex("tipo_pok")));
                p.setLocalizacao(c.getString(c.getColumnIndex("localizacao_pok")));
                Log.d("banco1", p.getNome());
                Log.d("banco1",String.valueOf(p.getId()));
                listaPok.add(p);
            }while(c.moveToNext());
        }

        return listaPok;
    }
    //------APAGA POKEMON
    public void excluiPokemon (String ID_POK)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_POKEMON,COLUMN_ID_POK +" = "+ID_POK,null);
        db.delete(TABLE_ATAQUES,COLUMN_ID_POK +" = "+ID_POK,null);
    }
}
