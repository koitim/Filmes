package br.edu.uni7.filmes.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.edu.uni7.filmes.BaseFragment;
import br.edu.uni7.filmes.R;

public class CadastroUsuario extends BaseFragment implements View.OnClickListener {

  private OnLoginInteractionListener mListener;

  // Referências para a IU
  private TextInputEditText tietEmail;
  private TextInputEditText tietSenha;
  private TextInputEditText tietConfirmaSenha;

  public static CadastroUsuario newInstance() {
    return new CadastroUsuario();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_cadastro_usuario_fragment, container, false);

    tietEmail = view.findViewById(R.id.tiet_login_cadastro_email);

    tietSenha = view.findViewById(R.id.tiet_login_cadastro_senha);

    tietConfirmaSenha = view.findViewById(R.id.tiet_login_cadastro_confirma_senha);

    Button btCadastro = view.findViewById(R.id.bt_cadastro);
    btCadastro.setOnClickListener(this);

    mView = view.findViewById(R.id.cadastro_form);
    mProgressBar = view.findViewById(R.id.pb_login_cadastro_progress);

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
  public void onClick(View v) {
    if (mListener.tarefaEmAndamento()) {
      return;
    }

    // Reset erros.
    tietEmail.setError(null);
    tietSenha.setError(null);
    tietConfirmaSenha.setError(null);

    // Armazenar valores no momento da tentativa de cadastro.
    String email = tietEmail.getText().toString();
    String senha = tietSenha.getText().toString();
    String senhaConfirmada = tietConfirmaSenha.getText().toString();

    boolean ehValido = true;
    View focusView = null;

    if (!senha.equals(senhaConfirmada)) {
      tietConfirmaSenha.setError("Confirmação de senha inválida!");
      focusView = tietConfirmaSenha;
      ehValido = false;
    }

    // Verifique se há uma senha válida, se o usuário inseriu uma.
    try {
      mListener.getLogin().validarSenha(senha);
    } catch (LoginException le) {
      tietSenha.setError(le.getMessage());
      focusView = tietSenha;
      ehValido = false;
    }

    // Verifique se há um endereço de e-mail válido.
    try {
      mListener.getLogin().validarEmail(email);
    } catch (LoginException le) {
      tietEmail.setError(le.getMessage());
      focusView = tietEmail;
      ehValido = false;
    }

    if (ehValido) {
      showProgress(true);
      mListener.iniciarTarefa(email, senha);
    } else {
      focusView.requestFocus();
    }

  }
}
