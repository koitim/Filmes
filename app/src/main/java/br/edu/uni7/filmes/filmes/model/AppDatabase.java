package br.edu.uni7.filmes.filmes.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Filme.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
  public abstract FilmeDAO filmeDAO();
}
