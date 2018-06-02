package br.edu.uni7.filmes.login;

public interface OnLoginInteractionListener {
//  void validarEmail(String email) throws LoginException;
//  void validarSenha(String senha) throws LoginException;
//  void validarLogin(String email, String senha);
//  boolean cadastrar(String email, String senha);

  Login getLogin();
  void login();
  void exibirCadastro();
  void exibirLogin();

  boolean tarefaEmAndamento();
  void iniciarTarefa(String email, String senha);
  void cancelarTarefa();
  void finalizarTarefa();
  void executarTarefa(String email, String senha);
}
