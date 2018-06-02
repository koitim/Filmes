package br.edu.uni7.filmes.filmes;

import android.os.AsyncTask;

import java.util.List;

public class FilmesAsyncTask extends AsyncTask<String,Integer,List<Filme>>  {

  private TMDBView tmdbView;

  public FilmesAsyncTask(TMDBView tmdbView){
    this.tmdbView = tmdbView;
  }

  @Override
  protected List<Filme> doInBackground(String... strings) {
    BaseFetcher fetcher = new RetrofitFetcher();

    return fetcher.fetchFilmeList();
  }

  @Override
  protected void onPostExecute(List<Filme> result) {
    super.onPostExecute(result);
    tmdbView.showFilmeList(result);
  }
}
