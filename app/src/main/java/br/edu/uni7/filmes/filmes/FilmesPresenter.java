package br.edu.uni7.filmes.filmes;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.edu.uni7.filmes.filmes.model.Filme;
import br.edu.uni7.filmes.filmes.model.FilmesModel;

public class FilmesPresenter implements IFilmesMVP.IFilmesPresenter {

  private IFilmesMVP.IFilmesModel model;
  private IFilmesMVP.IFilmesView view;
  private List<Filme> filmesFavoritos;
  private List<Filme> filmesPopulares;

  public FilmesPresenter() {
    model = new FilmesModel(this);
  }


  @Override
  public void setView(IFilmesMVP.IFilmesView view) {
    this.view = view;
  }

  @Override
  public Context getContexto() {
    return view.getContexto();
  }

  @Override
  public void carregarFilmes() {
    filmesPopulares = new ArrayList<>();
    filmesFavoritos = new ArrayList<>();

    model.recuperarListaDeFilmesPopulares();
    model.recuperarListaDeFilmesFavoritos(view.getContexto());
  }

  @Override
  public boolean usuarioJaEstaLogado() {
    return model.getUsuarioAtual() != null;
  }

  @Override
  public String getEmailUsuario() {
    return model.getUsuarioAtual().getEmail();
  }

  @Override
  public void logout() {
    model.logout();
  }

  @Override
  public List<Filme> getFilmesPopulares() {
    return filmesPopulares;
  }

  @Override
  public List<Filme> getFilmesFavoritos() {
    return filmesFavoritos;
  }

  @Override
  public void bloqueiaFilmesPopulares(String mensagem) {
    view.bloqueiaFilmesPopulares(mensagem);
  }

  @Override
  public void mudarFavoritismoFilme(Filme filme) {
    if (filme.isFavorito())
      model.removeFilmeFavoritos(filme);
    else
      model.addFilmeFavoritos(filme);
  }

  @Override
  public void addFilmesPopulares(List<Filme> filmes) {
    filmesPopulares.addAll(filmes);
    view.addFilmes(filmes);
  }

  @Override
  public void setListaFilmesFavoritos(List<Filme> filmes) {
    filmesFavoritos = filmes;
  }

  @Override
  public void addFavorito(Filme filme) {
    filme.setFavorito(true);
    filmesFavoritos.add(filme);
    if (filmesFavoritos.size() == 1)
      view.habilitaMenuFavoritos(true);
  }

  @Override
  public void removeFavorito(Filme filme) {
    filme.setFavorito(false);
    filmesFavoritos.remove(filme);
    if (filmesFavoritos.size() == 0)
      view.habilitaMenuFavoritos(false);
  }
}
