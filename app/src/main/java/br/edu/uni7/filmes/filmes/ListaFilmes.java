package br.edu.uni7.filmes.filmes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.uni7.filmes.BaseFragment;
import br.edu.uni7.filmes.R;
import br.edu.uni7.filmes.util.SpacesItemDecoration;

public class ListaFilmes extends BaseFragment {

  private FilmesAdapter mAdapter;
  private OnFilmesInteractionListener mListener;

  public static ListaFilmes newInstance() {
    return new ListaFilmes();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = mListener.getAdapter();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.filmes_lista_fragment, container, false);

    RecyclerView rvFilmes = view.findViewById(R.id.rv_filmes_listar);
    rvFilmes.addItemDecoration(new SpacesItemDecoration(6));
    rvFilmes.setHasFixedSize(true);
    int scrollPosition = 0;
    if (rvFilmes.getLayoutManager() != null) {
      scrollPosition = ((LinearLayoutManager) rvFilmes.getLayoutManager())
          .findFirstCompletelyVisibleItemPosition();
    }
    rvFilmes.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvFilmes.scrollToPosition(scrollPosition);
    rvFilmes.setItemAnimator(new DefaultItemAnimator());
    rvFilmes.setAdapter(mAdapter);

    mProgressBar = view.findViewById(R.id.pb_lista_filmes_progress);

    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFilmesInteractionListener) {
      mListener = (OnFilmesInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " deve implementar OnFilmesInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

}
