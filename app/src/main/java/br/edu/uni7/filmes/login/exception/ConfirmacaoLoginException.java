package br.edu.uni7.filmes.login.exception;

public class ConfirmacaoLoginException extends br.edu.uni7.filmes.login.exception.LoginException {
  public ConfirmacaoLoginException() {
    super("Confirmação de senha inválida.");
  }
}
