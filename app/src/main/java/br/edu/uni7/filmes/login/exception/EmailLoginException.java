package br.edu.uni7.filmes.login.exception;

public class EmailLoginException extends LoginException {

  public EmailLoginException() {
    super("E-mail inválido.");
  }

}
