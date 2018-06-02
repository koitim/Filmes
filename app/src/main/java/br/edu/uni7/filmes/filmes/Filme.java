package br.edu.uni7.filmes.filmes;

import com.google.gson.annotations.SerializedName;

public class Filme {

  @SerializedName("title")
  private String titulo;
  @SerializedName("overview")
  private String sinopse;
  @SerializedName("poster_path")
  private String imagem;

  public Filme(String titulo, String sinopse) {
    this.titulo = titulo;
    this.sinopse = sinopse;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getSinopse() {
    return sinopse;
  }

  public void setSinopse(String sinopse) {
    this.sinopse = sinopse;
  }

  public String getImagem() {
    return imagem;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  @Override
  public String toString() {
    return titulo.toString();
  }
}
