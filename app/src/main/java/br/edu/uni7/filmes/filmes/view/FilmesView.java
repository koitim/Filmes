package br.edu.uni7.filmes.filmes.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import br.edu.uni7.filmes.BaseActivity;
import br.edu.uni7.filmes.R;
import br.edu.uni7.filmes.filmes.FilmesPresenter;
import br.edu.uni7.filmes.filmes.IFilmesMVP;
import br.edu.uni7.filmes.filmes.model.Filme;
import br.edu.uni7.filmes.login.view.LoginView;

public class FilmesView extends BaseActivity
    implements OnFilmesInteractionListener, NavigationView.OnNavigationItemSelectedListener, IFilmesMVP.IFilmesView {

  private IFilmesMVP.IFilmesPresenter presenter;

  private NavigationView navigationView;

  private Filme mFilmeExibido;
  private int operacaoAnterior;
  private FilmesAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.filmes_principal_activity);

    if( presenter == null ){
      presenter = new FilmesPresenter();
    }
    presenter.setView(this);

    if (!presenter.usuarioJaEstaLogado()) {
      exibirLogin();
    }

    adapter = new FilmesAdapter(this);
    presenter.carregarFilmes();


    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    habilitaMenuFavoritos();

    TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
    tvEmail.setText(presenter.getEmailUsuario());

    listarFilmesPopulares();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      switch (presenter.getOperacaoAtual()) {
        case IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES:
          logout();
          break;
        case IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS:
          listarFilmesPopulares();
          break;
        case IFilmesMVP.IFilmesPresenter.EXIBE_FILME:
          switch (operacaoAnterior) {
            case IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES:
              listarFilmesPopulares();
              break;
            case IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS:
              listarFilmesFavoritos();
              break;
          }
          break;
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.filmes_principal_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    return id == R.id.action_settings || super.onOptionsItemSelected(item);
  }



  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.nav_lista_filmes_favoritos:
        if (presenter.getOperacaoAtual() != IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS)
          listarFilmesFavoritos();
        break;
      case R.id.nav_lista_filmes_populares:
        if (presenter.getOperacaoAtual() != IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES)
          listarFilmesPopulares();
        break;
      case R.id.nav_sair:
        logout();
        break;
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }



  @Override
  protected void carregaFragmento(int tela) {
    switch (tela) {
      case IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES:
        fragmento = ListarFilmes.newInstance();
        break;
      case IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS:
        fragmento = ListarFilmes.newInstance();
        break;
      case IFilmesMVP.IFilmesPresenter.EXIBE_FILME:
        fragmento = ExibirFilme.newInstance(mFilmeExibido);
        break;
    }
  }



  @Override
  public void listarFilmesPopulares() {
    int operacaoAtual = presenter.getOperacaoAtual();
    if (operacaoAtual == IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS ||
        (operacaoAtual == IFilmesMVP.IFilmesPresenter.EXIBE_FILME &&
            operacaoAnterior == IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS)) {
      adapter.replaceFilmes(presenter.getFilmesPopulares());
    }
    marcarMenu(IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES);
    operacaoAnterior = operacaoAtual;
    presenter.setOperacaoAtual(IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES);
    exibeTela(R.id.activity_filmes, IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES);
  }

  @Override
  public void listarFilmesFavoritos() {
    int operacaoAtual = presenter.getOperacaoAtual();
    if (operacaoAtual == IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES ||
        (operacaoAtual == IFilmesMVP.IFilmesPresenter.EXIBE_FILME &&
            operacaoAnterior == IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES)) {
      adapter.replaceFilmes(presenter.getFilmesFavoritos());
    }
    operacaoAnterior = operacaoAtual;
    presenter.setOperacaoAtual(IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS);
    exibeTela(R.id.activity_filmes, IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS);
  }

  @Override
  public void mudarFavoritismoFilme(Filme filme) {
    presenter.mudarFavoritismoFilme(filme);
  }

  @Override
  public FilmesAdapter getAdapter() {
    return adapter;
  }

  @Override
  public void exibirFilme(Filme filme) {
    mFilmeExibido = filme;
    operacaoAnterior = presenter.getOperacaoAtual();
    presenter.setOperacaoAtual(IFilmesMVP.IFilmesPresenter.EXIBE_FILME);
    exibeTela(R.id.activity_filmes, IFilmesMVP.IFilmesPresenter.EXIBE_FILME);
  }





  private void exibirLogin() {
    Intent it = new Intent(FilmesView.this, LoginView.class);
    startActivity(it);
    finish();
  }

  private void logout() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage("Tem certeza que deseja sair?")
        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            presenter.logout();
            exibirLogin();
            dialogInterface.dismiss();
          }
        })
        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            marcarMenu(presenter.getOperacaoAtual());
            dialogInterface.dismiss();
          }
        });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private void marcarMenu(int tela) {
    switch (tela) {
      case IFilmesMVP.IFilmesPresenter.LISTA_FILMES_POPULARES:
        navigationView.setCheckedItem(R.id.nav_lista_filmes_populares);
        break;
      case IFilmesMVP.IFilmesPresenter.LISTA_FILMES_FAVORITOS:
        navigationView.setCheckedItem(R.id.nav_lista_filmes_favoritos);
        break;
      default:
        break;
    }
  }

  @Override
  public void habilitaMenuFavoritos() {
    navigationView
        .getMenu()
        .findItem(R.id.nav_lista_filmes_favoritos)
        .setEnabled(presenter.temFilmeFavorito());
  }



  @Override
  public void exibirProgresso() {
    fragmento.showProgress(true);
  }

  @Override
  public void esconderProgresso() {
    fragmento.showProgress(false);
  }

  @Override
  public void addFilmes(List<Filme> filmes) {
    adapter.addFilmes(filmes);
  }

  @Override
  public Context getContexto() {
    return getApplicationContext();
  }
}
