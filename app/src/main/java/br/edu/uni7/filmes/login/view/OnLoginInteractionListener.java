package br.edu.uni7.filmes.login.view;

import br.edu.uni7.filmes.login.exception.ConfirmacaoLoginException;
import br.edu.uni7.filmes.login.exception.EmailLoginException;
import br.edu.uni7.filmes.login.exception.SenhaLoginException;

public interface OnLoginInteractionListener {
  void cadastrarUsuario(String email, String senha, String confirmaSenha) throws EmailLoginException, SenhaLoginException, ConfirmacaoLoginException;
  void exibirCadastro();
  void solicitarLogin(String email, String senha) throws EmailLoginException, SenhaLoginException;
}
