package com.sparklecow.soundscape.services.common;

import jakarta.mail.MessagingException;

import java.util.List;

public interface CrudService <Request, Response, Update> {

    Response create(Request request) throws MessagingException;

    List<Response> findAll();

    Response findById(Long id);

    Response updateById(Update update, Long id);

    void deleteById(Long id);
}
