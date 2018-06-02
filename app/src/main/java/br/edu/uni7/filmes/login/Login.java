package br.edu.uni7.filmes.login;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {

  private FirebaseAuth mAuth;
  private OnSuccessListener<AuthResult> mListenerSucess;
  private OnFailureListener mListenerFailure;

  public Login(OnSuccessListener<AuthResult> listenerSucess, OnFailureListener listenerFailure) {
    mAuth = FirebaseAuth.getInstance();
    mListenerFailure = listenerFailure;
    mListenerSucess = listenerSucess;
  }

  public void validarEmail(String email) throws LoginException {
    Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
    Matcher matcher = pattern.matcher(email);
    if (!matcher.find()) {
      throw new LoginException("E-mail inválido.");
    }
  }

  public void validarSenha(String senha) throws LoginException {
    if (senha.trim().equals("")) {
      throw new LoginException("Senha deve ser preenchida");
    }
  }

  public void validarLogin(String email, String senha) {
    mAuth.signInWithEmailAndPassword(email, senha)
        .addOnSuccessListener(mListenerSucess)
        .addOnFailureListener(mListenerFailure);
  }

  public void cadastrar(String email, String senha) {
    mAuth.createUserWithEmailAndPassword(email, senha)
        .addOnSuccessListener(mListenerSucess)
        .addOnFailureListener(mListenerFailure);
  }

}
