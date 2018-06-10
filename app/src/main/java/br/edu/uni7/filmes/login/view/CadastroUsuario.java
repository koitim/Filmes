package br.edu.uni7.filmes.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.uni7.filmes.BaseFragment;
import br.edu.uni7.filmes.R;
import br.edu.uni7.filmes.login.exception.ConfirmacaoLoginException;

public class CadastroUsuario extends BaseFragment implements View.OnClickListener {

  private OnLoginInteractionListener listener;

  private TextInputEditText mEmail;
  private TextInputEditText mSenha;
  private TextInputEditText mConfirmaSenha;

  @NonNull
  public static CadastroUsuario newInstance() {
    return new CadastroUsuario();
  }



  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_cadastro_usuario_fragment, container, false);

    view
        .findViewById(R.id.bt_cadastro)
        .setOnClickListener(this);

    mEmail         = view.findViewById(R.id.cadastro_email);
    mSenha         = view.findViewById(R.id.cadastro_senha);
    mConfirmaSenha = view.findViewById(R.id.cadastro_confirma_senha);
    mView          = view.findViewById(R.id.ll_cadastro);
    mProgressBar   = view.findViewById(R.id.pb_cadastro);

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
  public void onClick(View v) {
    String email            = mEmail.getText().toString();
    String senha            = mSenha.getText().toString();
    String confirmacaoSenha = mConfirmaSenha.getText().toString();

    mEmail.setError(null);
    mSenha.setError(null);
    mConfirmaSenha.setError(null);

    try {
      listener.cadastrarUsuario(email, senha, confirmacaoSenha);
    } catch (br.edu.uni7.filmes.login.exception.EmailLoginException ele) {
      mEmail.setError(ele.getMessage());
      mEmail.requestFocus();
    } catch (br.edu.uni7.filmes.login.exception.SenhaLoginException sle) {
      mSenha.setError(sle.getMessage());
      mSenha.requestFocus();
    } catch (ConfirmacaoLoginException cle) {
      mConfirmaSenha.setError(cle.getMessage());
      mConfirmaSenha.requestFocus();
    }
  }
}
