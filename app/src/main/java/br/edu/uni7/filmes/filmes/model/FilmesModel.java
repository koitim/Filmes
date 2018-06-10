package br.edu.uni7.filmes.filmes.model;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.uni7.filmes.filmes.IFilmesMVP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmesModel implements IFilmesMVP.IFilmesModel, Callback<Filmes>, ValueEventListener {

  private final String TABELA_FAVORITOS = "favoritos";
  private IFilmesMVP.IFilmesPresenter presenter;

  public FilmesModel(IFilmesMVP.IFilmesPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public FirebaseUser getUsuarioAtual() {
    return FirebaseAuth.getInstance().getCurrentUser();
  }

  @Override
  public void logout() {
    FirebaseAuth.getInstance().signOut();
  }

  @Override
  public void recuperarListaDeFilmesPopulares() {
    RetrofitFetcher retrofitFetcher = new RetrofitFetcher();
    retrofitFetcher.recuperarListaFilmes(this);
  }

  private void recuperarFilmesFavoritosAparelho(final Context context) {
    Runnable consulta = new Runnable() {
      @Override
      public void run() {
        AppDatabase appDatabase = MainDatabase.getInstance(context);
        presenter.setListaFilmesFavoritos(appDatabase.filmeDAO().getAll());
      }
    };
    AsyncTask.execute(consulta);
  }

  @Override
  public void onFailure(Call<Filmes> call, Throwable t) {

  }

  @Override
  public void onResponse(Call<Filmes> call, Response<Filmes> response) {
    if (response != null) {
      presenter.addFilmesPopulares(response.body().getListFilmes());
    }
  }


  private void sincronizarFilmesFavoritos() {
    FirebaseDatabase
        .getInstance()
        .getReference()
        .child(TABELA_FAVORITOS)
        .child(getUsuarioAtual().getUid())
        .addListenerForSingleValueEvent(this);
  }

  @Override
  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
    List<Filme> filmesFavoritosFirebase = new ArrayList<>();
    for( DataSnapshot child : dataSnapshot.getChildren()){
      Filme filme = child.getValue(Filme.class);
      filmesFavoritosFirebase.add(filme);
      if (!presenter.getFilmesFavoritos().contains(filme)) {
        inserirFavorito(filme);
      }
    }
    for (Filme filme : presenter.getFilmesFavoritos()) {
      if (!filmesFavoritosFirebase.contains(filme)) {
        removerFavorito(filme);
      }
    }
  }

  @Override
  public void onCancelled(@NonNull DatabaseError databaseError) {

  }

  private void removerFavorito(final Filme filme) {
    Runnable remover = new Runnable() {
      @Override
      public void run() {
        AppDatabase appDatabase = MainDatabase.getInstance(presenter.getContexto());
        appDatabase.filmeDAO().delete(filme);
      }
    };
    AsyncTask.execute(remover);
//    filmesFavoritos.remove(filme);
//    view.habilitaMenuFavoritos();
  }


  private void inserirFavorito(final Filme filme) {
    Runnable inserir = new Runnable() {
      @Override
      public void run() {
        AppDatabase appDatabase = MainDatabase.getInstance(presenter.getContexto());
        appDatabase.filmeDAO().insert(filme);
      }
    };
    AsyncTask.execute(inserir);
    presenter.addFavorito(filme);
  }

  void addFilmeFavoritos(Filme filme) {
//    firebaseDatabase
//        .getReference()
//        .child("favoritos")
//        .child(model.getUsuarioAtual().getUid())
//        .setValue(filme);
    inserirFavorito(filme);
  }

  void removerFilmeFavoritos(Filme filme) {
//    firebaseDatabase
//        .getReference()
//        .child("favoritos")
//        .child(model.getUsuarioAtual().getUid())
//        .removeValue();
    removerFavorito(filme);
  }

  public void recuperarListaDeFilmesFavoritos(Context contexto) {
    recuperarFilmesFavoritosAparelho(contexto);
    sincronizarFilmesFavoritos();
  }
}
