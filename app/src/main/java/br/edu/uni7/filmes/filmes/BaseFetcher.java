package br.edu.uni7.filmes.filmes;

import java.util.List;

import retrofit2.Callback;

public interface BaseFetcher {

  List<Filme> fetchFilmeList();

  void fetchFilmeListAsync(Callback<List<Filme>> callback);
  
}
