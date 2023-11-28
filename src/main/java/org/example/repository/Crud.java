package org.example.repository;

import java.util.List;

public interface Crud<Model> {
    List<Model> getList();
    Model getId(Integer id);
    int add(Model m);
    void update(Model m);
    void delete(Integer id);
}
