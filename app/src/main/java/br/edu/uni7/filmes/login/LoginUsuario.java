package br.edu.uni7.filmes.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import br.edu.uni7.filmes.BaseFragment;
import br.edu.uni7.filmes.R;

public class LoginUsuario extends BaseFragment implements TextView.OnEditorActionListener, View.OnClickListener {
  private OnLoginInteractionListener mListener;

  // Referências para a IU.
  private TextInputEditText tietEmail;
  private TextInputEditText tietSenha;

  public static LoginUsuario newInstance() {
    return new LoginUsuario();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_fragment, container, false);

    tietEmail = view.findViewById(R.id.tiet_login_email);

    tietSenha = view.findViewById(R.id.tiet_login_senha);
    tietSenha.setOnEditorActionListener(this);

    Button btLogin = view.findViewById(R.id.bt_login);
    btLogin.setOnClickListener(this);

    TextView tvCadastro = view.findViewById(R.id.tv_login_cadastro);
    tvCadastro.setOnClickListener(this);

    mView = view.findViewById(R.id.email_login_form);
    mProgressBar = view.findViewById(R.id.pb_login_progress);

    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnLoginInteractionListener) {
      mListener = (OnLoginInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " deve implementar OnLoginInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
      tentarLogar();
      return true;
    }
    return false;
  }

  private void tentarLogar() {
    if (mListener.tarefaEmAndamento()) {
      return;
    }

    // Reset erros.
    tietEmail.setError(null);
    tietSenha.setError(null);

    // Armazenar valores no momento da tentativa de login.
    String email = tietEmail.getText().toString();
    String senha = tietSenha.getText().toString();

    boolean cancel = false;
    View focusView = null;

    // Verifique se há uma senha válida, se o usuário inseriu uma.
    try {
      mListener.getLogin().validarSenha(senha);
    } catch (LoginException le) {
      tietSenha.setError(le.getMessage());
      focusView = tietSenha;
      cancel = true;
    }

    // Verifique se há um endereço de e-mail válido.
    try {
      mListener.getLogin().validarEmail(email);
    } catch (LoginException le) {
      tietEmail.setError(le.getMessage());
      focusView = tietEmail;
      cancel = true;
    }

    if (cancel) {
      focusView.requestFocus();
    } else {
      showProgress(true);
      mListener.iniciarTarefa(email, senha);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_login_cadastro:
        mListener.exibirCadastro();
        break;
      case R.id.bt_login:
        tentarLogar();
        break;
      default:
        break;
    }
  }

}
