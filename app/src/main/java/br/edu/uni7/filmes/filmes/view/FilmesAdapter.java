package br.edu.uni7.filmes.filmes.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.edu.uni7.filmes.R;
import br.edu.uni7.filmes.filmes.model.Filme;

class FilmesAdapter
    extends RecyclerView.Adapter<FilmesAdapter.ViewHolder>
    implements ItemClickListener {

  private List<Filme> filmes;
  private OnFilmesInteractionListener filmesInteractionListener;

  FilmesAdapter(OnFilmesInteractionListener listener) {
    filmes = new ArrayList<>();
    filmesInteractionListener = listener;
  }

  void replaceFilmes(List<Filme> filmesNovos) {
    filmes.clear();
    filmes.addAll(filmesNovos);
    notifyDataSetChanged();
  }

  void addFilmes(List<Filme> filmesNovos) {
    filmes.addAll(filmesNovos);
    notifyDataSetChanged();
  }

  void addFilme(Filme filme) {
    filmes.add(filme);
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public FilmesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.filmes_lista_item, viewGroup, false);
    return new FilmesAdapter.ViewHolder(v, this);
  }

  @Override
  public void onBindViewHolder(@NonNull FilmesAdapter.ViewHolder viewHolder, int position) {
    Filme filme = filmes.get(position);
    viewHolder.setFilmeView(filme);
  }

  @Override
  public int getItemCount() {
    return filmes.size();
  }

  @Override
  public void onItemClick(int position) {
    filmesInteractionListener.exibirFilme(filmes.get(position));
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView tvTitulo;
    private final ImageView ivPoster;
    private ItemClickListener itemClickListener;

    ViewHolder(View view, ItemClickListener itemClickListener) {
      super(view);
      view
          .findViewById(R.id.cv_filmes_item)
          .setOnClickListener(this);
      tvTitulo = view.findViewById(R.id.tv_filme_item);
      ivPoster = view.findViewById(R.id.iv_filme_item);
      this.itemClickListener = itemClickListener;
    }

    void setFilmeView(Filme filme) {
      tvTitulo.setText(filme.toString());
      Picasso
          .get()
          .load("https://image.tmdb.org/t/p/w154" + filme.getImagem())
          .placeholder(R.mipmap.ic_launcher)
          .into(ivPoster);
    }

    @Override
    public void onClick(View v) {
      if(itemClickListener != null) {
        itemClickListener.onItemClick(getAdapterPosition());
      }
    }
  }
}

interface ItemClickListener {
  void onItemClick(int position);
}