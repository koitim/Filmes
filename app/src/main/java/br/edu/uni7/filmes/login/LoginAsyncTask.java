package br.edu.uni7.filmes.login;

import android.os.AsyncTask;

public class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

  private final String mEmail;
  private final String mSenha;
  private OnLoginInteractionListener mListener;

  LoginAsyncTask(String email, String senha, OnLoginInteractionListener listener) {
    mEmail = email;
    mSenha = senha;
    mListener = listener;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    mListener.finalizarTarefa();
  }

  @Override
  protected Void doInBackground(Void... params) {
    mListener.executarTarefa(mEmail, mSenha);
    return null;
  }

  @Override
  protected void onCancelled() {
    mListener.cancelarTarefa();
  }
}
