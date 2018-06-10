package br.edu.uni7.filmes.login.exception;

public class SenhaLoginException extends LoginException {

  public SenhaLoginException() {
    super("Senha deve ser preenchida.");
  }

}
