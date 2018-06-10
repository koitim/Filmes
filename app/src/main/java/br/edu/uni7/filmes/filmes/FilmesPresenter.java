package br.edu.uni7.filmes.filmes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.uni7.filmes.filmes.model.AppDatabase;
import br.edu.uni7.filmes.filmes.model.Filme;
import br.edu.uni7.filmes.filmes.model.Filmes;
import br.edu.uni7.filmes.filmes.model.FilmesModel;
import br.edu.uni7.filmes.filmes.model.MainDatabase;
import br.edu.uni7.filmes.filmes.model.RetrofitFetcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmesPresenter implements IFilmesMVP.IFilmesPresenter {

  private IFilmesMVP.IFilmesModel model;
  private IFilmesMVP.IFilmesView view;
  private List<Filme> filmesFavoritos;
  private List<Filme> filmesPopulares;
  private FirebaseDatabase firebaseDatabase;
  private int operacaoAtual;

  public FilmesPresenter() {
    model = new FilmesModel(this);
  }


  @Override
  public void setView(IFilmesMVP.IFilmesView view) {
    this.view = view;
  }

  @Override
  public int getOperacaoAtual() {
    return operacaoAtual;
  }

  @Override
  public void setOperacaoAtual(int operacao) {
    operacaoAtual = operacao;
  }

  @Override
  public void carregarFilmes() {

    filmesFavoritos = new ArrayList<>();
    filmesPopulares = new ArrayList<>();

    model.recuperarListaDeFilmesPopulares();

    model.recuperarListaDeFilmesFavoritos(view.getContexto());
  }

  @Override
  public boolean usuarioJaEstaLogado() {
    return model.getUsuarioAtual() != null;
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
  public String getEmailUsuario() {
    return model.getUsuarioAtual().getEmail();
  }

  @Override
  public Context getContexto() {
    return view.getContexto();
  }

  @Override
  public boolean temFilmeFavorito() {
    return filmesFavoritos.size() != 0;
  }

  @Override
  public void logout() {
    model.logout();
  }

  @Override
  public void mudarFavoritismoFilme(Filme filme) {
//    if (filme.isFavorito())
//      model.removerFilmeFavoritos(filme);
//    else
//      model.addFilmeFavoritos(filme);
  }

  @Override
  public void addFilmesPopulares(List<Filme> filmes) {
    filmesPopulares.addAll(filmes);
    if (getOperacaoAtual() == IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES) {
      view.addFilmes(filmes);
    }
  }

  @Override
  public void setListaFilmesFavoritos(List<Filme> filmes) {
    filmesFavoritos = filmes;
  }

  @Override
  public void addFavorito(Filme filme) {
    filmesFavoritos.add(filme);
    view.habilitaMenuFavoritos();
  }
}
