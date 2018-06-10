package br.edu.uni7.filmes.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import br.edu.uni7.filmes.BaseFragment;
import br.edu.uni7.filmes.R;
import br.edu.uni7.filmes.login.exception.EmailLoginException;
import br.edu.uni7.filmes.login.exception.SenhaLoginException;

public class LoginUsuario extends BaseFragment implements TextView.OnEditorActionListener, View.OnClickListener {

  private OnLoginInteractionListener listener;

  private TextInputEditText mEmail;
  private TextInputEditText mSenha;

  @NonNull
  public static LoginUsuario newInstance() {
    return new LoginUsuario();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_fragment, container, false);

    view
        .findViewById(R.id.bt_login)
        .setOnClickListener(this);
    view
        .findViewById(R.id.tv_cadastro)
        .setOnClickListener(this);

    mEmail       = view.findViewById(R.id.login_email);
    mSenha       = view.findViewById(R.id.login_senha);
    mView        = view.findViewById(R.id.ll_login);
    mProgressBar = view.findViewById(R.id.pb_login);

    mSenha.setOnEditorActionListener(this);

    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnLoginInteractionListener) {
      listener = (OnLoginInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " deve implementar OnLoginInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    listener = null;
  }

  @Override
  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
      tentarLogar();
      return true;
    }
    return false;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_cadastro:
        listener.exibirCadastro();
        break;
      case R.id.bt_login:
        tentarLogar();
        break;
      default:
        break;
    }
  }



  private void tentarLogar() {
    String email = mEmail.getText().toString();
    String senha = mSenha.getText().toString();

    mEmail.setError(null);
    mSenha.setError(null);

    try {
      listener.solicitarLogin(email, senha);
    } catch (EmailLoginException ele) {
      mEmail.setError(ele.getMessage());
      mEmail.requestFocus();
    } catch (SenhaLoginException sle) {
      mSenha.setError(sle.getMessage());
      mSenha.requestFocus();
    }
  }

}
