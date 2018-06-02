package br.edu.uni7.filmes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

  protected int telaAtual;

  protected BaseFragment fragmento = null;

  protected void exibeTela(int activity, int tela) {
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    if (fragmento != null) {
      ft.remove(fragmento);
    }
    carregaFragmento(tela);
    ft.add(activity, fragmento);
    ft.commit();
  }

  protected abstract void carregaFragmento(int tela);
}
