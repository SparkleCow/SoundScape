package com.sparklecow.soundscape.services;

import java.util.List;

public interface CrudService <Request, Response, Update> {

    Response create(Request request);

    List<Response> findAll();

    Response findById(Long id);

    Response updateById(Update update, Long id);

    void deleteById(Long id);
}
