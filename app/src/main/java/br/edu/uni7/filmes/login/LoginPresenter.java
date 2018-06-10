package br.edu.uni7.filmes.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.uni7.filmes.login.exception.ConfirmacaoLoginException;
import br.edu.uni7.filmes.login.exception.EmailLoginException;
import br.edu.uni7.filmes.login.exception.SenhaLoginException;

public class LoginPresenter implements ILoginMVP.ILoginPresenter {

  private ILoginMVP.ILoginModel model;
  private ILoginMVP.ILoginView view;
  private int operacaoAtual;

  public LoginPresenter() {
    model = new LoginModel(this);
  }



  @Override
  public void setView(ILoginMVP.ILoginView view) {
    this.view = view;
  }

  @Override
  public void logar(String email, String senha) throws EmailLoginException, SenhaLoginException {
    if (model.operacaoEmExecucao()) {
      return;
    }

    validarEmail(email);
    validarSenha(senha);

    view.exibirProgresso();

    model.executarOperacao(email, senha);
  }

  @Override
  public boolean usuarioJaEstaLogado() {
    return model.getUsuarioAtual() != null;
  }

  @Override
  public void cadastrar(String email, String senha, String confirmaSenha) throws EmailLoginException, SenhaLoginException, ConfirmacaoLoginException {
    if (model.operacaoEmExecucao()) {
      return;
    }

    validarEmail(email);
    validarSenha(senha);
    validarConfirmacao(senha, confirmaSenha);

    view.exibirProgresso();

    model.executarOperacao(email, senha);
  }

  @Override
  public void bloquearUso() {
    view.esconderProgresso();
  }

  @Override
  public int getOperacaoAtual() {
    return operacaoAtual;
  }

  @Override
  public void setOperacaoAtual(int operacao) {
    operacaoAtual = operacao;
  }

  @Override
  public boolean operacaoDeLogin() {
    return operacaoAtual == ILoginMVP.ILoginPresenter.LOGIN;
  }

  @Override
  public void finalizarOperacao() {
    switch (getOperacaoAtual()) {
      case ILoginMVP.ILoginPresenter.LOGIN:
        view.exibirSistemaPrincipal();
        break;
      case ILoginMVP.ILoginPresenter.CADASTRO:
        view.finalizarCadastro();
        break;
    }
  }

  @Override
  public void emitirErro(String mensagem) {
    view.informarErro(mensagem);
  }



  private void validarEmail(String email) throws EmailLoginException {
    Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
    Matcher matcher = pattern.matcher(email);
    if (!matcher.find()) {
      throw new EmailLoginException();
    }
  }

  private void validarSenha(String senha) throws SenhaLoginException {
    if (senha.trim().equals("")) {
      throw new SenhaLoginException();
    }
  }

  private void validarConfirmacao(String senha, String confirmacao) throws ConfirmacaoLoginException {
    if (!senha.equals(confirmacao)) {
      throw new ConfirmacaoLoginException();
    }
  }
}
