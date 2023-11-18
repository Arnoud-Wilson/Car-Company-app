package com.novi.carcompany.services.businessEntities;


import java.util.List;

public interface ServiceStringIdInterface<G, T> {
    List<G> getAll();
    G getOne(String id);
    G createNew(T unit);
    String deleteOne(String id);
}
