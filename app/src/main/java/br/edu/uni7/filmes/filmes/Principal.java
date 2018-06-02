package br.edu.uni7.filmes.filmes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.edu.uni7.filmes.BaseActivity;
import br.edu.uni7.filmes.R;

public class Principal extends BaseActivity
    implements OnFilmesInteractionListener, NavigationView.OnNavigationItemSelectedListener,
    FilmeController.ControllerView, TMDBView {

  private static final int LISTA_FILMES = 1;
  private static final int FILMES_FAVORITOS = 2;

  private NavigationView navigationView;

  private FilmesAdapter mAdapter;
  private FilmeController mController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.filmes_principal_activity);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

//    Intent it = getIntent();
//    int idUsuario = it.getIntExtra(getString(R.string.usuario_param), 0);
//    UsuarioDAO usuarioDAO = new UsuarioDAO(this);
//    usuario = usuarioDAO.find(idUsuario);
//
//    tvOla = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_ola);
//    tvOla.setText(getString(R.string.ola) + usuario.getNome() + "!");

    mAdapter = new FilmesAdapter(this);
    mController = new FilmeController(this);
    mController.fetchFilmeList();

    listarFilmes();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      if (telaAtual == LISTA_FILMES) {
        logout();
      } else {
        super.onBackPressed();
        listarFilmes();
      }
    }
  }

  private void logout() {
    //TODO: Limpar variáveis por segurança???
    AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setCancelable(false)
        .setMessage("Tem certeza que deseja sair?")
        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Intent it = new Intent(Principal.this, br.edu.uni7.filmes.login.Principal.class);
            startActivity(it);
            finish();
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
    if (tela == LISTA_FILMES) {
      navigationView.setCheckedItem(R.id.nav_lista_filmes);
    } else {
      navigationView.setCheckedItem(R.id.nav_filmes_favoritos);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.filmes_principal_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    switch (id) {
      case R.id.nav_filmes_favoritos:
        break;
      case R.id.nav_lista_filmes:
        break;
      case R.id.nav_sair:
        break;
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public List<Filme> getFilmes() {
    List<Filme> list = new ArrayList<>();
    list.add(new Filme("A volta dos que não foram", "blablabla blablabla blablabla "));
    list.add(new Filme("As tranças do careca", "blablabla blablabla blablabla "));
    list.add(new Filme("Poeira em alto mar", "blablabla blablabla blablabla "));
    list.add(new Filme("Vida", "blablabla blablabla blablabla "));
    list.add(new Filme("Os Incríveis 2", "blablabla blablabla blablabla "));
    list.add(new Filme("Batman vs Superman", "blablabla blablabla blablabla "));
    list.add(new Filme("Liga da Justiça", "blablabla blablabla blablabla "));
    list.add(new Filme("Vingadores", "blablabla blablabla blablabla "));
    list.add(new Filme("Dança com lobos", "blablabla blablabla blablabla "));
    list.add(new Filme("O discurso do rei", "blablabla blablabla blablabla "));
    list.add(new Filme("Cisne negro", "blablabla blablabla blablabla "));
    list.add(new Filme("Amadeus", "blablabla blablabla blablabla "));
    list.add(new Filme("Contagem regressiva", "blablabla blablabla blablabla "));
    list.add(new Filme("Tron", "blablabla blablabla blablabla "));
    list.add(new Filme("Star wars", "blablabla blablabla blablabla "));
    list.add(new Filme("Homem aranha", "blablabla blablabla blablabla "));
    list.add(new Filme("Batman", "blablabla blablabla blablabla "));
    list.add(new Filme("Quarteto fantástico", "blablabla blablabla blablabla "));
    list.add(new Filme("X-Men", "blablabla blablabla blablabla "));
    list.add(new Filme("O senhor dos anéis", "blablabla blablabla blablabla "));
    list.add(new Filme("O poderoso chefinho", "blablabla blablabla blablabla "));
    list.add(new Filme("O poderoso chefão", "blablabla blablabla blablabla "));
    //Todo: Recuperar filmes
    return list;
  }

  @Override
  protected void carregaFragmento(int tela) {
    switch (tela) {
      case LISTA_FILMES:
        fragmento = ListaFilmes.newInstance();
        break;
      case FILMES_FAVORITOS:
        //fragmento = ListaFavoritos.newInstance();
        break;
    }
  }

  @Override
  public void listarFilmes() {
    telaAtual = LISTA_FILMES;
    exibeTela(R.id.activity_filmes, LISTA_FILMES);
  }

  @Override
  public void listarFavoritos() {
    telaAtual = FILMES_FAVORITOS;
    exibeTela(R.id.activity_filmes, FILMES_FAVORITOS);
  }

  @Override
  public void onSucess(Filme filme) {
    mAdapter.addFilme(filme);
  }

  @Override
  public void showFilmeList(List<Filme> filmeList) {
    if (filmeList != null) {
      for (Filme filme : filmeList) {
        mAdapter.addFilme(filme);
      }
    }

  }

  @Override
  public FilmesAdapter getAdapter() {
    return mAdapter;
  }
}
