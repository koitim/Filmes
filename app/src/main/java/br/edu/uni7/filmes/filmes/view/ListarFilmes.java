package br.edu.uni7.filmes.filmes.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.uni7.filmes.BaseFragment;
import br.edu.uni7.filmes.R;

public class ListarFilmes extends BaseFragment {

  private FilmesAdapter adapter;
  private OnFilmesInteractionListener listener;

  @NonNull
  public static ListarFilmes newInstance() {
    return new ListarFilmes();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = listener.getAdapter();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
    rvFilmes.setAdapter(adapter);

    mView        = rvFilmes;
    mProgressBar = view.findViewById(R.id.pb_lista_filmes_progress);

    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFilmesInteractionListener) {
      listener = (OnFilmesInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " deve implementar OnFilmesInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    listener = null;
  }


  private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    SpacesItemDecoration(int space) {
      this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
      outRect.left = space;
      outRect.right = space;
      outRect.bottom = space;

      if(parent.getChildAdapterPosition(view) == 0)
        outRect.top = space;

    }
  }

}
