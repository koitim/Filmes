package br.edu.uni7.filmes.filmes.model.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.edu.uni7.filmes.filmes.model.Filme;
import br.edu.uni7.filmes.filmes.model.FilmeDAO;

@Database(entities = {Filme.class}, version = 1)
public abstract class FilmeDatabase extends RoomDatabase {
  public abstract FilmeDAO filmeDAO();
}
