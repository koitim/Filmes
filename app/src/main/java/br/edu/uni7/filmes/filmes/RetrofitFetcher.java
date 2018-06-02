package br.edu.uni7.filmes.filmes;

import java.io.IOException;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFetcher implements BaseFetcher {

  private final String apikey = "942ccf9bf53c7651369a6116da7ed318";
  private final String urlBase = "https://api.themoviedb.org/";

  public List<Filme> fetchFilmeList() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    TMDBService tmdbService = retrofit.create(TMDBService.class);

    try {
      //Response<List<Filme>> response = tmdbService.fetchFilmeList(apikey).execute();
      Response<List<Filme>> response = tmdbService.fetchFilmeList().execute();
      return response.body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void fetchFilmeListAsync(Callback<List<Filme>> callback) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    TMDBService tmdbService = retrofit.create(TMDBService.class);

    //tmdbService.fetchFilmeList(apikey).enqueue(callback);
    tmdbService.fetchFilmeList().enqueue(callback);
  }

}
