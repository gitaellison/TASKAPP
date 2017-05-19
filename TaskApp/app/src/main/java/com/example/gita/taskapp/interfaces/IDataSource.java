package com.example.gita.taskapp.interfaces;

import java.util.List;

/**
 * Any object that supplies data to the view should implement this interface
 * @param <T>
 */
public interface IDataSource<T> {
    List<T> getAll();
    void add(T thing);
    void update(T thing);
    void delete(T thing);
    void close();
}
