package br.edu.uni7.filmes.filmes.model.local;

import android.arch.persistence.room.Room;
import android.content.Context;

public class MainDatabase {

  private static FilmeDatabase filmeDatabase;

  public static FilmeDatabase getInstance(Context context) {
    if (filmeDatabase == null) {
      filmeDatabase = Room
          .databaseBuilder(context, FilmeDatabase.class, "database-filmes")
          .build();
    }
    return filmeDatabase;
  }
}
