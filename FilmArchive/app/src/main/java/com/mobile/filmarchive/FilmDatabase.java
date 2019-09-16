package com.mobile.filmarchive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class FilmDatabase {
	
   private SQLiteDatabase database;
   private DatabaseOpenHelper databaseHelper;

   public FilmDatabase(Context context){
	   databaseHelper = new DatabaseOpenHelper(context, "Film", null, 1);
   } 

   public void open() throws SQLException{
	   database = databaseHelper.getWritableDatabase();
   }

   public void close(){
      if (database != null)
         database.close();
   }

   public void addFilm(String name, int runtime, int year, String genre, String director){
      ContentValues newFilm = new ContentValues();
      newFilm.put("name", name);
      newFilm.put("runtime", runtime);
      newFilm.put("year", year);
      newFilm.put("genre", genre);
      newFilm.put("director", director);
      open();
      database.insert("film", null, newFilm);
      close();
   }

   public void addGenre(String name){
	   ContentValues newGenre = new ContentValues();
	   newGenre.put("name", name);
	   open();
	   database.insert("genre", null, newGenre);
	   close();
   }

   public void addDirector(String name, String surname){
	   ContentValues newDirector = new ContentValues();
	   newDirector.put("name", name);
	   newDirector.put("surname", surname);
	   open();
	   database.insert("director", null, newDirector);
	   close();
   }

   public void updateFilm(long id, String name, int runtime, int year, String genre, String director){
      ContentValues newFilm = new ContentValues();
      newFilm.put("name", name);
      newFilm.put("runtime", runtime);
      newFilm.put("year", year);
      newFilm.put("genre", genre);
      newFilm.put("director", director);
      open();
      database.update("film", newFilm, "_id=" + id, null);
      close();
   }
   
   public void deleteFilm(long id){
	      open();
	      database.delete("film", "_id=" + id, null);
	      close();
   }

   public Cursor getAllFilms(){
      return database.query("film", new String[] {"_id", "name", "year"}, null, null, null, null, "name");
   }

   public Cursor getAllGenre(){
	   return database.query("genre", new String[] {"_id", "name"}, null, null, null, null, "name");
   }

   public Cursor getAllDirectors(){
	   return database.query("director", new String[] {"_id", "name", "surname"}, null, null, null, null, "name");
   }
   
   public Cursor getFilm(long id){
      return database.query("film", null, "_id=" + id, null, null, null, null);
   }

   public Cursor getGenre(long id){
	  return database.query("genre", null, "_id=" + id, null, null, null, null);
   }

   public Cursor getDirector(long id){
	  return database.query("director", null, "_id=" + id, null, null, null, null);
   }
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper{

	  public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version){
		  super(context, name, factory, version);
      }

      public void onCreate(SQLiteDatabase db){
          String sqlFilm = "CREATE TABLE film" +
                  "(_id integer primary key autoincrement," +
                  "name TEXT, runtime integer, year integer, genre TEXT," +
                  "director TEXT);";
          db.execSQL(sqlFilm);
          String sqlGenre = "CREATE TABLE genre" +
                  "(_id integer primary key autoincrement," +
                  "name TEXT);";
          db.execSQL(sqlGenre);
          String sqlDirector = "CREATE TABLE director" +
                  "(_id integer primary key autoincrement," +
                  "name TEXT, surname TEXT);";
          db.execSQL(sqlDirector);
      }

      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
      }
   }
}
