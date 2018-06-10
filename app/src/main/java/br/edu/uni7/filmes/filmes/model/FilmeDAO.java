package br.edu.uni7.filmes.filmes.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.edu.uni7.filmes.filmes.model.Filme;

@Dao
public interface FilmeDAO {

  @Query("SELECT * FROM filme")
  List<Filme> getAll();

  @Query("SELECT * FROM filme WHERE id IN (:filmeIds)")
  List<Filme> loadAllByIds(int[] filmeIds);

  @Query("SELECT * FROM filme WHERE id = :idFilme")
  Filme findById(int idFilme);

  @Insert
  void insertAll(Filme... filmes);

  @Insert
  void insert(Filme filme);

  @Delete
  void delete(Filme filme);
}
