package br.edu.uni7.filmes.login;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginModel implements ILoginMVP.ILoginModel, OnCompleteListener<AuthResult> {

  private ILoginMVP.ILoginPresenter presenter;
  private LoginAsyncTask mAuthTask;

  LoginModel(ILoginMVP.ILoginPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public boolean operacaoEmExecucao() {
    return mAuthTask != null;
  }

  @Override
  public void executarOperacao(String email, String senha) {
    mAuthTask = new LoginAsyncTask(email, senha, this);
    mAuthTask.execute((Void) null);
  }

  @Override
  public FirebaseUser getUsuarioAtual() {
    return FirebaseAuth.getInstance().getCurrentUser();
  }

  @Override
  public void onComplete(@NonNull Task<AuthResult> task) {
    if (task.isSuccessful()) {
      presenter.finalizarOperacao();
    } else {
      presenter.emitirErro(Objects.requireNonNull(task.getException()).getMessage());
    }
  }

  class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String email;
    private final String senha;
    private OnCompleteListener<AuthResult> listener;

    LoginAsyncTask(String email, String senha, OnCompleteListener<AuthResult> listener) {
      this.email    = email;
      this.senha    = senha;
      this.listener = listener;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      mAuthTask = null;
    }

    @Override
    protected Void doInBackground(Void... params) {
    switch (presenter.getOperacaoAtual()) {
      case ILoginMVP.ILoginPresenter.LOGIN:
        validarLogin(email, senha, listener);
        break;
      case ILoginMVP.ILoginPresenter.CADASTRO:
        cadastrar(email, senha, listener);
        break;
    }
      return null;
    }

    @Override
    protected void onCancelled() {
      mAuthTask = null;
      presenter.bloquearUso();
    }

    private void validarLogin(String email, String senha, OnCompleteListener<AuthResult> listener) {
      FirebaseAuth
          .getInstance()
          .signInWithEmailAndPassword(email, senha)
          .addOnCompleteListener(listener);
    }

    private void cadastrar(String email, String senha, OnCompleteListener<AuthResult> listener) {
      FirebaseAuth
          .getInstance()
          .createUserWithEmailAndPassword(email, senha)
          .addOnCompleteListener(listener);
    }
  }

}
