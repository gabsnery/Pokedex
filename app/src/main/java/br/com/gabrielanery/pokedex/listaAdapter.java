package br.com.gabrielanery.pokedex;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabi on 15/05/2016.
 */
public class listaAdapter extends ArrayAdapter<Pokemon>
{
    public Typeface mycustomFont;
    Context context;
    ArrayList<Pokemon> listinha;
    public listaAdapter(Context context, ArrayList<Pokemon> listinha,Typeface mycustomFont2)
    {
        super(context, 0, listinha);
        this.context=context;
        this.listinha=listinha;
        mycustomFont=mycustomFont2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pokemon PokPosicao = this.listinha.get(position);
        convertView= LayoutInflater.from(this.context).inflate(R.layout.layoutlistview,null);

        TextView textoNome = (TextView) convertView.findViewById(R.id.textView14);
        textoNome.setText(PokPosicao.getNome());
        textoNome.setTypeface(mycustomFont);

        TextView textoTipo = (TextView) convertView.findViewById(R.id.textView15);
        textoTipo.setText(PokPosicao.getTipo());
        textoTipo.setTypeface(mycustomFont);

        TextView textoId = (TextView) convertView.findViewById(R.id.textView16);
        textoId.setText(String.valueOf(PokPosicao.getId()));
        textoId.setTypeface(mycustomFont);

        ImageView imagem = (ImageView) convertView.findViewById(R.id.imageView2);
        imagem.setImageBitmap(PokPosicao.getImagem());
        return  convertView;
    }



}
