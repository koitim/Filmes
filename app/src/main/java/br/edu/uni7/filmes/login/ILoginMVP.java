package br.edu.uni7.filmes.login;

import com.google.firebase.auth.FirebaseUser;

import br.edu.uni7.filmes.login.exception.ConfirmacaoLoginException;
import br.edu.uni7.filmes.login.exception.EmailLoginException;
import br.edu.uni7.filmes.login.exception.SenhaLoginException;

public interface ILoginMVP {

  interface ILoginModel {
    boolean operacaoEmExecucao();

    void executarOperacao(String email, String senha);

    FirebaseUser getUsuarioAtual();
  }

  interface ILoginPresenter {

    int LOGIN    = 1;
    int CADASTRO = 2;

    void setView(ILoginView view);
    void bloquearUso();
    void setOperacaoAtual(int operacao);
    void finalizarOperacao();
    void emitirErro(String mensagem);

    boolean usuarioJaEstaLogado();
    boolean operacaoDeLogin();

    int getOperacaoAtual();

    void cadastrar(String email, String senha, String confirmaSenha)
        throws EmailLoginException, SenhaLoginException, ConfirmacaoLoginException;
    void logar(String email, String senha)
        throws EmailLoginException, SenhaLoginException;

  }

  interface ILoginView {
    void exibirProgresso();
    void esconderProgresso();
    void exibirSistemaPrincipal();
    void finalizarCadastro();
    void informarErro(String mensagem);
  }
}
