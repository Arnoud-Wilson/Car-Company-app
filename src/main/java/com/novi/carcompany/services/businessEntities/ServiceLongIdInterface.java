package com.novi.carcompany.services.businessEntities;

import java.util.List;

public interface ServiceLongIdInterface<G, T> {
    List<G> getAll();
    G getOne(Long id);
    G createNew(T unit);
    G deleteOne(Long id);
}
