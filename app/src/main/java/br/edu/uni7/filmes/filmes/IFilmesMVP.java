package br.edu.uni7.filmes.filmes;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import br.edu.uni7.filmes.filmes.model.Filme;

public class IFilmesMVP {

  public interface IFilmesModel {

    FirebaseUser getUsuarioAtual();

    void logout();
    void recuperarListaDeFilmesPopulares();
    void recuperarListaDeFilmesFavoritos(Context contexto);
    void addFilmeFavoritos(Filme filme);
    void removeFilmeFavoritos(Filme filme);

  }

  public interface IFilmesPresenter {

    void setView(IFilmesView view);
    void carregarFilmes();
    void logout();
    void mudarFavoritismoFilme(Filme filme);
    void setListaFilmesFavoritos(List<Filme> filmes);
    void addFilmesPopulares(List<Filme> filmes);
    void addFavorito(Filme filme);
    void removeFavorito(Filme filme);
    void bloqueiaFilmesPopulares(String mensagem);


    boolean usuarioJaEstaLogado();

    List<Filme> getFilmesPopulares();
    List<Filme> getFilmesFavoritos();

    String getEmailUsuario();

    Context getContexto();

  }

  public interface IFilmesView {

    void addFilmes(List<Filme> filmes);
    void habilitaMenuFavoritos(boolean habilita);
    void bloqueiaFilmesPopulares(String mensagem);

    Context getContexto();

  }
}
