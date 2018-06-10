package br.edu.uni7.filmes.filmes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity
public class Filme implements Parcelable {

  @PrimaryKey
  @SerializedName("id")
  private int id;

  @ColumnInfo(name = "titulo")
  @SerializedName("title")
  private String titulo;

  @ColumnInfo(name = "sinopse")
  @SerializedName("overview")
  private String sinopse;

  @ColumnInfo(name = "imagem")
  @SerializedName("poster_path")
  private String imagem;

  private boolean favorito;

  public Filme() {
    favorito = false;
  }

  protected Filme(Parcel in) {
    id       = in.readInt();
    titulo   = in.readString();
    sinopse  = in.readString();
    imagem   = in.readString();
    favorito = in.readByte() != 0;
  }

  public static final Creator<Filme> CREATOR = new Creator<Filme>() {
    @Override
    public Filme createFromParcel(Parcel in) {
      return new Filme(in);
    }

    @Override
    public Filme[] newArray(int size) {
      throw new UnsupportedOperationException();
    }
  };

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public boolean isFavorito() {
    return favorito;
  }

  public void setFavorito(boolean favorito) {
    this.favorito = favorito;
  }

  @Override
  public String toString() {
    return titulo.toString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeString(titulo);
    dest.writeString(sinopse);
    dest.writeString(imagem);
    dest.writeByte((byte)(favorito ? 1 : 0));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!(obj instanceof Filme))
      return false;
    Filme filme = (Filme) obj;
    return getId() == filme.getId();
  }
}
