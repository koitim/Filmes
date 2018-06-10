package br.edu.uni7.filmes.filmes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.edu.uni7.filmes.filmes.model.Filme;

public class Filmes {

    @SerializedName("results")
    private List<Filme> listFilmes;

    public List<Filme> getListFilmes() {
        return listFilmes;
    }
}
