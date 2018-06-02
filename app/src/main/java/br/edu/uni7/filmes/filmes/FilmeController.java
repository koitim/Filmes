package br.edu.uni7.filmes.filmes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmeController implements Callback<List<Filme>> {

  private ControllerView view;

  public FilmeController(ControllerView view) {
    this.view = view;
  }

  @Override
  public void onFailure(Call<List<Filme>> call, Throwable t) {

  }

  @Override
  public void onResponse(Call<List<Filme>> call, Response<List<Filme>> response) {
    if (response != null) {
      List<Filme> list = response.body();
      for (Filme filme:list) {
        view.onSucess(filme);
      }
    }
  }


  public void fetchFilmeList(){
    RetrofitFetcher retrofitFetcher = new RetrofitFetcher();
    retrofitFetcher.fetchFilmeListAsync(this);
  }


  interface ControllerView{
    void onSucess(Filme filme);
  }
}
