package br.edu.uni7.filmes.filmes.model;

import retrofit2.Callback;

interface BaseFetcher {

  void recuperarListaFilmes (Callback<Filmes> callback);
  
}
