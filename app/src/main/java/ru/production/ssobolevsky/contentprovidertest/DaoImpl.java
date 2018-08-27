package ru.production.ssobolevsky.contentprovidertest;

/**
 * Created by pro on 07.07.2018.
 */

public class DaoImpl implements DAO {

    private final MyDatabase database;

    public DaoImpl(MyDatabase database) {
        this.database = database;
    }

    @Override
    public void addNote(MyNote note) {
        database.addNote(note);
    }

    @Override
    public void updateNote(MyNote note) {
        database.updateNote(note);
    }

    @Override
    public void getNoteById(int id) {
        database.getNoteById(id);
    }
}
