package org.main.repository;

import java.util.List;

public interface Repository<T, k> {
    public boolean add(k params);
    public T get(k id);
    public List<T> getAll();
    public boolean remove(int id);
    public boolean remove(T p);
    public boolean update(T p);
}
