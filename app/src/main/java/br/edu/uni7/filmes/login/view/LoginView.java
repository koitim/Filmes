package br.edu.uni7.filmes.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import br.edu.uni7.filmes.BaseActivity;
import br.edu.uni7.filmes.R;
import br.edu.uni7.filmes.filmes.view.FilmesView;
import br.edu.uni7.filmes.login.ILoginMVP;
import br.edu.uni7.filmes.login.LoginPresenter;
import br.edu.uni7.filmes.login.exception.ConfirmacaoLoginException;
import br.edu.uni7.filmes.login.exception.EmailLoginException;
import br.edu.uni7.filmes.login.exception.SenhaLoginException;

public class LoginView extends BaseActivity implements OnLoginInteractionListener, ILoginMVP.ILoginView {

  private ILoginMVP.ILoginPresenter presenter;


// Redefinições da Activity
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_principal_activity);

    if( presenter == null ){
      presenter = new LoginPresenter();
    }
    presenter.setView(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    if (presenter.usuarioJaEstaLogado()) {
      exibirSistemaPrincipal();
    } else {
      exibirLogin();
    }
  }

  @Override
  public void onBackPressed() {
    if (presenter.operacaoDeLogin()) {
      super.onBackPressed();
    } else {
      exibirLogin();
    }
  }


// Redefinição de BaseActivity
  @Override
  protected void carregaFragmento(int tela) {
    switch (tela) {
      case ILoginMVP.ILoginPresenter.LOGIN:
        fragmento = LoginUsuario.newInstance();
        break;
      case ILoginMVP.ILoginPresenter.CADASTRO:
        fragmento = CadastroUsuario.newInstance();
        break;
    }
  }


// Implementações de OnLoginInteractionListener
  @Override
  public void cadastrarUsuario(String email, String senha, String confirmaSenha) throws EmailLoginException, SenhaLoginException, ConfirmacaoLoginException {
    presenter.cadastrar(email, senha, confirmaSenha);
  }

  @Override
  public void solicitarLogin(String email, String senha) throws EmailLoginException, SenhaLoginException {
    presenter.logar(email, senha);
  }

  @Override
  public void exibirCadastro() {
    presenter.setOperacaoAtual(ILoginMVP.ILoginPresenter.CADASTRO);
    exibeTela(R.id.activity_login, ILoginMVP.ILoginPresenter.CADASTRO);
  }


// Redefinições de ILoginView
  @Override
  public void exibirProgresso() {
    fragmento.showProgress(true);
  }

  @Override
  public void esconderProgresso() {
    fragmento.showProgress(false);
  }

  @Override
  public void exibirSistemaPrincipal() {
    Intent it = new Intent(this, FilmesView.class);
    startActivity(it);
    finish();
  }

  @Override
  public void finalizarCadastro() {
    Snackbar
        .make(fragmento.getView(), "Cadastro efetuado com sucesso!", Snackbar.LENGTH_SHORT)
        .show();
    exibirLogin();
  }

  @Override
  public void informarErro(String mensagem) {
    fragmento.showProgress(false);
    Snackbar
        .make(fragmento.getView(), mensagem, Snackbar.LENGTH_SHORT)
        .show();
  }


  private void exibirLogin() {
    presenter.setOperacaoAtual(ILoginMVP.ILoginPresenter.LOGIN);
    exibeTela(R.id.activity_login, ILoginMVP.ILoginPresenter.LOGIN);
  }
}
