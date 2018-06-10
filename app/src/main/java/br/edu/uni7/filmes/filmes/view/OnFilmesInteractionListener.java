package br.edu.uni7.filmes.filmes.view;

import java.util.List;

import br.edu.uni7.filmes.filmes.model.Filme;

public interface OnFilmesInteractionListener {
  void listarFilmesPopulares();
  void listarFilmesFavoritos();
  void exibirFilme(Filme filme);
  void mudarFavoritismoFilme(Filme filme);
  FilmesAdapter getAdapter();
}
