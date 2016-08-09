package br.com.gabrielanery.pokedex;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Gabi on 29/05/2016.
 */
public class ReplaceFont {
    public  static  void replaceDefaultFont(Context context, String nameOfFontBeingReplaced, String nameOfFontInAsset)
    {
       Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(),nameOfFontInAsset);
        //Log.d("lala", nameOfFontBeingReplaced + "\n" + nameOfFontInAsset);
       replaceFont(nameOfFontBeingReplaced, customFontTypeface);
    }

    private static void replaceFont(String nameOfFontBeingReplaced, Typeface customFontTypeface)
    {
        Field myfield = null;
        try {
            myfield = Typeface.class.getDeclaredField(nameOfFontBeingReplaced);
            myfield.setAccessible(true);
            myfield.set(null,customFontTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.d("lala",e.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.d("lala",e.toString());
        }


    }



}
