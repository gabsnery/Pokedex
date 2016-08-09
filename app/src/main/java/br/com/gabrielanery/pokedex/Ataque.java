package br.com.gabrielanery.pokedex;
/**
 * Created by Gabi on 11/05/2016.
 */
public class Ataque {
    private int id;
    private int id_pok;
    private String descricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pok() {
        return id_pok;
    }

    public void setId_pok(int id_pok) {
        this.id_pok = id_pok;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
