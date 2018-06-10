package br.edu.uni7.filmes.filmes.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.edu.uni7.filmes.BaseFragment;
import br.edu.uni7.filmes.R;
import br.edu.uni7.filmes.filmes.model.Filme;

public class ExibirFilme extends BaseFragment implements View.OnClickListener {

  private static final String PARAMETRO_FILME = "filme";

  private Filme filme;
  private OnFilmesInteractionListener listener;

  private Button btFavorito;

  public static ExibirFilme newInstance(Filme filme) {
    ExibirFilme exibirFilme = new ExibirFilme();
    Bundle bundle = new Bundle();
    bundle.putParcelable(PARAMETRO_FILME, filme);
    exibirFilme.setArguments(bundle);
    return exibirFilme;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      filme = getArguments().getParcelable(PARAMETRO_FILME);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.filmes_exibir_layout, container, false);

    ImageView ivPoster = view.findViewById(R.id.iv_filme);
    Picasso
        .get()
        .load("https://image.tmdb.org/t/p/w500" + filme.getImagem())
        .placeholder(R.mipmap.ic_launcher)
        .into(ivPoster);

    TextView tvTitulo = view.findViewById(R.id.tv_titulo);
    tvTitulo.setText(filme.getTitulo());

    TextView tvSinopse = view.findViewById(R.id.tv_sinopse);
    tvSinopse.setText(filme.getSinopse());

    btFavorito = view.findViewById(R.id.bt_favorito);
    btFavorito.setText((filme.isFavorito())?R.string.remover_favorito:R.string.adicionar_favorito);
    btFavorito.setOnClickListener(this);

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



  @Override
  public void onClick(View v) {
    btFavorito.setText((filme.isFavorito())?R.string.adicionar_favorito:R.string.remover_favorito);
    listener.mudarFavoritismoFilme(filme);
  }
}
