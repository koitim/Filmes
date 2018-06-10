package br.edu.uni7.filmes.filmes.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

  private static final int LISTA_FILMES_POPULARES = 1;
  private static final int LISTA_FILMES_FAVORITOS = 2;
  private static final int EXIBE_FILME = 3;

  private IFilmesMVP.IFilmesPresenter presenter;
  private Filme mFilmeExibido;
  private int telaAtual;
  private int telaAnterior;
  private FilmesAdapter adapter;

  private NavigationView mNavigationView;



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

    mNavigationView = findViewById(R.id.nav_view);
    mNavigationView.setNavigationItemSelectedListener(this);

    TextView tvEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tv_email);
    tvEmail.setText(presenter.getEmailUsuario());

    listarFilmesPopulares();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      switch (telaAtual) {
        case LISTA_FILMES_POPULARES:
          logout();
          break;
        case LISTA_FILMES_FAVORITOS:
          listarFilmesPopulares();
          break;
        case EXIBE_FILME:
          switch (telaAnterior) {
            case LISTA_FILMES_POPULARES:
              listarFilmesPopulares();
              break;
            case LISTA_FILMES_FAVORITOS:
              listarFilmesFavoritos();
              break;
          }
          break;
      }
    }
  }


  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.nav_lista_filmes_favoritos:
        if (telaAtual != LISTA_FILMES_FAVORITOS)
          listarFilmesFavoritos();
        break;
      case R.id.nav_lista_filmes_populares:
        if (telaAtual != LISTA_FILMES_POPULARES)
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
      case LISTA_FILMES_POPULARES:
        fragmento = ListarFilmes.newInstance();
        break;
      case LISTA_FILMES_FAVORITOS:
        fragmento = ListarFilmes.newInstance();
        break;
      case EXIBE_FILME:
        fragmento = ExibirFilme.newInstance(mFilmeExibido);
        break;
    }
  }



  @Override
  public void listarFilmesPopulares() {
    if (telaAtual == LISTA_FILMES_FAVORITOS ||
        (telaAtual == EXIBE_FILME &&
            telaAnterior == LISTA_FILMES_FAVORITOS)) {
      adapter.replaceFilmes(presenter.getFilmesPopulares());
    }
    marcarMenu(LISTA_FILMES_POPULARES);
    telaAnterior = telaAtual;
    telaAtual = LISTA_FILMES_POPULARES;
    exibeTela(R.id.activity_filmes, LISTA_FILMES_POPULARES);
  }

  @Override
  public void listarFilmesFavoritos() {
    if (telaAtual == LISTA_FILMES_POPULARES ||
        telaAtual == EXIBE_FILME) {
      adapter.replaceFilmes(presenter.getFilmesFavoritos());
    }
    telaAnterior = telaAtual;
    telaAtual = LISTA_FILMES_FAVORITOS;
    exibeTela(R.id.activity_filmes, LISTA_FILMES_FAVORITOS);
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
    telaAnterior = telaAtual;
    telaAtual = EXIBE_FILME;
    exibeTela(R.id.activity_filmes, EXIBE_FILME);
  }



  @Override
  public void habilitaMenuFavoritos(boolean habilita) {
    mNavigationView
        .getMenu()
        .findItem(R.id.nav_lista_filmes_favoritos)
        .setEnabled(habilita);
  }

  @Override
  public void bloqueiaFilmesPopulares(String mensagem) {
    mNavigationView
        .getMenu()
        .findItem(R.id.nav_lista_filmes_populares)
        .setEnabled(false);
    Snackbar
        .make(fragmento.getView(), mensagem, Snackbar.LENGTH_SHORT)
        .show();
  }

  @Override
  public void addFilmes(List<Filme> filmes) {
    if (telaAtual == LISTA_FILMES_POPULARES) {
      adapter.addFilmes(filmes);
    }
  }

  @Override
  public Context getContexto() {
    return getApplicationContext();
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
            marcarMenu(telaAtual);
            dialogInterface.dismiss();
          }
        });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private void marcarMenu(int tela) {
    switch (tela) {
      case LISTA_FILMES_POPULARES:
        mNavigationView.setCheckedItem(R.id.nav_lista_filmes_populares);
        break;
      case LISTA_FILMES_FAVORITOS:
        mNavigationView.setCheckedItem(R.id.nav_lista_filmes_favoritos);
        break;
      default:
        break;
    }
  }
}
