package br.edu.uni7.filmes.filmes.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface TMDBService {

  @GET("3/movie/popular")
  Call<Filmes> fetchFilmeList(@Query("api_key") String apiKey, @Query("language") String linguagem);

}
