package com.sparklecow.soundscape.services.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService <Request, Response, Update> {

    Response create(Request request);

    Page<Response> findAll(Pageable pageable);

    Response findById(Long id);

    Response updateById(Update update, Long id);

    void deleteById(Long id);
}
