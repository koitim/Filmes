package br.edu.uni7.filmes.filmes.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import br.edu.uni7.filmes.filmes.model.AppDatabase;

public class MainDatabase {

  private static AppDatabase appDatabase;

  public static AppDatabase getInstance(Context context) {
    if (appDatabase == null) {
      appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database-filmes").build();
    }
    return appDatabase;
  }
}
