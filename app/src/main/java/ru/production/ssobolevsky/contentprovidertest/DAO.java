package ru.production.ssobolevsky.contentprovidertest;

/**
 * Created by pro on 07.07.2018.
 */

public interface DAO {

    void addNote(MyNote note);
    void updateNote(MyNote note);
    void getNoteById(int id);
}
