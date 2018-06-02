package br.edu.uni7.filmes.filmes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.uni7.filmes.R;

class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.ViewHolder>{

  private List<Filme> filmes;

  private OnFilmesInteractionListener mListener;

  /*public FilmesAdapter(List<Filme> filmes, OnFilmesInteractionListener mListener) {
    this.filmes = filmes;
    this.mListener = mListener;
  }*/
  public FilmesAdapter(OnFilmesInteractionListener mListener) {
    //this.filmes = filmes;
    filmes = new ArrayList<>();
    this.mListener = mListener;
  }

  public void addFilme(Filme filme) {
    filmes.add(filme);
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.filmes_lista_item, viewGroup, false);
    return new ViewHolder(v, mListener, filmes);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {
    viewHolder.setTextView(filmes.get(position));
  }

  @Override
  public int getItemCount() {
    return filmes.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvTitulo;

    public ViewHolder(View v, final OnFilmesInteractionListener mListener, final List<Filme> disciplinas) {
      super(v);
      // Todo: Montar card
//      if (mListener.getTelaAtual() == Principal.LISTA_DISCIPLINAS) {
//        v.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//            mListener.exibirMatricular(disciplinas.get(getAdapterPosition()));
//          }
//        });
//      }
      tvTitulo = v.findViewById(R.id.tv_filme_item);
    }

    public void setTextView(Filme filme) {
      tvTitulo.setText(filme.toString());
      //Picasso
      //                .get()
      //                .load("http://www.giocattoli-on-line.com/wp-content/uploads/2016/03/avengers-giocattoli.jpg")
      //                .placeholder(R.mipmap.ic_launcher)
      //                .into(holder.imageView, object: Callback{
      //                    override fun onSuccess() {
      //                        print("success")
      //                    }
      //
      //                    override fun onError(e: Exception?) {
      //                        print("onErro")
      //
      //                    }
      //
      //                })
    }
  }
}
