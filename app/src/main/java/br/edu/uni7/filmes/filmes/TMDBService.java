package br.edu.uni7.filmes.filmes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBService {

//  @GET("/discover/movie?sort_by=popularity.desc&api_key={apikey}")
//  Call<List<Filme>> fetchFilmeList(@Query("apikey") String apikey);

  @GET("/3/discover/movie?sort_by=popularity.desc&api_key=942ccf9bf53c7651369a6116da7ed318")
  Call<List<Filme>> fetchFilmeList();

}
