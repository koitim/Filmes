package br.edu.uni7.filmes.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.uni7.filmes.BaseActivity;
import br.edu.uni7.filmes.R;

public class Principal extends BaseActivity implements OnLoginInteractionListener, OnSuccessListener<AuthResult>, OnFailureListener {

  private static final int LOGIN = 1;
  private static final int CADASTRO = 2;

  private LoginAsyncTask mAuthTask;
  private FirebaseUser usuario = null;
  private Login login;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_principal_activity);
    login = new Login(this, this);
  }

  @Override
  public void onStart() {
    super.onStart();
    FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
    if (usuarioAtual != null) {
      exibirCadastro();
      // user.getEmail(), user.isEmailVerified()
      // user.getUid()
    } else {
      exibirLogin();
    }
  }

  @Override
  public void onBackPressed() {
    if (telaAtual == LOGIN) {
      super.onBackPressed();
    } else {
      exibirLogin();
    }
  }



  @Override
  protected void carregaFragmento(int tela) {
    switch (tela) {
      case LOGIN:
        fragmento = LoginUsuario.newInstance();
        break;
      case CADASTRO:
        fragmento = CadastroUsuario.newInstance();
        break;
    }
  }



  @Override
  public Login getLogin() {
    return login;
  }

  @Override
  public void login() {
    Intent it = new Intent(this, br.edu.uni7.filmes.filmes.Principal.class);
    it.putExtra(getString(R.string.e_mail), usuario.getEmail());
    startActivity(it);
    finish();
    fragmento.showProgress(false);
  }

  @Override
  public void exibirCadastro() {
    telaAtual = CADASTRO;
    exibeTela(R.id.activity_login, CADASTRO);
  }

  @Override
  public void exibirLogin() {
    telaAtual = LOGIN;
    exibeTela(R.id.activity_login, LOGIN);
  }



  @Override
  public void onSuccess(AuthResult authResult) {
    fragmento.showProgress(false);
    fragmento.showProgress(true);
    switch (telaAtual) {
      case LOGIN:
        usuario = authResult.getUser();
        login();
        break;
      case CADASTRO:
        Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso!", Toast.LENGTH_LONG).show();
        exibirLogin();
        break;
    }
  }

  @Override
  public void onFailure(@NonNull Exception e) {
    fragmento.showProgress(false);
    Snackbar.make(fragmento.getView(), e.getMessage(),
        Snackbar.LENGTH_SHORT).show();
  }



  @Override
  public boolean tarefaEmAndamento() {
    return mAuthTask != null;
  }

  @Override
  public void iniciarTarefa(String email, String senha) {
    mAuthTask = new LoginAsyncTask(email, senha, this);
    mAuthTask.execute((Void) null);
  }

  @Override
  public void cancelarTarefa() {
    mAuthTask = null;
    fragmento.showProgress(false);
  }

  @Override
  public void finalizarTarefa() {
    mAuthTask = null;
  }

  @Override
  public void executarTarefa(String email, String senha) {
    switch (telaAtual) {
      case LOGIN:
        login.validarLogin(email, senha);
        break;
      case CADASTRO:
        login.cadastrar(email, senha);
        break;
    }
  }

/*
  private void signOut() {
    mAuth.signOut();
    updateUI(null);
  }


    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

  */
}
