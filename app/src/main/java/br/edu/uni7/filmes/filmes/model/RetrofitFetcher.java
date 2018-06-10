package br.edu.uni7.filmes.filmes.model;


import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFetcher implements BaseFetcher {


  @Override
  public void recuperarListaFilmes(Callback<Filmes> callback) {
    String urlBase = "https://api.themoviedb.org/";
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    TMDBService tmdbService = retrofit.create(TMDBService.class);
    String apikey = "942ccf9bf53c7651369a6116da7ed318";
    String linguagem = "pt-BR";
    tmdbService
        .fetchFilmeList(apikey, linguagem)
        .enqueue(callback);
  }
}
