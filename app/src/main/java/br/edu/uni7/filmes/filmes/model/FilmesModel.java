package br.edu.uni7.filmes.filmes.model;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.uni7.filmes.filmes.IFilmesMVP;
import br.edu.uni7.filmes.filmes.model.local.FilmeDatabase;
import br.edu.uni7.filmes.filmes.model.local.MainDatabase;
import br.edu.uni7.filmes.filmes.model.remoto.RetrofitFetcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmesModel implements IFilmesMVP.IFilmesModel, Callback<Filmes>, ValueEventListener {

  private final String TABELA_FAVORITOS = "favoritos";
  private IFilmesMVP.IFilmesPresenter presenter;


  public FilmesModel(IFilmesMVP.IFilmesPresenter presenter) {
    this.presenter = presenter;
  }



  private void recuperarFilmesFavoritosAparelho(final Context context) {
    Runnable consulta = new Runnable() {
      @Override
      public void run() {
        FilmeDatabase filmeDatabase = MainDatabase.getInstance(context);
        presenter.setListaFilmesFavoritos(filmeDatabase.filmeDAO().getAll());
      }
    };
    AsyncTask.execute(consulta);
  }

  private void sincronizarFilmesFavoritos() {
    FirebaseDatabase
        .getInstance()
        .getReference()
        .child(TABELA_FAVORITOS)
        .child(getUsuarioAtual().getUid())
        .addListenerForSingleValueEvent(this);
  }

  private void inserirFavoritoAparelho (final Filme filme) {
    Runnable inserir = new Runnable() {
      @Override
      public void run() {
        FilmeDatabase filmeDatabase = MainDatabase.getInstance(presenter.getContexto());
        filmeDatabase.filmeDAO().insert(filme);
      }
    };
    AsyncTask.execute(inserir);
  }

  private void inserirFavoritoFirebase (final Filme filme) {
    FirebaseDatabase
        .getInstance()
        .getReference()
        .child(TABELA_FAVORITOS)
        .child(getUsuarioAtual().getUid())
        .child(String.valueOf(filme.getId()))
        .setValue(filme).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        int a = 1;
      }
    }).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {
        int a = 1;
      }
    });
  }

  private void removerFavoritoAparelho(final Filme filme) {
    Runnable remover = new Runnable() {
      @Override
      public void run() {
        FilmeDatabase filmeDatabase = MainDatabase.getInstance(presenter.getContexto());
        filmeDatabase.filmeDAO().delete(filme);
      }
    };
    AsyncTask.execute(remover);;
  }

  private void removerFavoritoFirebase (final Filme filme) {
    FirebaseDatabase
        .getInstance()
        .getReference()
        .child(TABELA_FAVORITOS)
        .child(getUsuarioAtual().getUid())
        .child(String.valueOf(filme.getId()))
        .removeValue();
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
    new RetrofitFetcher().recuperarListaFilmesPopulares(this);
  }

  @Override
  public void recuperarListaDeFilmesFavoritos(Context contexto) {
    recuperarFilmesFavoritosAparelho(contexto);
    sincronizarFilmesFavoritos();
  }

  @Override
  public void addFilmeFavoritos(Filme filme) {
    inserirFavoritoFirebase(filme);
    inserirFavoritoAparelho(filme);
    presenter.addFavorito(filme);
  }

  @Override
  public void removeFilmeFavoritos(Filme filme) {
    removerFavoritoFirebase(filme);
    removerFavoritoAparelho(filme);
    presenter.removeFavorito(filme);
  }



  @Override
  public void onFailure(Call<Filmes> call, Throwable t) {
    presenter.bloqueiaFilmesPopulares("Não foi possível carregar a lista de filmes populares. Tente novamente mais tarde.");
  }

  @Override
  public void onResponse(Call<Filmes> call, Response<Filmes> response) {
    if (response != null) {
      presenter.addFilmesPopulares(response.body().getListFilmes());
    }
  }



  @Override
  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
    List<Filme> filmesFavoritosFirebase = new ArrayList<>();
    for( DataSnapshot child : dataSnapshot.getChildren()){
      Filme filme = child.getValue(Filme.class);
      filmesFavoritosFirebase.add(filme);
      if (!presenter.getFilmesFavoritos().contains(filme)) {
        inserirFavoritoAparelho(filme);
        presenter.addFavorito(filme);
      }
    }
    for (Filme filme : presenter.getFilmesFavoritos()) {
      if (!filmesFavoritosFirebase.contains(filme)) {
        removerFavoritoAparelho(filme);
        presenter.removeFavorito(filme);
      }
    }
  }

  @Override
  public void onCancelled(@NonNull DatabaseError databaseError) {

  }

}
