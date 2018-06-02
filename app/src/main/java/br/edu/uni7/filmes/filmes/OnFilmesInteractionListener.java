package br.edu.uni7.filmes.filmes;

import java.util.List;

public interface OnFilmesInteractionListener {
  void listarFilmes();
  void listarFavoritos();
  List<Filme> getFilmes();
  FilmesAdapter getAdapter();
}
