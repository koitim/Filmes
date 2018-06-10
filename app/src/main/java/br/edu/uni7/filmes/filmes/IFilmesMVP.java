package br.edu.uni7.filmes.filmes;


import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import br.edu.uni7.filmes.filmes.model.Filme;

public class IFilmesMVP {

  public interface IFilmesModel {
//    boolean operacaoEmExecucao();
//
//    void executarOperacao(String email, String senha);
//
    FirebaseUser getUsuarioAtual();
    void logout();
    void recuperarListaDeFilmesPopulares();
    void recuperarListaDeFilmesFavoritos(Context contexto);
  }

  public interface IFilmesPresenter {

    int LISTA_FILMES_POPULARES = 1;
    int LISTA_FILMES_FAVORITOS = 2;
    int EXIBE_FILME = 3;

    void setView(IFilmesView view);
    void setOperacaoAtual(int operacao);
    void carregarFilmes();
    void logout();
    void mudarFavoritismoFilme(Filme filme);
    void addFilmesPopulares(List<Filme> filmes);
    void setListaFilmesFavoritos(List<Filme> filmes);
    void addFavorito(Filme filme);

    int getOperacaoAtual();

    boolean temFilmeFavorito();
    boolean usuarioJaEstaLogado();

    List<Filme> getFilmesPopulares();
    List<Filme> getFilmesFavoritos();

    String getEmailUsuario();

    Context getContexto();
//    boolean operacaoDeLogin();
////    void bloquearUso();
////    void finalizarOperacao();
////    void emitirErro(String mensagem);


  }

  public interface IFilmesView {
    void exibirProgresso();
    void esconderProgresso();
    void addFilmes(List<Filme> filmes);
    Context getContexto();
    void habilitaMenuFavoritos();
//    void informarErro(String mensagem);
  }
}
