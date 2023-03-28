package com.example.architecture;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    public abstract NoteDAO noteDao();

    public static synchronized NoteDataBase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class, "note_database").
                    fallbackToDestructiveMigration().
                    addCallback(roomCallback).
                    build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void,Void>{
        private NoteDAO noteDAO;

        private PopulateDBAsyncTask(NoteDataBase db){
            noteDAO = db.noteDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("Title 1","Description 1",1));
            noteDAO.insert(new Note("Title 2","Description 2",2));
            noteDAO.insert(new Note("Title 3","Description 3",3));
            return null;
        }

    }


}
